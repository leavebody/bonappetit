package com.hophacks2018.bonappetit.bonappetit.models;

import java.util.ArrayList;

/**
 * Created by yujiaxiao on 2/17/18.
 */

public class ScanResult {
    public int foodCount;
    public ArrayList<Food> allFoods;

    public ArrayList<Food> getAllFoods() {
        return allFoods;
    }

    public void setAllFoods(ArrayList<Food> allFoods) {
        this.allFoods = allFoods;
    }
}
