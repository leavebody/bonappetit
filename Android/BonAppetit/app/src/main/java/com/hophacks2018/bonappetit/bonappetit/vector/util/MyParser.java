package com.hophacks2018.bonappetit.bonappetit.vector.util;

import android.content.Context;

import com.hophacks2018.bonappetit.bonappetit.util.AppHolder;
import com.hophacks2018.bonappetit.bonappetit.util.Globals;
import com.hophacks2018.bonappetit.bonappetit.vector.Matrix;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Java program implementing Singleton class
// with getInstance() method

public class MyParser {
    /**
     * Return the name from wiki in this page.
     * Find and return the string between "<strong>" and "</strong> - <strong>Wikipedia</strong>".
     * If not found, return null.
     * @param html raw html code of this page
     * @return a string of name or null
     */
    public static String BingHTMLParser(String html){
        try {
            int index = html.indexOf("</strong> - <strong>Wikipedia</strong>");
            String temp = null;
            int start = index - 1;
            int end = start + 8;
            while (!html.substring(start, end).equals("<strong>") && start > 0) {
                temp = html.substring(start, end);
                start--;
                end--;
            }
            if (start == 0) {
                return null;
            } else {
                return html.substring(end, index);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get vectors of raw features of the dish from html string of the cookbook web page.
     * @param html raw html code of the web page
     * @return Arraylist<double[] titleRawVector, double[] ingredientRawVector, double[] subcataRawVector, double[] cataRawVector>
     *          or null is input is invalid
     */
    public static ArrayList<double[]> CookbookHTMLParser(String html) throws IOException, ClassNotFoundException {
        Singleton sin = Singleton.getInstance(AppHolder.getContext());
        double[] titleRawVector = new double[sin.ingredientN];
        double[] ingredientRawVector = new double[sin.ingredientN];
        double[] subcataRawVector;
        double[] cataRawVector;
        int index1 = html.indexOf("title=\"Edit section: Ingredients\"");
        int index2 = html.indexOf("<ul>", index1);
        int index3 = html.indexOf("<h2>", index2);

        int index4 = html.indexOf("<title>Cookbook:");
        int index5 = html.indexOf(" - Wikibooks", index4);
        String target = html.substring(index2+4, index3);
        target = target.replace("<", " < ");
        target = target.replace(">", " > ");
        String[] temp = target.toLowerCase().split(" ");

        for (String s : temp) {
            if (sin.ingredient.containsKey(s)) {
                ingredientRawVector[sin.ingredient.get(s)] += 1;
            }
        }

        for (String s : html.substring(index4+16, index5).toLowerCase().split(" ")) {
            if (sin.ingredient.containsKey(s)) {
                titleRawVector[sin.ingredient.get(s)] += 1;
            }
        }

        subcataRawVector = Matrix.multiply(sin.ingrSub, ingredientRawVector);
        cataRawVector = Matrix.multiply(sin.ingrCate, ingredientRawVector);
        ArrayList<double[]> result = new ArrayList<>(4);
        result.add(titleRawVector);
        result.add(ingredientRawVector);
        result.add(subcataRawVector);
        result.add(cataRawVector);
        return result;
        
    }

    /**
     * Get the cookbook name of the dish from html string of the cookbook search web page.
     * Find the first "/wiki/Cookbook:" in the string, and extract the string between it and the next "\"".
     * If there is no "/wiki/Cookbook:", abort.
     * If the extracted string equals "Table_of_Contents", abort.
     * @param html raw html code of the web page
     * @return the cookbook name, eg. Mapo_Doufu. Or null if aborted.
     */
    public static String CookbookSearchHTMLParser(String html){
        int index1 = html.indexOf("<a href=\"/wiki/Cookbook:");
        if (index1 == -1 || html.substring(index1+24, index1 + 41).equals("Table_of_Contents")) {
            return null;
        }
        int index2 = html.indexOf("\"", index1+24);
        return html.substring(index1+24, index2);
    }
/*
    public static void main(String[] args) throws IOException, ClassNotFoundException{


        final String EoL = System.getProperty("line.separator");
        List<String> lines = Files.readAllLines(Paths.get("wikitest.html"), Charset.defaultCharset());

        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append(EoL);
        }
        final String content = sb.toString();
        
        System.out.print(CookbookHTMLParser(content));
        /*
        final String EoL = System.getProperty("line.separator");
        List<String> lines = Files.readAllLines(Paths.get("htmltest.html"),
                                Charset.defaultCharset());

        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append(EoL);
        }
        final String content = sb.toString();
        
        System.out.print(CookbookSearchHTMLParser(content));

    }*/
}
