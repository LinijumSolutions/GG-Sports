package com.linijumsolutions.gg_sports.screens;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.linijumsolutions.gg_sports.R;
import com.linijumsolutions.gg_sports.screens.MainActivity;


public class WorkoutEndActivity extends Activity {

    private ShareDialog shareDialog;
    private double time, speed;
    private long distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_end);
        shareDialog = new ShareDialog(this);
        Intent intent = getIntent();
        time = intent.getDoubleExtra("time", 0.0);
        speed = intent.getDoubleExtra("speed",0.0);
        distance = intent.getLongExtra("distance", 0L);
    }

    public void onSkipClicked(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onShareClicked(View v){
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("GG-Sport")
                    .setContentDescription(
                            "The 'Hello Facebook' sample  showcases simple Facebook integration")
                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                    .build();

            shareDialog.show(linkContent);
        }
    }
}
