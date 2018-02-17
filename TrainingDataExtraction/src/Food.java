import util.HTTPGetter;
import util.MyParser;

public class Food {
    private String rawName; // The name recognized from the menu.
    private String name; // The official name of this dish.
    private String cookname;
    private double[] ingredientRawVector;
    private double[] subcataRawVector;
    private double[] cataRawVector;

    public Food (String rawName) {
        this.rawName = rawName;
    }
        String splited =  this.rawName.replaceAll("\\s", "+");
        String URL = "https://www.bing.com/search?q=" + splited;
        System.out.println(splited);
        HTTPGetter getOfficial = new HTTPGetter(URL);
        String html1 = getOfficial.getHTML();
        MyParser aa = new MyParser();
        String realname = aa.BingHTMLParser(html1);
        this.name = realname;
        return this.name;
    public String getTurName() {
        String splited =  this.rawName.replaceAll("\\s", "+");
        String URL = "https://en.wikibooks.org/w/index.php?search=" + splited;
        HTTPGetter getOfficial = new HTTPGetter(URL);
        String html1 = getOfficial.getHTML();
        MyParser aa = new MyParser();
        String bb = aa.CookbookSearchHTMLParser(html1);
        this.cookname = bb;
        return bb;
    public String setDescription() {
        //https://en.wikibooks.org/wiki/Cookbook:Mapo_Doufu
        String URL = "https://en.wikibooks.org/wiki/Cookbook:" + this.cookname;
        HTTPGetter getOfficial = new HTTPGetter(URL);
        String html1 = getOfficial.getHTML();
        return html1;
        this.rawName = rawName;
    }


    }

    static public void main(String[] args) {
        Food obj = new Food("Mapo tofu");
        String realName = obj.getName();
        System.out.println (realName);
        String aa = obj.getTurName();
        System.out.println (aa);
        String outt = obj.setDescription();
        System.out.println (outt);


}