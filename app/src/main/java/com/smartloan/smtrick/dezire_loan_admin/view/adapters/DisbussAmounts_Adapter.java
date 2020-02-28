package com.smartloan.smtrick.dezire_loan_admin.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartloan.smtrick.dezire_loan_admin.R;

import java.util.ArrayList;
import java.util.List;

public class DisbussAmounts_Adapter extends RecyclerView.Adapter<DisbussAmounts_Adapter.ViewHolder> {

    private static List<String> searchArrayList;
    private Context context;
//    private boolean isFromRequest;
//    ProgressDialogClass progressDialogClass;
//    LeedRepository leedRepository;

    public DisbussAmounts_Adapter(Context context, List<String> userArrayList) {
        this.context = context;
        this.searchArrayList = userArrayList;

    }


    @Override
    public DisbussAmounts_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.disbussamt_layout, parent, false);
        DisbussAmounts_Adapter.ViewHolder viewHolder = new ViewHolder(v);
        //  context = parent.getContext();
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final DisbussAmounts_Adapter.ViewHolder holder, final int position) {
        final String plots = searchArrayList.get(position);

        if (plots != null) {
            holder.txtAmount.setText(searchArrayList.get(position));
        } else {
            holder.txtAmount.setText("Null");
        }

    }


    @Override
    public int getItemCount() {
        return searchArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView  txtAmount;


        public ViewHolder(View itemView) {
            super(itemView);

            txtAmount = (TextView) itemView.findViewById(R.id.disbussamt);

        }
    }

    public void reload(ArrayList<String> leedsModelArrayList) {
        searchArrayList.clear();
        searchArrayList.addAll(leedsModelArrayList);
        notifyDataSetChanged();
    }

}