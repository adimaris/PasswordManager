package com.alexanderdimaris.passwordmanager.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alexanderdimaris.passwordmanager.R;
import com.alexanderdimaris.passwordmanager.model.PassObj;

import java.util.ArrayList;
import java.util.List;

public class PasswordAdapter extends ArrayAdapter<PassObj> {

    private Context mContext;
    private List<PassObj> passwordList = new ArrayList<>();

    public PasswordAdapter(Context context, ArrayList<PassObj> passwords) {
        super(context, 0, passwords);
        mContext = context;
        passwordList = passwords;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null) listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        PassObj currentPassword = passwordList.get(position);

        TextView passwordTitle = listItem.findViewById(R.id.list_item_tv_title);
        passwordTitle.setText(currentPassword.getTitle());

        TextView usernameText = listItem.findViewById(R.id.list_item_tv_username);
        usernameText.setText(currentPassword.getUsername());

        return listItem;
    }
}