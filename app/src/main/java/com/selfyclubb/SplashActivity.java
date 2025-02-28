package com.selfyclubb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;

import com.splunk.mint.Mint;

/**
 * Created by Jiten on 06-01-2018.
 */

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mint.initAndStartSession(this.getApplication(), "13cb7d6f");
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        final FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        Log.e("", "Test");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (!fingerprintManager.isHardwareDetected()) {
//                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
//                } else {
//                    startActivity(new Intent(SplashActivity.this, FingerprintActivity.class));
//                }
//                startActivity(new Intent(SplashActivity.this, ChromeTabs.class));
                //startActivity(new Intent(SplashActivity.this, homescreenActivity.class));
//                startActivity(new Intent(SplashActivity.this, TutorialNewActivity.class));
                Intent intent = new Intent(SplashActivity.this, ChromeTabs.class);
                intent.putExtra("url","https://selfyclub.com/");
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
