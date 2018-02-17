package util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        //todo
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
        //todo
        return new String();
    }
}
