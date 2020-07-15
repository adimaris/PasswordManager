package com.alexanderdimaris.passwordmanager.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alexanderdimaris.passwordmanager.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;

public class PassGeneratorActivity extends AppCompatActivity {

    private SwitchMaterial mLowerCase;
    private SwitchMaterial mUpperCase;
    private SwitchMaterial mDigits;
    private SwitchMaterial mSpecial;
    private SwitchMaterial mBrackets;
    private SwitchMaterial mSpaces;

    private MaterialButton btSave;
    private TextInputLayout mTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_generator);

        mLowerCase = findViewById(R.id.activity_pass_generator_sw_lower_case);
        mUpperCase = findViewById(R.id.activity_pass_generator_sw_upper_case);
        mDigits = findViewById(R.id.activity_pass_generator_sw_digits);
        mSpecial = findViewById(R.id.activity_pass_generator_sw_special);
        mBrackets = findViewById(R.id.activity_pass_generator_sw_brackets);
        mSpaces = findViewById(R.id.activity_pass_generator_sw_spaces);

        btSave = findViewById(R.id.activity_pass_generator_bt_accept);

        mTextInputLayout = findViewById(R.id.activity_pass_generator_til_password);
        mTextInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePassword(btSave);
            }
        });
    }

    public void generatePassword(View v) {

    }
}