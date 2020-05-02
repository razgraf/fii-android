package ro.vansoftware.vantiles;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;
    private GameTextureView gameTextureView;

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
