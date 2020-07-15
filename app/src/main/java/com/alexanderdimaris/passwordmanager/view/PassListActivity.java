package com.alexanderdimaris.passwordmanager.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alexanderdimaris.passwordmanager.R;
import com.alexanderdimaris.passwordmanager.model.DataBaseHelper;
import com.alexanderdimaris.passwordmanager.model.PassObj;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class PassListActivity extends AppCompatActivity {

    private FloatingActionButton mFAB;
    private CoordinatorLayout mSnackBarLayout;
    private DataBaseHelper mDataBaseHelper;
    private ListView mListView;
    private PasswordAdapter mPasswordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_list);

        mFAB = findViewById(R.id.activity_pass_list_fab);
        mSnackBarLayout = findViewById(R.id.activity_pass_list_snackbar_layout);
        mListView = findViewById(R.id.activity_pass_list_lv);
        mDataBaseHelper = new DataBaseHelper(PassListActivity.this);
        updateList();

        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddPassActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PassObj selected = (PassObj) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ExistingPassActivity.class);
                intent.putExtra("passObj", selected);
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != 0) {
            PassObj passObj = (PassObj) data.getExtras().getSerializable("passObj");
            boolean success = false;
            String operation = "";

            if(resultCode == 1) {
                success = mDataBaseHelper.addOne(passObj);
                operation = "add";
            } else if (resultCode == 2) {
                success = mDataBaseHelper.updateOne(passObj);
                operation = "update";
            } else if(resultCode == 3) {
                success = mDataBaseHelper.deleteOne(passObj);
                operation = "delete";
            }
            updateList();
            buildSnackBarMessages(success, operation, passObj.getTitle());
        }
    }

    public void buildSnackBarMessages(boolean success, String op, String title) {
        String result = (success == true) ? "COMPLETED" : "FAILED";
        Snackbar.make(mSnackBarLayout, op + " operation for " + title + " " + result, Snackbar.LENGTH_LONG)
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                .show();
    }

    private void updateList() {
        mPasswordAdapter = new PasswordAdapter(this, mDataBaseHelper.getAll());
        mListView.setAdapter(mPasswordAdapter);
    }
}