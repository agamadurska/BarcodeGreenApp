package com.greenapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

  public static final String DB_NAME = "greenAppDB";

  public static final String SCANNED_ITEMS_TABLE = "ScannedItems";
  public static final String COL_ORDER = "ScannedItemOrder";
  public static final String COL_UPC = "ScannedItemUPC";
  public static final String COL_DESCRIPTION = "ScannedItemDesc";
  public static final String COL_COUNT = "ScannedItemCount";

  public static final String POINTS_TABLE = "LocationPoints";
  public static final String COL_TIMESTAMP = "GmtTimeStamp";
  public static final String COL_LATITUDE = "Latitude";
  public static final String COL_LONGITUDE = "Longitude";
  public static final String COL_ACCURACY = "Accuracy";
  public static final String COL_SPEED = "Speed";

  public static final String TRIPS_TABLE = "Trips";

  private final DateFormat timestampFormat =
      new SimpleDateFormat("yyyyMMddHHmmss");

  public DatabaseHelper(Context context) {
    super(context, DB_NAME , null, 1);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE IF NOT EXISTS" + SCANNED_ITEMS_TABLE  + " (" +
        COL_ORDER  + " INTEGER PRIMARY KEY , " + COL_DESCRIPTION  + " )");
    db.execSQL("CREATE TABLE IF NOT EXISTS " + POINTS_TABLE + " (" +
        COL_TIMESTAMP + " VARCHAR, " + COL_LATITUDE + " REAL, " +
        COL_LONGITUDE + " REAL," + COL_ACCURACY + " REAL, " + COL_SPEED +
        "REAL);");
    db.close();
  }

  @Override
  public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
    // TODO Auto-generated method stub

  }

  public void addItem(int order, String description, String upc) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues cv = new ContentValues();
    cv.put(COL_ORDER , order);
    cv.put(COL_DESCRIPTION , description);
    db.insert(SCANNED_ITEMS_TABLE , String.valueOf(order), cv);
    db.close();
  }

  public void addLocation(Location location) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    GregorianCalendar gregorian = new GregorianCalendar();
    TimeZone timeZone = gregorian.getTimeZone();
    gregorian.add(Calendar.SECOND,
        timeZone.getOffset(System.currentTimeMillis()) / 1000 * -1);
    contentValues.put(COL_TIMESTAMP,
        timestampFormat.format(gregorian.getTime()));
    contentValues.put(COL_LATITUDE, location.getLatitude());
    contentValues.put(COL_LONGITUDE, location.getLongitude());
    contentValues.put(COL_ACCURACY, location.hasAccuracy() ?
        String.valueOf(location.getAccuracy()) : "NULL");
    contentValues.put(COL_SPEED, location.hasSpeed() ?
        String.valueOf(location.getSpeed()) : "NULL");
    db.insert(POINTS_TABLE, null, contentValues);
    db.close();
  }

  List<String> getAllItemsDescription() {
    List<String> descriptions = new LinkedList<String>();
    SQLiteDatabase db=this.getReadableDatabase();
    Cursor cur=db.rawQuery("SELECT "+COL_ORDER +" as _id,"+COL_DESCRIPTION +" from "+SCANNED_ITEMS_TABLE , new String[]{});
    int columnIndex = cur.getColumnIndex(COL_DESCRIPTION );
    Log.v("DATABASE HELPER", "GETTING ITEMS");
    String desc = "";
    while (cur.moveToNext()) {
      desc = cur.getString(columnIndex);
      descriptions.add(desc);
      Log.v("DATABASE HELPER", "ITEM: " + desc);
    }
    db.close();
    return descriptions;
  }

}
