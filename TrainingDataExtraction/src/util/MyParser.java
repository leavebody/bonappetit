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
    	temp = null;
    	start = index - 1
    	end = start + 9
		while(html.substring(start,end)!="<strong>" && start > 0){
			temp = html.substring(start,end);
			start--;
			end--;
		}


        String regexe = "/\\<strong>\\w+\\<strong> - <strong>Wikipedia</strong>}/";
        Pattern pattern = Pattern.compile(regexe);
		Matcher matcher = pattern.matcher(html);
		if (matcher.find()) {
			String result = matcher.group(0);
			length = length(result)
    		return result.substring(8, length-36 ) //prints /{item}/
		} else {
    		return null;
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
