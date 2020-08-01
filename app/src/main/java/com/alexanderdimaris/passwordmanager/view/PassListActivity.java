package com.alexanderdimaris.passwordmanager.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexanderdimaris.passwordmanager.R;
import com.alexanderdimaris.passwordmanager.model.PassObj;
import com.alexanderdimaris.passwordmanager.viewmodel.PassObjViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PassListActivity extends AppCompatActivity implements PassObjListAdapter.OnPasswordListener {

    public static final int NEW_PASS_ACTIVITY_REQUEST_CODE = 1;
    private PassObjViewModel passObjViewModel;
    private ArrayList<PassObj> passObjArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_list);

        RecyclerView recyclerView = findViewById(R.id.activity_pass_list_rv);
        final PassObjListAdapter adapter = new PassObjListAdapter(this, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        passObjViewModel = new ViewModelProvider(this).get(PassObjViewModel.class);
        passObjViewModel.getAllPasswords().observe(this, passwords -> {
            passObjArrayList.clear();
            passObjArrayList.addAll(passwords);
            adapter.setWords(passwords);
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

        if (resultCode != 0) {
            assert data != null;
            Bundle bundle = data.getExtras();
            PassObj passObj = (PassObj) bundle.getSerializable("passObj");

            if(resultCode == 1) {
                passObjViewModel.insert(passObj);
            } else if (resultCode == 2) {
                passObjViewModel.update(passObj);
            } else if(resultCode == 3) {
                passObjViewModel.delete(passObj);
            }
        }
    }

    @Override
    public void onPasswordClick(int position) {
        PassObj selected = passObjArrayList.get(position);
        Intent intent = new Intent(getApplicationContext(), ExistingPassActivity.class);
        intent.putExtra("passObj", selected);
        startActivityForResult(intent, 2);
    }
}