package com.smartloan.smtrick.dezire_loan_admin.view.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.smartloan.smtrick.dezire_loan_admin.R;
import com.smartloan.smtrick.dezire_loan_admin.view.fragements.InvoicesTabFragment;

public class NewLeadSelectActivity extends AppCompatActivity {
    Button btnAccept;
    Button btnhl,btnpl,btnml,btntp,btnbt,btndl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_condition_);

        btnhl = (Button)findViewById(R.id.btn_hl);
        btnpl = (Button)findViewById(R.id.btn_pl);
        btnml = (Button)findViewById(R.id.btn_ml);
        btntp = (Button)findViewById(R.id.btn_tp);
        btnbt = (Button)findViewById(R.id.btn_bt);
        btndl = (Button)findViewById(R.id.btn_dl);


        btnhl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(NewLeadSelectActivity.this, InvoicesTabFragment.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

            }
        });

    }
}
