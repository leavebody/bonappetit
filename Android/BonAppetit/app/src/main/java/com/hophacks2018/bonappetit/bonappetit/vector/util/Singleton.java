package com.hophacks2018.bonappetit.bonappetit.vector.util;
import java.io.*;
import java.util.HashMap;

class Singleton
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
 
    // private constructor restricted to this class itself
    private Singleton() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        // Reading the object from a file
        FileInputStream file = new FileInputStream("./src/util/ingredient.ser");
        ObjectInputStream in = new ObjectInputStream(file);
        // Method for deserialization of object
        ingredient = (HashMap<String, Integer>) in.readObject();
        in.close();
        file.close();
        ingredientN = ingredient.size();

        file = new FileInputStream("./src/util/subcategory.ser");
        in = new ObjectInputStream(file);
        // Method for deserialization of object
        subcategory = (HashMap<String, Integer>) in.readObject();
        in.close();
        file.close();
        subcategoryN = subcategory.size();

        file = new FileInputStream("./src/util/category.ser");
        in = new ObjectInputStream(file);
        // Method for deserialization of object
        category = (HashMap<String, Integer>) in.readObject();
        in.close();
        file.close();
        categoryN = category.size();

        file = new FileInputStream("./src/util/ingrSub.ser");
        in = new ObjectInputStream(file);
        // Method for deserialization of object
        ingrSub = (double[][]) in.readObject();
        in.close();
        file.close();

        file = new FileInputStream("./src/util/ingrCate.ser");
        in = new ObjectInputStream(file);
        // Method for deserialization of object
        ingrCate = (double[][]) in.readObject();
        in.close();
        file.close();
    }
 
    // static method to create instance of Singleton class
    public static Singleton getInstance () throws IOException, FileNotFoundException, ClassNotFoundException
    {
        if (single_instance == null)
            single_instance = new Singleton();
        return single_instance;
    }
}