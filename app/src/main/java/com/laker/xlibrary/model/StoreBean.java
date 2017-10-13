package com.laker.xlibrary.model;

import java.io.Serializable;

/**
 * @author wangcheng
 * @date 创建时间：2017/9/21
 * @Description
 * @Version
 * @EditHistory
 */
public class StoreBean implements Serializable {
    String storeName;
    float storeRate;
    String storeLabel;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public float getStoreRate() {
        return storeRate;
    }

    public void setStoreRate(float storeRate) {
        this.storeRate = storeRate;
    }

    public String getStoreLabel() {
        return storeLabel;
    }

    public void setStoreLabel(String storeLabel) {
        this.storeLabel = storeLabel;
    }
}
