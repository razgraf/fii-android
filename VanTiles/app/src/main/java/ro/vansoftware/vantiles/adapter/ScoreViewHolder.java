package ro.vansoftware.vantiles.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ro.vansoftware.vantiles.R;
import ro.vansoftware.vantiles.activities.GameResultActivity;
import ro.vansoftware.vantiles.model.Score;


public class ScoreViewHolder {

    private TextView scoreView;
    private TextView timestampView;
    private View containerView;

    public ScoreViewHolder(View view){

        this.containerView = view.findViewById(R.id.container);
        this.scoreView = view.findViewById(R.id.score);
        this.timestampView = view.findViewById(R.id.timestamp);
    }

    public void setData(final Context context, final Score score){
        this.scoreView.setText(score.getScore());
        this.timestampView.setText(score.getTimestamp());
        this.containerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String body = "Hey! I've hit " +  score.getScore() + " pts on Van Tiles. Can you beat my highscore?";
                String title = "Van Tiles";

                intent.putExtra(Intent.EXTRA_SUBJECT, title);
                intent.putExtra(Intent.EXTRA_TEXT, body);
                context.startActivity(Intent.createChooser(intent, "Share your score on..."));

            }
        });
    }

}
