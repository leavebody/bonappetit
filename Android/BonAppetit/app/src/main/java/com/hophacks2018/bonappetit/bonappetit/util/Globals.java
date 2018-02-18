package com.hophacks2018.bonappetit.bonappetit.util;

/**
 * Created by yujiaxiao on 2/17/18.
 */

import android.app.Application;
import android.util.SparseArray;

import com.google.android.gms.vision.text.TextBlock;

/**
 * Created by yujiaxiao on 2/9/18.
 */

public class Globals extends Application {
    SparseArray<TextBlock> textBlockSparseArray = null;

    public SparseArray<TextBlock> getTextBlockSparseArray() {
        return textBlockSparseArray;
    }

    public void setTextBlockSparseArray(SparseArray<TextBlock> textBlockSparseArray) {
        this.textBlockSparseArray = textBlockSparseArray;
    }
}
