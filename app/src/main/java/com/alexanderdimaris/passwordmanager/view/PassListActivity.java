package com.alexanderdimaris.passwordmanager.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alexanderdimaris.passwordmanager.R;
import com.alexanderdimaris.passwordmanager.model.DataBaseHelper;
import com.alexanderdimaris.passwordmanager.model.PassObj;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class PassListActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private CoordinatorLayout snackBarLayout;
    private DataBaseHelper dataBaseHelper;
    private ListView listView;
    private PasswordAdapter passwordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_list);

        snackBarLayout = findViewById(R.id.activity_pass_list_snackbar_layout);
        dataBaseHelper = new DataBaseHelper(PassListActivity.this);

        fab = findViewById(R.id.activity_pass_list_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddPassActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        listView = findViewById(R.id.activity_pass_list_lv);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PassObj selected = (PassObj) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ExistingPassActivity.class);
                intent.putExtra("passObj", selected);
                startActivityForResult(intent, 2);
            }
        });
        updateList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != 0) {
            PassObj passObj = (PassObj) data.getExtras().getSerializable("passObj");
            boolean success = false;
            String operation = "";

            if(resultCode == 1) {
                success = dataBaseHelper.addOne(passObj);
                operation = "add";
            } else if (resultCode == 2) {
                success = dataBaseHelper.updateOne(passObj);
                operation = "update";
            } else if(resultCode == 3) {
                success = dataBaseHelper.deleteOne(passObj);
                operation = "delete";
            }
            updateList();

            if(!success) {
                Snackbar.make(snackBarLayout, operation + " operation for " + passObj.getTitle() + " failed", Snackbar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                        .show();
            }
        }
    }

    private void updateList() {
        passwordAdapter = new PasswordAdapter(this, dataBaseHelper.getAll());
        listView.setAdapter(passwordAdapter);
    }
}