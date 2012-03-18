package com.greenapp;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ScannedArrayAdapter extends ArrayAdapter<String> {
	
	private List<String> scannedItems;

	public ScannedArrayAdapter(Context context, List<String> scannedItems) {
		super(context, 0);
		this.scannedItems = scannedItems;
	}

	@Override
	public int getCount() {
		return scannedItems.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		TextView row;
 
		if (null == convertView) {
			row = new TextView(getContext());
			row.setMinHeight(60);
			row.setGravity(Gravity.CENTER);
		} else {
			row = (TextView)convertView;
		}
		if (row != null) {
			row.setText(scannedItems.get(position));
		}
 
		return row;
	}

}
