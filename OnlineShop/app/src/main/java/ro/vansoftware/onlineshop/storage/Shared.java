package ro.vansoftware.onlineshop.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class Shared {

    public static final String STORAGE = "STORAGE";
    public static final String SETTINGS = "SETTINGS";


    public static final String USER_USERNAME = "USER_USERNAME";
    public static final String USER_PASSWORD = "USER_PASSWORD";

    public static final String SETTING_MESSAGE = "SETTING_MESSAGE";
    public static final String SETTING_AGE = "SETTING_AGE";


    public SharedPreferences instance;


    public Shared(Context context, String name){
        this.instance = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void set(String key, String value){
        SharedPreferences.Editor editor  = this.instance.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String get(String key, String defValue){
        return instance.getString(key, defValue);
    }

    public String get(String key){
        return get(key, null);
    }

}
