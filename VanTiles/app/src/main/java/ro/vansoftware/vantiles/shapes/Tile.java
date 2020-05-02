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
import ro.vansoftware.vantiles.model.Size;
import ro.vansoftware.vantiles.utils.Utils;

public class Tile {

    private Context context;
    private Location location = Constants.INITIAL_TILE_LOCATION;
    private Rect rectangle;
    private Paint paint;

    public boolean isVisible = false;
    public boolean isUsed = false;


    public Tile(Context context) {
        this.context = context;
        this.initialize();
    }

    public void initialize(){
        this.rectangle = new Rect();
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setColor(Color.RED);
        this.paint.setStyle(Paint.Style.FILL);

        Map<String, Integer> screen = Utils.getScreenDimensions(this.context);
        int width = Math.round(screen.get("width")) / 4;
        int height = Math.round(screen.get("height")) / 4;
        this.setLocation(new Location(-height,-width, new Size(width, height)));
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
        canvas.drawRect(this.rectangle, this.paint);
    }

    public Tile copy(){
        Tile result = new Tile(this.context);
        result.setLocation(this.location);
        return result;
    }
}
