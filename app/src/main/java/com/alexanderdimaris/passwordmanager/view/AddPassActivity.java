package com.alexanderdimaris.passwordmanager.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alexanderdimaris.passwordmanager.R;
import com.google.android.material.textfield.TextInputLayout;

public class AddPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pass);

        TextInputLayout textInputLayout = findViewById(R.id.activity_add_pass_password_edit_text);
        textInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PassGeneratorActivity.class);
                startActivity(intent);
            }
        });
    }
}