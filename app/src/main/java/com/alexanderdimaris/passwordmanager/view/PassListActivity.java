package com.alexanderdimaris.passwordmanager.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexanderdimaris.passwordmanager.R;
import com.alexanderdimaris.passwordmanager.model.PassObj;
import com.alexanderdimaris.passwordmanager.viewmodel.PassObjViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PassListActivity extends AppCompatActivity {

    public static final int NEW_PASS_ACTIVITY_REQUEST_CODE = 1;
    private PassObjViewModel passObjViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_list);


        RecyclerView recyclerView = findViewById(R.id.activity_pass_list_rv);
        final PassObjListAdapter adapter = new PassObjListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        passObjViewModel = new ViewModelProvider(this).get(PassObjViewModel.class);

        passObjViewModel.getAllPasswords().observe(this, new Observer<List<PassObj>>() {
            @Override
            public void onChanged(@Nullable final List<PassObj> passwords) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(passwords);
            }
        });

        FloatingActionButton fab = findViewById(R.id.activity_pass_list_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PassListActivity.this, AddPassActivity.class);
                startActivityForResult(intent, NEW_PASS_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_PASS_ACTIVITY_REQUEST_CODE && resultCode != 0) {
            Bundle bundle = data.getExtras();
            PassObj passObj = (PassObj) bundle.getSerializable("passObj");
            passObjViewModel.insert(passObj);
        } else {
            Toast.makeText(getApplicationContext(), "NOT SAVED", Toast.LENGTH_LONG).show();
        }
    }
}