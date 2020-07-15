package com.alexanderdimaris.passwordmanager.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import com.alexanderdimaris.passwordmanager.R;
import com.alexanderdimaris.passwordmanager.model.PassObj;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

public class ExistingPassActivity extends AppCompatActivity {
    private PassObj mPassObj = null;
    private TextInputEditText etTitle;
    private TextInputEditText etUsername;
    private TextInputEditText etPassword;
    private TextInputEditText etComments;
    private TextInputLayout tilPassword;
    private MaterialTextView tvTitle;
    private MaterialButton btEdit;
    private MaterialButton btDelete;
    private MaterialButton btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_pass);

        etTitle = findViewById(R.id.activity_existing_pass_et_title);
        etUsername = findViewById(R.id.activity_existing_pass_et_username);
        etPassword = findViewById(R.id.activity_existing_pass_et_password);
        etComments = findViewById(R.id.activity_existing_pass_et_comment);

        tilPassword = findViewById(R.id.activity_existing_pass_til_password);

        tvTitle = findViewById(R.id.activity_existing_pass_tv_title);

        Intent intent = getIntent();
        mPassObj = (PassObj) intent.getExtras().getSerializable("passObj");

        etTitle.setText(mPassObj.getTitle());
        etUsername.setText(mPassObj.getUsername());
        etPassword.setText(mPassObj.getPassword());
        etComments.setText(mPassObj.getComments());

        tvTitle.setText(mPassObj.getTitle());

        btEdit = findViewById(R.id.activity_existing_pass_bt_edit);
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editClicked(v);
            }
        });

        btBack = findViewById(R.id.activity_existing_pass_bt_back);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btDelete = findViewById(R.id.activity_existing_pass_bt_delete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("passObj", mPassObj);
                setResult(3, resultIntent);
                finish();
            }
        });
    }

    public void editClicked(View v) {
        btEdit.setText("SAVE");
        btEdit.setIconResource(R.drawable.baseline_save_black_18dp);

        etPassword.setInputType(InputType.TYPE_CLASS_TEXT);

        tilPassword.setEndIconDrawable(R.drawable.ic_baseline_build_24);
        tilPassword.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PassGeneratorActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String comments = etComments.getText().toString();

                if(title.equals("") || username.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "One of the required fields were left blank", Toast.LENGTH_LONG).show();
                } else {
                    mPassObj.setTitle(title);
                    mPassObj.setUsername(username);
                    mPassObj.setPassword(password);
                    mPassObj.setComments(comments);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("passObj", mPassObj);
                    setResult(5, resultIntent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2) {
            etPassword.setText(data.getStringExtra("generatedPassword"));
        }
    }
}