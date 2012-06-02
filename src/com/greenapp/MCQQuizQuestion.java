package com.greenapp;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MCQQuizQuestion extends QuizQuestion {

	protected String[] choices;
	protected List<RadioButton> choiceViews;
	protected int chosenIndex = -1;

	public MCQQuizQuestion(Context context, String question, String[] choices) {
		super(context, question);
		this.choices = choices;
	}

	@Override
	public View prepareAnswerView() {
		RadioGroup wrapper = new RadioGroup(context);
		this.choiceViews = new LinkedList<RadioButton>();
		for (String choice : choices) {
			RadioButton c = new RadioButton(context);
			choiceViews.add(c);
			c.setText(choice);
			c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (isChecked)
						setChosenIndex(buttonView);
				}
			});
			wrapper.addView(c);
		}
		if (chosenIndex != -1) {
			choiceViews.get(chosenIndex).setChecked(true);
		}
		return wrapper;
	}

	protected void setChosenIndex(CompoundButton buttonView) {
		chosenIndex = choiceViews.indexOf(buttonView);
	}

	@Override
	public void prepareAnswer() {}

	public int returnChosenIndex() {
		return chosenIndex;
	}

}
