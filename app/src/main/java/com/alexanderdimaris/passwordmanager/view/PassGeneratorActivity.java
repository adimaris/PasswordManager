package com.alexanderdimaris.passwordmanager.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alexanderdimaris.passwordmanager.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;

public class PassGeneratorActivity extends AppCompatActivity implements View.OnClickListener {

    private SwitchMaterial swLowerCase, swUpperCase, swDigits, swSpecial, swBrackets, swSpaces;
    private Slider slider;
    private TextInputEditText etPasswordOutput;
    private CoordinatorLayout snackBar;
    private String result = "";

    final static int PASSWORD_GENERATED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_generator);

        initializeViews();
    }

    private void initializeViews() {
        swLowerCase = findViewById(R.id.activity_pass_generator_sw_lower_case);
        swUpperCase = findViewById(R.id.activity_pass_generator_sw_upper_case);
        swDigits = findViewById(R.id.activity_pass_generator_sw_digits);
        swSpecial = findViewById(R.id.activity_pass_generator_sw_special);
        swBrackets = findViewById(R.id.activity_pass_generator_sw_brackets);
        swSpaces = findViewById(R.id.activity_pass_generator_sw_spaces);
        etPasswordOutput = findViewById(R.id.activity_pass_generator_et_password);
        slider = findViewById(R.id.activity_pass_generator_slider);
        snackBar = findViewById(R.id.activity_pass_generator_coordinator_layout);
        TextInputLayout tilPassword = findViewById(R.id.activity_pass_generator_til_password);
        MaterialButton btBack = findViewById(R.id.activity_pass_generator_bt_back);
        MaterialButton btAccept = findViewById(R.id.activity_pass_generator_bt_accept);

        swDigits.setChecked(true);
        swLowerCase.setChecked(true);
        swUpperCase.setChecked(true);

        tilPassword.setEndIconOnClickListener(this::generatePassword);
        btBack.setOnClickListener(this);
        btAccept.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_pass_generator_bt_back:
                finish();
                break;
            case R.id.activity_pass_generator_bt_accept:
                accept();
                break;
            default:
                break;
        }
    }

    public void generatePassword(View v) {

        int n = 0;

        try {
            n = (int) slider.getValue();
        } catch (Exception e) {
            Snackbar.make(snackBar, "Please type a length for the password", Snackbar.LENGTH_LONG)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    .show();
        }

        if (n <= 0) {
            Snackbar.make(snackBar, "Please enter a length greater than zero for the password", Snackbar.LENGTH_LONG)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    .show();
        } else {
            String set = "";

            if(swLowerCase.isChecked()) {
                set += "abcdefghijklmnopqrstuvwxyz";
            }

            if(swUpperCase.isChecked()) {
                set += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            }

            if(swDigits.isChecked()) {
                set += "0123456789";
            }

            if(swSpecial.isChecked()) {
                set += "!@#$%^&*_-+=";
            }

            if(swBrackets.isChecked()) {
                set += "(){}[]";
            }

            if(swSpaces.isChecked()) {
                set += " ";
            }

            StringBuilder sb = new StringBuilder();
            Random rand = new Random();
            int size = set.length()-1;

            if(size < 0) {
                Snackbar.make(snackBar, "Please select at least one switch.", Snackbar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                        .show();
            } else if(size == 0 && swSpaces.isChecked()){
                Snackbar.make(snackBar, "Please select more switches than just the spaces switch.", Snackbar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                        .show();
            } else {
                for(int i = 0; i < n; i++) {
                    int k = rand.nextInt(size);
                    sb.append(set.charAt(k));
                }

                result = sb.toString();
                etPasswordOutput.setText(result);
            }
        }
    }

    public void accept() {
        if(etPasswordOutput.getText().toString().length() == 0) {
            Snackbar.make(snackBar, "Cannot accept an empty password.", Snackbar.LENGTH_LONG)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    .show();
        } else {
            Intent intent = new Intent();
            intent.putExtra("generatedPassword", result);
            setResult(PASSWORD_GENERATED, intent);
            finish();
        }
    }
}