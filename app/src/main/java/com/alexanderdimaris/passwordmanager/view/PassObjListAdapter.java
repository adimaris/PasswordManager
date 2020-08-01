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

    private final LayoutInflater mInflater;
    private List<PassObj> mPassObjList;
    private OnPasswordListener mOnPasswordListener;

    PassObjListAdapter(Context context, OnPasswordListener onPasswordListener) {
        mInflater = LayoutInflater.from(context);
        this.mOnPasswordListener = onPasswordListener;
    }

    @Override
    public PassObjViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new PassObjViewHolder(itemView, mOnPasswordListener);
    }

    @Override
    public void onBindViewHolder(PassObjViewHolder holder, int position) {
        if (mPassObjList != null) {
            PassObj current = mPassObjList.get(position);
            holder.passwordTitle.setText(current.getTitle());
            holder.usernameText.setText(current.getUsername());
        }
    }

    void setWords(List<PassObj> passList){
        mPassObjList = passList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mPassObjList != null) {
            return mPassObjList.size();
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