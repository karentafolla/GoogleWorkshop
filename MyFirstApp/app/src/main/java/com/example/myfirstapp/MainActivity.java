package com.example.myfirstapp;

import android.graphics.Interpolator;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Integer score = 0;
    private Integer turnScore = 0;
    private Integer computerScore = 0;
    private Integer computerTurnScore = 0;
    String playerScore = "Your Score: ";
    String compScore = " Computer Score: ";
    String playerTurnScore = " Your Turn Score: ";
    String compTurnScore = " Computer Turn Score: ";
    String player = "player";
    String computer = "computer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button rollButton = (Button) findViewById(R.id.button_roll);
        Button resetButton = (Button) findViewById(R.id.button_reset);
        Button holdButton = (Button) findViewById(R.id.button_hold);

        resetButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                score = 0;
                turnScore = 0;
                computerScore = 0;
                computerTurnScore = 0;

                TextView newScore = (TextView) findViewById(R.id.score);
                newScore.setText(playerScore + score + compScore + computerScore + playerTurnScore + turnScore + compTurnScore + computerTurnScore);

            }
        });
        holdButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                turnScore += 1;
                TextView newScore = (TextView) findViewById(R.id.score);
                newScore.setText(playerScore + score + compScore + computerScore + playerTurnScore + turnScore + compTurnScore + computerTurnScore);
                computerTurn();
            }
        });
        rollButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Boolean isTrue = true;

                Log.d("PLAYER: ROLL" , "rolling");
                if(scoreUpdate(player)==false){
                    computerTurn();
                    isTrue = false;
                }
                Log.d("PLAYER: ROLL", Boolean.toString(isTrue));


            }
        });

    }
    public void computerTurn(){
        Button roll = (Button) findViewById(R.id.button_roll);
        Button hold = (Button) findViewById(R.id.button_hold);
        roll.setEnabled(false);
        hold.setEnabled(false);

        while(scoreUpdate(computer));


        roll.setEnabled(true);
        hold.setEnabled(true);

    }
    public boolean scoreUpdate(String temp){
        int dice = new Random().nextInt(5) + 1;
        String updatedDice = "dice"+Integer.toString(dice);


        // Sets drawable based on random dice roll
        ImageView newDice = (ImageView) findViewById(R.id.dice_image);
        int id = getResources().getIdentifier(updatedDice, "drawable", "com.example.myfirstapp");
        Drawable myDrawable = getResources().getDrawable(id);
        newDice.setImageDrawable(myDrawable);


        Log.d("PLAYER:" + temp, Integer.toString(dice));


        // Set Updated Turn Score
        TextView newScore = (TextView) findViewById(R.id.score);



        if(temp.equals(player)) {
            if (dice == 1) {
                turnScore++;
                //newScore.setText(playerScore + score + compScore + computerScore + playerTurnScore + turnScore + compTurnScore + computerTurnScore);
                return false;
            } else {
                turnScore++;
                score += dice;
            }
        }else{
            if (dice == 1) {
                computerTurnScore++;
                //newScore.setText(playerScore + score + compScore + computerScore + playerTurnScore + turnScore + compTurnScore + computerTurnScore);
                return false;
            } else{
                if(computerTurnScore < 20) {
                    computerTurnScore++;
                    computerScore += dice;
                } else{
                    computerTurnScore += 1;
                    TextView s = (TextView) findViewById(R.id.score);
                   // s.setText(playerScore + score + compScore + computerScore + playerTurnScore + turnScore + compTurnScore + computerTurnScore);

                }
            }
        }


        newScore.setText(playerScore + score + compScore + computerScore + playerTurnScore + turnScore + compTurnScore + computerTurnScore);

        return true;
    }


}
