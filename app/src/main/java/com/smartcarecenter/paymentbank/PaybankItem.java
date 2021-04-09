/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.paymentbank;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaybankItem {
    public PaybankItem() {
    }
    Integer img;
    public PaybankItem( Integer img, String paymentGatewayCd, int position, String name, String description, String merchantId, String sharedKey, String requestUrl, String requestUrl2, String clientKey, String serverKey, String returnUrl, boolean stsActive) {
        this.img = img;
        PaymentGatewayCd = paymentGatewayCd;
        Position = position;
        Name = name;
        Description = description;
        MerchantId = merchantId;
        SharedKey = sharedKey;
        RequestUrl = requestUrl;
        RequestUrl2 = requestUrl2;
        ClientKey = clientKey;
        ServerKey = serverKey;
        ReturnUrl = returnUrl;
        StsActive = stsActive;
    }
    public Integer getImg() {
        return this.img;
    }
    public void setImg(Integer n) {
        this.img = n;
    }

    public String getPaymentGatewayCd() {
        return PaymentGatewayCd;
    }

    public void setPaymentGatewayCd(String paymentGatewayCd) {
        PaymentGatewayCd = paymentGatewayCd;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMerchantId() {
        return MerchantId;
    }

    public void setMerchantId(String merchantId) {
        MerchantId = merchantId;
    }

    public String getSharedKey() {
        return SharedKey;
    }

    public void setSharedKey(String sharedKey) {
        SharedKey = sharedKey;
    }

    public String getRequestUrl() {
        return RequestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        RequestUrl = requestUrl;
    }

    public String getRequestUrl2() {
        return RequestUrl2;
    }

    public void setRequestUrl2(String requestUrl2) {
        RequestUrl2 = requestUrl2;
    }

    public String getClientKey() {
        return ClientKey;
    }

    public void setClientKey(String clientKey) {
        ClientKey = clientKey;
    }

    public String getServerKey() {
        return ServerKey;
    }

    public void setServerKey(String serverKey) {
        ServerKey = serverKey;
    }

    public String getReturnUrl() {
        return ReturnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        ReturnUrl = returnUrl;
    }

    public boolean isStsActive() {
        return StsActive;
    }

    public void setStsActive(boolean stsActive) {
        StsActive = stsActive;
    }

    @Expose
    @SerializedName("PaymentGatewayCd")
    private String PaymentGatewayCd;
    @Expose
    @SerializedName("Position")
    private int Position;
    @Expose
    @SerializedName("Name")
    private String Name;
    @Expose
    @SerializedName("Description")
    private String Description;
    @Expose
    @SerializedName("MerchantId")
    private String MerchantId;
    @Expose
    @SerializedName("SharedKey")
    private String SharedKey;
    @Expose
    @SerializedName("RequestUrl")
    private String RequestUrl;
    @Expose
    @SerializedName("RequestUrl2")
    private String RequestUrl2;
    @Expose
    @SerializedName("ClientKey")
    private String ClientKey;
    @Expose
    @SerializedName("ServerKey")
    private String ServerKey;
    @Expose
    @SerializedName("ReturnUrl")
    private String ReturnUrl;
    @Expose
    @SerializedName("StsActive")
    private boolean StsActive;


}

