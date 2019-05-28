package com.smartloan.smtrick.dezire_loan_admin.view.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.smartloan.smtrick.dezire_loan_admin.R;
import com.smartloan.smtrick.dezire_loan_admin.models.Invoice;

import java.util.List;

public class Invoice_Adapter extends RecyclerView.Adapter<Invoice_Adapter.ViewHolder> {

    private Context context;
    private List<Invoice> list;
    String item;
    private FirebaseStorage mStorage;

    public Invoice_Adapter(List<Invoice> catalogList) {
        list = catalogList;
    }


    @Override
    public Invoice_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invoice_data, parent, false);
        Invoice_Adapter.ViewHolder viewHolder = new Invoice_Adapter.ViewHolder(v);
        //  context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final Invoice_Adapter.ViewHolder holder, final int position) {
        Invoice catname = list.get(position);

        holder.leednumber.setText(catname.getLeedNumber());
        holder.applicantname.setText(catname.getCustomerName());
        holder.expectedloan.setText(catname.getApprovedLoan());
        holder.approveddate.setText(catname.getApprovedDate());
        holder.status.setText(catname.getStatus());

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(holder.cardView.getContext(), User_Invoice_Details_Activity.class);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView leednumber, applicantname, expectedloan, approveddate, status;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            leednumber = (TextView) itemView.findViewById(R.id.txtidvalue);
            applicantname = (TextView) itemView.findViewById(R.id.txtcnamevalue);
            expectedloan = (TextView) itemView.findViewById(R.id.txtbankvalue);
            approveddate = (TextView) itemView.findViewById(R.id.txtapproveddate1);
            status = (TextView) itemView.findViewById(R.id.txtcommisionvalue);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
