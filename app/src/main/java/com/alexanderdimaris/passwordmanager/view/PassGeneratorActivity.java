package com.alexanderdimaris.passwordmanager.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alexanderdimaris.passwordmanager.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;

public class PassGeneratorActivity extends AppCompatActivity {

    private SwitchMaterial mLowerCase;
    private SwitchMaterial mUpperCase;
    private SwitchMaterial mDigits;
    private SwitchMaterial mSpecial;
    private SwitchMaterial mBrackets;
    private SwitchMaterial mSpaces;

    private MaterialButton btSave;
    private TextInputLayout mTextInputLayout;
    private TextInputEditText etLength;
    private TextInputEditText etPasswordOutput;
    private MaterialButton btAccept;

    private String lowers = "abcdefghijklmnopqrstuvwxyz";
    private String capitals = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String digits = "0123456789";
    private String specials = "!@#$%^&*_-+=";
    private String brackets = "(){}[]";

    private String result = "";

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
        etLength = findViewById(R.id.activity_pass_generator_et_length);
        etPasswordOutput = findViewById(R.id.activity_pass_generator_et_password);
        btAccept = findViewById(R.id.activity_pass_generator_bt_accept);

        mTextInputLayout = findViewById(R.id.activity_pass_generator_til_password);
        mTextInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePassword(btSave);
            }
        });

        btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept(btAccept);
            }
        });
    }

    public void generatePassword(View v) {

        int n = 0;

        try {
            n = Integer.valueOf(etLength.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "Please type a length for the password", Toast.LENGTH_LONG).show(); // TODO change to snack bar
        }

        if (n <= 0) {
            Toast.makeText(this, "Please enter a length greater than zero for the password", Toast.LENGTH_SHORT).show(); //TODO snackbar
        } else {
            String set = "";

            if(mLowerCase.isChecked()) {
                set += lowers;
            }

            if(mUpperCase.isChecked()) {
                set += capitals;
            }

            // digi special bracktes spaces

            if(mDigits.isChecked()) {
                set += digits;
            }

            if(mSpecial.isChecked()) {
                set += specials;
            }

            if(mBrackets.isChecked()) {
                set += brackets;
            }

            if(mSpaces.isChecked()) {
                set += " ";
            }

            StringBuilder sb = new StringBuilder();
            Random rand = new Random();
            int size = set.length()-1;

            for(int i = 0; i < n; i++) {
                int k = rand.nextInt(size);
                sb.append(set.charAt(k));
            }

            result = sb.toString();
            etPasswordOutput.setText(result);
        }
    }

    public void accept(View v) {
        Intent intent = new Intent();
        intent.putExtra("generatedPassword", result);
        setResult(2, intent);
        finish();
    }
}