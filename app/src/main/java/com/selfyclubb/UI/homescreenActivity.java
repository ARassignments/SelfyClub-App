package com.selfyclubb.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.selfyclubb.ChromeTabs;
import com.selfyclubb.R;

public class homescreenActivity extends AppCompatActivity {
    TextView hub_usersignin,spoke_usersignin;
    TextView tv_click_here,tv_privacy_policy,tv_terms,tv_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);


        hub_usersignin = findViewById(R.id.hub_usersignin);
        spoke_usersignin = findViewById(R.id.spoke_usersignin);

        tv_click_here = findViewById(R.id.tv_click_here);
        tv_privacy_policy = findViewById(R.id.tv_privacy_policy);
        tv_terms = findViewById(R.id.tv_terms);
        tv_contact = findViewById(R.id.tv_contact);


        hub_usersignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreenActivity.this, Hubuser_signinActivity.class));
            }
        });

        spoke_usersignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreenActivity.this, Spokeuser_signinActivity.class));
            }
        });

        tv_click_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(homescreenActivity.this, ChromeTabs.class);
                intent.putExtra("url","https://arunachaltelemed.arogyammedisoft.com/home/adminfunction");
                startActivity(intent);
            }
        });

        tv_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homescreenActivity.this, ChromeTabs.class);
                intent.putExtra("url","https://arogyammedisoft.com/privacy-policy");
                startActivity(intent);
            }
        });

        tv_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homescreenActivity.this, ChromeTabs.class);
                intent.putExtra("url","https://arogyammedisoft.com/terms-%26-conditions");
                startActivity(intent);
            }
        });

        tv_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(homescreenActivity.this, ChromeTabs.class);
//                intent.putExtra("url","mailto:support@arogyammedisoft.com");
//                startActivity(intent);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, "support@arogyammedisoft.com" );
                // intent.putExtra(Intent.EXTRA_SUBJECT, "Subject"); // if you want extra
                // intent.putExtra(Intent.EXTRA_TEXT, "I'm email body."); // if you want extra

                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

//        tv_google_signin_assist = findViewById(R.id.tv_google_signin_assist);
//        tv_google_signin_assist_hindi = findViewById(R.id.tv_google_signin_assist_hindi);
//        tv_privacy_policy = findViewById(R.id.tv_privacy_policy);

//        String text1 = "If you are not assigned access to your Google Id, please request for access by clicking ";
//        String link1 = "<font color='#267f9a'><u>here</u>.</font>";
//        tv_google_signin_assist.setText(Html.fromHtml(text1 + link1));
//
//        String link2 = "<font color='#267f9a'><u>yahaan</u></font>";
//        String text2 = "Yadi appke paas aapka Google Id par access nahi hay, kripaya "+link2+"  click karen. ";
//        tv_google_signin_assist_hindi.setText(Html.fromHtml(text2 + link2));


    }
}