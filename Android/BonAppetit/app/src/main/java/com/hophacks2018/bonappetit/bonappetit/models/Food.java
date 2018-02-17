package com.hophacks2018.bonappetit.bonappetit.models;

import android.graphics.Bitmap;

/**
 * @author Xiaochen Li
 */

public class Food {
    private String name;
    private String description;
    private Bitmap image;
    // todo add info about the position of the item in the menu image

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
}
