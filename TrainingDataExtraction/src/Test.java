import util.HTTPGetter;
import util.MyParser;


public class Test {

    public Test(String url) {
        HTTPGetter getter = new HTTPGetter(url);
        String html = getter.getHTML();
        String name = MyParser.BingHTMLParser(html);
        System.out.println(name);
    }

    static public void main(String[] args) {
        String url = "https://en.wikibooks.org/wiki/Cookbook:Mapo_Doufu";
        url = "https://www.bing.com/search?q=soon+tofu";
        Test t = new Test(url);
    }
}
