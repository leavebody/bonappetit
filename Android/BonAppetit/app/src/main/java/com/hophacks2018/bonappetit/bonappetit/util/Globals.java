package com.hophacks2018.bonappetit.bonappetit.util;

/**
 * Created by yujiaxiao on 2/17/18.
 */

import android.app.Application;
import android.service.autofill.SaveCallback;
import android.util.SparseArray;

import com.google.android.gms.vision.text.TextBlock;
import com.hophacks2018.bonappetit.bonappetit.R;
import com.hophacks2018.bonappetit.bonappetit.models.Food;
import com.hophacks2018.bonappetit.bonappetit.models.ScanResult;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by yujiaxiao on 2/9/18.
 */

public class Globals extends Application {
    SparseArray<TextBlock> textBlockSparseArray = null;

    ScanResult scanResult;

    public ScanResult rateResult;

    private Food ques1 = new Food("Kong pao chicken", " name", "descrip", String.valueOf(R.drawable.kongpao_chicken));
    private Food ques2 = new Food("chicken Salad", " name", "descrip", String.valueOf(R.drawable.chicken_salad));
    private Food ques3 = new Food("Ginger beef", " name", "descrip", String.valueOf(R.drawable.ginger_beef));

    public ArrayList<Food> quesList = new ArrayList<Food>(Arrays.asList(ques1, ques2, ques3));

    String menuPath;

    public SparseArray<TextBlock> getTextBlockSparseArray() {
        return textBlockSparseArray;
    }

    public void setTextBlockSparseArray(SparseArray<TextBlock> textBlockSparseArray) {
        this.textBlockSparseArray = textBlockSparseArray;
    }

    public ScanResult getScanResult() {
        return scanResult;
    }

    public void setScanResult(ScanResult scanResult) {
        this.scanResult = scanResult;
    }

    public String getImagePath() {
        return menuPath;
    }

    public void setImagePath(String imagePath) {
        this.menuPath = imagePath;
    }

    public ScanResult getRateResult() {
        return rateResult;
    }

    public void setRateResult(ScanResult rateResult) {
        this.rateResult = rateResult;
    }

    public ArrayList<Food> getQuesList() {
        return quesList;
    }

    public void setQuesList(ArrayList<Food> quesList) {
        this.quesList = quesList;
    }
}
