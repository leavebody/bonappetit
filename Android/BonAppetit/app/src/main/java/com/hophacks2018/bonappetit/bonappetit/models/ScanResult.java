package com.hophacks2018.bonappetit.bonappetit.models;

import android.content.Intent;
import android.util.Log;

import com.hophacks2018.bonappetit.bonappetit.activity.CameraActivity;
import com.hophacks2018.bonappetit.bonappetit.activity.MenuActivity;

import java.util.ArrayList;

/**
 * Created by yujiaxiao on 2/17/18.
 */

public class ScanResult {
    public int itemCount;
    public int doneCount;
    public CameraActivity grandpa;
    public ArrayList<Food> allFoods;
    public boolean isFinishedFilling;
    public String imageString;

    public ScanResult(CameraActivity grandpa, String imageString){
        this.grandpa = grandpa;
        this.imageString = imageString;
        this.allFoods = new ArrayList<>();
        itemCount = 0;
        doneCount = 0;
        isFinishedFilling = false;
    }

    public void addFoodItem(Food food){
        this.itemCount++;
        this.allFoods.add(food);
        food.setFromApi();
        Log.d("father", "add1");
    }

    public void finishFilling(){
        this.isFinishedFilling = true;
    }

    public void doneOne(){
        doneCount++;

        Log.d("father", "minus1");
        if (isFinishedFilling && doneCount>=2*itemCount) {
            this.doneAll();
        }
    }

    public void doneAll(){
        Intent intent = new Intent(grandpa, MenuActivity.class);
        grandpa.startActivity(intent);
        grandpa.finish();
    }

    public ArrayList<Food> getAllFoods() {
        return allFoods;
    }

    public void setAllFoods(ArrayList<Food> allFoods) {
        this.allFoods = allFoods;
    }
}
