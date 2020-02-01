package com.smartloan.smtrick.dezire_loan_admin.view.fragements;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartloan.smtrick.dezire_loan_admin.R;
import com.smartloan.smtrick.dezire_loan_admin.callback.CallBack;
import com.smartloan.smtrick.dezire_loan_admin.databinding.AdminfragmentSanctionBinding;
import com.smartloan.smtrick.dezire_loan_admin.databinding.InvoicedialogBinding;
import com.smartloan.smtrick.dezire_loan_admin.models.Invoice;
import com.smartloan.smtrick.dezire_loan_admin.preferences.AppSharedPreference;
import com.smartloan.smtrick.dezire_loan_admin.recyclerListener.RecyclerTouchListener;
import com.smartloan.smtrick.dezire_loan_admin.repository.InvoiceRepository;
import com.smartloan.smtrick.dezire_loan_admin.repository.LeedRepository;
import com.smartloan.smtrick.dezire_loan_admin.repository.impl.InvoiceRepositoryImpl;
import com.smartloan.smtrick.dezire_loan_admin.repository.impl.LeedRepositoryImpl;
import com.smartloan.smtrick.dezire_loan_admin.singleton.AppSingleton;
import com.smartloan.smtrick.dezire_loan_admin.utilities.Utility;
import com.smartloan.smtrick.dezire_loan_admin.view.activites.Add_Updatelead__Sanction_Activity;
import com.smartloan.smtrick.dezire_loan_admin.view.activites.Add_Updatelead__submittobank_Activity;
import com.smartloan.smtrick.dezire_loan_admin.view.adapters.InvoiceAdapter;
import com.smartloan.smtrick.dezire_loan_admin.view.dialog.ProgressDialogClass;

import java.util.ArrayList;

import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.GLOBAL_DATE_FORMATE;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.INVICES_LEEDS;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.STATUS_SANCTION;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.STATUS_VERIFIED;

public class Admin__Sanction_leed_Fragment extends Fragment {
    InvoiceAdapter invoiceAdapter;
    AppSingleton appSingleton;
    ProgressDialogClass progressDialogClass;
    AppSharedPreference appSharedPreference;
    AdminfragmentSanctionBinding fragmentInvoiceBinding;
    ArrayList<Invoice> invoiceArrayList;
    InvoiceRepository invoiceRepository;
    LeedRepository leedRepository;
    InvoicedialogBinding invoicedialogBinding;

    DatabaseReference databaseReference;

    public Admin__Sanction_leed_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (fragmentInvoiceBinding == null) {
            fragmentInvoiceBinding = DataBindingUtil.inflate(inflater, R.layout.adminfragment_sanction, container, false);
            invoiceRepository = new InvoiceRepositoryImpl();
            leedRepository = new LeedRepositoryImpl();

            databaseReference = FirebaseDatabase.getInstance().getReference();

            progressDialogClass = new ProgressDialogClass(getActivity());
            appSingleton = AppSingleton.getInstance(getActivity());
            appSharedPreference = new AppSharedPreference(getActivity());
            fragmentInvoiceBinding.recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            fragmentInvoiceBinding.recyclerView.setLayoutManager(layoutManager);
            fragmentInvoiceBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            fragmentInvoiceBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));

            getInvoices();

            fragmentInvoiceBinding.searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable s) {


                    if (!s.toString().isEmpty()) {
                        setAdapter(s.toString());
                    } else {
                        /*
                         * Clear the list when editText is empty
                         * */
//                        invoiceArrayList1.clear();
                        if (invoiceArrayList != null) {
//                            invoiceArrayList.clear();
                            fragmentInvoiceBinding.recyclerView.removeAllViews();
                        }
                    }

                }
            });

        }


        return fragmentInvoiceBinding.getRoot();
    }

    private void setAdapter(final String toString) {

        databaseReference.child("leeds").orderByChild("status").equalTo(STATUS_SANCTION).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                invoiceArrayList.clear();
                fragmentInvoiceBinding.recyclerView.removeAllViews();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    Invoice leedsModel = snapshot.getValue(Invoice.class);

                    if (leedsModel.getCustomerName() != null) {
                        if (leedsModel.getCustomerName().toLowerCase().contains(toString)) {
                            invoiceArrayList.add(leedsModel);
                        }
                    }

                }

                serAdapter(invoiceArrayList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private Invoice getModel(int position) {
        return invoiceArrayList.get(invoiceArrayList.size() - 1 - position);
    }

    private void onClickListner() {
        fragmentInvoiceBinding.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), fragmentInvoiceBinding.recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Invoice invoice = getModel(position);
                Intent intent = new Intent(getActivity(), Add_Updatelead__Sanction_Activity.class);
                intent.putExtra(INVICES_LEEDS, invoice);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }

        }));
    }

    private void getInvoices() {
        progressDialogClass.showDialog(this.getString(R.string.loading), this.getString(R.string.PLEASE_WAIT));
        leedRepository.readLeedsByStatus(STATUS_SANCTION, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    invoiceArrayList = (ArrayList<Invoice>) object;
                    filterList(invoiceArrayList);
                }
                progressDialogClass.dismissDialog();
            }

            @Override
            public void onError(Object object) {
                progressDialogClass.dismissDialog();
                Utility.showLongMessage(getActivity(), getString(R.string.server_error));
            }
        });
    }




    private void filterList(ArrayList<Invoice> invoiceArrayList) {
        ArrayList<Invoice> arrayList = new ArrayList<>();
        if (invoiceArrayList != null) {
            for (Invoice invoice : invoiceArrayList) {
                // if (!Utility.isEmptyOrNull(invoice.getStatus()) && invoice.getStatus().equalsIgnoreCase(STATUS_SENT))
                arrayList.add(invoice);
            }
        }
        serAdapter(arrayList);
    }

    private void serAdapter(ArrayList<Invoice> invoiceArrayList) {
        if (invoiceArrayList != null) {
            if (invoiceAdapter == null) {
                invoiceAdapter = new InvoiceAdapter(getActivity(), invoiceArrayList);
                fragmentInvoiceBinding.recyclerView.setAdapter(invoiceAdapter);
                onClickListner();
            } else {
                ArrayList<Invoice> arrayList = new ArrayList<>();
                arrayList.addAll(invoiceArrayList);
                invoiceAdapter.reload(arrayList);
            }
        }
    }

    private void showInvoiceDialog(Invoice invoice) {
        final Dialog dialog = new Dialog(getActivity());
        invoicedialogBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.invoicedialog, null, false);
        dialog.setContentView(invoicedialogBinding.getRoot());
        dialog.setTitle("Title...");
        invoicedialogBinding.txtleadidvalue.setText(invoice.getLeedNumber());
        invoicedialogBinding.txtcnamevalue.setText(invoice.getCustomerName());
        invoicedialogBinding.txtccontactvalue.setText(invoice.getMobileNumber());
        invoicedialogBinding.txtcaddressvalue.setText(invoice.getAddress());
        invoicedialogBinding.txtloantyprvalue.setText(invoice.getLoanType());
        invoicedialogBinding.txtdatevalue.setText(Utility.convertMilliSecondsToFormatedDate(invoice.getCreatedDateTimeLong(), GLOBAL_DATE_FORMATE));

        invoicedialogBinding.dialogButtonaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
       /* invoicedialogBinding.dialogButtonreject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/
        dialog.show();
    }
}
