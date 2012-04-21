package com.greenapp;

import java.util.LinkedList;
import java.util.List;

import con.greenapp.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

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
        questions.add(new QuizQuestion(this, "How are you?"){

			@Override
			public View prepareAnswerView() {
				final EditText answer = new EditText(context);
				answer.setInputType(InputType.TYPE_CLASS_NUMBER);
				hideKeyboard(answer);
				return answer;
			}
        });

        questions.add(new QuizQuestion(this, "Do you turn off your computer at night?"){

			@Override
			public View prepareAnswerView() {
				LinearLayout wrapper = new LinearLayout(context);
				CheckBox yes = new CheckBox(context);
				yes.setText("yes");
				CheckBox no = new CheckBox(context);
				no.setText("no");
				wrapper.addView(yes);
				wrapper.addView(no);
				return wrapper;
			}
        	
        });
    
        questions.add(new QuizQuestion(this, "How often do you take a plane flight (per year)?"){

			@Override
			public View prepareAnswerView() {
				EditText answer = new EditText(context);
				hideKeyboard(answer);
				return answer;
			}
        	
        });
        
        questions.add(new QuizQuestion(this, "Do you turn off the light when you go out of the room?"){

			@Override
			public View prepareAnswerView() {
				EditText answer = new EditText(context);
				hideKeyboard(answer);
				return answer;
			}
        	
        });
        
        questions.add(new QuizQuestion(this, "How old are you?"){

			@Override
			public View prepareAnswerView() {
				EditText answer = new EditText(context);
				hideKeyboard(answer);
				return answer;
			}
        	
        });
	}

	protected void hideKeyboard(final EditText answer) {
		answer.setOnEditorActionListener(new OnEditorActionListener() {

	        @Override
	        public boolean onEditorAction(TextView v, int actionId,
	                KeyEvent event) {
	            if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
	                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	                in.hideSoftInputFromWindow(answer
	                        .getApplicationWindowToken(),
	                        InputMethodManager.HIDE_NOT_ALWAYS);
	            }
	            return false;
	        }

	    });
	}

}
