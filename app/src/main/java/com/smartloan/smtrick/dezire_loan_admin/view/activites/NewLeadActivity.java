package com.smartloan.smtrick.dezire_loan_admin.view.activites;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.smartloan.smtrick.dezire_loan_admin.R;
import com.smartloan.smtrick.dezire_loan_admin.view.fragements.Fragment_GenerateLeads;


public class NewLeadActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{
    Button btnhl,btnpl,btnml,btntp,btnbt,btndl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newleadactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnpl = (Button) findViewById(R.id.btn_pl);
        btnhl = (Button) findViewById(R.id.btn_hl);
        btnml = (Button) findViewById(R.id.btn_ml);
        btntp = (Button) findViewById(R.id.btn_tp);
        btnbt = (Button) findViewById(R.id.btn_bt);
        btndl = (Button) findViewById(R.id.btn_dl);

        btnpl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();

                Bundle bundle = new Bundle();
                bundle.putString("edttext", "From Activity");
                Fragment_GenerateLeads fragobj = new Fragment_GenerateLeads();
                fragobj.setArguments(bundle);
                intent.setClass(NewLeadActivity.this, Fragment_GenerateLeads.class);
                startActivity(intent);
            }
        });
    }






    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
