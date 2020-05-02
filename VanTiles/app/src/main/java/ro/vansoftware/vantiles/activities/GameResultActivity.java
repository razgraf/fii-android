package ro.vansoftware.vantiles.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import ro.vansoftware.vantiles.MainActivity;
import ro.vansoftware.vantiles.R;
import ro.vansoftware.vantiles.model.Score;
import ro.vansoftware.vantiles.storage.Internal;
import ro.vansoftware.vantiles.utils.Utils;

public class GameResultActivity extends AppCompatActivity {

    public static final String TAG = "GameResultActivity";


    boolean stateWin = false;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        Intent intent = getIntent();
        this.stateWin = intent.getBooleanExtra("win", false);
        this.score = intent.getIntExtra("score", 0);


        Log.e(TAG, this.score + "");
        Log.e(TAG, this.stateWin ? "y": "n");


        this.handleState();
        this.handleClick();
        this.handleStorage();

    }


    private void handleStorage(){

        ArrayList<Score> scores;
        try{
            scores = Internal.getScores(GameResultActivity.this);
            scores.add(new Score(this.score + "", Utils.getNow()));
            Internal.setScores(GameResultActivity.this, scores);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }


    private void handleState(){

        ((TextView) findViewById(R.id.score)).setText(this.score + "");

        if(this.stateWin){
            findViewById(R.id.background).setBackgroundResource(R.color.van);
            ((TextView) findViewById(R.id.title)).setText(R.string.gameWon);
        }
        else {
            findViewById(R.id.background).setBackgroundResource(R.color.red);
            ((TextView) findViewById(R.id.title)).setText(R.string.gameOver);
        }
    }


    private void handleClick(){

        final Context self = this;

        findViewById(R.id.returnHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.tryAgain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameResultActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String body = "Hey! I've just hit " + GameResultActivity.this.score + " pts on Van Tiles. Can you beat it?";
                String title = "Van Tiles";

                intent.putExtra(Intent.EXTRA_SUBJECT, title);
                intent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(intent, "Share your score on..."));
            }
        });
    }
}
