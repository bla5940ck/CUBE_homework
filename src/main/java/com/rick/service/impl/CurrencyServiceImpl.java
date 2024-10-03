package com.rick.service.impl;

import com.rick.dto.CurrencyRequest;
import com.rick.entity.Currency;
import com.rick.repository.CurrencyRepository;
import com.rick.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    @Override
    public Currency getCurrencyByCode(String code) {
        return currencyRepository.findByCurrencyCode(code);
    }

    @Override
    public Currency addCurrency(CurrencyRequest currencyRequest) {
        return currencyRepository.save(convertToCurrency(currencyRequest));
    }

    @Override
    public Currency updateCurrency(Long id, CurrencyRequest newCurrency) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (optionalCurrency.isPresent()) {
            Currency existingCurrency = optionalCurrency.get();
            existingCurrency.setCurrencyCode(newCurrency.getCurrencyCode());
            existingCurrency.setCurrencyName(newCurrency.getCurrencyName());
            return currencyRepository.save(existingCurrency);
        }
        return null;
    }

    @Override
    public void deleteCurrency(Long id) {
        currencyRepository.deleteById(id);
    }

    // 轉換
    private Currency convertToCurrency(CurrencyRequest currencyRequest) {
        Currency currency = new Currency();
        currency.setCurrencyCode(currencyRequest.getCurrencyCode());
        currency.setCurrencyName(currencyRequest.getCurrencyName());

        return currency;
    }



}
