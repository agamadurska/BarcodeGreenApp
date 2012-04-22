package com.greenapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import con.greenapp.R;


public class NotificationService extends Service {

  private NotificationManager notificationManager;
  private final IBinder binder = new LocalBinder();

  @Override
  public IBinder onBind(Intent arg0) {
    return binder;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    notificationManager =
        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    notificationManager.cancelAll();
  }

  private void showNotification(CharSequence text) {
    Notification notification = new Notification(R.drawable.ic_launcher,
        text, System.currentTimeMillis());
    //notificationManager.notify(id, notification);
  }

  private class LocalBinder extends Binder {

    NotificationService getService() {
      return NotificationService.this;
    }

  }

}
