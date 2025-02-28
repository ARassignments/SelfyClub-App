package com.selfyclubb.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.selfyclubb.ChromeTabs;
import com.selfyclubb.R;

public class Spokeuser_signinActivity extends AppCompatActivity {

    LinearLayout tv_clinic_text1,tv_clinic_text2,tv_clinic_text3,tv_clinic_text4,tv_clinic_text5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spokeuser_signin);

        tv_clinic_text1 = findViewById(R.id.tv_clinic_text1);
        tv_clinic_text2 = findViewById(R.id.tv_clinic_text2);
        tv_clinic_text3 = findViewById(R.id.tv_clinic_text3);
        tv_clinic_text4 = findViewById(R.id.tv_clinic_text4);
        tv_clinic_text5 = findViewById(R.id.tv_clinic_text5);

        tv_clinic_text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Spokeuser_signinActivity.this, ChromeTabs.class);
                intent.putExtra("url","https://arunachaltelehmedsp.arogyammedisoft.com/clinic-functions-subcentres-chc-bordumsa-changlang-district");
                startActivity(intent);
            }
        });
        tv_clinic_text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Spokeuser_signinActivity.this, ChromeTabs.class);
                intent.putExtra("url","https://arunachaltelehmedsp.arogyammedisoft.com/clinic-functions-subcentres-chc-miao-changlang-district");
                startActivity(intent);
            }
        });
        tv_clinic_text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Spokeuser_signinActivity.this, ChromeTabs.class);
                intent.putExtra("url","https://arunachaltelehmedsp.arogyammedisoft.com/clinic-functions-subcentres-phc-yatdam-changlang-district");
                startActivity(intent);
            }
        });
        tv_clinic_text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Spokeuser_signinActivity.this, ChromeTabs.class);
                intent.putExtra("url","https://arunachaltelehmedsp.arogyammedisoft.com/clinic-functions-subcentres-phc-diyun-changlang-district");
                startActivity(intent);
            }
        });
        tv_clinic_text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Spokeuser_signinActivity.this, ChromeTabs.class);
//                intent.putExtra("url","");
//                startActivity(intent);
                finish();
            }
        });
    }
}