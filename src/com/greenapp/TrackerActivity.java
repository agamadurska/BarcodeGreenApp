package com.greenapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
  private TextView totalTrees;
  private ImageView treeImage;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tracker);

    totalLength = (TextView) findViewById(R.id.totalLength);
    tripLength = (TextView) findViewById(R.id.tripLength);
    startButton = (Button) findViewById(R.id.buttonStartTracker);
    stopButton = (Button) findViewById(R.id.buttonStopTracker);
    totalTrees = (TextView) findViewById(R.id.totalTrees);
    treeImage = (ImageView) findViewById(R.id.treeImage);

    sharedPreferences = getSharedPreferences(GREEN_APP, 0);
    sharedPreferences.registerOnSharedPreferenceChangeListener(
        new SharedPrefListener());
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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("tripDistance", 0);
        editor.commit();
      }
    };
    stopButton.setOnClickListener(listener);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.green_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_preferences:
        startActivity(new Intent(this, PreferencesActivity.class));
        return true;
      case R.id.menu_info:
        showDialog();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void showDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage(R.string.green_info);
    AlertDialog alert = builder.create();
    alert.show();
  }

  private void setImageTree(float tripDistance) {
    int numTree = (int)tripDistance / 180 + 1;
    if (numTree > 7) {
      numTree = 7;
    }
    switch (numTree) {
      case 1: treeImage.setImageResource(R.drawable.green1);
      break;
      case 2: treeImage.setImageResource(R.drawable.green2);
      break;
      case 3: treeImage.setImageResource(R.drawable.green3);
      break;
      case 4: treeImage.setImageResource(R.drawable.green4);
      break;
      case 5: treeImage.setImageResource(R.drawable.green5);
      break;
      case 6: treeImage.setImageResource(R.drawable.green6);
      break;
      default: treeImage.setImageResource(R.drawable.green7);
      break;
    }
  }

  private class SharedPrefListener implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
        String key) {
      if (key.equals("totalDistance")) {
        float totalDistance = sharedPreferences.getFloat("totalDistance", 0);
        long numTree = (int)totalDistance / 360;
        totalDistance /= METERS_IN_MILE;
        totalDistance = (float)(Math.round(totalDistance * 10.0) / 10.0);
        totalLength.setText("Total Distance Travelled: " + totalDistance +
            " (miles)");
        totalTrees.setText("Total Trees Saved: " + numTree);
      } else if (key.equals("tripDistance")) {
        float tripDistance = sharedPreferences.getFloat("tripDistance", 0);
        setImageTree(tripDistance);
        tripDistance /= METERS_IN_MILE;
        tripDistance = (float)(Math.round(tripDistance * 10) / 10.0);
        tripLength.setText("Current Trip Length: " + tripDistance + " (miles)");
      }

    }

  }

}
