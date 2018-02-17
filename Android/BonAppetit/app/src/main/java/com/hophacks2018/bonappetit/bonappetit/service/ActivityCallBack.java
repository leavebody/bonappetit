package com.hophacks2018.bonappetit.bonappetit.service;

/**
 * Use this interface to handle callback function in the caller class.
 * @author Xiaochen Li
 */

public interface ActivityCallBack <M> {
    void getModelOnSuccess(ModelResult<M> modelResult);
}
