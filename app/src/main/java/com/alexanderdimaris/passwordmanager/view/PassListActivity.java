package com.alexanderdimaris.passwordmanager.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alexanderdimaris.passwordmanager.R;
import com.alexanderdimaris.passwordmanager.model.DataBaseHelper;
import com.alexanderdimaris.passwordmanager.model.PassObj;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PassListActivity extends AppCompatActivity {

    FloatingActionButton mFAB;
    CoordinatorLayout mSnackBarLayout;
    DataBaseHelper mDataBaseHelper;
    private ListView mListView;
    private PasswordAdapter mPasswordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_list);

        mFAB = findViewById(R.id.activity_pass_list_fab);
        // mSnackBarLayout = findViewById(R.id.activity_pass_list_snackbar_layout);
        mListView = findViewById(R.id.activity_pass_list_lv);
        mDataBaseHelper = new DataBaseHelper(PassListActivity.this);
        updateList();

        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar snackbar = Snackbar.make(mSnackBarLayout, "You have clicked the FAB", Snackbar.LENGTH_LONG);
//                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
//                snackbar.show();
                Intent intent = new Intent(getApplicationContext(), AddPassActivity.class);
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2) {
            PassObj passObj = (PassObj) data.getExtras().getSerializable("passObj");
            boolean success = mDataBaseHelper.addOne(passObj);
            updateList();
        }
    }

    private void updateList() {
        mPasswordAdapter = new PasswordAdapter(this, mDataBaseHelper.getAll());
        mListView.setAdapter(mPasswordAdapter);
    }
}