package com.smartloan.smtrick.dezire_loan_admin.view.activites;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smartloan.smtrick.dezire_loan_admin.R;
import com.smartloan.smtrick.dezire_loan_admin.exception.ExceptionUtil;
import com.smartloan.smtrick.dezire_loan_admin.interfaces.OnFragmentInteractionListener;
import com.smartloan.smtrick.dezire_loan_admin.models.Users;
import com.smartloan.smtrick.dezire_loan_admin.preferences.AppSharedPreference;
import com.smartloan.smtrick.dezire_loan_admin.utilities.Utility;
import com.smartloan.smtrick.dezire_loan_admin.view.fragements.Admin1_Invoices_TabFragment;
import com.smartloan.smtrick.dezire_loan_admin.view.fragements.Admin_Leed_TabFragment;
import com.smartloan.smtrick.dezire_loan_admin.view.fragements.Admin_Userslist_Fragment;
import com.smartloan.smtrick.dezire_loan_admin.view.fragements.Fragment5;
import com.smartloan.smtrick.dezire_loan_admin.view.fragements.Fragment_Calculator;
import com.smartloan.smtrick.dezire_loan_admin.view.fragements.Fragment_Reports;
import com.smartloan.smtrick.dezire_loan_admin.view.fragements.Fragment_image_upload;
import com.smartloan.smtrick.dezire_loan_admin.view.fragements.InvoicesTabFragment;
import com.squareup.picasso.Picasso;

import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.REQUEST_CODE;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.RESULT_CODE;

public class MainActivity extends AppCompatActivity
        implements OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    private AppSharedPreference appSharedPreference;
    private NavigationView navigationView;
    private Fragment selectedFragement;
    ImageUploadReceiver imageUploadReceiver;
    String mobile;
    String agentId;
    Users username1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appSharedPreference = new AppSharedPreference(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent =getIntent();
        mobile = intent.getStringExtra("mobile");
        agentId = intent.getStringExtra("agentid");
        // NOTE : Just remove the fab button
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //NOTE:  Checks first item in the navigation drawer initially
        navigationView.setCheckedItem(R.id.generateleads);
        updateNavigationHeader();
        //NOTE:  Open fragment1 initially.
        selectedFragement = new Fragment5();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, selectedFragement);
        ft.commit();

        Boolean per = isStoragePermissionGranted();
        if (per){
            //   Toast.makeText(this, "Storage Premission Granted", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Storage Premission Required", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //   Log.v(TAG,"Permission is granted");
                return true;
            } else {

                // Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    return true;
                }else {
                    return false;
                }
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            //  Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        //NOTE: creating fragment object
        Fragment fragment = null;
        if (id == R.id.generateleads) {
            fragment = new Fragment5();
        } else if (id == R.id.Leads) {
            fragment = new InvoicesTabFragment();
        } else if (id == R.id.Reports) {
            fragment = new Fragment_Reports();
        } else if (id == R.id.Calulator) {
            fragment = new Fragment_Calculator();
        } else if (id == R.id.item_logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent logout = new Intent(MainActivity.this,Phone_Verification_Activity.class);
            startActivity(logout);
          //  fragment = new Admin_Userslist_Fragment();
        }
        else if (id == R.id.users) {
            fragment = new Admin_Userslist_Fragment();
        }
        else if (id == R.id.adleeds) {
            fragment = new Admin_Leed_TabFragment();
        }
        else if (id == R.id.offers) {
            fragment = new Fragment_image_upload();
        }
//        else if (id == R.id.invoice) {
//            fragment = new Admin_Invoices_TabFragment();
//        }
        else if (id == R.id.admininvoice) {
            fragment = new Admin1_Invoices_TabFragment();
        }
        //NOTE: Fragment changing code
        selectedFragement = fragment;
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();
        }
        //NOTE:  Closing the drawer after selecting
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); //Ya you can also globalize this variable :P
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String title) {
        // NOTE:  Code to replace the toolbar title based current visible fragment
      //  getSupportActionBar().setTitle(title);
    }

    private void clearDataWithSignOut() {
        FirebaseAuth.getInstance().signOut();
        appSharedPreference.clear();
        logOut();
    }

    private void logOut() {
        Intent intent = new Intent(this, LoginScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void updateNavigationHeader() {
        try {
            View header = navigationView.getHeaderView(0);
            TextView textViewAgentId = (TextView) header.findViewById(R.id.textView_agent_id);
            final TextView textViewUserName = (TextView) header.findViewById(R.id.textView_user_name);
            TextView textViewEmailId = (TextView) header.findViewById(R.id.text_view_email);
            final TextView textViewMobileNumber = (TextView) header.findViewById(R.id.textView_contact);
            ImageView imageViewProfile = (ImageView) header.findViewById(R.id.image_view_profile);
           // Button btneditprofile = (Button) header.findViewById(R.id.buttonviewprofile);

            DatabaseReference Dref = FirebaseDatabase.getInstance().getReference("users");
            Dref.orderByChild("mobilenumber").equalTo(mobile).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                         username1 = appleSnapshot.getValue(Users.class);
                        textViewUserName.setText(username1.getName());
                        textViewMobileNumber.setText(username1.getMobilenumber());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
//                textViewUserName.setText(appSharedPreference.getUserName());
//                textViewEmailId.setText(appSharedPreference.getEmaiId());
//                textViewAgentId.setText(appSharedPreference.getAgeniId());
                //textViewMobileNumber.setText(appSharedPreference.getMobileNo());
                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                Query applesQuery1 = ref1.child("users").orderByChild("mobilenumber").equalTo(user.getPhoneNumber());

                applesQuery1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            Users username = appleSnapshot.getValue(Users.class);
                            textViewUserName.setText(username.getName());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    //    Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
                textViewMobileNumber.setText(user.getPhoneNumber());
            }

            textViewMobileNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, UpdateProfileActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            });

            if (!Utility.isEmptyOrNull(appSharedPreference.getProfileLargeImage())) {
                Picasso.with(this).load(appSharedPreference.getProfileLargeImage()).resize(200, 200).centerCrop().placeholder(R.drawable.imagelogo).into(imageViewProfile);
            } else
                imageViewProfile.setImageResource(R.drawable.imagelogo);


        } catch (Exception ex) {
            ExceptionUtil.logException(ex);
        }
    }

    @Override
    public void changeFragement(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            updateNavigationHeader();
        } else if (selectedFragement != null)
            selectedFragement.onActivityResult(requestCode, resultCode, data);
    }

    private void setReceiver() {
        try {
            IntentFilter filter = new IntentFilter(ImageUploadReceiver.PROCESS_RESPONSE);
            imageUploadReceiver = new ImageUploadReceiver();
            LocalBroadcastManager.getInstance(this).registerReceiver(imageUploadReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        setReceiver();
        super.onStart();
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(imageUploadReceiver);
        super.onStop();
    }

    public class ImageUploadReceiver extends BroadcastReceiver {
        public static final String PROCESS_RESPONSE = "com.smartloan.smtrick.dezire_loan_admin.intent.action.UPDATE_USER_DATA";

        @Override
        public void onReceive(Context context, Intent intent) {
            updateNavigationHeader();
        }
    }
}
