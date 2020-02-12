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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartloan.smtrick.dezire_loan_admin.R;
import com.smartloan.smtrick.dezire_loan_admin.callback.CallBack;
import com.smartloan.smtrick.dezire_loan_admin.databinding.FragmentInvoiceBinding;
import com.smartloan.smtrick.dezire_loan_admin.databinding.InvoicedialogBinding;
import com.smartloan.smtrick.dezire_loan_admin.models.Invoice;
import com.smartloan.smtrick.dezire_loan_admin.models.LeedsModel;
import com.smartloan.smtrick.dezire_loan_admin.preferences.AppSharedPreference;
import com.smartloan.smtrick.dezire_loan_admin.recyclerListener.RecyclerTouchListener;
import com.smartloan.smtrick.dezire_loan_admin.repository.InvoiceRepository;
import com.smartloan.smtrick.dezire_loan_admin.repository.LeedRepository;
import com.smartloan.smtrick.dezire_loan_admin.repository.impl.InvoiceRepositoryImpl;
import com.smartloan.smtrick.dezire_loan_admin.repository.impl.LeedRepositoryImpl;
import com.smartloan.smtrick.dezire_loan_admin.singleton.AppSingleton;
import com.smartloan.smtrick.dezire_loan_admin.utilities.Utility;
import com.smartloan.smtrick.dezire_loan_admin.view.activites.Invoice_Details_Activity;
import com.smartloan.smtrick.dezire_loan_admin.view.adapters.InvoiceAdapter;
import com.smartloan.smtrick.dezire_loan_admin.view.dialog.ProgressDialogClass;

import java.util.ArrayList;

import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.GLOBAL_DATE_FORMATE;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.INVICES_LEEDS;

public class Invoices_Fragment_Payed extends Fragment {
    InvoiceAdapter invoiceAdapter;
    AppSingleton appSingleton;
    ProgressDialogClass progressDialogClass;
    AppSharedPreference appSharedPreference;
    FragmentInvoiceBinding fragmentInvoiceBinding;
    ArrayList<LeedsModel> invoiceArrayList;
    ArrayList<LeedsModel> invoiceArrayList1;
    InvoiceRepository invoiceRepository;
    LeedRepository leedRepository;
    InvoicedialogBinding invoicedialogBinding;

    public Invoices_Fragment_Payed() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (fragmentInvoiceBinding == null) {
            fragmentInvoiceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_invoice, container, false);
            invoiceRepository = new InvoiceRepositoryImpl();
            leedRepository = new LeedRepositoryImpl();

            invoiceArrayList1 = new ArrayList<>();
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
        }
        return fragmentInvoiceBinding.getRoot();
    }

    private LeedsModel getModel(int position) {
        return invoiceArrayList1.get(invoiceArrayList1.size() - 1 - position);
    }

    private void onClickListner() {
        try {
            fragmentInvoiceBinding.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), fragmentInvoiceBinding.recyclerView, new RecyclerTouchListener.ClickListener() {

                @Override
                public void onClick(View view, int position) {
                    LeedsModel invoice = getModel(position);
                    Intent intent = new Intent(getActivity(), Invoice_Details_Activity.class);
                    intent.putExtra(INVICES_LEEDS, invoice);
                    startActivity(intent);
                }

                @Override
                public void onLongClick(View view, int position) {
                }

            }));
        }catch (Exception e){
            Log.e("ex",e.getMessage());
        }
        }

        private void getInvoices () {
            String userid = appSharedPreference.getAgeniId();

            //     invoiceArrayList.clear();
            progressDialogClass.showDialog(this.getString(R.string.loading), this.getString(R.string.PLEASE_WAIT));
            invoiceRepository.readInvoicesByAgentId(userid, new CallBack() {
                @Override
                public void onSuccess(Object object) {
                    if (object != null) {
                        invoiceArrayList = (ArrayList<LeedsModel>) object;

                        for (int i = 0; i < invoiceArrayList.size(); i++) {
                            LeedsModel in = invoiceArrayList.get(i);
                            if (in.getStatus().equalsIgnoreCase("PAID")) {
                                invoiceArrayList1.add(in);
                            }
                        }
                        filterList(invoiceArrayList1);
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


        private void filterList (ArrayList < LeedsModel > invoiceArrayList) {
            ArrayList<LeedsModel> arrayList = new ArrayList<>();
            if (invoiceArrayList != null) {
                for (LeedsModel invoice : invoiceArrayList) {
                    // if (!Utility.isEmptyOrNull(invoice.getStatus()) && invoice.getStatus().equalsIgnoreCase(STATUS_SENT))
                    arrayList.add(invoice);
                }
            }
            serAdapter(arrayList);
        }

        private void serAdapter (ArrayList < LeedsModel > invoiceArrayList) {
            if (invoiceArrayList != null) {
                if (invoiceAdapter == null) {
                    invoiceAdapter = new InvoiceAdapter(getActivity(), invoiceArrayList);
                    fragmentInvoiceBinding.recyclerView.setAdapter(invoiceAdapter);
                    onClickListner();
                } else {
                    ArrayList<LeedsModel> arrayList = new ArrayList<>();
                    arrayList.addAll(invoiceArrayList);
                    invoiceAdapter.reload(arrayList);
                }
            }
        }

        private void showInvoiceDialog (LeedsModel invoice){
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
