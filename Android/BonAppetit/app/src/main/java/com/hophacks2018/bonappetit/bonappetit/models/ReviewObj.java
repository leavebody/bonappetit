package com.hophacks2018.bonappetit.bonappetit.models;


import java.util.Date;

/**
 * Created by molly on 2/18/18.
 */

public class ReviewObj {
    public String name; // The name recognized from the menu.
    public String path; // The official name of this dish.
    private Date time;
    private double[] features;

    public ReviewObj(String name, String path, Date time, double[] features) {
        this.name = name;
        this.path = path;
        this.time = time;
        this.features = features;
    }
}
