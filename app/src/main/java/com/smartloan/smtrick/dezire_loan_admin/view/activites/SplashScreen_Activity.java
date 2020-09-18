package com.smartloan.smtrick.dezire_loan_admin.view.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.smartloan.smtrick.dezire_loan_admin.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen_Activity extends AppCompatActivity implements Animation.AnimationListener {

    long Delay = 4000;
    ImageView splashImage;
    Animation animBounce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_);

        splashImage = (ImageView) findViewById(R.id.splashimage);

        animBounce = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
        animBounce.setAnimationListener(this);

        splashImage.setVisibility(View.VISIBLE);
        splashImage.startAnimation(animBounce);

        // Create a Timer
        Timer RunSplash = new Timer();

        // Task to do when the timer ends
        TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {

                // Close SplashScreenActivity.class
                finish();

                // Start MainActivity.class
                Intent myIntent = new Intent(SplashScreen_Activity.this, Activity_Phone_Verification.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

            }
        };

        // Start the timer
        RunSplash.schedule(ShowSplash, Delay);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation

        // check for zoom in animation
        if (animation == animBounce) {
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
