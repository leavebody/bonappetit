

import java.util.Hashtable;
import java.io.*;

public class DicBuild{
	public static void main(String[] args) throws FileNotFoundException, IOException {

		Hashtable<String, Integer> ingredient = new Hashtable<String, Integer>();
		Hashtable<String, Integer> category = new Hashtable<String, Integer>();
		Hashtable<String, Integer> subcategory = new Hashtable<String, Integer>();

		File file = new File("Clean_Dic.csv");
		BufferedReader reader = new BufferedReader(new FileReader(file));
    	String text = null;
    	int i = 0;
    	int j = 0;
    	int l = 0;
    	while ((text = reader.readLine()) != null) {
    		String temp[]= text.toLowerCase().split(",");
    		if (!ingredient.contains(temp[0])) {
        		ingredient.put(temp[0], i);
        		i++;
        	}
        	if (!category.contains(temp[1])) {
        		category.put(temp[1], j);
        		j++;
        	}
        	if (!subcategory.contains(temp[2])) {
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
/*
        FileOutputStream file = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(object);
        out.close();
        file.close();

        FileOutputStream file = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(object);
        out.close();
        file.close();
*/

	}
}

