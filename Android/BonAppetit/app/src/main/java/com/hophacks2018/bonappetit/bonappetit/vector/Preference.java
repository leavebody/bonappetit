package com.hophacks2018.bonappetit.bonappetit.vector;

import com.hophacks2018.bonappetit.bonappetit.models.Food;


import java.util.ArrayList;


public class Preference {
    private double[] vec;
    private double n;

    public Preference(){
        this.initVec();
    }

    public double[] getVec() {
        return this.vec;
    }

    public void initVec(){
        //this.vec = VecManip.getMat1[];
        this.vec = new double[15];
        for (int i = 0; i < 15; i++){
            this.vec[i] = 0;
        }
    }

    public double[] updateVec(Food prevFood, double rate){
        // get food vector 20 length
        ArrayList<double[]> fea =  prevFood.getFeatures();
        VecManip a  = VecManip.getInstance();
        double[] added = a.compute(fea);
        double[] result = new double[added.length];
        for (int i = 0; i < added.length; i++){
            result[i]= added[i]+rate * this.vec[i];
        }
        this.vec = result;
        return result;
    }

    public double computeDot(double[] inn){
        return Matrix.dot(inn,this.vec);
    }

}
