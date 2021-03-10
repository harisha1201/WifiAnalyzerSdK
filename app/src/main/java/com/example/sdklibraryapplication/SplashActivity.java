package com.example.sdklibraryapplication;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.act.sdk.wifianalyser.view.SdkLoginActivity;

public class SplashActivity extends Activity {
    protected int splashTime = 3000;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ImageView image = (ImageView) findViewById(R.id.img_splash_logo);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animation_zoom);
        image.startAnimation(animation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               Intent intent = new Intent(SplashActivity.this, SdkLoginActivity.class);
         //    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                if (Build.VERSION.SDK_INT > 20) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }

                finish();
            }
        }, splashTime);

    }
}
