package com.hophacks2018.bonappetit.bonappetit.vector.util;
import android.content.Context;

import com.hophacks2018.bonappetit.bonappetit.R;

import java.io.*;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

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
    private Singleton(Context c) throws IOException, FileNotFoundException, ClassNotFoundException
    {

        HashMap<String, Integer> ingredient = new HashMap<String, Integer>();
        HashMap<String, Integer> category = new HashMap<String, Integer>();
        HashMap<String, Integer> subcategory = new HashMap<String, Integer>();

        File file = new File("Clean_Dic.csv");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String text = null;
        int i = 0;
        int j = 0;
        int l = 0;
        while ((text = reader.readLine()) != null) {
            String temp[]= text.toLowerCase().split(",");

            if (!ingredient.containsKey(temp[0])) {
                ingredient.put(temp[0], i);
                i++;
            }
            if (!category.containsKey(temp[1])) {
                category.put(temp[1], j);
                j++;
            }
            if (!subcategory.containsKey(temp[2])) {
                subcategory.put(temp[2], l);
                l++;
            }
        }
        reader.close();

        double ingrCate[][] = new double[j][i];
        double ingrSub[][] = new double[l][i];

        reader = new BufferedReader(new FileReader(file));
        text = null;
        while ((text = reader.readLine()) != null) {
            String temp[]= text.toLowerCase().split(",");
            ingrCate[category.get(temp[1])][ingredient.get(temp[0])]+=1;
            ingrSub[subcategory.get(temp[2])][ingredient.get(temp[0])]+=1;
        }
        reader.close();

        for (int k = 0; k < i; k++) {
            double sum = 0;
            for (int h = 0; h < j; h++) {
                sum += ingrCate[h][k];
            }
            for (int h = 0; h < j; h++) {
                ingrCate[h][k] = ingrCate[h][k]/sum;
            }
        }
        for (int k = 0; k < i; k++) {
            double sum = 0;
            for (int h = 0; h < l; h++) {
                sum += ingrSub[h][k];
            }
            if (sum != 0) {
                for (int h = 0; h < l; h++) {
                    ingrSub[h][k] = ingrSub[h][k]/sum;
                }
            }
        }

        FileOutputStream file1 = c.openFileOutput("ingredient.ser", MODE_PRIVATE);
        ObjectOutputStream out1 = new ObjectOutputStream(file1);
        out1.writeObject(ingredient);
        out1.close();
        file1.close();

        FileOutputStream file2 = c.openFileOutput("subcategory.ser", MODE_PRIVATE);
        ObjectOutputStream out2 = new ObjectOutputStream(file2);
        out2.writeObject(subcategory);
        out2.close();
        file2.close();

        FileOutputStream file3 = c.openFileOutput("category.ser", MODE_PRIVATE);
        ObjectOutputStream out3 = new ObjectOutputStream(file3);
        out3.writeObject(category);
        out3.close();
        file3.close();

        FileOutputStream file4 = c.openFileOutput("ingrCate.ser",MODE_PRIVATE);
        ObjectOutputStream out4 = new ObjectOutputStream(file4);
        out4.writeObject(ingrCate);
        out4.close();
        file4.close();

        FileOutputStream file5 = c.openFileOutput("ingrSub.ser",MODE_PRIVATE);
        ObjectOutputStream out5 = new ObjectOutputStream(file5);
        out5.writeObject(ingrSub);
        out5.close();
        file5.close();
        // Reading the object from a file
        this.c = c;


        FileInputStream fis = c.openFileInput("ingredient.ser");
        ObjectInputStream is = new ObjectInputStream(fis);
        ingredient = (HashMap<String, Integer>) is.readObject();
        is.close();
        fis.close();
        ingredientN = ingredient.size();

        fis = c.openFileInput("subcategory.ser");
        is = new ObjectInputStream(fis);
        // Method for deserialization of object
        subcategory = (HashMap<String, Integer>) is.readObject();
        is.close();
        fis.close();
        subcategoryN = subcategory.size();


        fis = c.openFileInput("category.ser");
        is = new ObjectInputStream(fis);
        // Method for deserialization of object
        category = (HashMap<String, Integer>) is.readObject();
        is.close();
        fis.close();
        categoryN = category.size();

        fis = c.openFileInput("ingrSub.ser");
        is = new ObjectInputStream(fis);
        // Method for deserialization of object
        ingrSub = (double[][]) is.readObject();
        is.close();
        fis.close();


        fis = c.openFileInput("ingrCate.ser");
        is = new ObjectInputStream(fis);
        // Method for deserialization of object
        ingrCate = (double[][]) is.readObject();
        is.close();
        fis.close();

    }

    // static method to create instance of Singleton class
    public static Singleton getInstance (Context c) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        if (single_instance == null)
            single_instance = new Singleton(c);
        return single_instance;
    }
}