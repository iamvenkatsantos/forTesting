package com.onedata.sunexchange.logintask.bean;

import com.google.gson.annotations.SerializedName;

public class KycSecondStep {
    @SerializedName("businessName")
    private String    businessName;
    @SerializedName("businessAddress")
    private String    businessAddress;
    @SerializedName("taxCountry")
    private String    taxCountry;
    @SerializedName("tinNo")
    private String    tinNo;
    private String    kycDocumentPath;
    private Integer   userId;
    private Boolean   isTaxSameCountry;
    private Boolean   isTaxAnotherCountry;

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getTaxCountry() {
        return taxCountry;
    }

    public void setTaxCountry(String taxCountry) {
        this.taxCountry = taxCountry;
    }

    public String getTinNo() {
        return tinNo;
    }

    public void setTinNo(String tinNo) {
        this.tinNo = tinNo;
    }

    public String getKycDocumentPath() {
        return kycDocumentPath;
    }

    public void setKycDocumentPath(String kycDocumentPath) {
        this.kycDocumentPath = kycDocumentPath;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getTaxSameCountry() {
        return isTaxSameCountry;
    }

    public void setTaxSameCountry(Boolean taxSameCountry) {
        isTaxSameCountry = taxSameCountry;
    }

    public Boolean getTaxAnotherCountry() {
        return isTaxAnotherCountry;
    }

    public void setTaxAnotherCountry(Boolean taxAnotherCountry) {
        isTaxAnotherCountry = taxAnotherCountry;
    }
}
