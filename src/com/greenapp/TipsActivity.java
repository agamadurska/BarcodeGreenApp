package com.greenapp;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;
import con.greenapp.R;


public class TipsActivity extends Activity {

  List<Tip> tips = new LinkedList<Tip>();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.tips);
    Gallery gallery = (Gallery) findViewById(R.id.galleryTips);
    buildTips();
    gallery.setAdapter(new TipsArrayAdapter(this, tips));

    /*
    Toast toast = Toast.makeText(getApplicationContext(),
        "Test Toast",
        Toast.LENGTH_SHORT);
    toast.show();
     */
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

  private class TipsArrayAdapter extends BaseAdapter {
    private final List<Tip> tips;

    public TipsArrayAdapter(Context context, List<Tip> tips) {
      this.tips = tips;
    }

    @Override
    public int getCount() {
      return tips.size();
    }

    @Override
    public Object getItem(int position) {
      return tips.get(position);
    }

    @Override
    public long getItemId(int position) {
      return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      if (null == convertView) {
        return tips.get(position).getView();
      } else {
        return convertView;
      }
    }

  }

  private class Tip {

    private final Context context;
    private final String tip;

    public Tip(Context context, String tip) {
      this.context = context;
      this.tip = tip;
    }

    public View getView() {
      LinearLayout wrap = new LinearLayout(context);
      TextView view = new TextView(context);
      view.setText(tip);
      view.setTextColor(Color.WHITE);
      view.setTextSize(18);
      view.setGravity(Gravity.CENTER);
      wrap.addView(view);

      wrap.setOrientation(LinearLayout.VERTICAL);
      wrap.setGravity(Gravity.TOP);
      wrap.setLayoutParams(
          new Gallery.LayoutParams(LayoutParams.FILL_PARENT,
              LayoutParams.FILL_PARENT));

      return wrap;
    }
  }

  protected void buildTips() {
    tips.add(new Tip(this, "Next time you go shopping " +
        "pick the products that have travelled the least. " +
        "Transport uses energy! Choosing locally produced food saves energy " +
        "and causes less pollution."));
    tips.add(new Tip(this, "Drive slower! It can save lives, but it will " +
        "also save you money. There is a 30% difference in fuel usage between driving at " +
        "70mph and 50mph"));
    tips.add(new Tip(this, "Drive smoothly! Think ahead and avoid sharp " +
        "breaking and rapid acceleration. It saves fuel."));
    tips.add(new Tip(this, "Under-inflated tyres create more resistance, " +
        "making your engine work more then necessary. This can increase your fuel " +
        "consumption by up to 3 per cent"));
    tips.add(new Tip(this, "Using air conditioning and electrical devices " +
        "like mobile phone chargers in your car increases fuel consumption and pollution levels." +
        " Keep their usage to a minimum"));
    tips.add(new Tip(this, "Reduce the amount paper of paper you use. " +
    	"When printing, use both sides of a " +
        "sheet. Keep a pile of scrap paper made up of " +
        "misprinted or unnecessary documents to re-use."));
    tips.add(new Tip(this, "Reduce the duration of your showers. With water " +
        "flowing up to five gallons per minute, showers account for about " +
        "one-fifth of water used indoors"));
    tips.add(new Tip(this, "Purchase organic fruit and vegetables. " +
        "Organic foods avoids the use of pesticides. This is safer for the " +
        "environment!"));
    tips.add(new Tip(this, "Watch out for pipe leaks. It is estimated that 13.7% of " +
        "household water is wasted through leakage!"));
    tips.add(new Tip(this, "Buy products made from recycling material to " +
        "support the recycled product market. When purchasing paper products " +
        "look for paper that has been recycled using a minimum of 50% " +
        "post-consumer waste"));
    Collections.shuffle(tips);
  }

}
