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

    public static Map<String, Integer> getColors(){
        Map<String, Integer> result = new HashMap<String, Integer>();

        result.put("white", Color.WHITE);
        result.put("black", Color.BLACK);
        result.put("gray", Color.parseColor("#dddddd"));
        result.put("light", Color.parseColor("#f5f5f5"));
        result.put("van",Color.parseColor("#4C20FA"));
        result.put("dark",Color.parseColor("#1F2133"));


        return result;
    }

    public static Map<String, Paint> getBasePaints(){

        Map<String,Integer> colors = getColors();
        Map<String, Paint> result = new HashMap<String, Paint>();

        Paint white = new Paint(Paint.ANTI_ALIAS_FLAG);
        white.setColor(colors.get("white"));
        white.setStyle(Paint.Style.FILL);
        result.put("white", white);

        Paint black = new Paint(Paint.ANTI_ALIAS_FLAG);
        black.setColor(colors.get("black"));
        black.setStyle(Paint.Style.FILL);
        result.put("black", black);

        Paint gray = new Paint(Paint.ANTI_ALIAS_FLAG);
        gray.setColor(colors.get("gray"));
        gray.setStyle(Paint.Style.FILL);
        result.put("gray", gray);

        Paint light = new Paint(Paint.ANTI_ALIAS_FLAG);
        light.setColor(colors.get("light"));
        light.setStyle(Paint.Style.FILL);
        result.put("light", light);

        Paint van = new Paint(Paint.ANTI_ALIAS_FLAG);
        van.setColor(colors.get("van"));
        van.setStyle(Paint.Style.FILL);
        result.put("van", van);


        return result;

    }


    public static Integer getResourceByNote(String note){

        if(note.equals("a3")) return R.raw.a3;
        if(note.equals("a4")) return R.raw.a4;
        if(note.equals("a5")) return R.raw.a5;
        if(note.equals("a")) return R.raw.a5;

        if(note.equals("b3")) return R.raw.b3;
        if(note.equals("b4")) return R.raw.b4;
        if(note.equals("b5")) return R.raw.b5;
        if(note.equals("b")) return R.raw.b5;

        if(note.equals("c3")) return R.raw.c3;
        if(note.equals("c4")) return R.raw.c4;
        if(note.equals("c5")) return R.raw.c5;
        if(note.equals("c6")) return R.raw.c6;
        if(note.equals("c")) return R.raw.c5;

        if(note.equals("d3")) return R.raw.d3;
        if(note.equals("d4")) return R.raw.d4;
        if(note.equals("d5")) return R.raw.d5;
        if(note.equals("d")) return R.raw.d5;

        if(note.equals("e3")) return R.raw.e3;
        if(note.equals("e4")) return R.raw.e4;
        if(note.equals("e5")) return R.raw.e5;
        if(note.equals("e")) return R.raw.e5;

        if(note.equals("f3")) return R.raw.f3;
        if(note.equals("f4")) return R.raw.f4;
        if(note.equals("f5")) return R.raw.f5;
        if(note.equals("f")) return R.raw.f5;

        if(note.equals("g3")) return R.raw.g3;
        if(note.equals("g4")) return R.raw.g4;
        if(note.equals("g5")) return R.raw.g5;
        if(note.equals("g")) return R.raw.g5;


        return R.raw.a5;
    }




}
