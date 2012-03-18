package com.greenapp;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	static final String dbName="greenAppDB";
	static final String scannedItemsTable="ScannedItems";
	static final String colOrder="ScannedItemOrder";
	static final String colUPC="ScannedItemUPC";
	static final String colDescription="ScannedItemDesc";
	static final String colCount="ScannedItemCount";

	public DatabaseHelper(Context context) {
		super(context, dbName, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + scannedItemsTable +
				" (" + colOrder + " INTEGER PRIMARY KEY , "+ colDescription + " )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	public void addItem(int order, String description, String upc) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(colOrder, order);
		cv.put(colDescription, description);
		db.insert(scannedItemsTable, String.valueOf(order), cv);
		db.close();
	}

	List<String> getAllItemsDescription() {
		List<String> descriptions = new LinkedList<String>();
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur=db.rawQuery("SELECT "+colOrder+" as _id,"+colDescription+" from "+scannedItemsTable, new String[]{});
		int columnIndex = cur.getColumnIndex(colDescription);
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
