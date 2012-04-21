package com.greenapp;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NumericalQuizQuestion extends QuizQuestion {
	EditText answer;
	String text;
	
	public NumericalQuizQuestion(Context context, String question) {
		super(context, question);
	}

	@Override
	public View prepareAnswerView() {
		answer = new EditText(context);
		answer.setInputType(InputType.TYPE_CLASS_NUMBER);
		hideKeyboard(answer);
		answer.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				text = s.toString();
			}
		});
		if (text != null) {
			answer.setText(text);
		}

		return answer;
	}

	@Override
	public void prepareAnswer() {}

}
