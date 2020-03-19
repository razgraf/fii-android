package ro.vansoftware.onlineshop.storage;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ro.vansoftware.onlineshop.model.Product;

public class Internal {


    private static final String FILE = "ONLINE_SHOP_STORAGE.txt";



    public static void setProducts(Context context, ArrayList<Product> products) throws Exception{
        File file = new File(context.getFilesDir(), FILE);
        boolean created = file.exists()  &&  file.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(products);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public static ArrayList<Product> getProducts(Context context) throws Exception{

        File file = new File(context.getFilesDir(), FILE);
        if(!file.exists()) return new ArrayList<>();
        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
        return (ArrayList<Product>) stream.readObject();


    }




}
