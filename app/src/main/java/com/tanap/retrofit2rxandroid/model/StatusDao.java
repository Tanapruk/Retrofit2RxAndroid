package com.tanap.retrofit2rxandroid.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by trusttanapruk on 7/26/2016.
 */

@Parcel
public class StatusDao {
    String resultCode;
    @SerializedName("resultDesc")
    String resultDescription;
    String developerMessage;

    public StatusDao() {
    }
    public StatusDao createErrorObject() {
        resultCode = "500";
        resultDescription = "Network Erro";
        developerMessage = "5555";
        return this;
    }

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
