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
                startActivityForResult(intent, 2);
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
        if(resultCode == 2) {
            PassObj passObj = (PassObj) data.getExtras().getSerializable("passObj");
            boolean success = mDataBaseHelper.addOne(passObj);
            if(success) {
                Snackbar.make(mSnackBarLayout, "Successfully added " +passObj.getTitle(), Snackbar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                        .show();
                updateList();
            } else {
                Snackbar.make(mSnackBarLayout, "Unable to add " +passObj.getTitle(), Snackbar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                        .show();
            }
        } else if(resultCode == 3) {
            PassObj passObj = (PassObj) data.getExtras().getSerializable("passObj");
            boolean success = mDataBaseHelper.deleteOne(passObj);
            Toast.makeText(this, "Success = " + success, Toast.LENGTH_LONG).show();
            updateList();
        } else if (resultCode == 5) {
            PassObj passObj = (PassObj) data.getExtras().getSerializable("passObj");
            mDataBaseHelper.updateOne(passObj);
            updateList();
        }
    }

    private void updateList() {
        mPasswordAdapter = new PasswordAdapter(this, mDataBaseHelper.getAll());
        mListView.setAdapter(mPasswordAdapter);
    }
}