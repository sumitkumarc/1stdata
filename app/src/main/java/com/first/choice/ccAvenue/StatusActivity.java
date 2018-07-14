package com.first.choice.ccAvenue;

import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;

import com.first.choice.R;


public class StatusActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        Intent mainIntent = getIntent();
        TextView tv4 = (TextView) findViewById(R.id.textView1);
        tv4.setText(mainIntent.getStringExtra("transStatus"));
        showToast(mainIntent.getStringExtra("transStatus"));
    }
    public void showToast(String msg) {
        Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
    }


}
