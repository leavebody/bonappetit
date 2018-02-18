package com.hophacks2018.bonappetit.bonappetit.models;

import android.graphics.Bitmap;

/**
 * @author Xiaochen Li
 */

public class Food {
    private String rawName; // The name recognized from the menu.
    private String name; // The official name of this dish.
    private String description; // Detailed description of the dish got from google knowledge graph.
    private Bitmap image; //
    private double[] ingredientRawVector;
    private double[] subcataRawVector;
    private double[] cataRawVector;
    private double[] featureVector;
    // todo add info about the position of the item in the menu image


    public Food(String rawName, String name, String description, Bitmap image) {
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
