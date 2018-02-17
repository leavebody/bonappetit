package com.hophacks2018.bonappetit.bonappetit.service;

/**
 * The result of a network request.
 * Other than the fields in its parent class,
 * a model is added to help the request caller
 * to get an arbitary class on demand.
 * @author Xiaochen Li
 */

public class ModelResult <T> {
    private T model;
    private boolean status = false;

    public final boolean isStatus() {
        return status;
    }

    public final void setStatus(boolean status) {
        this.status = status;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}