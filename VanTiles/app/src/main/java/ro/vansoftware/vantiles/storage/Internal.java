package ro.vansoftware.vantiles.storage;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import ro.vansoftware.vantiles.model.Score;

public class Internal {


    private static final String FILE = "VAN_TILES_SCORE.txt";


    public static void setScores(Context context, ArrayList<Score> scores) throws Exception {
        File file = new File(context.getFilesDir(), FILE);
        boolean created = file.exists()  &&  file.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(scores);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public static ArrayList<Score> getScores(Context context) throws Exception {

        File file = new File(context.getFilesDir(), FILE);
        if(!file.exists()) return new ArrayList<>();
        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
        return (ArrayList<Score>) stream.readObject();


    }




}
