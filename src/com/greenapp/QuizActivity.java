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
