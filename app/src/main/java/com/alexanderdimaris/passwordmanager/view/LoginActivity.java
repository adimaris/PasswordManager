package com.alexanderdimaris.passwordmanager.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.alexanderdimaris.passwordmanager.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword;
    private CoordinatorLayout snackBar;

    private final static String USERNAME = "username";
    private final static String PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
    }

    private void initializeViews() {
        etUsername = findViewById(R.id.login_username_et);
        etPassword = findViewById(R.id.login_password_et);
        MaterialButton btLogin = findViewById(R.id.login_button);
        btLogin.setOnClickListener(v -> login());
        snackBar = findViewById(R.id.login_snackbar);
    }

    private void login() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if(username.equals("") || password.equals("")) {
            snackBar("Required field left blank");
        } else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor prefsEdit = prefs.edit();

            String originalUsername = prefs.getString(USERNAME, null);
            String originalPassword = prefs.getString(PASSWORD, null);


            if(originalUsername == null || originalPassword == null) {
                prefsEdit.putString(USERNAME, username);
                prefsEdit.putString(PASSWORD, password);
                prefsEdit.apply();
                launchPassListActivity();
            } else {
                if(username.equals(originalUsername) && password.equals(originalPassword)) {
                    launchPassListActivity();
                } else {
                    snackBar("Incorrect username or password");
                }
            }
        }
    }

    public void snackBar(String string) {
        Snackbar.make(snackBar, string, Snackbar.LENGTH_LONG)
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                .show();
    }

    public void launchPassListActivity() {
        Intent intent = new Intent(this, PassListActivity.class);
        startActivity(intent);
    }
}