package ro.vansoftware.vantiles.activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import ro.vansoftware.vantiles.views.GameView;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.gameView = new GameView(this);
        setContentView(gameView);

    }


    @Override
    protected void onPause() {
        super.onPause();
        this.gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.gameView.resume();
    }

}
