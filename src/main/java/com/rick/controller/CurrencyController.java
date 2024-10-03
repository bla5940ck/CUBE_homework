package com.rick.controller;

import com.rick.dto.CurrencyRequest;
import com.rick.entity.Currency;
import com.rick.service.CoindeskService;
import com.rick.service.CurrencyService;
import com.rick.service.impl.CurrencyServiceImpl;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {
    private final CurrencyService currencyService;
    private final CoindeskService coindeskService;

    public CurrencyController(CurrencyService currencyService, CoindeskService coindeskService) {
        this.currencyService = currencyService;
        this.coindeskService = coindeskService;
    }

    // 1. 取得所有幣別
    @GetMapping
    public ResponseEntity<List<Currency>> getAllCurrencies() {
        return ResponseEntity.status(HttpStatus.OK).body(currencyService.getAllCurrencies());
    }

    // 2. 根據code取得幣別
    @GetMapping("/{code}")
    public ResponseEntity<Currency> getCurrencyByCode(@PathVariable String code) {
        return ResponseEntity.status(HttpStatus.OK).body(currencyService.getCurrencyByCode(code));
    }

    // 3. 新增幣別
    @PostMapping
    public ResponseEntity<Currency> addCurrency(@RequestBody CurrencyRequest currencyRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(currencyService.addCurrency(currencyRequest));
    }

    // 4. 更新幣別
    @PutMapping("/{id}")
    public ResponseEntity<Currency> updateCurrency(@PathVariable Long id, @RequestBody CurrencyRequest currencyRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(currencyService.updateCurrency(id, currencyRequest));
    }

    // 5. 删除幣別
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCurrency(@PathVariable Long id) {
        currencyService.deleteCurrency(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 6. call coindesk API
    @GetMapping("/coindesk")
    public ResponseEntity<String> getCoindeskData() throws Exception {
        // 取得coindesk API
        JSONObject data = coindeskService.fetchCoindeskData();
        return ResponseEntity.status(HttpStatus.OK).body(data.toString());
    }

    // 6. 取得轉換後coindesk API
    @GetMapping("/getTransformedCoindeskData")
    public ResponseEntity<String> getTransformedCoindeskData() throws Exception {
        // 取得轉換後coindesk API
        JSONObject data = coindeskService.getTransformedCoindeskData();
        return ResponseEntity.status(HttpStatus.OK).body(data.toString());
    }
}
