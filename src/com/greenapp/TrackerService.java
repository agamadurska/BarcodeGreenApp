package com.greenapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import con.greenapp.R;


public class TrackerService extends Service {

  public static final long MIN_TIME_MS = 120000;
  public static final long MIN_DISTANCE_METERS = 20;
  public static final float MIN_ACCURACY_METERS = 50;

  private static DatabaseHelper dbHelper;
  private final IBinder binder = new LocalBinder();
  private LocationManager locationManager;
  private LocationListener locationListener;
  private int lastStatus = 0;
  private Location lastLocation = null;
  private double distance = 0;
  private NotificationManager notificationManager;

  @Override
  public IBinder onBind(Intent intent) {
    return binder;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    notificationManager =
        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    dbHelper = new DatabaseHelper(this);
    startTrackerService();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    stopTrackerService();
  }

  private void showNotification(CharSequence text) {
    Notification notification = new Notification(R.drawable.ic_launcher, text,
        System.currentTimeMillis());
    PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
        new Intent(this, TrackerService.class), 0);
    notification.setLatestEventInfo(this, text, text, contentIntent);
    notificationManager.notify(1, notification);
  }

  private void startTrackerService() {
    locationManager =
        (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    locationListener = new GreenLocationListener();
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
        MIN_TIME_MS, MIN_DISTANCE_METERS, locationListener);
  }

  private void stopTrackerService() {
    locationManager.removeUpdates(locationListener);
  }

  private class GreenLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location location) {
      if (location != null) {
        if (location.hasAccuracy() &&
            location.getAccuracy() <= MIN_ACCURACY_METERS) {
          dbHelper.addLocation(location);
          if (lastLocation != null) {
            distance += lastLocation.distanceTo(location);
          }
          lastLocation = location;
        }
      }
    }

    @Override
    public void onProviderDisabled(String provider) {
      CharSequence text = getText(R.string.no_gps);
      showNotification(text);
    }

    @Override
    public void onProviderEnabled(String provider) {
      // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
      if (status == LocationProvider.AVAILABLE) {

      }
      if (status == LocationProvider.TEMPORARILY_UNAVAILABLE) {

      }
      if (status == LocationProvider.OUT_OF_SERVICE) {

      }
      lastStatus = status;
    }

  }

  private class LocalBinder extends Binder {

    TrackerService getService() {
      return TrackerService.this;
    }

  }

}
