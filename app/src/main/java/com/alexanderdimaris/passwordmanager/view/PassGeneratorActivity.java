package com.alexanderdimaris.passwordmanager.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alexanderdimaris.passwordmanager.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;

public class PassGeneratorActivity extends AppCompatActivity {

    private SwitchMaterial mLowerCase, mUpperCase, mDigits, mSpecial, mBrackets, mSpaces;
    private MaterialButton btAccept, btBack;
    private Slider slider;
    private TextInputLayout mTextInputLayout;
    private TextInputEditText etPasswordOutput;
    private CoordinatorLayout snackBar;

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

        etPasswordOutput = findViewById(R.id.activity_pass_generator_et_password);
        slider = findViewById(R.id.activity_pass_generator_slider);

        snackBar = findViewById(R.id.activity_pass_generator_coordinator_layout);

        mTextInputLayout = findViewById(R.id.activity_pass_generator_til_password);
        mTextInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePassword(v);
            }
        });

        btAccept = findViewById(R.id.activity_pass_generator_bt_accept);
        btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept(btAccept);
            }
        });

        btBack = findViewById(R.id.activity_pass_generator_bt_back);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

            if(mLowerCase.isChecked()) {
                set += lowers;
            }

            if(mUpperCase.isChecked()) {
                set += capitals;
            }

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

            if(size < 0) {
                Snackbar.make(snackBar, "Please select at least one switch.", Snackbar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                        .show();
            } else if(size == 0 && mSpaces.isChecked()){
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

    public void accept(View v) {
        Intent intent = new Intent();
        intent.putExtra("generatedPassword", result);
        setResult(1, intent);
        finish();
    }
}