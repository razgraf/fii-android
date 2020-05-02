package ro.vansoftware.vantiles;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.TextureView;

import androidx.annotation.Nullable;

public class GameActivity extends Activity implements TextureView.SurfaceTextureListener, Runnable {


    public static final String TAG = "GameActivity";

    private final Object lock = new Object();
    private TextureView textureView;
    private Surface surface;

    private Thread thread = null;
    private Canvas canvas;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.textureView = new TextureView(this);
        this.textureView.setSurfaceTextureListener(this);

        setContentView(this.textureView);

    }



    public void run() {
        Log.e(TAG,"run");
        while (true) {
            synchronized (lock) {

                if (!this.surface.isValid()) {
                    continue;
                }
                this.canvas = this.surface.lockCanvas(new Rect(0, 0, 2,2));
                // CODE
//            Tile tile = new Tile(this.context);
//            tile.draw(this.canvas);


                Rect r = new Rect(0, 0, 100, 100);
                Paint p = new Paint();
                p.setColor(Color.RED);
                this.canvas.drawRect(r, p);

                this.surface.unlockCanvasAndPost(this.canvas);

            }
        }
    }




    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        synchronized (this.lock){
            this.surface = new Surface(surfaceTexture);
            this.lock.notify();
        }

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        synchronized (this.lock) {
            this.surface = null;
        }
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
