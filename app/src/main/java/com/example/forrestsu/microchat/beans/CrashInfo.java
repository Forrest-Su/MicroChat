package com.example.forrestsu.microchat.beans;

import cn.bmob.v3.BmobObject;

public class CrashInfo extends BmobObject {

    private String cause;
    private String localizedMessage;
    private int sdk;
    private String brand;
    private String model;
    private String id;

    public String getCause() {
        return cause;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public int getSdk() {
        return sdk;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getId() {
        return id;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }

    public void setSdk(int sdk) {
        this.sdk = sdk;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setId(String id) {
        this.id = id;
    }

}
