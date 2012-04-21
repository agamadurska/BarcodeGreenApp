package com.greenapp;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public abstract class QuizQuestion {
	protected Context context;
	private String question;
	protected int valueOfAnswer = 0;
	
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
	
	public View getView() {
		LinearLayout wrap = new LinearLayout(context);
		wrap.addView(prepareQuestionView());
		wrap.addView(prepareAnswerView());
		wrap.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		wrap.setGravity(Gravity.TOP);
		wrap.setOrientation(LinearLayout.VERTICAL);
		return wrap;
	}

	public abstract View prepareAnswerView();
	public abstract void prepareAnswer();

	public int getAnswer() {
		return valueOfAnswer;
	}

	protected void hideKeyboard(final EditText answer) {
		answer.setOnEditorActionListener(new OnEditorActionListener() {

	        @Override
	        public boolean onEditorAction(TextView v, int actionId,
	                KeyEvent event) {
	            if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
	                InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
	                in.hideSoftInputFromWindow(answer
	                        .getApplicationWindowToken(),
	                        InputMethodManager.HIDE_NOT_ALWAYS);
	            }
	            return false;
	        }

	    });
	}
}
