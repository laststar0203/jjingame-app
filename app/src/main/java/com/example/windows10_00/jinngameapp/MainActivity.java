package com.example.windows10_00.jinngameapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

enum Arrow{ RIGHT , LEFT};

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    String[] choiceWorld;
    Button rightArrow , leftArrow, intentBtn;
    TextView chocieText;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUp();


    }

    private void setUp() {

        this.choiceWorld = new Text().choiceWord;
        rightArrow = findViewById(R.id.rightArrow);
        leftArrow = findViewById(R.id.leftArrow);
        intentBtn = findViewById(R.id.goGameBtn);
        chocieText = findViewById(R.id.chocieText);

        rightArrow.setOnClickListener(this);
        leftArrow.setOnClickListener(this);
        intentBtn.setOnClickListener(this);


        chocieText.setText(choiceWorld[0]);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goGameBtn:
               Intent intent = new Intent(getApplicationContext() , GameRoomActivity.class);
               intent.putExtra("Choice" , count);
               startActivity(intent);
                break;
            case R.id.rightArrow:
                changeWord(Arrow.RIGHT);
                break;
            case R.id.leftArrow:
                changeWord(Arrow.LEFT);
                break;
        }

    }

    void changeWord(Arrow arrow){



        switch (arrow){
            case RIGHT:
                if(count >= choiceWorld.length-1) count = 0; else count++;
                chocieText.setText(choiceWorld[count]);
                //Toast.makeText(getApplicationContext() , count , Toast.LENGTH_LONG).show();
                break;
            case LEFT:
                if(count <= 0) count = choiceWorld.length - 1; else count--;
                chocieText.setText(choiceWorld[count]);
                //Toast.makeText(getApplicationContext() , count , Toast.LENGTH_LONG).show();
                break;
        }


    }
}
