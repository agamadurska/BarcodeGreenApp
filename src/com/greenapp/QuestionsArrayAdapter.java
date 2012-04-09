package com.greenapp;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.TextView;

public class QuestionsArrayAdapter extends BaseAdapter {

	private List<QuizQuestion> questions;
	private Context context;

	public QuestionsArrayAdapter(Context context, List<QuizQuestion> questions) {
		this.context = context;
		this.questions = questions;
	}
	@Override
	public int getCount() {
		return questions.size();
	}

	@Override
	public Object getItem(int position) {
		return questions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView question;
		 
		if (null == convertView) {
			question = new TextView(context);
			question.setText("Question " + position);
			question.setLayoutParams(new Gallery.LayoutParams(400, 500));
			question.setGravity(Gravity.CENTER);
			question.setTextColor(Color.WHITE);
		} else {
			question = (TextView)convertView;
		}
		return question;
	}
}
