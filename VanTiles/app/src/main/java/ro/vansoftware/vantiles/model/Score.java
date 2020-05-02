package ro.vansoftware.vantiles.model;



import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Score implements Serializable, Parcelable {


    private String score;
    private String timestamp;


    public Score(String score, String timestamp) {
        this.score = score;
        this.timestamp = timestamp;
    }



    protected Score(Parcel in) {
        this.score = in.readString();
        this.timestamp = in.readString();
    }

    public static final Creator<Score> CREATOR = new Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.score);
        dest.writeString(this.timestamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }



    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
