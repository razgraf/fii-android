package ro.vansoftware.vantiles.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import ro.vansoftware.vantiles.R;
import ro.vansoftware.vantiles.model.Score;

public class ScoreAdapter extends ArrayAdapter<Score> {

    private Context context;
    private ArrayList<Score> scores = new ArrayList<>();


    public ScoreAdapter(Context context, int resource, ArrayList<Score> scores){
        super(context,resource, scores);
        this.context = context;
        this.scores = scores;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ScoreViewHolder viewHolder;


        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_list_item, parent, false);
            viewHolder = new ScoreViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ScoreViewHolder) convertView.getTag();
        }

        Score score = this.scores.get(position);
        viewHolder.setData(this.context, score);

        return convertView;
    }

    @NonNull
    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores.clear();
        this.scores.addAll(scores);
        this.notifyDataSetChanged();
    }

}


