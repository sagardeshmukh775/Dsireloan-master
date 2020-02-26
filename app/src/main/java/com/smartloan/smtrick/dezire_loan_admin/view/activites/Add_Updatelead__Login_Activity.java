package com.smartloan.smtrick.dezire_loan_admin.view.activites;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.smartloan.smtrick.dezire_loan_admin.R;
import com.smartloan.smtrick.dezire_loan_admin.callback.CallBack;
import com.smartloan.smtrick.dezire_loan_admin.models.Invoice;
import com.smartloan.smtrick.dezire_loan_admin.models.LeedsModel;
import com.smartloan.smtrick.dezire_loan_admin.preferences.AppSharedPreference;
import com.smartloan.smtrick.dezire_loan_admin.repository.InvoiceRepository;
import com.smartloan.smtrick.dezire_loan_admin.repository.impl.InvoiceRepositoryImpl;
import com.smartloan.smtrick.dezire_loan_admin.utilities.Utility;
import com.smartloan.smtrick.dezire_loan_admin.view.dialog.ProgressDialogClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.CALANDER_DATE_FORMATE;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.GLOBAL_DATE_FORMATE;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.INVICES_LEEDS;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.STATUS_CLOSE;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.STATUS_LOGIN;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.STATUS_REJECTED;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.STATUS_SANCTION;

