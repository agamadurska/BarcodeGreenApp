package com.greenapp;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import con.greenapp.R;

public class ScanActivity extends Activity {
  private static final String SUCCESS_SCAN = "Congratualtions! You have recycled another item";
  private static DatabaseHelper dbHelper;
  private Button scanButton;
  private ListView itemList;
  private List<String> scannedItems;
  private final Button.OnClickListener mScan = new Button.OnClickListener() {
    @Override
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

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    if (requestCode == 0) {
      if (resultCode == RESULT_OK) {
        String productID = intent.getStringExtra("SCAN_RESULT");
        // Handle successful scan
        // Show toast
        Context context = getApplicationContext();
        String siteContent = HttpRequestHelper.fetchProductInfo(productID);
        String content = HtmlParser.getDescription(siteContent);
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