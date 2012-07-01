package com.greenapp;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.greenapp.R;


public class PreferencesActivity extends PreferenceActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.layout.preferences);
  }

}
