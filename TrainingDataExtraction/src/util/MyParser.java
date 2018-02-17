package util;

import java.util.ArrayList;

public class MyParser {
    /**
     * Return the name from wiki in this page.
     * Find and return the string between "<strong>" and "</strong> - <strong>Wikipedia</strong>".
     * If not found, return null.
     * @param html raw html code of this page
     * @return a string of name or null
     */
    public static String BingHTMLParser(String html){
        //todo
    	int index = html.indexOf("</strong> - <strong>Wikipedia</strong>}");
    	String temp = null;
    	int start = index - 1;
    	int end = start + 9;
		while(html.substring(start,end)!="<strong>" && start > 0){
			temp = html.substring(start,end);
			start--;
			end--;
		}
		if (start == 0){
			return null;
		}
		else{
			return html.substring(end+1, index);
		}
    }

    /**
     * Get a list of ingredients of the dish from html string of the cookbook web page.
     * @param html raw html code of the web page
     * @return a list of ingredients
     */
    public static ArrayList<String> CookbookHTMLParser(String html){
        //todo
        return new ArrayList<String>();
    }
}
