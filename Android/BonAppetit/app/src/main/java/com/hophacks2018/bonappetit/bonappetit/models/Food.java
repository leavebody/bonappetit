package com.hophacks2018.bonappetit.bonappetit.models;

import android.content.Context;
import android.graphics.Bitmap;

import com.hophacks2018.bonappetit.bonappetit.service.ApiService;
import com.hophacks2018.bonappetit.bonappetit.service.ModelResult;
import com.hophacks2018.bonappetit.bonappetit.service.ServiceCallBack;

/**
 * @author Xiaochen Li
 */

public class Food {
    private String rawName; // The name recognized from the menu.
    private String name; // The official name of this dish.
    private String description; // Detailed description of the dish got from google knowledge graph.
    private String image; //
    private double[] ingredientRawVector;
    private double[] subcataRawVector;
    private double[] cataRawVector;
    private double[] featureVector;
    private ScanResult father;
    private static Context c;
    public boolean isValid = true;

    public Food(String rawName, ScanResult father, Context c) {
        this.rawName = rawName;
        this.father = father;
    }

    public void setNameBing(){
        ApiService.getDishName(c, this.rawName, new ServiceCallBack<String>() {
            @Override
            public void getModelOnSuccess(ModelResult<String> modelResult) {
                if (!modelResult.isStatus()){
                    Food.this.isValid = false;
                    return;
                }
                Food.this.name = modelResult.getModel();

                ApiService.getKnowledge(Food.c, Food.this.name, new ServiceCallBack<KnowledgeGraphRaw>() {
                    @Override
                    public void getModelOnSuccess(ModelResult<KnowledgeGraphRaw> modelResult) {
                        if (!modelResult.isStatus()){
                            Food.this.isValid = false;
                            return;
                        }
                        KnowledgeGraphRaw know = modelResult.getModel();
                        Food.this.description = know.itemListElement.get(0).result.description;
                        Food.this.image = know.itemListElement.get(0).result.image.url;
                    }
                });

                ApiService.getFeature(Food.c, Food.this.name, new ServiceCallBack<double[]>() {
                    @Override
                    public void getModelOnSuccess(ModelResult<double[]> modelResult) {

                    }
                });
            }
        });
    }

    public Food(String rawName, String name, String description, String image) {
        this.rawName = rawName;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
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

    public double[] getFeatureVector() {
        return featureVector;
    }

    public void setFeatureVector(double[] featureVector) {
        this.featureVector = featureVector;
    }
}
