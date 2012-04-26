package com.greenapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import con.greenapp.R;


public class NotificationAlarm extends BroadcastReceiver {

  private NotificationManager notificationManager;

  @Override
  public void onReceive(Context context, Intent intent) {
    notificationManager =
        (NotificationManager) context.getSystemService(
            Context.NOTIFICATION_SERVICE);
    Notification notification = new Notification(R.drawable.ic_launcher,
        "GreenApp", System.currentTimeMillis());
    PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
        new Intent(context, TipsActivity.class), 0);
    notification.setLatestEventInfo(context, "GreenApp",
        "Check tip of the day", contentIntent);
    notificationManager.notify(1, notification);
  }

}