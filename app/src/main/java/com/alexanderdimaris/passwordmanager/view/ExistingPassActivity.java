package com.alexanderdimaris.passwordmanager.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.alexanderdimaris.passwordmanager.R;
import com.alexanderdimaris.passwordmanager.model.PassObj;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

public class ExistingPassActivity extends AppCompatActivity implements View.OnClickListener {

    private PassObj passObj = null;
    private TextInputEditText etTitle, etUsername, etPassword, etComments;
    private TextInputLayout tilPassword;
    private MaterialButton btEdit;
    private MaterialButton btDelete;
    private CoordinatorLayout snackBar;

    final static int GENERATE_PASSWORD = 1;
    final static int PASSWORD_UPDATED = 2;
    final static int PASSWORD_DELETED = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_pass);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        passObj = (PassObj) bundle.getSerializable("passObj");

        initializeViews();
    }

    private void initializeViews() {
        etTitle = findViewById(R.id.activity_existing_pass_et_title);
        etUsername = findViewById(R.id.activity_existing_pass_et_username);
        etPassword = findViewById(R.id.activity_existing_pass_et_password);
        etComments = findViewById(R.id.activity_existing_pass_et_comment);
        tilPassword = findViewById(R.id.activity_existing_pass_til_password);
        MaterialTextView tvTitle = findViewById(R.id.activity_existing_pass_tv_title);
        snackBar = findViewById(R.id.activity_existing_pass_coordinator_layout);
        etTitle.setText(passObj.getTitle());
        etUsername.setText(passObj.getUsername());
        etPassword.setText(passObj.getPassword());
        etComments.setText(passObj.getComments());
        tvTitle.setText(passObj.getTitle());
        MaterialButton btBack = findViewById(R.id.activity_existing_pass_bt_back);
        btEdit = findViewById(R.id.activity_existing_pass_bt_edit);
        btDelete = findViewById(R.id.activity_existing_pass_bt_delete);

        btBack.setOnClickListener(this);
        btEdit.setOnClickListener(this);
        btDelete.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_existing_pass_bt_back:
                finish();
                break;
            case R.id.activity_existing_pass_bt_edit:
                edit(v);
                break;
            case R.id.activity_existing_pass_bt_delete:
                delete(v);
                break;
            case R.id.text_input_end_icon:
                Intent intent = new Intent(getApplicationContext(), PassGeneratorActivity.class);
                startActivityForResult(intent, GENERATE_PASSWORD);
            default:
                break;
        }
    }

    public void delete(View v) {

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(ExistingPassActivity.this);
        builder.setTitle("Are you sure?");

        builder.setPositiveButton("YES", (dialog, which) -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("passObj", passObj);
            setResult(PASSWORD_DELETED, resultIntent);
            finish();
        });

        builder.setNegativeButton("NO", (dialog, which) -> {
            // DO NOTHING
        });

        builder.show();
    }

    public void edit(View v) {
        btEdit.setIconResource(R.drawable.baseline_save_black_18dp);
        etTitle.setEnabled(true);
        etUsername.setEnabled(true);
        etPassword.setEnabled(true);
        etComments.setEnabled(true);
        etPassword.setInputType(InputType.TYPE_CLASS_TEXT);

        tilPassword.setEndIconDrawable(R.drawable.ic_baseline_build_24);
        tilPassword.setEndIconOnClickListener(this);

        btEdit.setOnClickListener(v1 -> save());
    }

    public void save() {
        String title = etTitle.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String comments = etComments.getText().toString();

        if (title.equals("") || username.equals("") || password.equals("")) {
            Snackbar.make(snackBar, "A required field was left blank. Please try again.", Snackbar.LENGTH_LONG)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    .setAnchorView(btDelete)
                    .show();
        } else if(title.length() > 20 || username.length() > 20 || password.length() > 20) {
            Snackbar.make(snackBar, "Required field is too long.", Snackbar.LENGTH_LONG)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    .setAnchorView(btDelete)
                    .show();
        } else {
            passObj.setTitle(title);
            passObj.setUsername(username);
            passObj.setPassword(password);
            passObj.setComments(comments);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("passObj", passObj);
            setResult(PASSWORD_UPDATED, resultIntent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == GENERATE_PASSWORD) {
            assert data != null;
            etPassword.setText(data.getStringExtra("generatedPassword"));
        }
    }
}