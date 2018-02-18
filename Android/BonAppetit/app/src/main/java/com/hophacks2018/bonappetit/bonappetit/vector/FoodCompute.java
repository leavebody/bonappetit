package com.hophacks2018.bonappetit.bonappetit.vector;

import com.hophacks2018.bonappetit.bonappetit.util.MyParser;
import com.hophacks2018.bonappetit.bonappetit.vector.util.HTTPGetter;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;



public class FoodCompute {
    private String rawName; // The name recognized from the menu.
    private String name; // The official name of this dish.
    private String cookname;
    public String splitName;
    private String description; // Detailed description of the dish got from google knowledge graph.
    private double[] ingredientRawVector;
    private double[] subcataRawVector;
    private double[] cataRawVector;

    public FoodCompute(String rawName) {
        this.rawName = rawName;
    }

    public String getName() {
        String splited =  this.rawName.replaceAll("\\s", "+");
        String URL = "https://www.bing.com/search?q=" + splited + "+wiki";
        System.out.println(splited);
        this.splitName = splited;
        HTTPGetter getOfficial = new HTTPGetter(URL);
        System.out.println(getOfficial.getStatus());
        String html1 = getOfficial.getHTML();
        System.out.println(html1.length());
        MyParser aa = new MyParser();
        String realname = aa.BingHTMLParser(html1);
        this.name = realname;
        System.out.println(realname);
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTurName() {
        if (this.name == null){
            return null;
        }
        String splited =  this.name.replaceAll("\\s", "+");
        String URL = "https://en.wikibooks.org/w/index.php?search=" + splited;
        HTTPGetter getOfficial = new HTTPGetter(URL);
        System.out.println(getOfficial.getStatus());
        String html1 = getOfficial.getHTML();
        MyParser aa = new MyParser();
        String bb = aa.CookbookSearchHTMLParser(html1);
        System.out.println(bb);
        this.cookname = bb;
        return bb;
    }

    public String setDescription() {
        //https://en.wikibooks.org/wiki/Cookbook:Mapo_Doufu
        if (this.cookname == null){
            return null;
        }
        String URL = "https://en.wikibooks.org/wiki/Cookbook:" + this.cookname;
        HTTPGetter getOfficial = new HTTPGetter(URL);
        System.out.println(getOfficial.getStatus());
        String html1 = getOfficial.getHTML();
        return html1;
    }

    public String getRawName() {
        return rawName;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }

    public double[] getIngredientRawVector() {
        return ingredientRawVector;
    }

    public void setIngredientRawVector(double[] ingredientRawVector) {
        this.ingredientRawVector = ingredientRawVector;
    }

    public double[] getSubcataRawVector() {
        return subcataRawVector;
    }

    public void setSubcataRawVector(double[] subcataRawVector) {
        this.subcataRawVector = subcataRawVector;
    }

    public double[] getCataRawVector() {
        return cataRawVector;
    }

    public void setCataRawVector(double[] cataRawVector) {
        this.cataRawVector = cataRawVector;
    }

    public ArrayList<double[]> getFeatures() {
        this.getName();
        this.getTurName();
        String outt = this.setDescription();
        MyParser res = new MyParser();
        ArrayList<double[]> result = null;
        try {
            result = res.CookbookHTMLParser(outt);
        } catch (Exception a) {
            a.printStackTrace();
        }
        return result;
    }

    public void WriteIntoFile(ArrayList<double[]> result, String foodName){
        String target = "./data/filename-"+foodName+".txt";
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(

                    new FileOutputStream(target), "utf-8"));
            for (double [] var : result){
                for (double temp : var) {
                    writer.write(String.valueOf(temp));
                    writer.write(" ");
                }
                writer.write("\n");
            }
        } catch (IOException ex) {
            // report
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }

    }
    static public void main(String[] args) {

//        BufferedReader reader = null;
//
//        ArrayList<String> names = new ArrayList<String>();
//        try {
//            File file = new File("data.txt");
//            reader = new BufferedReader(new FileReader(file));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                names.add(line);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                reader.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        ArrayList<String> names = new ArrayList<String>();
        names.add("Lamingtons");

        for (String temp : names) {
            FoodCompute obj = new FoodCompute(temp);
            obj.getName();
            obj.getTurName();
            String outt = obj.setDescription();
            MyParser res = new MyParser();
            ArrayList<double[]> result = null;
            try {
                result = res.CookbookHTMLParser(outt);
            } catch (Exception a) {
                a.printStackTrace();
            }
            obj.WriteIntoFile(result, obj.splitName);
            System.out.println(result);
        }
    }

}