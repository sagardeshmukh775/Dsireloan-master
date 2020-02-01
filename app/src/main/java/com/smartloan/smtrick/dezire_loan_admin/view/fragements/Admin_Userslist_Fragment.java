package com.smartloan.smtrick.dezire_loan_admin.view.fragements;

import android.app.Dialog;
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
import com.smartloan.smtrick.dezire_loan_admin.databinding.FragmentInvoiceBinding;
import com.smartloan.smtrick.dezire_loan_admin.databinding.InvoicedialogBinding;
import com.smartloan.smtrick.dezire_loan_admin.databinding.UserdialogBinding;

import com.smartloan.smtrick.dezire_loan_admin.models.Invoice;
import com.smartloan.smtrick.dezire_loan_admin.models.User;
import com.smartloan.smtrick.dezire_loan_admin.models.Users;
import com.smartloan.smtrick.dezire_loan_admin.preferences.AppSharedPreference;
import com.smartloan.smtrick.dezire_loan_admin.recyclerListener.RecyclerTouchListener;
import com.smartloan.smtrick.dezire_loan_admin.repository.InvoiceRepository;
import com.smartloan.smtrick.dezire_loan_admin.repository.impl.InvoiceRepositoryImpl;
import com.smartloan.smtrick.dezire_loan_admin.singleton.AppSingleton;
import com.smartloan.smtrick.dezire_loan_admin.utilities.Utility;
import com.smartloan.smtrick.dezire_loan_admin.view.adapters.UserAdapter;
import com.smartloan.smtrick.dezire_loan_admin.view.dialog.ProgressDialogClass;

import java.util.ArrayList;

import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.GLOBAL_DATE_FORMATE;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.STATUS_GENERATED;

public class Admin_Userslist_Fragment extends Fragment {
    UserAdapter invoiceAdapter;
    AppSingleton appSingleton;
    ProgressDialogClass progressDialogClass;
    AppSharedPreference appSharedPreference;
    FragmentInvoiceBinding fragmentInvoiceBinding;
    ArrayList<Users> invoiceArrayList;
    InvoiceRepository invoiceRepository;
    InvoicedialogBinding invoicedialogBinding;
    UserdialogBinding userdialogBinding;

    DatabaseReference databaseReference;

    public Admin_Userslist_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           getActivity().setTitle("Users List");
        if (fragmentInvoiceBinding == null) {
            getActivity().setTitle("Users List");
            fragmentInvoiceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_invoice, container, false);
            invoiceRepository = new InvoiceRepositoryImpl();
            progressDialogClass = new ProgressDialogClass(getActivity());
            appSingleton = AppSingleton.getInstance(getActivity());
            appSharedPreference = new AppSharedPreference(getActivity());
            fragmentInvoiceBinding.recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            fragmentInvoiceBinding.recyclerView.setLayoutManager(layoutManager);
            fragmentInvoiceBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            fragmentInvoiceBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

            databaseReference = FirebaseDatabase.getInstance().getReference();

            getuserlist();

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
                            invoiceArrayList.clear();
                            fragmentInvoiceBinding.recyclerView.removeAllViews();
                        }
                    }

                }
            });
        }
        return fragmentInvoiceBinding.getRoot();
    }

    private void setAdapter(final String toString) {

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                invoiceArrayList.clear();
                fragmentInvoiceBinding.recyclerView.removeAllViews();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    Users leedsModel = snapshot.getValue(Users.class);

                    if (leedsModel.getName() != null) {
                        if (leedsModel.getName().toLowerCase().contains(toString)) {
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

    private Users getModel(int position) {
        return invoiceArrayList.get(invoiceArrayList.size() - 1 - position);
    }

    private void onClickListner() {
        fragmentInvoiceBinding.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), fragmentInvoiceBinding.recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position)
            {
                Users user = getModel(position);
               showInvoiceDialog(user);
            }

            @Override
            public void onLongClick(View view, int position) {
            }

        }));
    }

    private void getuserlist() {
        progressDialogClass.showDialog(this.getString(R.string.loading), this.getString(R.string.PLEASE_WAIT));
        invoiceRepository.readAllusers(new CallBack(){
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    invoiceArrayList = (ArrayList<Users>) object;
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

    private void filterList(ArrayList<Users> invoiceArrayList) {
        ArrayList<Users> arrayList = new ArrayList<>();
        if (invoiceArrayList != null) {
            for (Users invoice : invoiceArrayList) {
                // if (!Utility.isEmptyOrNull(invoice.getStatus()) && invoice.getStatus().equalsIgnoreCase(STATUS_SENT))
                arrayList.add(invoice);
            }
        }
        serAdapter(arrayList);
    }

    private void serAdapter(ArrayList<Users> invoiceArrayList) {
        if (invoiceArrayList != null) {
            if (invoiceAdapter == null) {
                invoiceAdapter = new UserAdapter(getActivity(), invoiceArrayList);
                fragmentInvoiceBinding.recyclerView.setAdapter(invoiceAdapter);
                onClickListner();
            } else {
                ArrayList<Users> arrayList = new ArrayList<>();
                arrayList.addAll(invoiceArrayList);
                invoiceAdapter.reload(arrayList);
            }
        }
    }

    private void showInvoiceDialog(Users invoice) {
        final Dialog dialog = new Dialog(getActivity());
        userdialogBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.userdialog, null, false);
        dialog.setContentView(userdialogBinding.getRoot());
        dialog.setTitle("Title...");
        userdialogBinding.txtagentid.setText(invoice.getAgentId());
        userdialogBinding.txtcnamevalue.setText(invoice.getAgentId());
        userdialogBinding.txtccontactvalue.setText(invoice.getMobilenumber());
        userdialogBinding.txtcaddressvalue.setText(invoice.getName());
//        userdialogBinding.txtemailidvalue.setText(invoice.getEmail());

//        userdialogBinding.txtdatevalue.setText(Utility.convertMilliSecondsToFormatedDate(invoice.getCreatedDateTimeLong(), GLOBAL_DATE_FORMATE));
        userdialogBinding.dialogButtonaccept.setOnClickListener(new View.OnClickListener() {
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
