package com.alexanderdimaris.passwordmanager.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.alexanderdimaris.passwordmanager.R;
import com.alexanderdimaris.passwordmanager.model.PassObj;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class PassObjListAdapter extends RecyclerView.Adapter<PassObjListAdapter.PassObjViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<PassObj> passObjList;
    private OnPasswordListener onPasswordListener;

    PassObjListAdapter(Context context, OnPasswordListener onPasswordListener) {
        layoutInflater = LayoutInflater.from(context);
        this.onPasswordListener = onPasswordListener;
    }

    @Override
    public PassObjViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new PassObjViewHolder(itemView, onPasswordListener);
    }

    @Override
    public void onBindViewHolder(PassObjViewHolder holder, int position) {
        if (passObjList != null) {
            PassObj current = passObjList.get(position);
            holder.passwordTitle.setText(current.getTitle());
            holder.usernameText.setText(current.getUsername());
        }
    }

    void setPasswords(List<PassObj> passList){
        passObjList = passList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (passObjList != null) {
            return passObjList.size();
        } else {
            return 0;
        }
    }

    static class PassObjViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final MaterialTextView passwordTitle;
        private final MaterialTextView usernameText;
        OnPasswordListener onPasswordListener;

        public PassObjViewHolder(View itemView, OnPasswordListener onPasswordListener) {
            super(itemView);
            passwordTitle = itemView.findViewById(R.id.list_item_tv_title);
            usernameText = itemView.findViewById(R.id.list_item_tv_username);
            this.onPasswordListener = onPasswordListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPasswordListener.onPasswordClick(getAdapterPosition());
        }
    }

    public interface OnPasswordListener {
        void onPasswordClick(int position);
    }
}