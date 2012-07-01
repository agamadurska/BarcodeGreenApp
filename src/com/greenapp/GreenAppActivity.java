package com.greenapp;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.greenapp.R;

/**
 * Main menu activity
 *
 * @author aga
 */
public class GreenAppActivity extends Activity {

  private Button trackButton;
  private Button quizButton;
  private Button tipsButton;
  private Intent quizIntent;
  private Intent trackIntent;
  private Intent tipsIntent;
  private Button.OnClickListener listener;
  private SharedPreferences sharedPreferences;

  /* Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    trackButton = (Button) findViewById(R.id.buttonTracker);
    quizButton = (Button) findViewById(R.id.buttonQuiz);
    tipsButton = (Button) findViewById(R.id.buttonTips);

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

    // Tips button
    tipsIntent = new Intent();
    tipsIntent.setClass(this, TipsActivity.class);
    listener = new Button.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(tipsIntent);
      }
    };
    tipsButton.setOnClickListener(listener);


    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    sharedPreferences.registerOnSharedPreferenceChangeListener(
        new SharedPrefListener());

    if (sharedPreferences.getBoolean("do_notif", false) &&
        !sharedPreferences.getBoolean("notif_started", false)) {
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putBoolean("notif_started", true);
      editor.commit();
      startNotifications(Long.valueOf(
          sharedPreferences.getString("notif_interval", "900000")));
    }
  }

  private class SharedPrefListener implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
        String key) {

      if (key.equals("notif_started")) {
        // Start notifications
        startNotifications(Long.valueOf(
            sharedPreferences.getString("notif_interval", "900000")));
      }

      if (key.equals("do_notif")) {
        if (sharedPreferences.getBoolean("do_notif", false)) {
          startNotifications(Long.valueOf(
              sharedPreferences.getString("notif_interval", "900000")));
        } else {
          stopNotifications();
        }
      }

      if (key.equals("notif_interval")) {
        if (sharedPreferences.getBoolean("do_notif", false)) {
          stopNotifications();
          startNotifications(Long.valueOf(
              sharedPreferences.getString("notif_interval", "900000")));
        }
      }
    }
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

  private void startNotifications(long interval) {
    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    PendingIntent displayIntent = PendingIntent.getBroadcast(this,
        0, new Intent(this, NotificationAlarm.class),
        PendingIntent.FLAG_UPDATE_CURRENT);
    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
        Calendar.getInstance().getTimeInMillis() + interval,
        interval, displayIntent);
  }

  private void stopNotifications() {
    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    alarmManager.cancel(PendingIntent.getBroadcast(this,
        0, new Intent(this, NotificationAlarm.class),
        PendingIntent.FLAG_UPDATE_CURRENT));
  }

}
