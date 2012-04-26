package com.greenapp;

import java.util.LinkedList;
import java.util.List;

import con.greenapp.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ScanActivity extends Activity {
	private static final String SUCCESS_SCAN = "Congratualtions! You have recycled another item";
	private static DatabaseHelper dbHelper;  
	private Button scanButton;
	private ListView itemList;
	private List<String> scannedItems;
    private Button.OnClickListener mScan = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.setPackage("com.google.zxing.client.android");
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan);
        dbHelper = new DatabaseHelper(this);
        scannedItems = dbHelper.getAllItemsDescription();
        if (scannedItems == null) {
        	scannedItems = new LinkedList<String>();
        }
        itemList = (ListView) findViewById(R.id.itemList);
        itemList.setAdapter(new ScannedArrayAdapter(this, scannedItems));
        scanButton = (Button)findViewById(R.id.buttonScan);
        scanButton.setOnClickListener(mScan);
        itemList.invalidate();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String productID = intent.getStringExtra("SCAN_RESULT");
                // Handle successful scan
                // Show toast
                Context context = getApplicationContext();
                String siteContent = HttpRequestHelper.fetchProductInfo(productID);
                Log.v("HTTP HELPER SITE CONTENT", siteContent);
                String content = HtmlParser.getDescription(siteContent);
                //String content = "";
               // content = translateProductID(productID);
                if (content == "") {
                	content = productID + " [description unknown]";
                }
                CharSequence text = SUCCESS_SCAN;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
 
                scannedItems.add(content);
                ((ArrayAdapter<String>)itemList.getAdapter()).notifyDataSetChanged();
                itemList.invalidate();
                dbHelper.addItem(scannedItems.size(), content, productID);
            } else if (resultCode == RESULT_CANCELED) {
                // Do nothing
            }
        }
    }

	private String translateProductID(String productID) {
		if (productID.equals("9780593062739")) {
			return "\"Merde! 1000 Years of Annoying the French\" [Book, 400 pages] - saved 3 trees!";
		}
		return null;
	}
}