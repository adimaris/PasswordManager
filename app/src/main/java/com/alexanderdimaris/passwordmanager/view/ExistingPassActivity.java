package com.alexanderdimaris.passwordmanager.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import com.alexanderdimaris.passwordmanager.R;
import com.alexanderdimaris.passwordmanager.model.PassObj;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

public class ExistingPassActivity extends AppCompatActivity {
    private PassObj mPassObj = null;
    private TextInputEditText etTitle, etUsername, etPassword, etComments;
    private TextInputLayout tilPassword;
    private MaterialTextView tvTitle;
    private MaterialButton btEdit, btDelete, btBack;
    private CoordinatorLayout snackBar;

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
        snackBar = findViewById(R.id.activity_existing_pass_coordinator_layout);

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
                deleteClicked(v);
            }
        });
    }

    public void deleteClicked(View v) {

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(ExistingPassActivity.this);
        builder.setTitle("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("passObj", mPassObj);
                setResult(3, resultIntent);
                finish();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // DO NOTHING
            }
        });

        builder.show();
    }

    public void editClicked(View v) {
        btEdit.setText("SAVE");
        btEdit.setIconResource(R.drawable.baseline_save_black_18dp);
        etTitle.setEnabled(true);
        etUsername.setEnabled(true);
        etPassword.setEnabled(true);
        etComments.setEnabled(true);
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
                String title = etTitle.getText().toString(); // TODO seems like this should be its own method
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String comments = etComments.getText().toString();

                if(title.equals("") || username.equals("") || password.equals("")) {
                    Snackbar.make(snackBar, "A required field was left blank. Please try again.", Snackbar.LENGTH_LONG)
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                            .setAnchorView(btDelete)
                            .show();
                } else {
                    mPassObj.setTitle(title);
                    mPassObj.setUsername(username);
                    mPassObj.setPassword(password);
                    mPassObj.setComments(comments);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("passObj", mPassObj);
                    setResult(2, resultIntent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            etPassword.setText(data.getStringExtra("generatedPassword"));
        }
    }
}