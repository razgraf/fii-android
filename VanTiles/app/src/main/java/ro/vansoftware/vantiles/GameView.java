package ro.vansoftware.vantiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import ro.vansoftware.vantiles.model.Note;
import ro.vansoftware.vantiles.shapes.Tile;
import ro.vansoftware.vantiles.utils.Utils;

public class GameView extends SurfaceView implements Runnable {

    public static final String TAG = "Tile";

    private Context context;
    private Thread thread = null;
    private boolean canDraw = false;
    private final Map<String, Paint> paints = Utils.getBasePaints();

    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private List<Tile> tiles = new ArrayList<>();
    private JSONArray notes;

    private List<Note> song = new ArrayList<>();
    private int songIndex = 0;


    private int width = 0;
    private int height = 0;


    private int lineIndex = 0;

    public GameView(Context context) {
        super(context);
        this.init(context, null);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, null);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, null);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init(context, null);
    }

    private void init(Context context, @Nullable AttributeSet set){
        this.context = context;
        this.surfaceHolder = getHolder();
        this.setZOrderOnTop(true);

        Map<String, Integer> screen = Utils.getScreenDimensions(context);
        this.width =  screen.get("width");
        this.height =  screen.get("height");

        readSong();

        Tile mold = new Tile(this.context);
        for(int i = 0; i < 20; i++){
            int line = i / 4;
            int column = i % 4;

            Tile tile = mold.copy();
            tile.move( 0 - (this.height / 4 * (line + 1)), column *  (this.width / 4), true, true );
            tiles.add(tile);
        }


    }



    private void drawBackground(){

        int lineWidth = 2;

        Rect background = new Rect(0,0, this.width , this.height);
        this.canvas.drawRect(background, paints.get("white"));

        Rect line1 = new Rect(this.width / 4,0, this.width/4 + lineWidth, this.height );
        Rect line2 = new Rect(this.width / 4 * 2,0, this.width/4 * 2 + lineWidth, this.height );
        Rect line3 = new Rect(this.width / 4 * 3,0, this.width/4 * 3 + lineWidth, this.height );

        this.canvas.drawRect(line1, paints.get("gray"));
        this.canvas.drawRect(line2, paints.get("gray"));
        this.canvas.drawRect(line3, paints.get("gray"));

    }

    private void setupTilesForOneLine(){
        try{
            JSONArray lineConfiguration = this.notes.getJSONArray(this.lineIndex);
            for (int t = 4 * this.lineIndex; t < 4 + (4 * this.lineIndex); t += 1)
                if(!this.tiles.get((t)).isUsed)
                {
                    int line = this.lineIndex % 4;
                    int column = t%4;

                    this.tiles.get(t).isUsed = true;
                    this.tiles.get(t).isVisible = (lineConfiguration.getInt(column) == 1);
                    this.tiles.get(t).move( - (line + 1) * height / 4, column * width/4, true, true);

                }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void ACTUAL_SETUP_TILES(){
        /**
         *
         *
         * Go through the json lines and construct a stack of notes
         * Setup the initial 20 notes with coordinates
         * Parse the 20 tile holders each time
         *      If one is below the screen (it finished its role), reassign it with the next note in the stack (put it above, on the right column)
         */

        


    }

    private void setupTiles(int offset){
        try{
            int trueLineIndex = offset + this.lineIndex;
            JSONArray lineConfiguration = this.notes.getJSONArray(trueLineIndex);
            for (int t = 4 * trueLineIndex; t < 4 + (4 * trueLineIndex); t += 1)
                if(!this.tiles.get((t)).isUsed)
                {
                    int line = trueLineIndex % 4;
                    int column = t%4;

                    this.tiles.get(t).isUsed = true;
                    this.tiles.get(t).isVisible = (lineConfiguration.getInt(column) == 1);
                    this.tiles.get(t).move( - (line + 1) * height / 4, column * width/4, true, true);

                }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void printTiles(int offset){
        int trueLineIndex = offset + this.lineIndex;

        try{
            for (int t = 4 * trueLineIndex; t < 4 + (4 * trueLineIndex); t += 1) {
                Tile tile = this.tiles.get(t);
                tile.move(10, 0, false, false);

                if(tile.isBelowScreen()){
                    for (int markUnused = 4 * trueLineIndex; markUnused < 4 + (4 * trueLineIndex); markUnused += 1)
                        this.tiles.get(markUnused).isUsed = false;
                    this.lineIndex += 1;
                    Log.e(TAG, "next line");
                    Log.e(TAG, this.notes.getJSONArray( offset + this.lineIndex).toString());
                    break;
                }

                if(tile.isVisible) tile.draw(this.canvas);
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private void drawTiles(){
        try{


            this.setupTiles(0);
            this.printTiles(0);

            for(int offset = 0; offset < 2; offset ++){
                this.setupTiles(offset);
                this.printTiles(offset);
            }




        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void readSong() {
        try {

            InputStream stream = context.getAssets().open("lines.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            this.notes = new JSONArray(new String(buffer));


            for(int i = 0; i < this.notes.length(); i++){
                JSONArray line  = this.notes.getJSONArray(i);
                for(int j = 0; j < line.length(); j++){
                    Note note = new Note(i, j, (line.getInt(j) == 1));
                    this.song.add(note);
                }
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void run() {
        Log.e(TAG,"run");
        while (this.canDraw){
            if(!this.surfaceHolder.getSurface().isValid()){
                continue;
            }
            this.canvas = this.surfaceHolder.lockCanvas();


            this.drawBackground();
            this.drawTiles();

            this.surfaceHolder.unlockCanvasAndPost(this.canvas);
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
