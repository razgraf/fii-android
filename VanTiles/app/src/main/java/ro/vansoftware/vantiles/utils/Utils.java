package ro.vansoftware.vantiles.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Map;

import ro.vansoftware.vantiles.R;

public class Utils {

    public static Map<String, Integer> getScreenDimensions(Context context){
        Map<String,Integer> result = new HashMap<String,Integer>();
        DisplayMetrics displayMetrics = new DisplayMetrics();

        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        result.put("height", displayMetrics.heightPixels);
        result.put("width", displayMetrics.widthPixels);
        return result;
    }

    public static Map<String, Paint> getBasePaints(){

        Map<String, Paint> result = new HashMap<String, Paint>();

        Paint white = new Paint(Paint.ANTI_ALIAS_FLAG);
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);
        result.put("white", white);

        Paint black = new Paint(Paint.ANTI_ALIAS_FLAG);
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);
        result.put("black", black);

        Paint gray = new Paint(Paint.ANTI_ALIAS_FLAG);
        gray.setColor(Color.parseColor("#dddddd"));
        gray.setStyle(Paint.Style.FILL);
        result.put("gray", gray);

        return result;

    }




}
