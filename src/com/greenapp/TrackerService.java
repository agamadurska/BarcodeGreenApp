package com.greenapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import con.greenapp.R;


public class TrackerService extends Service {

  public static final String GREEN_APP = "GreenApp";
  public static final long MIN_TIME_MS = 2000;
  public static final long MIN_DISTANCE_METERS = 20;
  public static final float MIN_ACCURACY_METERS = 100;

  private final IBinder binder = new LocalBinder();
  private LocationManager locationManager;
  private LocationListener locationListener;
  private Location lastLocation = null;
  private NotificationManager notificationManager;
  private SharedPreferences sharedPreferences;
  private float totalDistance = 0;
  private float tripDistance = 0;

  @Override
  public IBinder onBind(Intent intent) {
    return binder;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    notificationManager =
        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    getDistances();
    setTracking(true);
    startTrackerService();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    putDistances();
    setTracking(false);
    stopTrackerService();
  }

  private void setTracking(boolean tracking) {
    sharedPreferences = getSharedPreferences(GREEN_APP, 0);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean("tracking", tracking);
    editor.commit();
  }

  private void getDistances() {
    sharedPreferences = getSharedPreferences(GREEN_APP, 0);
    totalDistance = sharedPreferences.getFloat("totalDistance", 0);
    tripDistance = 0;
  }

  private void putDistances() {
    sharedPreferences = getSharedPreferences(GREEN_APP, 0);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putFloat("totalDistance", totalDistance);
    editor.putFloat("tripDistance", tripDistance);
    editor.commit();
  }

  private void showNotification(CharSequence text) {
    Notification notification = new Notification(R.drawable.ic_launcher,
        "GreenApp", System.currentTimeMillis());
    PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
        new Intent(this, TrackerService.class), 0);
    notification.setLatestEventInfo(this, "GreenApp", text, contentIntent);
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
          if (lastLocation != null) {
            float distance = lastLocation.distanceTo(location);
            tripDistance += distance;
            totalDistance += distance;
            putDistances();
          }
          lastLocation = location;
        }
      }
    }

    @Override
    public void onProviderDisabled(String provider) {
      showNotification(getText(R.string.no_gps));
    }

    @Override
    public void onProviderEnabled(String provider) {
      // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

  }

  private class LocalBinder extends Binder {

    TrackerService getService() {
      return TrackerService.this;
    }

  }

}
