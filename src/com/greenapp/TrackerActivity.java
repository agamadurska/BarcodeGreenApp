package com.greenapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import con.greenapp.R;


public class TrackerActivity extends Activity {

  public static final double METERS_IN_MILE = 1609.344;
  public static final String GREEN_APP = "GreenApp";

  private SharedPreferences sharedPreferences;
  private Button startButton;
  private Button stopButton;
  private Button.OnClickListener listener;
  private TextView totalLength;
  private TextView tripLength;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tracker);

    totalLength = (TextView) findViewById(R.id.totalLength);
    tripLength = (TextView) findViewById(R.id.tripLength);
    startButton = (Button) findViewById(R.id.buttonStartTracker);
    stopButton = (Button) findViewById(R.id.buttonStopTracker);

    sharedPreferences = getSharedPreferences(GREEN_APP, 0);
    if (sharedPreferences.getBoolean("tracking", false)) {
      startButton.setEnabled(false);
      stopButton.setEnabled(true);
    } else {
      startButton.setEnabled(true);
      stopButton.setEnabled(false);
    }

    // Start button
    listener = new Button.OnClickListener() {
      @Override
      public void onClick(View view) {
        startService(new Intent(TrackerActivity.this, TrackerService.class));
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
      }
    };
    startButton.setOnClickListener(listener);

    // Stop button
    listener = new Button.OnClickListener() {
      @Override
      public void onClick(View view) {
        stopService(new Intent(TrackerActivity.this, TrackerService.class));
        startButton.setEnabled(true);
        stopButton.setEnabled(false);

        float totalDistance = sharedPreferences.getFloat("totalDistance", 0);
        float tripDistance = sharedPreferences.getFloat("tripDistance", 0);
        totalDistance /= METERS_IN_MILE;
        totalDistance = Math.round(totalDistance * 10) / 10;
        totalLength.setText("Total Distance Travelled: " + totalDistance +
            " (miles)");
      }
    };
    stopButton.setOnClickListener(listener);

    float tripDistance = sharedPreferences.getFloat("tripDistance", 0);
    tripDistance /= METERS_IN_MILE;
    tripDistance = Math.round(tripDistance * 10) / 10;
    tripLength.setText("Current Trip Length: " + tripDistance + " (miles)");
  }

}
