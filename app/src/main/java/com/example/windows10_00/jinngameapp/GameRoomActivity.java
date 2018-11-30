package com.example.windows10_00.jinngameapp;

import android.content.Intent;
import android.graphics.Color;
import android.mtp.MtpConstants;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

enum CountDownMode { START , UPDATE}

public class GameRoomActivity extends AppCompatActivity {


    String[] question;
    String[] answer;
    TextView scoreText , timeText , wordText;
    EditText inputBox;
    TextView startCountDown;
    LinearLayout gameLayout;
    CountDownTimer countDownTimer;
    ProgressBar progressBar;
    final long STARTT_TIME_SECOND = 3000;
    final long UPDATE_TIME_SECOND = 15000;
    long timer = STARTT_TIME_SECOND;
    int score= 0 , number , remmeberCode = 0 , count;
    double difficulty =  (4*Math.sqrt(10)) / 10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_room);

        setUp();


        inputBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    Toast.makeText(getApplicationContext() , "엔터키가 눌러졌습니다" , Toast.LENGTH_LONG).show();


                    switchWord();

                    return inputAction();
                }
                return false;
            }
        });
        startCountDownTimer();
    }

    private void setUp() {

        scoreText = findViewById(R.id.score);
        timeText = findViewById(R.id.timeText);
        wordText = findViewById(R.id.word);
        inputBox = findViewById(R.id.input);
        startCountDown = findViewById(R.id.countDownText);
        gameLayout = findViewById(R.id.gameLayout);
        progressBar = findViewById(R.id.progreesBar);

        number = getIntent().getIntExtra("Choice", 1);

        Text text = new Text();

        scoreText.setText(0+"");

        question = text.queastions[number];
        answer = text.answers[number];

    }

    void setCountDownTimer() {

        countDownTimer = new CountDownTimer(timer, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer = millisUntilFinished;
                updateTimeNow(timeText , CountDownMode.UPDATE);
            }

        @Override
        public void onFinish() {
            stopGame();
        }
    }.start();
}

    void updateTimeNow(TextView textView , CountDownMode mode) {
        int second = (int)timer/1000%60;
        String timeFormat;
        timeFormat = String.format(Locale.getDefault() ,"%2d" , second+1);
        startCountDown.setText(timeFormat);
        switch (mode){
            case START:

                switch (second+1){
                    case 3:
                        break;
                    case 2:
                        startCountDown.setTextColor(Color.BLUE);
                        break;
                        case 1:
                        startCountDown.setTextColor(Color.RED);
                        break;
                }

                break;
            case UPDATE:
                progressBar.setProgress(second);
               timeFormat = String.format(Locale.getDefault() ,"%2d초" , second);
                textView.setText(timeFormat);
                break;
        }
    }

    void switchWord(){

        do
            count = new Random().nextInt(question.length-1);
        while (count == remmeberCode);

        wordText.setText(question[count]);

    }

    boolean inputAction(){


        String input = inputBox.getText().toString();
        if(input.equals("")) return false;

        if(input.equals(answer[count])){
            score++;
            scoreText.setText(score+"");
        }else
            stopGame();


        timer = (long) (UPDATE_TIME_SECOND * (difficulty / (Math.sqrt(score)))); //무리함수
        Toast.makeText(getApplicationContext() , timer+"" , Toast.LENGTH_LONG).show();
       countDownTimer.cancel();
       setCountDownTimer();
       inputBox.setText("");

       return true;
    }

    void startCountDownTimer(){
        countDownTimer = new CountDownTimer(3000 , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer = millisUntilFinished;
                updateTimeNow(startCountDown , CountDownMode.START);
            }

            @Override
            public void onFinish() {
                gameLayout.setVisibility(View.VISIBLE);
                timer = UPDATE_TIME_SECOND;
                setCountDownTimer();
                startCountDown.setVisibility(View.INVISIBLE);
                switchWord();

            }
        }.start();
    }
    void stopGame(){
/*
        Intent intent = new Intent(getApplicationContext() , );
        intent.putExtra("Score" , score);
        startActivity(intent);
*/
    }



}

