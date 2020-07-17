package com.alexanderdimaris.passwordmanager.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alexanderdimaris.passwordmanager.R;
import com.alexanderdimaris.passwordmanager.model.PassObj;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class PasswordAdapter extends ArrayAdapter<PassObj> {

    private Context context;
    private List<PassObj> passwordList;

    public PasswordAdapter(Context context, ArrayList<PassObj> passwords) {
        super(context, 0, passwords);
        this.context = context;
        passwordList = passwords;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null) listItem = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);

        PassObj currentPassword = passwordList.get(position);

        MaterialTextView passwordTitle = listItem.findViewById(R.id.list_item_tv_title);
        passwordTitle.setText(currentPassword.getTitle());

        MaterialTextView usernameText = listItem.findViewById(R.id.list_item_tv_username);
        usernameText.setText(currentPassword.getUsername());

        return listItem;
    }
}