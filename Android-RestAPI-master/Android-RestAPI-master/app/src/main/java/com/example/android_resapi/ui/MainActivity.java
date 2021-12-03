package com.example.android_resapi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android_resapi.R;


public class MainActivity extends AppCompatActivity {
    final static String TAG = "AndroidAPITest";
    EditText thingShadowURL, getLogsURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thingShadowURL = findViewById(R.id.thingShadowURL);
        getLogsURL = findViewById(R.id.getLogsURL);

        Button listThingsBtn = findViewById(R.id.listThingsBtn);
        listThingsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String urlstr = "https://31pr48s6n1.execute-api.ap-northeast-2.amazonaws.com/prod/devices";
                Log.i(TAG, "listThingsURL=" + urlstr);
                if (urlstr == null || urlstr.equals("")) {
                    Toast.makeText(MainActivity.this, "사물목록 조회 API URI 입력이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, ListThingsActivity.class);
                intent.putExtra("listThingsURL", urlstr);
                startActivity(intent);
                //  new GetThings(MainActivity.this).execute();
                //  new GetThingShadow(MainActivity.this, "MyMKRWiFi1010").execute();

            }
        });

        Button thingShadowBtn = findViewById(R.id.thingShadowBtn);
        thingShadowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlstr = "https://31pr48s6n1.execute-api.ap-northeast-2.amazonaws.com/prod/devices/";
                if (thingShadowURL.getText().toString() == null || thingShadowURL.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "사물상태 이름 입력이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, DeviceActivity.class);
                intent.putExtra("thingShadowURL", urlstr + thingShadowURL.getText().toString());
                startActivity(intent);

            }
        });

        Button listLogsBtn = findViewById(R.id.listLogsBtn);
        listLogsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlstr = "https://31pr48s6n1.execute-api.ap-northeast-2.amazonaws.com/prod/devices/" + getLogsURL.getText().toString() + "/log";
                if (getLogsURL.getText().toString() == null || getLogsURL.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "사물 이름 입력이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, LogActivity.class);
                intent.putExtra("getLogsURL", urlstr);
                startActivity(intent);
            }
        });
    }
}


