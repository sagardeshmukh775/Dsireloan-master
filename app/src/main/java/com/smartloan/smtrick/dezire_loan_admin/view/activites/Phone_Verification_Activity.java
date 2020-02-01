package com.smartloan.smtrick.dezire_loan_admin.view.activites;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartloan.smtrick.dezire_loan_admin.R;
import com.smartloan.smtrick.dezire_loan_admin.exception.ExceptionUtil;
import com.smartloan.smtrick.dezire_loan_admin.models.Users;
import com.smartloan.smtrick.dezire_loan_admin.preferences.AppSharedPreference;

public class Phone_Verification_Activity extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;
    private EditText edtname;
    private Button btnlogin;
    private ProgressBar progressBar;
    private AppSharedPreference appSharedPreference;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone__verification_);

        FirebaseApp.initializeApp(this);
        appSharedPreference = new AppSharedPreference(this);
        checkLoginState();

        progressBar = findViewById(R.id.progressbar1);
        mAuth = FirebaseAuth.getInstance();

        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, CountryData.countryNames));

        editText = findViewById(R.id.editTextPhone);
        edtname = findViewById(R.id.editTextName);
        
        btnlogin = findViewById(R.id.buttonLogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                String number = editText.getText().toString().trim();
                final String username = edtname.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }
                if (username.isEmpty()) {
                    edtname.setError("Username is required");
                    edtname.requestFocus();
                    return;
                }

                final String phoneNumber = "+" + code + number;


                DatabaseReference Dref = FirebaseDatabase.getInstance().getReference("users");
                Dref.orderByChild("mobilenumber").equalTo(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.getValue() != null) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                Users upload = postSnapshot.getValue(Users.class);

                                progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(Phone_Verification_Activity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Phone_Verification_Activity.this, MainActivity.class);
                                appSharedPreference.addUserDetails(upload);
                                appSharedPreference.createUserLoginSession();
                                intent.putExtra("mobile",upload.getMobilenumber());
                                intent.putExtra("agentid", upload.getAgentId());
                                startActivity(intent);

                            }



                        }else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Phone_Verification_Activity.this, "Login failed Please Register", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                
                
            }
        });
        

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                String number = editText.getText().toString().trim();
                final String username = edtname.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }
                if (username.isEmpty()) {
                    edtname.setError("Username is required");
                    edtname.requestFocus();
                    return;
                }

                final String phoneNumber = "+" + code + number;


                DatabaseReference Dref = FirebaseDatabase.getInstance().getReference("users");
                Dref.orderByChild("mobilenumber").equalTo(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.getValue() != null) {
                            progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(Phone_Verification_Activity.this, "User Already Exists Please Login", Toast.LENGTH_SHORT).show();

                        }else {
                            progressBar.setVisibility(View.INVISIBLE);

                            Intent intent = new Intent(Phone_Verification_Activity.this, VerifyPhoneActivity.class);
                            intent.putExtra("phonenumber", phoneNumber);
                            intent.putExtra("name", username);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });
            }
        });
    }

    public void checkLoginState() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (appSharedPreference != null && appSharedPreference.getUserLoginStatus()) {
                        if (appSharedPreference.getRegId() != null && appSharedPreference.getUserId() != null) {
                            String id = appSharedPreference.getUserName();

                            loginToApp();
                        }
                    }
                } catch (Exception e) {
                    ExceptionUtil.logException( e);
                }
            }
        });
    }
    private void loginToApp() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
            finish();
        }
    }
}
