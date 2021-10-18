package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;



import com.example.trivia.data.data;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.trivia.model.question;


import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String SHARED_PREFS_NAME = "MY_SHARED_PREF";

    ConstraintLayout constraintLayout;

    int highScore;

    CardView highScoreContainer;
    CardView highScoreContainer2;

    TextView gameName;
    TextView questioncontent;
    TextView gameStatment;
    TextView highScore1;
    TextView highScore2;

    CardView cardView;

    MediaPlayer mp;

    ImageView logo;
    ImageView highScoreImage;

    double randomNum;
    int randomIndex;

    List<question> questions;
    int questionNumber = 0;
    int[] randomNumbers = new int[1000];

    Button f;
    Button t;
    Button start;
    Button reset1;
    Button reset2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintlayout);

        logo = (ImageView) findViewById(R.id.logo);
        highScoreImage = (ImageView) findViewById(R.id.highScoreImage);

        getHighScore();

        f = (Button) findViewById(R.id.FALSE);
        t = (Button) findViewById(R.id.TRUE);
        start = (Button) findViewById(R.id.start);
        reset1 = (Button) findViewById(R.id.reset1);
        reset2 = (Button) findViewById(R.id.reset2);

        gameName = (TextView) findViewById(R.id.gameName);
        gameStatment = (TextView) findViewById(R.id.gameStatment);
        questioncontent = (TextView) findViewById(R.id.questioncontent);
        highScore1= (TextView) findViewById(R.id.highScore);
        highScore2 = (TextView) findViewById(R.id.highScore2);

        highScore1.setText("High Score: "+ highScore);

        cardView = (CardView) findViewById(R.id.cardView);
        highScoreContainer = (CardView) findViewById(R.id.highScoreContainer);
        highScoreContainer2 = (CardView) findViewById(R.id.highScoreContainer2);

        questions = new data().getQuestions(questionArrayList -> {
                Log.d("info", "size " + questions.size());
        });
    }


    public void start(View view) {
        if(questions.isEmpty()){
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
            questions = new data().getQuestions(questionArrayList -> {
                Log.d("info", "size " + questions.size());
            });
        }
        else {
            reset1.setVisibility(View.GONE);
            reset2.setVisibility(View.GONE);
            highScoreImage.setVisibility(View.GONE);
            highScoreContainer.setVisibility(View.GONE);
            highScoreContainer2.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
            gameStatment.setVisibility(View.GONE);
            gameName.setVisibility(View.VISIBLE);
            logo.setVisibility(View.INVISIBLE);
            questioncontent.setTextSize(34);
            constraintLayout.setBackgroundColor(Color.rgb(98, 0, 234));
            questionNumber++;
            gameName.setText("LEVEL " + questionNumber);
            f.setVisibility(View.VISIBLE);
            t.setVisibility(View.VISIBLE);
            start.setVisibility(View.INVISIBLE);
            generateRandomQuestion();
            questioncontent.setVisibility(View.VISIBLE);
        }

    }

    public void showResetButton1(View view){
        if(reset1.getVisibility()==View.GONE)
            reset1.setVisibility(View.VISIBLE);
        else if(reset1.getVisibility()==View.VISIBLE)
            reset1.setVisibility(View.GONE);
    }

    public void showResetButton2(View view){
        if(reset2.getVisibility()==View.GONE)
            reset2.setVisibility(View.VISIBLE);
        else if(reset2.getVisibility()==View.VISIBLE)
            reset2.setVisibility(View.GONE);
    }

    public void resetHighScore(View view){
        reset1.setVisibility(View.GONE);
        reset2.setVisibility(View.GONE);
        highScore = 0;
        saveHighScore();
        highScore2.setText("High Score: "+ highScore);
        highScore1.setText("High Score: "+ highScore);
    }


    public void generateRandomQuestion() {
        questioncontent.setTextSize(34);
        randomNum = Math.random() * questions.size();
        randomIndex = (int) Math.floor(randomNum);
        for (int i = 0; i < randomNumbers.length; i++) {
            if (randomNumbers[i] == randomIndex) {
                randomNum = Math.random() * questions.size();
                randomIndex = (int) Math.floor(randomNum);
            }
        }
        if(questions.get(randomIndex).getAnswer().length() > 158 && questions.get(randomIndex).getAnswer().length() < 320 ){
            questioncontent.setTextSize(27);
        }else if(questions.get(randomIndex).getAnswer().length() >= 320 ){
            questioncontent.setTextSize(20);
        }
        questioncontent.setText(questions.get(randomIndex).getAnswer());
    }

    public void nextQuestion() {

        randomNumbers[questionNumber - 1] = randomIndex;
        questionNumber++;
        gameName.setText("LEVEL " + questionNumber);
        generateRandomQuestion();

    }

    public void wrongAnswer() {
        gameName.setText("GAME OVER!");
        gameStatment.setText("you lost at level " + questionNumber);
        gameStatment.setVisibility(View.VISIBLE);
        start.setText("PLAY AGAIN");
        t.setVisibility(View.GONE);
        f.setVisibility(View.GONE);
        questioncontent.setVisibility(View.GONE);
        cardView.setVisibility(View.INVISIBLE);

        highScoreContainer2.setCardBackgroundColor(Color.BLUE);

        start.setBackgroundColor(Color.YELLOW);
        start.setVisibility(View.VISIBLE);

        if(questionNumber > highScore && questionNumber > 1) {
            highScore = questionNumber;
            playSound("wsound");
            saveHighScore();
            highScoreImage.setVisibility(View.VISIBLE);
            constraintLayout.setBackgroundColor(Color.GREEN);
            start.setVisibility(View.VISIBLE);
            highScoreContainer2.setVisibility(View.VISIBLE);
        }else if(questionNumber == 1 && questionNumber> highScore){
                highScore = 1;
                playSound("wrong");
                constraintLayout.setBackgroundColor(Color.RED);
                start.setBackgroundColor(Color.YELLOW);
                start.setVisibility(View.VISIBLE);
                highScoreContainer2.setVisibility(View.VISIBLE);
        }else{
            playSound("wrong");
            constraintLayout.setBackgroundColor(Color.RED);
            start.setBackgroundColor(Color.YELLOW);
            start.setVisibility(View.VISIBLE);
            highScoreContainer2.setVisibility(View.VISIBLE);
        }

        highScore2.setText("High Score: "+ highScore);
        highScoreContainer2.setVisibility(View.VISIBLE);
        questionNumber = 0;

    }

    public void F(View view) {
        if (questions.get(randomIndex).getAnswerTrue() == false) {
            playSound("correct");
            nextQuestion();

        } else {
            wrongAnswer();
        }

    }

    public void T(View view) {
        if (questions.get(randomIndex).getAnswerTrue() == true) {
            playSound("correct");
            nextQuestion();
        } else {
            wrongAnswer();
        }

    }

public void playSound(String sound){
    if (mp != null && mp.isPlaying()) {
        mp.stop();
        mp.reset();
    }
    mp = MediaPlayer.create(this, getResources().getIdentifier(sound, "raw", getPackageName()));
    mp.start();
}

    public void saveHighScore() {
        SharedPreferences sp1 = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor mEdit2 = sp1.edit();
        mEdit2.putInt("High score", highScore);
        mEdit2.apply();
    }

    public void getHighScore() {
        SharedPreferences sp1 = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        highScore = sp1.getInt("High score", 0);
        }
    }


