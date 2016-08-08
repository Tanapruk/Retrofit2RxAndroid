package com.tanap.retrofit2rxandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by trusttanapruk on 8/8/2016.
 */
public abstract class GenericDao {
    String resultCode;
    @SerializedName("resultDesc")
    String resultDescription;
    String developerMessage;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }
}
