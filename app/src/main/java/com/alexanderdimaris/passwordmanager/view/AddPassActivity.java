package com.alexanderdimaris.passwordmanager.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import com.alexanderdimaris.passwordmanager.R;
import com.alexanderdimaris.passwordmanager.model.PassObj;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddPassActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText etTitle, etUsername, etPassword, etComments;
    MaterialButton btSave;
    MaterialButton btBack;
    CoordinatorLayout snackBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pass);

        etTitle = findViewById(R.id.activity_add_pass_et_title);
        etUsername = findViewById(R.id.activity_add_pass_et_username);
        etPassword = findViewById(R.id.activity_add_pass_et_password);
        etComments = findViewById(R.id.activity_add_pass_et_comments);
        snackBar = findViewById(R.id.activity_add_pass_coordinator_layout);

        btBack = findViewById(R.id.activity_add_pass_btn_back);
        btBack.setOnClickListener(this);

        btSave = findViewById(R.id.activity_add_pass_btn_save);
        btSave.setOnClickListener(this);

        TextInputLayout textInputLayout = findViewById(R.id.activity_add_pass_til_password);
        textInputLayout.setEndIconOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.activity_add_pass_btn_back:
                finish();
                break;

            case R.id.activity_add_pass_btn_save:
                save();
                break;

            case R.id.text_input_end_icon:
                Intent intent = new Intent(getApplicationContext(), PassGeneratorActivity.class);
                startActivityForResult(intent, 1);
                break;

            default:
                break;
        }
    }

    public void save() {
        String title = etTitle.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String comments = etComments.getText().toString();

        if(title.equals("") || username.equals("") || password.equals("")) {
            Snackbar.make(snackBar, "A required field was left blank. Please try again.", Snackbar.LENGTH_LONG)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    .show();
        } else if(title.length() > 20 || username.length() > 20 || password.length() > 20) {
            Snackbar.make(snackBar, "Required field is too long.", Snackbar.LENGTH_LONG)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    .show();
        } else {
            PassObj passObj = new PassObj(-1, title, username, password, comments);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("passObj", passObj);
            setResult(1, resultIntent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1) {
            assert data != null;
            etPassword.setText(data.getStringExtra("generatedPassword"));
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }
}