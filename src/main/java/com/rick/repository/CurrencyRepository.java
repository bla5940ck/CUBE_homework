package com.rick.repository;

import com.rick.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    /** 查詢幣別
     * @param currencyCode
     * */
    Currency findByCurrencyCode(String currencyCode);

}
