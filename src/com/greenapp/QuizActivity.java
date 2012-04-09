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

        questions.add(new QuizQuestion());
        questions.add(new QuizQuestion());
        questions.add(new QuizQuestion());
        questions.add(new QuizQuestion());
        questions.add(new QuizQuestion());
        
        Gallery gallery = (Gallery) findViewById(R.id.galleryQuestions);
        gallery.setAdapter(new QuestionsArrayAdapter(this, questions));
    }

}
