package com.alexanderdimaris.passwordmanager.view;

import android.content.Intent;
import android.os.Bundle;

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

    private PassObjViewModel passObjViewModel;
    private ArrayList<PassObj> passObjArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PassObjListAdapter adapter;

    static final int ADD_PASSWORD = 1;
    static final int UPDATE_PASSWORD = 2;
    static final int DELETE_PASSWORD = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_list);

        passObjViewModel = new ViewModelProvider(this).get(PassObjViewModel.class);
        adapter = new PassObjListAdapter(this, this);

        initializeViews();
        recyclerView.setAdapter(adapter);
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.activity_pass_list_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        passObjViewModel.getAllPasswords().observe(this, passwords -> {
            passObjArrayList.clear();
            passObjArrayList.addAll(passwords);
            adapter.setPasswords(passwords);
        });

        FloatingActionButton fab = findViewById(R.id.activity_pass_list_fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(PassListActivity.this, AddPassActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != 0) {
            assert data != null;
            Bundle bundle = data.getExtras();
            assert bundle != null;
            PassObj passObj = (PassObj) bundle.getSerializable("passObj");

            if(resultCode == ADD_PASSWORD) {
                passObjViewModel.insert(passObj);
            } else if (resultCode == UPDATE_PASSWORD) {
                passObjViewModel.update(passObj);
            } else if(resultCode == DELETE_PASSWORD) {
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

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}