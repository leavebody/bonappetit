

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.nio.file.Files;
import java.nio.charset.Charset;
import java.lang.StringBuilder;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;


// Java program implementing Singleton class
// with getInstance() method
class Singleton
{
    // static variable single_instance of type Singleton
    private static Singleton single_instance = null;
 
    // variable of type String
    public String s;
 
    // private constructor restricted to this class itself
    private Singleton()
    {
        s = "Hello I am a string part of Singleton class";
    }
 
    // static method to create instance of Singleton class
    public static Singleton getInstance()
    {
        if (single_instance == null)
            single_instance = new Singleton();
 
        return single_instance;
    }
}


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
     * @return Arraylist<double[] ingredientRawVector, double[] subcataRawVector, double[] cataRawVector>
     *          or null is input is invalid
     */
    public static ArrayList<double[]> CookbookHTMLParser(String html){
        int index1 = html.indexOf("title=\"Edit section: Ingredients\"");
        int index2 = html.indexOf("<ul>", index1);
        int index3 = html.indexOf("<h2>", index2);
        String target = html.substring(index2+4, index3);
        target.replace("<", " < ");
        target.replace(">", " > ");
        String[] temp = target.split(" ");
        for (String s : temp) {
            
        } 

        return null;
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

    public static void main(String[] args) throws IOException{
        
        final String EoL = System.getProperty("line.separator");
        List<String> lines = Files.readAllLines(Paths.get("htmltest.html"),
                                Charset.defaultCharset());

        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append(EoL);
        }
        final String content = sb.toString();
        
        System.out.print(CookbookSearchHTMLParser(content));
    }
}
