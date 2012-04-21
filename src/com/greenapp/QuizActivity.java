package com.greenapp;

import java.util.LinkedList;
import java.util.List;

import con.greenapp.R;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class QuizActivity extends Activity {
	List<QuizQuestion> questions = new LinkedList<QuizQuestion>();
	Button submitButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        buildQuestions();
        
        Gallery gallery = (Gallery) findViewById(R.id.galleryQuestions);
        gallery.setAdapter(new QuestionsArrayAdapter(this, questions));

        submitButton = (Button) findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				calculateScore();
			}
		});
    }

	protected void calculateScore() {
		LinearLayout view = new LinearLayout(this);
		view.setOrientation(LinearLayout.VERTICAL);
		view.setPadding(20, 20, 20, 20);
		TextView answer;
		int i = 1;
		for(QuizQuestion q : questions) {
			answer = new TextView(this);
			answer.setText("Question " + i + ": " + q.getAnswer());
			view.addView(answer);
			i++;
		}
		
		Dialog pw = new Dialog(this);
		pw.setTitle("Your Greenapp Quiz Results!");
		pw.addContentView(view, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		pw.show();
		
	}

	private void buildQuestions() {
		
        questions.add(new NumericalQuizQuestion(this, "How old are you?"){

			@Override
			public void prepareAnswer() {
				valueOfAnswer = Integer.parseInt(answer.getText().toString());
			}
        });

        questions.add(new MCQQuizQuestion(this, "Do you turn off your computer at night?",
        		new String[]{"yes", "no"}){

			@Override
			public void prepareAnswer() {
				// TODO Auto-generated method stub
				
			}
        	
        });
    
        questions.add(new MCQQuizQuestion(this, "How often do you hop on a plane (per year)?",
        		new String[]{"1-2 a year", "4-5 a year", "even more often!"}){

			@Override
			public void prepareAnswer() {
				// TODO Auto-generated method stub
			}
        	
        });
        
        questions.add(new MCQQuizQuestion(this, "Do you turn off the light when you go out of the room?",
        		new String[]{"yes","no"}){

			@Override
			public void prepareAnswer() {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        questions.add(new NumericalQuizQuestion(this, "How often do you shower?"){

			@Override
			public void prepareAnswer() {
				// TODO Auto-generated method stub
				
			}
        	
        });
	}

}
