package com.hophacks2018.bonappetit.bonappetit.models;

import android.content.Context;

import com.hophacks2018.bonappetit.bonappetit.service.ApiService;
import com.hophacks2018.bonappetit.bonappetit.service.ModelResult;
import com.hophacks2018.bonappetit.bonappetit.service.ServiceCallBack;

import java.util.ArrayList;

/**
 * @author Xiaochen Li
 */

public class Food {
    private String rawName; // The name recognized from the menu.
    private String name; // The official name of this dish.
    private String description; // Detailed description of the dish got from google knowledge graph.
    private String image;
    private double[] featureVector;
    private ScanResult father;
    private static Context c;
    public boolean isValid = true;
    private double rate;
    private boolean isOrdered = false;
    private ArrayList<double[]> features;

    public Food(String rawName, ScanResult father, Context c) {
        this.rawName = rawName;
        this.father = father;
        this.c = c;
    }

    public void setFromApi(){
        ApiService.getDishName(c, this.rawName, new ServiceCallBack<String>() {
            @Override
            public void getModelOnSuccess(ModelResult<String> modelResult) {
                if (!modelResult.isStatus()){
                    Food.this.isValid = false;
                    return;
                }
                Food.this.name = modelResult.getModel();

                ApiService.getFeature(Food.c, Food.this.name, new ServiceCallBack<double[]>() {
                    @Override
                    public void getModelOnSuccess(ModelResult<double[]> modelResult) {
                        if (!modelResult.isStatus()){
                            Food.this.isValid = false;
                        } else {
                            Food.this.featureVector = modelResult.getModel();
                        }

                        Food.this.father.doneOne();
                    }
                });

                ApiService.getKnowledge(Food.c, Food.this.name, new ServiceCallBack<KnowledgeGraphRaw>() {
                    @Override
                    public void getModelOnSuccess(ModelResult<KnowledgeGraphRaw> modelResult) {
                        if (!modelResult.isStatus()){
                            Food.this.isValid = false;
                        } else {
                            KnowledgeGraphRaw know = modelResult.getModel();
                            try {
                                Food.this.description = know.itemListElement.get(0).result.detailedDescription.articleBody;
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                            try{
                                Food.this.image = know.itemListElement.get(0).result.image.contentUrl;
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        Food.this.father.doneOne();
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

    public Food(String rawName, String image, double[] featureVector) {
        this.rawName = rawName;
        this.image = image;
        this.featureVector = featureVector;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRawName() {
        return rawName;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }


    public double[] getFeatureVector() {
        return featureVector;
    }

    public void setFeatureVector(double[] featureVector) {
        this.featureVector = featureVector;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public void setOrdered(boolean ordered) {
        isOrdered = ordered;
    }

    public ArrayList<double[]> getFeatures() {
        return features;
    }
}
