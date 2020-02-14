package com.smartloan.smtrick.dezire_loan_admin.view.fragements;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.smartloan.smtrick.dezire_loan_admin.R;
import com.smartloan.smtrick.dezire_loan_admin.interfaces.OnFragmentInteractionListener;
import com.smartloan.smtrick.dezire_loan_admin.view.adapters.ViewPagerAdapter;

public class Admin_Leed_TabFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    public Admin_Leed_TabFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mListener != null) {



            mListener.onFragmentInteraction("Leeds");


        }

        View view = inflater.inflate(R.layout.view_pager_tab_layout, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragement(new Admin_generate_leed_Fragment(), "Generated");
        viewPagerAdapter.addFragement(new Admin__verified_leed_Fragment(), "Veryfied");
        viewPagerAdapter.addFragement(new Admin__Inprocess_leed_Fragment(), "In_Process");
        viewPagerAdapter.addFragement(new Admin__DocPickup_leed_Fragment(), "Dock_Pickup");
        viewPagerAdapter.addFragement(new Admin__Login_leed_Fragment(), "Login");
        viewPagerAdapter.addFragement(new Admin__Sanction_leed_Fragment(), "Sanction");
//        viewPagerAdapter.addFragement(new Admin__Submitted_for_Disbuss_leed_Fragment(), "Full_Disbuss");
        viewPagerAdapter.addFragement(new Admin__Partialy_disbuss_leed_Fragment(), "Partialy_Disbuss");
        viewPagerAdapter.addFragement(new Admin__Full_disbuss_leed_Fragment(), "Full_Disbuss");
        viewPagerAdapter.addFragement(new Admin__submited_leed_Fragment(), "Submited");
        viewPagerAdapter.addFragement(new Admin__approved_leed_Fragment(), "Approved");
        viewPagerAdapter.addFragement(new Admin__Rejected_leed_Fragment(), "Rejected");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
//        tabLayout.setTabMode(1);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            // NOTE: This is the part that usually gives you the error
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



}
