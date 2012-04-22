package com.greenapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import con.greenapp.R;

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
  private Intent trackIntent;
  private Button.OnClickListener listener;

  /* Called when the activity is first created. */
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
      @Override
      public void onClick(View v) {
        startActivity(quizIntent);
      }
    };
    quizButton.setOnClickListener(listener);

    // Scan button
    scanIntent = new Intent();
    scanIntent.setClass(this, ScanActivity.class);
    listener = new Button.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(scanIntent);
      }
    };
    scanButton.setOnClickListener(listener);

    // Tracker button
    trackIntent = new Intent();
    trackIntent.setClass(this, TrackerActivity.class);
    listener = new Button.OnClickListener() {
      @Override
      public void onClick(View arg0) {
        startActivity(trackIntent);
      }
    };
    trackButton.setOnClickListener(listener);
  }

}