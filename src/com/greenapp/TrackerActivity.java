package com.greenapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import con.greenapp.R;


public class TrackerActivity extends Activity {

  private Button startButton;
  private Button stopButton;
  private Button.OnClickListener listener;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tracker);

    // Start button
    startButton = (Button) findViewById(R.id.buttonStartTracker);
    listener = new Button.OnClickListener() {
      @Override
      public void onClick(View view) {
        startService(new Intent(TrackerActivity.this, TrackerService.class));
      }
    };
    startButton.setOnClickListener(listener);

    // Stop button
    stopButton = (Button) findViewById(R.id.buttonStopTracker);
    listener = new Button.OnClickListener() {
      @Override
      public void onClick(View view) {
        stopService(new Intent(TrackerActivity.this, TrackerService.class));
      }
    };
    stopButton.setOnClickListener(listener);
  }

}
