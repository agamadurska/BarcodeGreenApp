package com.greenapp;

import java.util.LinkedList;
import java.util.List;

import con.greenapp.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Gallery;

public class QuizActivity extends Activity {
	List<QuizQuestion> questions = new LinkedList<QuizQuestion>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        buildQuestions();
        
        Gallery gallery = (Gallery) findViewById(R.id.galleryQuestions);
        gallery.setAdapter(new QuestionsArrayAdapter(this, questions));
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
