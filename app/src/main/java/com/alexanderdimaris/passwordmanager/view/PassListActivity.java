package com.alexanderdimaris.passwordmanager.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alexanderdimaris.passwordmanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class PassListActivity extends AppCompatActivity {

    FloatingActionButton mFAB;
    CoordinatorLayout mSnackBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_list);

        mFAB = findViewById(R.id.activity_pass_list_fab);
        mSnackBarLayout = findViewById(R.id.activity_pass_list_snackbar_layout);

        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar snackbar = Snackbar.make(mSnackBarLayout, "You have clicked the FAB", Snackbar.LENGTH_LONG);
//                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
//                snackbar.show();
                Intent intent = new Intent(getApplicationContext(), AddPassActivity.class);
                startActivity(intent);
            }
        });
    }
}