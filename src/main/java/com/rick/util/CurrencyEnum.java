package com.rick.util;

public enum CurrencyEnum {

    USD("USD", "美元"),
    EUR("EUR", "歐元"),
    GBP("GBP", "英鎊"),
    JPY("JPY", "日圓"),
    CNY("CNY", "人民幣"),
    AUD("AUD", "澳大利亞元"),
    CAD("CAD", "加拿大元"),
    CHF("CHF", "瑞士法郎"),
    HKD("HKD", "港元"),
    NZD("NZD", "紐西蘭元"),
    SEK("SEK", "瑞典克朗"),
    KRW("KRW", "韓元"),
    SGD("SGD", "新加坡元"),
    NOK("NOK", "挪威克朗"),
    MXN("MXN", "墨西哥比索"),
    INR("INR", "印度盧比"),
    RUB("RUB", "俄羅斯盧布"),
    ZAR("ZAR", "南非蘭特"),
    TRY("TRY", "土耳其里拉"),
    BRL("BRL", "巴西雷亞爾"),
    TWD("TWD", "新台幣"),
    MYR("MYR", "馬來西亞令吉"),
    THB("THB", "泰銖"),
    PHP("PHP", "菲律賓比索"),
    IDR("IDR", "印尼盧比"),
    VND("VND", "越南盾");

    private final String code;
    private final String name;

    CurrencyEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    // 根據代碼取得中文名稱
    public static String getNameByCode(String code) {
        for (CurrencyEnum currency : values()) {
            if (currency.getCode().equals(code)) {
                return currency.getName();
            }
        }
        return null;  // 如果找不到代碼，返回 null 或拋出例外
    }
}
