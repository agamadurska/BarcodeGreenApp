package com.greenapp;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class QuizQuestion {
	protected Context context;
	private String question;
	
	protected View prepareQuestionView() {
		TextView view;
		view = new TextView(context);
		view.setText(question);
		view.setGravity(Gravity.TOP);
		view.setTextColor(Color.WHITE);
		return view;
	}

	public QuizQuestion(Context context, String question) {
		this.context = context;
		this.question = question;
	}

	public abstract View prepareAnswerView();
	
	public View getView() {
		LinearLayout wrap = new LinearLayout(context);
		wrap.addView(prepareQuestionView());
		wrap.addView(prepareAnswerView());
		wrap.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		wrap.setGravity(Gravity.TOP);
		wrap.setOrientation(LinearLayout.VERTICAL);
		return wrap;
	}

}
