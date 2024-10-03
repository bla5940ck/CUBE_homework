package com.rick.dto;

import jakarta.persistence.Column;

public class CurrencyRequest {

    // 貨幣code
    private String currencyCode;

    // 貨幣名稱
    private String currencyName;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}
