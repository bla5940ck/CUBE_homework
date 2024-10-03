import com.fasterxml.jackson.databind.ObjectMapper;
import com.rick.MainApplication;
import com.rick.dto.CurrencyRequest;
import com.rick.entity.Currency;
import com.rick.util.CurrencyEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MainApplication.class)
@AutoConfigureMockMvc(addFilters = false)  // 禁用安全性過濾器
class CurrencyControllerTest {


    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 1. 測試查詢幣別對應表資料 API
    @Test
    void testGetCurrencyList() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/currency");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(26));
    }

    // 2. 測試新增幣別對應表資料 API
    @Test
    void testAddCurrency() throws Exception {
        CurrencyRequest currencyRequest = new CurrencyRequest();
        currencyRequest.setCurrencyCode("BTC");
        currencyRequest.setCurrencyName("比特幣");


        String jsonContent = objectMapper.writeValueAsString(currencyRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/currency")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.currencyCode").value("BTC"))
                .andExpect(jsonPath("$.currencyName").value("比特幣"));
    }

    // 3. 測試更新幣別對應表資料 API
    @Test
    void testUpdateCurrency() throws Exception {
        Long currencyId = 1L;  // 假設已存在的幣別ID
        Currency updatedCurrency = new Currency();
        updatedCurrency.setCurrencyCode(CurrencyEnum.USD.getCode());
        updatedCurrency.setCurrencyName("美元修改");

        String jsonContent = objectMapper.writeValueAsString(updatedCurrency);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/currency/{id}", currencyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currencyName").value("美元修改"));
    }

    // 4. 測試刪除幣別對應表資料 API
    @Test
    void testDeleteCurrency() throws Exception {
        Long currencyId = 2L;  // 假設已存在的幣別ID

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/currency/{id}", currencyId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNoContent());  // 假設刪除操作成功返回204
    }

    // 5. 測試呼叫 Coindesk API，並顯示其內容
    @Test
    void testCallCoindeskApi() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/currency/coindesk")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    // 6. 測試呼叫資料轉換 API，並顯示其內容
    @Test
    void testCallTransformedApi() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/currency/getTransformedCoindeskData")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                // 測試有新增更新時間
        .andExpect(jsonPath("$.updatedTime").isNotEmpty())
                // 測試有加入貨幣名稱
        .andExpect(jsonPath("$.bpi.USD.currencyName").value(CurrencyEnum.USD.getName()));
    }
}

