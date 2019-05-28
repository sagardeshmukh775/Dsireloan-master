package com.smartloan.smtrick.dezire_loan_admin.view.fragements;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smartloan.smtrick.dezire_loan_admin.R;
import com.smartloan.smtrick.dezire_loan_admin.callback.CallBack;
import com.smartloan.smtrick.dezire_loan_admin.models.Invoice;
import com.smartloan.smtrick.dezire_loan_admin.preferences.AppSharedPreference;
import com.smartloan.smtrick.dezire_loan_admin.repository.InvoiceRepository;
import com.smartloan.smtrick.dezire_loan_admin.repository.impl.InvoiceRepositoryImpl;
import com.smartloan.smtrick.dezire_loan_admin.utilities.Utility;
import com.smartloan.smtrick.dezire_loan_admin.view.activites.MainActivity;
import com.smartloan.smtrick.dezire_loan_admin.view.dialog.ProgressDialogClass;

import java.util.ArrayList;
import java.util.Map;

import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.GLOBAL_DATE_FORMATE;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.INVICES_LEEDS;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.STATUS_APPROVED;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.STATUS_REJECTED;

public class Add_Updatelead__approvedloan_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinloantype, spinemptype, spinincome;
    Button btupdate, btverify, btnsendinvoice, btnnext;
    Invoice invoice;
    ProgressDialogClass progressDialogClass;
    AppSharedPreference appSharedPreference;
    InvoiceRepository invoiceRepository;
    ArrayList<Invoice> leedsModelArrayList;
    private DatabaseReference mDatabase;

    EditText etbankname, etdate, etexloanamount, etcname, etaddress, etloantype, etagentname,
            etdissbuss, etcontatct, etalternatecontact, etbirthdate, etpanno, etadharno,
            etoccupation, etincome, etexammount, etgenerated, etdisburseamt, etpaymentdate, etcommition;

    String cExloanamount, cApproved, cDisbuss, cLeedNumber, cBankname, cNmae, cAdress, cLoantype,
            cAgentname, cAgentid, cContatct, cAltcontatct, cBdate, cPanno, cAdharno,
            cIncome, cExamount, cLeedid, cApproveddate, cPayment, cCommission;

    TextView txtldate, txtleadid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_approvedloan_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        invoice = (Invoice) getIntent().getSerializableExtra(INVICES_LEEDS);
        progressDialogClass = new ProgressDialogClass(this);
        invoiceRepository = new InvoiceRepositoryImpl();
        appSharedPreference = new AppSharedPreference(this);
        String[] loanType = new String[]{"HL", "LAP"};
        String[] empType = new String[]{"Salaried", "Businessman"};

        btnnext = (Button) findViewById(R.id.buttonupdatenext);
        btnsendinvoice = (Button) findViewById(R.id.buttonsendinvoice);

        mDatabase = FirebaseDatabase.getInstance().getReference("invoice");


        btnnext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                cNmae = etcname.getText().toString();
                cAdress = etaddress.getText().toString();
                cContatct = etcontatct.getText().toString();
                cAltcontatct = etalternatecontact.getText().toString();
                cLoantype = etloantype.getText().toString();
                cExloanamount = etexloanamount.getText().toString();
                cAgentname = etagentname.getText().toString();
                cBankname = etbankname.getText().toString();
                cApproved = etdissbuss.getText().toString();
                cPayment = etpaymentdate.getText().toString();
                cCommission = etcommition.getText().toString();
                cDisbuss = etdisburseamt.getText().toString();

                updateLeadDetails(invoice);
                Toast.makeText(Add_Updatelead__approvedloan_Activity.this, "Lead Update Successfully", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Add_Updatelead__approvedloan_Activity.this, MainActivity.class);
                i.putExtra(INVICES_LEEDS, invoice);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

            }
        });

        btnsendinvoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                cNmae = etcname.getText().toString();
                cLeedid = invoice.getLeedId();
                cLeedNumber = invoice.getLeedNumber();
                cAgentid = appSharedPreference.getAgeniId();
                // cAdress = etaddress.getText().toString();
                // cContatct = etcontatct.getText().toString();
                //  cAltcontatct = etalternatecontact.getText().toString();
                //   cLoantype = etloantype.getText().toString();
                   cExloanamount = etexloanamount.getText().toString();
                cAgentname = etagentname.getText().toString();
                //  cBankname = etbankname.getText().toString();
                cApproved = etdissbuss.getText().toString();
                cPayment = etpaymentdate.getText().toString();
                cCommission = etcommition.getText().toString();
                cDisbuss = etdisburseamt.getText().toString();
                cApproveddate = invoice.getApprovedDate();

                generateinvoice(cLeedid);
                Toast.makeText(Add_Updatelead__approvedloan_Activity.this, "Lead Update Successfully", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Add_Updatelead__approvedloan_Activity.this, MainActivity.class);
                i.putExtra(INVICES_LEEDS, invoice);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

            }
        });


        txtleadid = (TextView) findViewById(R.id.textheader);
        etcname = (EditText) findViewById(R.id.txtcamevalue);
        etaddress = (EditText) findViewById(R.id.txtcurrentaddressvalue);
        etcontatct = (EditText) findViewById(R.id.txtcontatctvalue);
        etalternatecontact = (EditText) findViewById(R.id.txtaltcontatctvalue);
        etloantype = (EditText) findViewById(R.id.txtloantypevalue);
        etagentname = (EditText) findViewById(R.id.txtgenbyvalue);
        etexloanamount = (EditText) findViewById(R.id.txtexloanamountvalue);
        etdate = (EditText) findViewById(R.id.txtdatevalue);
        etbankname = (EditText) findViewById(R.id.txtbankvalue);
        etdissbuss = (EditText) findViewById(R.id.txtdissamountvalue);
        etdisburseamt = (EditText) findViewById(R.id.txtdissamountvalue1);
        etpaymentdate = (EditText) findViewById(R.id.txtpaymentdate1);
        etcommition = (EditText) findViewById(R.id.txtcommition1);
        getdata();


    }

    private void getdata() {

        try {

            String leednumber = invoice.getLeedNumber();
            String cname = invoice.getCustomerName();
            String caddress = invoice.getAddress();
            String contact = invoice.getMobileNumber();
            String altcontact = invoice.getAltmobile();
            String loantype = invoice.getLoanType();
            String agentname = invoice.getAgentName();
            String exloanamount = invoice.getExpectedLoanAmount();
            Long ldatetime = invoice.getCreatedDateTimeLong();
            String strdate = Long.toString(ldatetime);
            String sbank = invoice.getBankName();
            String dissbuss = invoice.getApprovedLoan();
            String disburseamt = invoice.getDissbussLoan();
            String paymentdate = invoice.getPaymentDate();
            String commission = invoice.getCommission();

            if (leednumber != null) {
                txtleadid.setText(leednumber);

            }
            if (strdate != null) {
                etdate.setText(Utility.convertMilliSecondsToFormatedDate(invoice.getCreatedDateTimeLong(), GLOBAL_DATE_FORMATE));

            }
            if (cname != null) {
                etcname.setText(cname);

            }
            if (caddress != null) {
                etaddress.setText(caddress);

            }

            if (contact != null) {
                etcontatct.setText(contact);

            }
            if (altcontact != null) {
                etalternatecontact.setText(altcontact);
            }
            if (loantype != null) {
                etloantype.setText(loantype);
            }
            if (agentname != null) {
                etagentname.setText(agentname);
            }
            if (exloanamount != null) {
                etexloanamount.setText(exloanamount);
            }
            if (sbank != null) {
                etbankname.setText(sbank);
            }

            if (dissbuss != null) {
                etdissbuss.setText(dissbuss);
            }

            if (disburseamt != null) {
                etdisburseamt.setText(disburseamt);
            }

            if (paymentdate != null) {
                etpaymentdate.setText(paymentdate);
            }

            if (commission != null) {
                etcommition.setText(commission);
            }

        } catch (Exception e) {
        }

    }


    private void setLeedStatus(Invoice invoice) {
        invoice.setStatus(STATUS_APPROVED);
        updateLeed(invoice.getLeedId(), invoice.getLeedStatusMap1());
    }

    private void setLeedStatus2(Invoice invoice) {
        invoice.setStatus(STATUS_REJECTED);
        updateLeed(invoice.getLeedId(), invoice.getLeedStatusMap1());
    }

    private void updateLeadDetails(Invoice invoice) {

        invoice.setCustomerName(cNmae);
        invoice.setAddress(cAdress);
        invoice.setMobileNumber(cContatct);
        invoice.setAltmobile(cAltcontatct);
        invoice.setLoanType(cLoantype);
        invoice.setAgentName(cAgentname);
        invoice.setExpectedLoanAmount(cExloanamount);
        invoice.setBankName(cBankname);
        invoice.setApprovedLoan(cApproved);
        invoice.setDissbussLoan(cDisbuss);
        invoice.setPaymentDate(cPayment);
        invoice.setCommission(cCommission);
        updateLeed(invoice.getLeedId(), invoice.getUpdateLeedMap());
    }

    private Invoice sendInvoiceDetails() {

        Invoice invoice = new Invoice();
        invoice.setLeedId(cLeedid);
        invoice.setLeedNumber(cLeedNumber);
        invoice.setAgentId(cAgentid);
        invoice.setCustomerName(cNmae);
        invoice.setAgentName(cAgentname);
        invoice.setApprovedDate(cApproveddate);
        invoice.setApprovedLoan(cApproved);
        invoice.setDissbussLoan(cDisbuss);
        invoice.setPaymentDate(cPayment);
        invoice.setCommission(cCommission);
        invoice.setExpectedLoanAmount(cExloanamount);
        invoice.setStatus("PENDING");

        return invoice;
    }


    private void updateLeed(String leedId, Map leedsMap) {
        progressDialogClass.showDialog(this.getString(R.string.loading), this.getString(R.string.PLEASE_WAIT));
        invoiceRepository.updateLeed(leedId, leedsMap, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                Toast.makeText(Add_Updatelead__approvedloan_Activity.this, "", Toast.LENGTH_SHORT).show();
                progressDialogClass.dismissDialog();

                Intent i = new Intent(Add_Updatelead__approvedloan_Activity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }

            @Override
            public void onError(Object object) {
                progressDialogClass.dismissDialog();
                Utility.showLongMessage(Add_Updatelead__approvedloan_Activity.this, getString(R.string.server_error));
            }
        });
    }

    private void generateinvoice(String leedId) {
        Invoice invoice1 = sendInvoiceDetails();
            progressDialogClass.showDialog(this.getString(R.string.leed_In_loading), this.getString(R.string.PLEASE_WAIT));
//        invoiceRepository.createInvoice(invoice1, new CallBack() {
//                @Override
//                public void onSuccess(Object object) {
//                    Toast.makeText(Add_Updatelead__approvedloan_Activity.this, "Send Invoice Successfully", Toast.LENGTH_SHORT).show();
//                    progressDialogClass.dismissDialog();
//                }
//
//                @Override
//                public void onError(Object object) {
//                    progressDialogClass.dismissDialog();
//                    Toast.makeText(Add_Updatelead__approvedloan_Activity.this, "invoice fail", Toast.LENGTH_SHORT).show();
//
//                }
//            });

        String uploadId = mDatabase.push().getKey();
        mDatabase.child(uploadId).setValue(invoice1);

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