public class Add_Updatelead__Login_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinloantype, spinemptype, spinincome;
    Button btupdate, btverify, btnfail, btnnext, btClose;
    LeedsModel invoice;
    ProgressDialogClass progressDialogClass;
    AppSharedPreference appSharedPreference;
    InvoiceRepository invoiceRepository;
    ArrayList<LeedsModel> leedsModelArrayList;
    EditText etbankname, etdate, etexloanamount, etcname, etaddress, etloantype, etagentname,
            etApprovedamt, etcontatct, etalternatecontact, etapproveddate, etpanno, etadharno,
            etoccupation, etincome, etexammount, etgenerated, etdescription,etNote;
    String cExloanamount, cApproved, cDissbus, cDate, cBankname, cNmae, cAdress, cLoantype,
            cAgentname, cOffaddress, cContatct, cAltcontatct, cApproveddate, cPanno, cAdharno,
            cIncome, cExamount, lGenby, cDescreption, sploantype, spoccupation,cNote;
    TextView txtldate, txtleadid;
    EditText etdissbussAmt;

    int fromYear, fromMonth, fromDay;
    int toYear, toMonth, toDay;
    long fromDate, toDate;

    ArrayList<String> NotesList;


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_update_login_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_c_details);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        invoice = (LeedsModel) getIntent().getSerializableExtra(INVICES_LEEDS);
        progressDialogClass = new ProgressDialogClass(this);
        invoiceRepository = new InvoiceRepositoryImpl();
        appSharedPreference = new AppSharedPreference(this);
        String[] loanType = new String[]{"HL", "LAP"};
        String[] empType = new String[]{"Salaried", "Businessman"};

        NotesList = new ArrayList<>();

        btnnext = (Button) findViewById(R.id.buttonupdatenext);
        btClose = (Button) findViewById(R.id.buttonClose);
        btverify = (Button) findViewById(R.id.buttonverify);
        btverify.setText("SANCTION");
        btnfail = (Button) findViewById(R.id.buttonfail);


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
        etApprovedamt = (EditText) findViewById(R.id.txtapprovedamountvalue);
        etdissbussAmt = (EditText) findViewById(R.id.txtdissamountvalue1);
        etapproveddate = (EditText) findViewById(R.id.txtapproveddate1);
        etNote = (EditText) findViewById(R.id.txtnotevalue);

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
                cApproved = etApprovedamt.getText().toString();
                cDissbus = etdissbussAmt.getText().toString();
                cApproveddate = etapproveddate.getText().toString();
                cNote = etNote.getText().toString();

                if (!TextUtils.isEmpty(cNote)) {
                    NotesList.add(cNote);
                }

                updateLeadDetails(invoice);
                Toast.makeText(Add_Updatelead__Login_Activity.this, "Lead Update Successfully", Toast.LENGTH_SHORT).show();

             /*   Intent i = new Intent(Add_Updatelead_C_Details_Activity.this, MainActivity.class);
                i.putExtra(INVICES_LEEDS, invoice);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);*/

            }
        });


        getdata();

        setFromCurrentDate();
        etapproveddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker = new DatePickerDialog(Add_Updatelead__Login_Activity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        SimpleDateFormat sdf = new SimpleDateFormat(CALANDER_DATE_FORMATE, Locale.FRANCE);
                        String formatedDate = sdf.format(myCalendar.getTime());
                        etapproveddate.setText(formatedDate);
                        fromDay = selectedday;
                        fromMonth = selectedmonth;
                        fromYear = selectedyear;
                        fromDate = Utility.convertFormatedDateToMilliSeconds(formatedDate, CALANDER_DATE_FORMATE);

                    }
                }, fromYear, fromMonth, fromDay);
                mDatePicker.show();
            }
        });

        btverify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final Dialog dialog = new Dialog(Add_Updatelead__Login_Activity.this);
                dialog.setContentView(R.layout.confermationdialog);
                Button Yes = (Button) dialog.findViewById(R.id.dialogButtonYes);
                Button No = (Button) dialog.findViewById(R.id.dialogButtonNo);

                Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setLeedStatus(invoice);
                    }
                });
                No.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });


        btnfail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final Dialog dialog = new Dialog(Add_Updatelead__Login_Activity.this);
                dialog.setContentView(R.layout.confermationdialog);
                Button Yes = (Button) dialog.findViewById(R.id.dialogButtonYes);
                Button No = (Button) dialog.findViewById(R.id.dialogButtonNo);

                Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setLeedStatus2(invoice);
                    }
                });
                No.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();


            }
        });
        btClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final Dialog dialog = new Dialog(Add_Updatelead__Login_Activity.this);
                dialog.setContentView(R.layout.confermationdialog);
                Button Yes = (Button) dialog.findViewById(R.id.dialogButtonYes);
                Button No = (Button) dialog.findViewById(R.id.dialogButtonNo);

                Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setLeedStatus3(invoice);
                    }
                });
                No.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();


            }
        });

    }

    private void setFromCurrentDate() {
        Calendar mcurrentDate = Calendar.getInstance();
        fromYear = mcurrentDate.get(Calendar.YEAR);
        fromMonth = mcurrentDate.get(Calendar.MONTH);
        fromDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
    }

    private void getdata() {

        try {

            String leednumber = invoice.getLeedNumber();
            String cname = invoice.getCustomerName();
            String caddress = invoice.getAddress();
            String contact = invoice.getMobileNumber();
            String altcontact = invoice.getaltmobile();
            String loantype = invoice.getLoanType();
            String agentname = invoice.getAgentName();
            String exloanamount = invoice.getExpectedLoanAmount();
            Long ldatetime = invoice.getCreatedDateTimeLong();
            String strdate = Long.toString(ldatetime);
            String sbank = invoice.getBankName();
            String Approved = invoice.getApprovedLoan();
            String dissbuss = invoice.getDissbussloan();
            String approveddate = invoice.getApprovedDate();

            if (invoice.getNotes() != null){
                NotesList = invoice.getNotes();
            }

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
                etApprovedamt.setText(Approved);
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


    private void setLeedStatus(LeedsModel invoice) {
        invoice.setStatus(STATUS_SANCTION);
        updateLeed(invoice.getLeedId(), invoice.getLeedStatusMap1());
    }

    private void setLeedStatus2(LeedsModel invoice) {
        invoice.setStatus(STATUS_REJECTED);
        updateLeed(invoice.getLeedId(), invoice.getLeedStatusMap1());
    }
    private void setLeedStatus3(LeedsModel invoice) {
        invoice.setStatus(STATUS_CLOSE);
        updateLeed(invoice.getLeedId(), invoice.getLeedStatusMap1());
    }

    private void updateLeadDetails(LeedsModel invoice) {

        invoice.setCustomerName(cNmae);
        invoice.setAddress(cAdress);
        invoice.setMobileNumber(cContatct);
        invoice.setAltmobile(cAltcontatct);
        invoice.setLoanType(cLoantype);
        invoice.setAgentName(cAgentname);
        invoice.setExpectedLoanAmount(cExloanamount);
        invoice.setBankName(cBankname);
        invoice.setApprovedLoan(cApproved);
        invoice.setdissbussloan(cDissbus);
        invoice.setApprovedDate(cApproveddate);
        if(NotesList != null) {
            invoice.setNotes(NotesList);
        }
        updateLeed(invoice.getLeedId(), invoice.getLeedStatusMap());
    }


    private void updateLeed(String leedId, Map leedsMap) {
        progressDialogClass.showDialog(this.getString(R.string.loading), this.getString(R.string.PLEASE_WAIT));
        invoiceRepository.updateLeed(leedId, leedsMap, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                //  Toast.makeText(Add_Updatelead__bankresult_Activity.this, "", Toast.LENGTH_SHORT).show();
                progressDialogClass.dismissDialog();

                Intent i = new Intent(Add_Updatelead__Login_Activity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }

            @Override
            public void onError(Object object) {
                progressDialogClass.dismissDialog();
                Utility.showLongMessage(Add_Updatelead__Login_Activity.this, getString(R.string.server_error));
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