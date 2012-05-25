package com.greenapp;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Gallery;
import android.widget.TextView;

public class DummyQuestion extends QuizQuestion {

	public DummyQuestion(Context context, String question) {
		super(context, question);
		// TODO Auto-generated constructor stub
	}

	public DummyQuestion(Context context) {
		super(context, "");
	}

	public View getView() {
		TextView text = new TextView(context);
		text.setText("Welcome to our Greenpact Quiz! If you're curious how environmentaly friendly" +
				" is your lifestyle, this set of questions should give you an answer. Swipe to start " +
				"the fun!");
		text.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		text.setGravity(Gravity.CENTER_HORIZONTAL);
		text.setTextColor(Color.WHITE);
		text.setPadding(0, 100, 0, 0);
		return text;
	}

	@Override
	public View prepareAnswerView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepareAnswer() {
		// TODO Auto-generated method stub

	}

}
