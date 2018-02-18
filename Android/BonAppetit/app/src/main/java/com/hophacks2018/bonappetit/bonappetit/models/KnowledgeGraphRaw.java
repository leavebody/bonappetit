package com.hophacks2018.bonappetit.bonappetit.models;

import java.util.ArrayList;

/**
 * @author Xiaochen Li
 */

public class KnowledgeGraphRaw {
    public ArrayList<ItemListElement> itemListElement;

    @Override
    public String toString() {
        return "KnowledgeGraphRaw{" +
                "itemListElement=" + itemListElement +
                '}';
    }

    public class ItemListElement {
        public ItemListElement.Result result;

        @Override
        public String toString() {
            return "ItemListElement{" +
                    "result=" + result +
                    '}';
        }

        public class Result {
            public String name;
            public String description;
            public MyImage image;
            public Detail detailedDescription;

            @Override
            public String toString() {
                return "Result{" +
                        "name='" + name + '\'' +
                        ", description='" + description + '\'' +
                        ", image=" + image +
                        ", detailedDescription=" + detailedDescription +
                        '}';
            }

            public class MyImage {
                public String contentUrl;
                public String url;

                @Override
                public String toString() {
                    return "MyImage{" +
                            "contentUrl='" + contentUrl + '\'' +
                            ", url='" + url + '\'' +
                            '}';
                }
            }

            public class Detail {
                public String articleBody;
                public String url;

                @Override
                public String toString() {
                    return "Detail{" +
                            "articleBody='" + articleBody + '\'' +
                            ", url='" + url + '\'' +
                            '}';
                }
            }
        }
    }


}
