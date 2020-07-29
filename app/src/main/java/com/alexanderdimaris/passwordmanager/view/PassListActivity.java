package com.alexanderdimaris.passwordmanager.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.alexanderdimaris.passwordmanager.R;
import com.alexanderdimaris.passwordmanager.model.PassObj;
import com.alexanderdimaris.passwordmanager.presenter.PassListActivityPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class PassListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, PassListActivityPresenter.View {

    private PassListActivityPresenter presenter;
    private CoordinatorLayout snackBarLayout;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_list);

        FloatingActionButton fab = findViewById(R.id.activity_pass_list_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddPassActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        listView = findViewById(R.id.activity_pass_list_lv);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PassObj selected = (PassObj) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ExistingPassActivity.class);
                intent.putExtra("passObj", selected);
                startActivityForResult(intent, 2);
            }
        });

        snackBarLayout = findViewById(R.id.activity_pass_list_snackbar_layout);
        SearchView searchView = findViewById(R.id.activity_pass_list_search);
        searchView.setOnQueryTextListener(this);
        presenter = new PassListActivityPresenter(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != 0) {
            assert data != null;
            PassObj passObj = data.getParcelableExtra("passObj");

            if(resultCode == 1) {
                presenter.addToDatabase(passObj);
            } else if (resultCode == 2) {
                presenter.updateItemInDatabase(passObj);
            } else if(resultCode == 3) {
                presenter.removeFromDatabase(passObj);
            }
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        presenter.searchDatabase(newText);
        return false;
    }

    @Override
    public void updateDisplay(ArrayList<PassObj> list) {
        PasswordAdapter passwordAdapter = new PasswordAdapter(this, list);
        listView.setAdapter(passwordAdapter);
    }

    @Override
    public void displayToast(String operation) {
        Snackbar.make(snackBarLayout, operation, Snackbar.LENGTH_LONG)
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                .show();
    }
}