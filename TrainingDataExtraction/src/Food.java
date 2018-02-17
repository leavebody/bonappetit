import util.HTTPGetter;
import util.MyParser;

public class Food {
    private String rawName; // The name recognized from the menu.
    private String name; // The official name of this dish.
    private String cookname;
    private String description; // Detailed description of the dish got from google knowledge graph.
    private double[] ingredientRawVector;
    private double[] subcataRawVector;
    private double[] cataRawVector;

    public Food (String rawName) {
        this.rawName = rawName;
    }

    public String getName() {
        String splited =  this.rawName.replaceAll("\\s", "+");
        String URL = "https://www.bing.com/search?q=" + splited + "+wiki";
        System.out.println(splited);
        HTTPGetter getOfficial = new HTTPGetter(URL);
        String html1 = getOfficial.getHTML();
        MyParser aa = new MyParser();
        String realname = aa.BingHTMLParser(html1);
        this.name = realname;
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
        String html1 = getOfficial.getHTML();
        MyParser aa = new MyParser();
        String bb = aa.CookbookSearchHTMLParser(html1);
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

    static public void main(String[] args) {
        Food obj = new Food("mango lessi");
        String realName = obj.getName();
        System.out.println (realName);
        String aa = obj.getTurName();
        System.out.println (aa);
        String outt = obj.setDescription();
        System.out.println (outt);

    }

}