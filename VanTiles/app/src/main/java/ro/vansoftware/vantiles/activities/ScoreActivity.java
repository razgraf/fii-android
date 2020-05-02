package ro.vansoftware.vantiles.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import androidx.appcompat.app.AppCompatActivity;
import ro.vansoftware.vantiles.MainActivity;
import ro.vansoftware.vantiles.R;
import ro.vansoftware.vantiles.adapter.ScoreAdapter;
import ro.vansoftware.vantiles.model.Score;
import ro.vansoftware.vantiles.storage.Internal;

public class ScoreActivity extends AppCompatActivity {

    public static final String TAG = "ScoreActivity";

    ListView listView;
    ScoreAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        this.onViewBinder();
        this.onDataBinder();


        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Leaderboards");
        }
        catch (Exception e){
            e.printStackTrace();
        }




    }



    private void onViewBinder(){
        this.listView = findViewById(R.id.list);

    }


    private void onDataBinder(){
        ArrayList<Score> temp = new ArrayList<>();
        this.adapter = new ScoreAdapter(this, R.layout.activity_score, temp);
        this.listView.setAdapter(adapter);

        try{
            ArrayList<Score> scores = new ArrayList<>();
            scores = Internal.getScores(ScoreActivity.this);

            Collections.reverse(scores);

            this.adapter.setScores(scores);

        }
        catch (Exception e)
        {
            Log.e("Error", e.getMessage());
        }


    }
}
