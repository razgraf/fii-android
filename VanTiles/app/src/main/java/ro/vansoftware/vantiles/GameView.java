package ro.vansoftware.vantiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.media.MediaPlayer;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
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
import ro.vansoftware.vantiles.constants.Constants;
import ro.vansoftware.vantiles.model.Location;
import ro.vansoftware.vantiles.model.Note;
import ro.vansoftware.vantiles.shapes.Tile;
import ro.vansoftware.vantiles.utils.SoundPoolPlayer;
import ro.vansoftware.vantiles.utils.Utils;

public class GameView extends SurfaceView implements Runnable {

    public static final String TAG = "Tile";


    private Context context;
    private Thread thread = null;
    private boolean canDraw = false;
    private final Map<String, Paint> paints = Utils.getBasePaints();
    private final Map<String, Integer> colors = Utils.getColors();

    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private List<Tile> tiles = new ArrayList<>();
    private List<Note> song = new ArrayList<>();
    private int songIndex = 0;
    private boolean isSongFinished = false;

    private SoundPoolPlayer player;


    private int width = 0;
    private int height = 0;
    private int score = 0;
    private int speed = 20;


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
        for(int i = 0; i < Constants.TILE_COUNT; i++){
            Tile tile = mold.copy();
            tiles.add(tile);
        }

         this.player = new SoundPoolPlayer(context);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        float x = event.getX();
        float y = event.getY();

        Log.e(TAG, "x: " + x  + " - y: " + y);

        for(Tile tile: this.tiles){
            Location location = tile.getLocation();
            if(     tile.note.isVisible &&
                    (location.left <= x && x <= location.right) &&
                    (location.top <= y && y <= location.bottom)
            )
            {
                tile.click(player);
                this.score+=1;
                break;
            }
        }

        performClick();
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void setupTilesInitial(){
        int i;
        for(i = 0; i < Constants.TILE_COUNT && this.songIndex < this.song.size(); i +=1 ){
            Tile tile = this.tiles.get(i);
            Note note = this.song.get(i);
            tile.note = note;
            tile.move( - ((note.line % 5) + 1) * height / 4, note.column * width/4, true, true);
        }
        this.songIndex = i ;// - 1;
    }
    private void reassignTiles(){


        for(int i = 0; i < Constants.TILE_COUNT && this.songIndex < this.song.size(); i +=1 ){
            /* Should only account for the first 4 tiles - head - but let's ask all of them */
            Tile tile = this.tiles.get(i);
            if(tile.isBelowScreen()){
                Note note = this.song.get(this.songIndex);
                tile.note = note;
                tile.isClicked = false;
                tile.move( - height / 4, note.column * width/4, true, true);
                this.songIndex += 1;
                if(this.songIndex % 16 == 0) this.speed += 2;
            }
        }
    }
    private void moveTiles(){
        boolean anyTileAbove = false;
        for(Tile tile: this.tiles){
            if(tile.note.isVisible) tile.draw(this.canvas);
            if(!anyTileAbove && !tile.isBelowScreen()) anyTileAbove = true;
            tile.move(speed,0,false, false);
        }


        if(this.songIndex == this.song.size() && !anyTileAbove){
            this.isSongFinished = true;
        }
    }
    private void drawTiles(){
        try{

           if(this.songIndex == 0){
               this.setupTilesInitial();
           }

            this.reassignTiles();
            this.moveTiles();



        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void drawBackground(){

        int lineWidth = 5;

        Rect background = new Rect(0,0, this.width , this.height);
        this.canvas.drawRect(background, paints.get("light"));


        Rect line1 = new Rect(this.width / 4,0, this.width/4 + lineWidth, this.height );
        Rect line2 = new Rect(this.width / 4 * 2,0, this.width/4 * 2 + lineWidth, this.height );
        Rect line3 = new Rect(this.width / 4 * 3,0, this.width/4 * 3 + lineWidth, this.height );

        this.canvas.drawRect(line1, paints.get("gray"));
        this.canvas.drawRect(line2, paints.get("gray"));
        this.canvas.drawRect(line3, paints.get("gray"));

    }
    private void drawScore(){



        String text = this.score + "";

        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(50 * getResources().getDisplayMetrics().density);
        textPaint.setColor(this.colors.get("white"));

        int width = (int) textPaint.measureText(text);
        StaticLayout staticLayout = new StaticLayout(text, textPaint, (int) width, Layout.Alignment.ALIGN_CENTER, 1.0f, 0, false);


        Rect scoreBackground = new Rect( (this.width - width)/2 - 100, 25, (this.width + width)/2 + 100,  300);
        this.canvas.drawRect(scoreBackground, paints.get("van"));

        this.canvas.translate((float)(this.width - width)/2, 50);
        staticLayout.draw(this.canvas);
        this.canvas.translate(0,0);
    }

    private void readSong() {
        try {

            InputStream stream = context.getAssets().open("song.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            JSONArray notes = new JSONArray(new String(buffer));

            for(int i = 0; i < notes.length(); i++){
                JSONArray line  = notes.getJSONArray(i);
                for(int j = 0; j < line.length(); j++){
                    Note note = new Note(i, j, (! line.getString(j).equals("0")), line.getString(j));
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
        while (this.canDraw && !this.isSongFinished){
            if(!this.surfaceHolder.getSurface().isValid()){
                continue;
            }
            this.canvas = this.surfaceHolder.lockCanvas();


            this.drawBackground();
            this.drawTiles();
            this.drawScore();

            this.surfaceHolder.unlockCanvasAndPost(this.canvas);
        }

        if(this.isSongFinished) {
            Log.e(TAG,"song finished - run stopping");
            this.player.release();
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
