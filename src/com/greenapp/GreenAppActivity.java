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
	private Button trackButton;
	private Button quizButton;
	private Intent scanIntent;
	private Intent quizIntent;
    private Button.OnClickListener listener;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        scanButton = (Button) findViewById(R.id.buttonScan);
        trackButton = (Button) findViewById(R.id.buttonTracker);
        quizButton = (Button) findViewById(R.id.buttonQuiz);

        // Quiz button
        quizIntent = new Intent();
        quizIntent.setClass(this, QuizActivity.class);
        listener = new Button.OnClickListener() {
            public void onClick(View v) {
            	startActivity(quizIntent);
            }
        };
        quizButton.setOnClickListener(listener);
        
        // Scan button
        scanIntent = new Intent();
        scanIntent.setClass(this, ScanActivity.class);
        listener = new Button.OnClickListener() {
            public void onClick(View v) {
            	startActivity(scanIntent);
            }
        };
        scanButton.setOnClickListener(listener);
    }
}