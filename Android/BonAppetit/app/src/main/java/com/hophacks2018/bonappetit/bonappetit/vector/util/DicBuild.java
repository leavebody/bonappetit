package com.hophacks2018.bonappetit.bonappetit.vector.util;

import java.util.HashMap;
import java.io.*;

public class DicBuild{
	public static void main(String[] args) throws FileNotFoundException, IOException {

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

        FileOutputStream file1 = new FileOutputStream("ingredient.ser");
        ObjectOutputStream out1 = new ObjectOutputStream(file1);
        out1.writeObject(ingredient);
        out1.close();
        file1.close();

        FileOutputStream file2 = new FileOutputStream("subcategory.ser");
        ObjectOutputStream out2 = new ObjectOutputStream(file2);
        out2.writeObject(subcategory);
        out2.close();
        file2.close();

        FileOutputStream file3 = new FileOutputStream("category.ser");
        ObjectOutputStream out3 = new ObjectOutputStream(file3);
        out3.writeObject(category);
        out3.close();
        file3.close();

        FileOutputStream file4 = new FileOutputStream("ingrCate.ser");
        ObjectOutputStream out4 = new ObjectOutputStream(file4);
        out4.writeObject(ingrCate);
        out4.close();
        file4.close();

        FileOutputStream file5 = new FileOutputStream("ingrSub.ser");
        ObjectOutputStream out5 = new ObjectOutputStream(file5);
        out5.writeObject(ingrSub);
        out5.close();
        file5.close();
	}
}

