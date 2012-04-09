package com.greenapp;

import java.util.LinkedList;
import java.util.List;

import con.greenapp.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Main menu activity
 *
 * @author aga
 */
public class GreenAppActivity extends Activity {
	private Button scanButton;
	private Intent scanIntent = new Intent();
    private Button.OnClickListener mScan;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        scanButton = (Button) findViewById(R.id.buttonScan);
        scanIntent.setClass(this, ScanActivity.class);
        mScan = new Button.OnClickListener() {
            public void onClick(View v) {
            	startActivity(scanIntent);
            }
        };
        scanButton.setOnClickListener(mScan);
    }
}