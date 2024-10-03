package com.rick.service;


import com.rick.entity.Currency;
import com.rick.repository.CurrencyRepository;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

@Service
public class CoindeskService {

    private final CurrencyRepository currencyRepository;

    public CoindeskService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    private static final String COINDESK_API_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";
    private static final SimpleDateFormat INPUT_DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss z", Locale.ENGLISH);
    private static final SimpleDateFormat OUTPUT_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public JSONObject fetchCoindeskData() throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(COINDESK_API_URL);
        HttpResponse response = httpClient.execute(request);
        String json = EntityUtils.toString(response.getEntity());
        return new JSONObject(json);
    }

    public JSONObject getTransformedCoindeskData() {

        JSONObject result = new JSONObject();
        try {
            JSONObject coindeskData = fetchCoindeskData();
            String updatedTime = getFormattedUpdateTime(coindeskData);

            JSONObject bpi = coindeskData.getJSONObject("bpi");

            result.put("updatedTime", updatedTime);

            Iterator keys = bpi.keys();

            // 貨幣轉換
            while (keys.hasNext()) {
                String currencyCode = keys.next().toString();
                JSONObject currencyData = bpi.getJSONObject(currencyCode);
                // 取得貨幣名稱
                Currency currency = currencyRepository.findByCurrencyCode(currencyCode);
                JSONObject currencyInfo = new JSONObject();
                currencyInfo.put("currencyCode", currencyCode);
                currencyInfo.put("currencyName", currency != null ? currency.getCurrencyName() : "Unknown");
                currencyInfo.put("rate", currencyData.getString("rate"));
                bpi.put(currencyCode, currencyInfo);

            }
            result.put("bpi", bpi);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 获取格式化后的更新时间
    private String getFormattedUpdateTime(JSONObject data) throws ParseException {
        String updatedTimeRaw = null;
        try {
            updatedTimeRaw = data.getJSONObject("time").getString("updated");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Date updatedTime = INPUT_DATE_FORMAT.parse(updatedTimeRaw);
        return OUTPUT_DATE_FORMAT.format(updatedTime);
    }
}
