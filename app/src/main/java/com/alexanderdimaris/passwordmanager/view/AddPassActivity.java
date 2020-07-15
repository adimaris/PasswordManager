package com.alexanderdimaris.passwordmanager.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alexanderdimaris.passwordmanager.R;
import com.alexanderdimaris.passwordmanager.model.PassObj;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddPassActivity extends AppCompatActivity {

    TextInputEditText etTitle, etUsername, etPassword, etComments;
    MaterialButton btSave;
    MaterialButton btBack;

    // TODO generate a password button
    // TODO snackbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pass);

        etTitle = findViewById(R.id.activity_add_pass_et_title);
        etUsername = findViewById(R.id.activity_add_pass_et_username);
        etPassword = findViewById(R.id.activity_add_pass_et_password);
        etComments = findViewById(R.id.activity_add_pass_et_comments);

        btSave = findViewById(R.id.activity_add_pass_btn_save);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);
            }
        });

        btBack = findViewById(R.id.activity_add_pass_btn_back);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextInputLayout textInputLayout = findViewById(R.id.activity_add_pass_til_password);
        textInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PassGeneratorActivity.class);
                startActivityForResult(intent, 2);
            }
        });
    }

    public void save(View v) {
        String title = etTitle.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String comments = etComments.getText().toString();

        if(title.equals("") || username.equals("") || password.equals("")) {
            Toast.makeText(this, "One of the required fields were left blank", Toast.LENGTH_LONG).show();
        } else {
            PassObj passObj = new PassObj(-1, title, username, password, comments);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("passObj", passObj);
            setResult(2, resultIntent);
            finish();
        }
    }

}