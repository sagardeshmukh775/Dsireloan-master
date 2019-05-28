package com.smartloan.smtrick.dezire_loan_admin.view.holders;

import android.support.v7.widget.RecyclerView;


import com.smartloan.smtrick.dezire_loan_admin.databinding.InvoiceAdapterLayoutInvoicesBinding;

public class InvoiceViewHolderInvoices extends RecyclerView.ViewHolder {
    public InvoiceAdapterLayoutInvoicesBinding invoiceAdapterLayoutBinding;

    public InvoiceViewHolderInvoices(InvoiceAdapterLayoutInvoicesBinding invoiceAdapterLayoutBinding) {
        super(invoiceAdapterLayoutBinding.getRoot());
        this.invoiceAdapterLayoutBinding = invoiceAdapterLayoutBinding;
    }
}
