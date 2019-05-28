package com.smartloan.smtrick.dezire_loan_admin.view.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.smartloan.smtrick.dezire_loan_admin.R;
import com.smartloan.smtrick.dezire_loan_admin.callback.CallBack;
import com.smartloan.smtrick.dezire_loan_admin.models.Invoice;
import com.smartloan.smtrick.dezire_loan_admin.preferences.AppSharedPreference;
import com.smartloan.smtrick.dezire_loan_admin.repository.InvoiceRepository;
import com.smartloan.smtrick.dezire_loan_admin.repository.impl.InvoiceRepositoryImpl;
import com.smartloan.smtrick.dezire_loan_admin.utilities.Utility;
import com.smartloan.smtrick.dezire_loan_admin.view.dialog.ProgressDialogClass;

import java.util.ArrayList;
import java.util.Map;

import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.INVICES_LEEDS;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.STATUS_PAID;

public class Invoice_Details_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinloantype, spinemptype, spinincome;
    Button btupdate, btverify, btcancel,btnnext;
  Invoice invoice;
    ProgressDialogClass progressDialogClass;
    AppSharedPreference appSharedPreference;
    InvoiceRepository invoiceRepository;
    ArrayList<Invoice> leedsModelArrayList;
    EditText etpaymentdate,etcname,  etagentname, etapproveddate,etsanctionamt,etdisburseamt,etcommission;
    String cExloanamount,cDate,cparents,cNmae, cAdress, cLoantype,cAgentname,cOffaddress, cContatct, cAltcontatct, cBdate, cPanno, cAdharno, cIncome, cExamount, lGenby, cDescreption, sploantype, spoccupation;
    TextView txtldate, txtleadid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_details_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        invoice = (Invoice) getIntent().getSerializableExtra(INVICES_LEEDS);
        progressDialogClass = new ProgressDialogClass(this);
        invoiceRepository = new InvoiceRepositoryImpl();
        appSharedPreference = new AppSharedPreference(this);
        String[] loanType = new String[]{"HL", "LAP"};
        String[] empType = new String[]{"Salaried", "Businessman"};

        btnnext = (Button) findViewById(R.id.buttonpay);
      //  btverify = (Button) findViewById(R.id.buttonverify);
//
//        btnnext.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                cNmae=etcname.getText().toString();
//                cAgentname=etagentname.getText().toString();
//
//
//
//                updateLeadDetails(invoice);
//                Toast.makeText(Invoice_Details_Activity.this, "Lead Update Successfully", Toast.LENGTH_SHORT).show();
//
//             /*   Intent i = new Intent(Add_Updatelead_C_Details_Activity.this, MainActivity.class);
//                i.putExtra(INVICES_LEEDS, invoice);
//                startActivity(i);
//                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);*/
//
//            }
//        });


        txtleadid = (TextView) findViewById(R.id.textheader);
        etcname = (EditText) findViewById(R.id.txtcamevalue);
        etagentname = (EditText) findViewById(R.id.txtgenbyvalue);
        etapproveddate = (EditText) findViewById(R.id.txtdatevalue);
        etsanctionamt = (EditText) findViewById(R.id.txtsanctionamountvalue);
        etdisburseamt = (EditText) findViewById(R.id.txtdisburseamountvalue);
        etcommission = (EditText) findViewById(R.id.txtcommitionvalue);
        etpaymentdate = (EditText) findViewById(R.id.txtpaymentdatevalue);
        getdata();



        btnnext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setLeedStatus(invoice);
            }
        });

/*
        btcancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(TL_Updatelead_C_Details_Activity.this, MainActivity_telecaller.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

            }
        });
*/

    }//end of oncreate



    private void getdata() {

        try {

            String leednumber = invoice.getLeedNumber();
            String cname = invoice.getCustomerName();
            String agetname = invoice.getAgentName();
            String approveddate = invoice.getApprovedDate();
            String sanctioamt = invoice.getApprovedLoan();
            String disburseamt = invoice.getDissbussLoan();
            String commission = invoice.getCommission();
            String paymentdate = invoice.getPaymentDate();



            if(leednumber != null)
             {
                 txtleadid.setText(leednumber);

             }
           if(cname != null)
             {
                 etcname.setText(cname);

             } if(agetname != null)
             {
                 etagentname.setText(agetname);

             }

            if(approveddate != null)
             {
                 etapproveddate.setText(approveddate);

             }
             if(sanctioamt != null)
             {
                 etsanctionamt.setText(sanctioamt);
             }
            if(disburseamt != null)
            {
                etdisburseamt.setText(disburseamt);
            }
            if(commission != null)
            {
                etcommission.setText(commission);
            }
            if(paymentdate != null)
            {
                etpaymentdate.setText(paymentdate);
            }

        }catch (Exception e){}

    }


    private void setLeedStatus(Invoice invoice) {
        invoice.setStatus(STATUS_PAID);
        updateLeed(invoice.getLeedId(), invoice.getLeedStatusMap1());
    }


//    private void updateLeadDetails(Invoice invoice) {
//
//        invoice.setCustomerName(cNmae);
//        invoice.setAddress(cAdress);
//        invoice.setMobileNumber(cContatct);
//        invoice.setAltmobile(cAltcontatct);
//        invoice.setLoanType(cLoantype);
//        invoice.setAgentName(cAgentname);
//        invoice.setExpectedLoanAmount(cExloanamount);
//        updateLeed(invoice.getLeedId(), invoice.getUpdateLeedMap());
//    }



    private void updateLeed(String leedId, Map leedsMap) {
        progressDialogClass.showDialog(this.getString(R.string.loading), this.getString(R.string.PLEASE_WAIT));
        invoiceRepository.updateInvoice(leedId, leedsMap, new CallBack() {
            @Override
            public void onSuccess(Object object) {
               Toast.makeText(Invoice_Details_Activity.this, "Lead Verify Successfully", Toast.LENGTH_SHORT).show();
                progressDialogClass.dismissDialog();

                Intent i = new Intent(Invoice_Details_Activity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }

            @Override
            public void onError(Object object) {
                progressDialogClass.dismissDialog();
                Utility.showLongMessage(Invoice_Details_Activity.this, getString(R.string.server_error));
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        // sploantype = spinloantype.getSelectedItem().toString();
        // spoccupation = spinemptype.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


}