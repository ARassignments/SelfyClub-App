package com.selfyclubb.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.selfyclubb.ChromeTabs;
import com.selfyclubb.R;

public class Hubuser_signinActivity extends AppCompatActivity {

    LinearLayout text1,text2,text3,text4,text5,text6,text7,text8,text9,text10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hubuser_signin);

        text1 = findViewById(R.id.tv_text1);
        text2 = findViewById(R.id.tv_text2);
        text3 = findViewById(R.id.tv_text3);
        text4 = findViewById(R.id.tv_text4);
        text5 = findViewById(R.id.tv_text5);
        text6 = findViewById(R.id.tv_text6);
        text7 = findViewById(R.id.tv_text7);
        text8 = findViewById(R.id.tv_text8);
        text9 = findViewById(R.id.tv_text9);
        text10 = findViewById(R.id.tv_text10);

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hubuser_signinActivity.this, ChromeTabs.class);
                intent.putExtra("url","https://arunachaltelemedhub.arogyammedisoft.com/home/prepare-eprescription");
                startActivity(intent);
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hubuser_signinActivity.this, ChromeTabs.class);
                intent.putExtra("url","https://arunachaltelemedhub.arogyammedisoft.com/home/view-patient-record");
                startActivity(intent);
            }
        });

        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hubuser_signinActivity.this, ChromeTabs.class);
                intent.putExtra("url","");
                startActivity(intent);
            }
        });
        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hubuser_signinActivity.this, ChromeTabs.class);
                intent.putExtra("url","https://arunachaltelemedhub.arogyammedisoft.com/home/send-to-spoke");
                startActivity(intent);
            }
        });

        text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hubuser_signinActivity.this, ChromeTabs.class);
                intent.putExtra("url","https://arunachaltelemedhub.arogyammedisoft.com/home/remove-patient");
                startActivity(intent);
            }
        });
        text6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hubuser_signinActivity.this, ChromeTabs.class);
                intent.putExtra("url","https://arunachaltelemedhub.arogyammedisoft.com/home/view-print-eprescription");
                startActivity(intent);
            }
        });

        text7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hubuser_signinActivity.this, ChromeTabs.class);
                intent.putExtra("url","https://hangouts.google.com/call/V6yE1l3b7TcHh-eImsVfACEM");
                startActivity(intent);
            }
        });
        text8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hubuser_signinActivity.this, ChromeTabs.class);
                intent.putExtra("url","https://hangouts.google.com/call/bY2KulOGXXymqfQdGxhKACEM");
                startActivity(intent);
            }
        });

        text9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hubuser_signinActivity.this, ChromeTabs.class);
                intent.putExtra("url","https://hangouts.google.com/call/bY2KulOGXXymqfQdGxhKACEM");
                startActivity(intent);
            }
        });
        text10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Hubuser_signinActivity.this, homescreenActivity.class);
//                startActivity(intent);
                finish();
            }
        });

    }
}