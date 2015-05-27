package com.linijumsolutions.gg_sports.screens;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.linijumsolutions.gg_sports.R;
import com.linijumsolutions.gg_sports.screens.MainActivity;

import java.text.DecimalFormat;


public class WorkoutEndActivity extends Activity {

    private ShareDialog shareDialog;
    private double time, speed;
    private float distance;
    private String t, s, d;
    private ProgressBar progressBar;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_end);
        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                onSkipClicked(null);
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException e) {}
        });
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        Intent intent = getIntent();
        time = intent.getDoubleExtra("time", 0.0)/3600;
        t = new DecimalFormat("#.##").format(time);
        speed = intent.getDoubleExtra("speed", 0.0);
        s = new DecimalFormat("#.##").format(speed);
        distance = intent.getFloatExtra("distance", 0f)/1000;
        d = new DecimalFormat("#.##").format(distance);
    }

    public void onSkipClicked(View v){
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void onShareClicked(final View v){
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            progressBar.setVisibility(View.VISIBLE);
            String content = getString(R.string.time) + " " + t + "h, " + getString(R.string.distance_title)+ " "
                    + d + "km, " + getString(R.string.avg_speed)+ " " + s + "km/h.";
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(getString(R.string.shareTitle))
                    .setContentDescription(content)
                    .setContentUrl(Uri.parse("http://ggtryhard.com/"))
                    .setImageUrl(Uri.parse("http://www.logodesignlove.com/images/simple-logos/aol-running-man-logo.jpg"))
                    .build();
            shareDialog.show(linkContent);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
