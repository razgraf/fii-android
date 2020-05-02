package ro.vansoftware.vantiles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

import androidx.annotation.Nullable;

public class GameTextureView extends TextureView implements Runnable {

    public static final String TAG = "GameTextureView";

    private Context context;
    private Thread thread = null;
    private boolean canDraw = false;

    private Surface surface;
    private Canvas canvas;


    public GameTextureView(Context context) {
        super(context);
        this.init(context, null);
    }

    public GameTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, null);
    }

    public GameTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, null);
    }

    public GameTextureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init(context, null);
    }

    private void init(Context context, @Nullable AttributeSet set){
        this.context = context;
        this.setBackgroundColor(Color.WHITE);

        SurfaceTexture texture = this.getSurfaceTexture();
        this.surface = new Surface(texture);

    }



    @Override
    public void run() {
        Log.e(TAG,"run");
        while (this.canDraw){
            if(!this.surface.isValid()){
                continue;
            }
            this.canvas = this.lockCanvas();
            // CODE
//            Tile tile = new Tile(this.context);
//            tile.draw(this.canvas);


            Rect r = new Rect(0,0, 100, 100);
            Paint p = new Paint();
            p.setColor(Color.RED);
            this.canvas.drawRect(r,p);

            this.surface.unlockCanvasAndPost(this.canvas);

        }
    }

    public void pause(){
        Log.e(TAG, "resume");
        this.canDraw = false;

        while(true){
            try {
                this.thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e(TAG, "Thread could not join...");
            }

        }
        this.thread = null;
    }

    public void resume(){
        Log.e(TAG, "resume");
        this.canDraw = true;
        this.thread = new Thread(this);
        this.thread.start();
    }
}
