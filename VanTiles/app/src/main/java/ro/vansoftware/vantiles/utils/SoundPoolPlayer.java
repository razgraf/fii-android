package ro.vansoftware.vantiles.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

import ro.vansoftware.vantiles.R;

public class SoundPoolPlayer {

    private SoundPool shortPlayer = null;
    private HashMap<Integer, Integer> sounds = new HashMap<>();

    public SoundPoolPlayer(Context context)
    {

        this.shortPlayer = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);

        this.sounds.put(R.raw.a3, this.shortPlayer.load(context, R.raw.a3, 1));
        this.sounds.put(R.raw.a4, this.shortPlayer.load(context, R.raw.a4, 1));
        this.sounds.put(R.raw.a5, this.shortPlayer.load(context, R.raw.a5, 1));

        this.sounds.put(R.raw.b3, this.shortPlayer.load(context, R.raw.b3, 1));
        this.sounds.put(R.raw.b4, this.shortPlayer.load(context, R.raw.b4, 1));
        this.sounds.put(R.raw.b5, this.shortPlayer.load(context, R.raw.b5, 1));

        this.sounds.put(R.raw.c3, this.shortPlayer.load(context, R.raw.c3, 1));
        this.sounds.put(R.raw.c4, this.shortPlayer.load(context, R.raw.c4, 1));
        this.sounds.put(R.raw.c5, this.shortPlayer.load(context, R.raw.c5, 1));
        this.sounds.put(R.raw.c6, this.shortPlayer.load(context, R.raw.c6, 1));

        this.sounds.put(R.raw.d3, this.shortPlayer.load(context, R.raw.d3, 1));
        this.sounds.put(R.raw.d4, this.shortPlayer.load(context, R.raw.d4, 1));
        this.sounds.put(R.raw.d5, this.shortPlayer.load(context, R.raw.d5, 1));

        this.sounds.put(R.raw.e3, this.shortPlayer.load(context, R.raw.e3, 1));
        this.sounds.put(R.raw.e4, this.shortPlayer.load(context, R.raw.e4, 1));
        this.sounds.put(R.raw.e5, this.shortPlayer.load(context, R.raw.e5, 1));


        this.sounds.put(R.raw.f3, this.shortPlayer.load(context, R.raw.f3, 1));
        this.sounds.put(R.raw.f4, this.shortPlayer.load(context, R.raw.f4, 1));
        this.sounds.put(R.raw.f5, this.shortPlayer.load(context, R.raw.f5, 1));



        this.sounds.put(R.raw.g3, this.shortPlayer.load(context, R.raw.g3, 1));
        this.sounds.put(R.raw.g4, this.shortPlayer.load(context, R.raw.g4, 1));
        this.sounds.put(R.raw.g5, this.shortPlayer.load(context, R.raw.g5, 1));



    }

    public void playShortResource(int resource) {
        try{
            int iSoundId = (Integer) this.sounds.get(resource);
            this.shortPlayer.play(iSoundId, 0.99f, 0.99f, 0, 0, 1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    // Cleanup
    public void release() {
        this.shortPlayer.release();
        this.shortPlayer = null;
    }
}