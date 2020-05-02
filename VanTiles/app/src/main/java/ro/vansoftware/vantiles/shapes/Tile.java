package ro.vansoftware.vantiles.shapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.Map;

import androidx.annotation.Nullable;
import ro.vansoftware.vantiles.constants.Constants;
import ro.vansoftware.vantiles.model.Location;
import ro.vansoftware.vantiles.model.Note;
import ro.vansoftware.vantiles.model.Size;
import ro.vansoftware.vantiles.utils.SoundPoolPlayer;
import ro.vansoftware.vantiles.utils.Utils;

public class Tile {

    private Context context;

    private Location location = Constants.INITIAL_TILE_LOCATION;
    private Rect rectangle;
    private Paint paint;
    public Note note;

    private Paint afterPaint;

    public boolean isClicked = false;

    public Tile(Context context) {
        this.context = context;
        this.initialize();
    }

    public void initialize(){
        this.rectangle = new Rect();
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setColor(Color.parseColor("#1F2133"));
        this.paint.setStyle(Paint.Style.FILL);

        this.afterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.afterPaint.setColor(Color.parseColor("#eeeeee"));
        this.afterPaint.setStyle(Paint.Style.FILL);

        Map<String, Integer> screen = Utils.getScreenDimensions(this.context);
        int width = Math.round(screen.get("width")) / 4;
        int height = Math.round(screen.get("height")) / 4;
        this.setLocation(new Location(-height,-width, new Size(width, height)));
    }


    public Location getLocation() {
        return location;
    }

    protected void setLocation(Location location){
        this.location = location;
        this.rectangle.top = this.location.top;
        this.rectangle.left = this.location.left;
        this.rectangle.bottom = this.location.bottom;
        this.rectangle.right = this.location.right;
    }

    public void move(int distanceTop, int distanceLeft, boolean topAbsolute, boolean leftAbsolute){

        this.setLocation(new Location(
                topAbsolute ? distanceTop : this.location.top + distanceTop,
                leftAbsolute ? distanceLeft : this.location.left + distanceLeft,
                    this.location.size));

    }

    public boolean isBelowScreen(){
        Map<String, Integer> screen = Utils.getScreenDimensions(this.context);
        return this.location.top >= screen.get("height");
    }


    public void draw(Canvas canvas){
        canvas.drawRect(this.rectangle, this.isClicked ? this.afterPaint : this.paint);
    }

    public Tile copy(){
        Tile result = new Tile(this.context);
        result.setLocation(this.location);
        result.note = this.note;

        return result;
    }

    public void click(SoundPoolPlayer player){
        if(!this.isClicked){
            this.isClicked = true;
            player.playShortResource(Utils.getResourceByNote(this.note.sound));
        }

    }
}
