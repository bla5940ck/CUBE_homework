package com.rick.service;

import com.rick.dto.CurrencyRequest;
import com.rick.entity.Currency;
import com.rick.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyService {

    /***
     * 取得所有貨幣
     * @return List<Currency>
     */
    public List<Currency> getAllCurrencies();

    /**
     * 取得貨幣
     * @param code
     * @return Currency
     * */
    public Currency getCurrencyByCode(String code);

    /** 新增貨幣
     * @param currencyRequest
     * */
    public Currency addCurrency(CurrencyRequest currencyRequest);

    /** 更新貨幣
     * @param id
     * @param newCurrency
     * */
    public Currency updateCurrency(Long id, CurrencyRequest newCurrency);


    /**
     * 刪除貨幣
     * @param id
     * */
    public void deleteCurrency(Long id);
}
