package com.greenapp;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;
import con.greenapp.R;

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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.green_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_preferences:
        startActivity(new Intent(this, PreferencesActivity.class));
        return true;
      case R.id.menu_info:
        showDialog();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void showDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage(R.string.green_info);
    AlertDialog alert = builder.create();
    alert.show();
  }

  protected void calculateScore() {
    LinearLayout view = new LinearLayout(this);
    view.setOrientation(LinearLayout.VERTICAL);
    view.setPadding(20, 20, 20, 20);
    TextView heading = new TextView(this);
    int sum = 0;
    boolean broken = false;
    int i = -1;
    for(QuizQuestion q : questions) {
    	i++;
    	if (i == 0)
    		continue;
		q.prepareAnswer();
		if (q.getAnswer() == 0) {
			Dialog pw = new Dialog(this);
			pw.setTitle("Please answer all the questions!");
			pw.show();
			broken = true;
			break;
		}
        sum += q.getAnswer();
    }
    if (broken)
    	return;
    heading.setText("Your impact score is: " + sum + "/23\n");
    view.addView(heading);
    view.addView(getScoreDescription(sum));
    Dialog pw = new Dialog(this);
    pw.setTitle("Your Ecobug Quiz Results!");
    pw.addContentView(view, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    pw.show();

  }

  private View getScoreDescription(int sum) {
	TextView description = new TextView(this);
	String text = "";
	switch(sum) {
		case 0:case 1:case 2:case 3:case 4:case 5:
		case 6:case 7:case 8:case 9:case 10: text = "Congratulations! Your green footprint is extremely low." +
				" You care for your planet and thanks to your lifestyle the future generations" +
				" will have the chance to enjoy a safe and friendly environment. Keep up the good work!"; break;
		case 11:case 12:case 13:case 14: text = "You are doing a relatively good job" +
				" at keeping the planet healthy. But this might not be enough! Try looking at our tips to" +
				" see what else you might work on to make your life greener."; break;
		case 15:case 16:case 17:case 18:case 19:
		case 20: text = "Unfortunately, your lifestyle is not very environmentally friendly! " +
				"You should think about changing a lot of your habits so that your children have a" +
				"friendly and health planet to live on. Try giving our tips a chance."; break;
		case 21:case 22:case 23: text = "You are ruining the planet! You should seriously think about" +
				" your lifestyle and its impact on the environment. If everyone behaved like you, we would" +
				" need more then one Earth to sustain life!"; break;
	}
	description.setText(text);
	return description;
}

private void buildQuestions() {

	questions.add(new DummyQuestion(this));
	
    questions.add(new MCQQuizQuestion(this, "How would you describe the place you live in?",
    		new String[]{"Small studio flat", "2-3 bedroom flat", "Small house in the suburbs", "Impressive mansion with a maid"}){

      @Override
      public void prepareAnswer() {
    	  valueOfAnswer = returnChosenIndex() + 1;
      }
    });

    questions.add(new MCQQuizQuestion(this, "Do you turn off your computer at night?",
        new String[]{"yes", "no"}){

      @Override
      public void prepareAnswer() {
    	  valueOfAnswer = returnChosenIndex() + 1;
      }

    });

    questions.add(new MCQQuizQuestion(this, "How much do you travel by air per year?",
        new String[]{"1-2 a year", "4-5 a year", "more then 5 times a year"}){

      @Override
      public void prepareAnswer() {
    	  valueOfAnswer = returnChosenIndex() + 1;
      }

    });

    questions.add(new MCQQuizQuestion(this, "Do you turn off the light when you go out of the room?",
        new String[]{"yes","no"}){

      @Override
      public void prepareAnswer() {
    	  valueOfAnswer = returnChosenIndex() + 1;
      }

    });

    questions.add(new MCQQuizQuestion(this, "What best describes your diet?"
    		,new String[]{"Vegan", "Vegetarian", "Meat and dairy as often as fruit and vegetables", "Mostly meat and dairy"}){

      @Override
      public void prepareAnswer() {
    	  valueOfAnswer = returnChosenIndex() + 1;
      }

    });

    questions.add(new MCQQuizQuestion(this, "Where do you do most of your shopping?",
    		new String[]{"Local farmers market", "Supermarkets", "Restaurants, fast-food chains and similar"}){

        @Override
        public void prepareAnswer() {
        	valueOfAnswer = returnChosenIndex() + 1;
        }
      });

    questions.add(new MCQQuizQuestion(this, "What's your attitude towards second-hand items?",
    		new String[]{"Great idea to save money and energy!", "I have both new and second-hand items", "I don't like the idea of used stuff"}){

        @Override
        public void prepareAnswer() {
        	valueOfAnswer = returnChosenIndex() + 1;
        }
      });

    questions.add(new MCQQuizQuestion(this, "Do you recycle?",
    		new String[]{"Yes", "No"}){

        @Override
        public void prepareAnswer() {
        	valueOfAnswer = returnChosenIndex() + 1;
        }
      });
  }

}
