package com.smartloan.smtrick.dezire_loan_admin.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.smartloan.smtrick.dezire_loan_admin.R;
import com.smartloan.smtrick.dezire_loan_admin.databinding.UseradapterLayoutListitemBinding;
import com.smartloan.smtrick.dezire_loan_admin.models.User;
import com.smartloan.smtrick.dezire_loan_admin.view.holders.UserViewHolder;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private ArrayList<User> invoiceArrayList;
    private Context context;

    public UserAdapter(Context context, ArrayList<User> data) {
        this.invoiceArrayList = data;
        this.context = context;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent,
                                                int viewType) {
        UseradapterLayoutListitemBinding invoiceAdapterLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.useradapter_layout_listitem, parent, false);
        return new UserViewHolder(invoiceAdapterLayoutBinding);
    }

    private User getModel(int position) {
        return (invoiceArrayList.get(invoiceArrayList.size() - 1 - position));
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int listPosition) {
        try {
            User user = getModel(listPosition);
            holder.useradapterLayoutListitemBinding.txtcnamevalue.setText(user.getUserName());
            holder.useradapterLayoutListitemBinding.txtbankvalue.setText(user.getMobileNumber());
           // holder.invoiceAdapterLayoutBinding.txtbankvalue.setText(invoice.getUserName());
          //  holder.invoiceAdapterLayoutBinding.txtStatusValue.setText(invoice.getUserName());
          //  holder.invoiceAdapterLayoutBinding.txtcommisionvalue.setText(invoice.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return invoiceArrayList.size();
    }

    public void reload(ArrayList<User> arrayList) {
        invoiceArrayList.clear();
        invoiceArrayList.addAll(arrayList);
        notifyDataSetChanged();
    }
}