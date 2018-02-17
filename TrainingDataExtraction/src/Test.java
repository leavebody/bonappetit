import util.HTTPGetter;


public class Test {

    public Test(String url) {
        HTTPGetter getter = new HTTPGetter(url);
        String html = getter.getHTML();
        System.out.println(html);
    }

    static public void main(String[] args) {
        String url = "https://en.wikibooks.org/wiki/Cookbook:Mapo_Doufu";
        url = "https://www.bing.com/search?q=mapo+tofu";
        Test t = new Test(url);
    }
}
