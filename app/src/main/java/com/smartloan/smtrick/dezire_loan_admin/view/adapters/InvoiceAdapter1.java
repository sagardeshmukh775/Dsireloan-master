package com.smartloan.smtrick.dezire_loan_admin.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.smartloan.smtrick.dezire_loan_admin.R;
import com.smartloan.smtrick.dezire_loan_admin.databinding.InvoiceAdapterLayoutInvoicesBinding;
import com.smartloan.smtrick.dezire_loan_admin.models.Invoice;
import com.smartloan.smtrick.dezire_loan_admin.view.holders.InvoiceViewHolderInvoices;

import java.util.ArrayList;

public class InvoiceAdapter1 extends RecyclerView.Adapter<InvoiceViewHolderInvoices> {

    private ArrayList<Invoice> invoiceArrayList;
    private Context context;

    public InvoiceAdapter1(Context context, ArrayList<Invoice> data) {
        this.invoiceArrayList = data;
        this.context = context;
    }

    @Override
    public InvoiceViewHolderInvoices onCreateViewHolder(ViewGroup parent, int viewType) {
        InvoiceAdapterLayoutInvoicesBinding invoiceAdapterLayoutinvoicesBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.invoice_adapter_layout_invoices, parent, false);
        return new InvoiceViewHolderInvoices(invoiceAdapterLayoutinvoicesBinding);
    }

    private Invoice getModel(int position) {
        return (invoiceArrayList.get(invoiceArrayList.size() - 1 - position));
    }

    @Override
    public void onBindViewHolder(final InvoiceViewHolderInvoices holder, final int listPosition) {
        try {
            Invoice invoice = getModel(listPosition);
            holder.invoiceAdapterLayoutBinding.txtidvalue.setText(invoice.getLeedNumber());
            holder.invoiceAdapterLayoutBinding.txtcnamevalue.setText(invoice.getCustomerName());
            holder.invoiceAdapterLayoutBinding.txtbankvalue.setText(invoice.getApprovedLoan());
            holder.invoiceAdapterLayoutBinding.txtapproveddate1.setText(invoice.getApprovedDate());
            holder.invoiceAdapterLayoutBinding.txtcommisionvalue.setText(invoice.getStatus());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return invoiceArrayList.size();
    }

    public void reload(ArrayList<Invoice> arrayList) {
        invoiceArrayList.clear();
        invoiceArrayList.addAll(arrayList);
        notifyDataSetChanged();
    }
}