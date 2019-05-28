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

import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.GLOBAL_DATE_FORMATE;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.INVICES_LEEDS;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.STATUS_APPROVED;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.STATUS_REJECTED;

public class Add_Updatelead__bankresult_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinloantype, spinemptype, spinincome;
    Button btupdate, btverify, btnfail, btnnext;
    Invoice invoice;
    ProgressDialogClass progressDialogClass;
    AppSharedPreference appSharedPreference;
    InvoiceRepository invoiceRepository;
    ArrayList<Invoice> leedsModelArrayList;
    EditText etbankname, etdate, etexloanamount, etcname, etaddress, etloantype, etagentname,
            etdissbuss, etcontatct, etalternatecontact, etapproveddate, etpanno, etadharno,
            etoccupation, etincome, etexammount, etgenerated, etdescription;
    String cExloanamount, cApproved, cDissbus, cDate, cBankname, cNmae, cAdress, cLoantype,
            cAgentname, cOffaddress, cContatct, cAltcontatct, cApproveddate, cPanno, cAdharno,
            cIncome, cExamount, lGenby, cDescreption, sploantype, spoccupation;
    TextView txtldate, txtleadid;
    EditText etdissbussAmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_bankresult_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        invoice = (Invoice) getIntent().getSerializableExtra(INVICES_LEEDS);
        progressDialogClass = new ProgressDialogClass(this);
        invoiceRepository = new InvoiceRepositoryImpl();
        appSharedPreference = new AppSharedPreference(this);
        String[] loanType = new String[]{"HL", "LAP"};
        String[] empType = new String[]{"Salaried", "Businessman"};

        btnnext = (Button) findViewById(R.id.buttonupdatenext);
        btverify = (Button) findViewById(R.id.buttonverify);
        btnfail = (Button) findViewById(R.id.buttonfail);


        btnnext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cNmae = etcname.getText().toString();
                cAdress = etaddress.getText().toString();
                cContatct = etcontatct.getText().toString();
                cAltcontatct = etalternatecontact.getText().toString();
                cLoantype = etloantype.getText().toString();
                cAgentname = etagentname.getText().toString();
                cExloanamount = etexloanamount.getText().toString();
                cDate = etdate.getText().toString();
                cBankname = etbankname.getText().toString();
                cApproved = etdissbuss.getText().toString();
                cDissbus = etdissbussAmt.getText().toString();
                cApproveddate = etapproveddate.getText().toString();
                updateLeadDetails(invoice);
                Toast.makeText(Add_Updatelead__bankresult_Activity.this, "Lead Update Successfully", Toast.LENGTH_SHORT).show();

             /*   Intent i = new Intent(Add_Updatelead_C_Details_Activity.this, MainActivity.class);
                i.putExtra(INVICES_LEEDS, invoice);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);*/

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
        etdissbussAmt = (EditText) findViewById(R.id.txtdissamountvalue1);
        etapproveddate = (EditText) findViewById(R.id.txtapproveddate1);
        getdata();


        btverify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setLeedStatus(invoice);
            }
        });


        btnfail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                setLeedStatus2(invoice);

            }
        });


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
            String Approved = invoice.getApprovedLoan();
            String dissbuss = invoice.getDissbussLoan();
            String approveddate = invoice.getApprovedDate();

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

            if (Approved != null) {
                etdissbuss.setText(Approved);
            }

            if (dissbuss != null) {
                etdissbussAmt.setText(dissbuss);
            }

            if (approveddate != null) {
                etapproveddate.setText(approveddate);
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
        invoice.setDissbussLoan(cDissbus);
        invoice.setApprovedDate(cApproveddate);
        updateLeed(invoice.getLeedId(), invoice.getUpdateLeedMap());
    }


    private void updateLeed(String leedId, Map leedsMap) {
        progressDialogClass.showDialog(this.getString(R.string.loading), this.getString(R.string.PLEASE_WAIT));
        invoiceRepository.updateLeed(leedId, leedsMap, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                //  Toast.makeText(Add_Updatelead__bankresult_Activity.this, "", Toast.LENGTH_SHORT).show();
                progressDialogClass.dismissDialog();

                Intent i = new Intent(Add_Updatelead__bankresult_Activity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }

            @Override
            public void onError(Object object) {
                progressDialogClass.dismissDialog();
                Utility.showLongMessage(Add_Updatelead__bankresult_Activity.this, getString(R.string.server_error));
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