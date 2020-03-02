package com.smartloan.smtrick.dezire_loan_admin.view.fragements;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smartloan.smtrick.dezire_loan_admin.R;
import com.smartloan.smtrick.dezire_loan_admin.interfaces.OnFragmentInteractionListener;
import com.smartloan.smtrick.dezire_loan_admin.models.Upload;
import com.smartloan.smtrick.dezire_loan_admin.view.activites.Constants;
import com.smartloan.smtrick.dezire_loan_admin.view.adapters.ImageAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment_Advertise extends Fragment {

    private static int NUM_PAGES = 0;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;


    // NOTE: Removed Some unwanted Boiler Plate Codes
    private OnFragmentInteractionListener mListener;
    private List<Upload> uploads;
    private List<Upload> uploads1;
    ViewPager viewPager;

    private boolean flag = false;

    public Fragment_Advertise() {
    }

    Context context;
    //    Button btnhl, btnpl, btnml, btntp, btnbt, btndl;
    ProgressBar progressBar;
    String abcd = "abcd";
    Animation animBounce;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_adds, container, false);

//        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);

        uploads = new ArrayList<>();
        uploads1 = new ArrayList<>();


        Query query = FirebaseDatabase.getInstance().getReference("Advertise");

        query.addValueEventListener(valueEventListener);

        viewPager = view.findViewById(R.id.viewPager);
        dots = new ImageView[0];

        /*After setting the adapter use the timer */


        // NOTE : We are calling the onFragmentInteraction() declared in the MainActivity
        // ie we are sending "Fragment 1" as title parameter when fragment1 is activated
        if (mListener != null) {
            mListener.onFragmentInteraction("Adds");
        }

        return view;
    }


    ValueEventListener valueEventListener = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            uploads.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Upload upload = postSnapshot.getValue(Upload.class);

                uploads.add(upload);
                int size = uploads.size() - 1;
                uploads1.clear();
                for (int i = size; i >= 0; i--) {
                    uploads1.add(uploads.get(i));
                }

            }
            NUM_PAGES = uploads1.size();
//            showDots();
            ImageAdapter adapter = new ImageAdapter(getContext(), uploads1);
            viewPager.setAdapter(adapter);
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new SliderTimer(), 500, 3000);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };



    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation

        // check for zoom in animation
        if (animation == animBounce) {
        }

    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            if (isVisible()) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() < NUM_PAGES - 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }

}

