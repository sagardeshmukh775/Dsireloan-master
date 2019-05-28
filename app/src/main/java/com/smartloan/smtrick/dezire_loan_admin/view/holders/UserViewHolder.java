package com.smartloan.smtrick.dezire_loan_admin.view.holders;

import android.support.v7.widget.RecyclerView;

import com.smartloan.smtrick.dezire_loan_admin.databinding.UseradapterLayoutListitemBinding;

public class UserViewHolder extends RecyclerView.ViewHolder {
    public UseradapterLayoutListitemBinding useradapterLayoutListitemBinding;

    public UserViewHolder(UseradapterLayoutListitemBinding useradapterLayoutListitemBinding) {
        super(useradapterLayoutListitemBinding.getRoot());
        this.useradapterLayoutListitemBinding = useradapterLayoutListitemBinding;
    }
}
