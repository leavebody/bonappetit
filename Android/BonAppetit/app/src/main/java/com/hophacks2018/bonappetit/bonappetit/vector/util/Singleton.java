package com.hophacks2018.bonappetit.bonappetit.vector.util;
import android.content.Context;

import com.hophacks2018.bonappetit.bonappetit.R;

import java.io.*;
import java.util.HashMap;

public class Singleton
{
    // static variable single_instance of type Singleton
    private static Singleton single_instance = null;
 
    // variable of type String
    public HashMap<String, Integer> ingredient = null;
    public HashMap<String, Integer> subcategory = null;
    public HashMap<String, Integer> category = null;
    public double ingrCate[][];
    public double ingrSub[][];
    public int ingredientN = 0;
    public int subcategoryN = 0;
    public int categoryN = 0;
    private Context c;
 
    // private constructor restricted to this class itself
    private Singleton() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        // Reading the object from a file

//
//        InputStream is = this.c.getApplicationContext().getResources().openRawResource(R.raw.ingredient);
//        ObjectInputStream in = new ObjectInputStream(is);
//
//        // Method for deserialization of object
//        ingredient = (HashMap<String, Integer>) in.readObject();
//        in.close();
//        ingredientN = ingredient.size();
//
//        is = this.c.getApplicationContext().getResources().openRawResource(R.raw.subcategory);
//        in = new ObjectInputStream(is);
//        // Method for deserialization of object
//        subcategory = (HashMap<String, Integer>) in.readObject();
//        in.close();
//        subcategoryN = subcategory.size();
//
//        is = this.c.getApplicationContext().getResources().openRawResource(R.raw.category);
//        in = new ObjectInputStream(is);
//        // Method for deserialization of object
//        category = (HashMap<String, Integer>) in.readObject();
//        in.close();
//        categoryN = category.size();
//
//        is = this.c.getApplicationContext().getResources().openRawResource(R.raw.ingrSub);
//        in = new ObjectInputStream(is);
//        // Method for deserialization of object
//        ingrSub = (double[][]) in.readObject();
//        in.close();
//
//        is = this.c.getApplicationContext().getResources().openRawResource(R.raw.ingrCate);
//        in = new ObjectInputStream(is);
//        // Method for deserialization of object
//        ingrCate = (double[][]) in.readObject();
//        in.close();
    }
 
    // static method to create instance of Singleton class
    public static Singleton getInstance () throws IOException, FileNotFoundException, ClassNotFoundException
    {
        if (single_instance == null)
            single_instance = new Singleton();
        return single_instance;
    }
}