package com.example.postgresservice;

import com.example.postgresservice.model.Prices;
import com.example.postgresservice.repository.PricesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PricesControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PricesRepository pricesRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenPrices_whenGetPrices_thenReturnJsonPrices() throws Exception {
        Prices prices1 = new Prices(620,1.63,1.99,1.43,22.79);
        List<Prices> prices = new ArrayList<>();
        prices.add(prices1);

        given(pricesRepository.findAll()).willReturn(prices);

        mockMvc.perform(get("/prices"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].appId",is(620)))
                .andExpect(jsonPath("$[0].euro",is(1.63)))
                .andExpect(jsonPath("$[0].dollar",is(1.99)))
                .andExpect(jsonPath("$[0].pound",is(1.43)))
                .andExpect(jsonPath("$[0].peso",is(22.79)));
    }

    @Test
    public void givenPrices_whenGetPricesByAppId_thenReturnJsonPrices() throws Exception {
        Prices prices1 = new Prices(620,1.63,1.99,1.43,22.79);

        given(pricesRepository.findPricesByAppId(620)).willReturn(prices1);

        mockMvc.perform(get("/prices/{appId}",620))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appId",is(620)))
                .andExpect(jsonPath("$.euro",is(1.63)))
                .andExpect(jsonPath("$.dollar",is(1.99)))
                .andExpect(jsonPath("$.pound",is(1.43)))
                .andExpect(jsonPath("$.peso",is(22.79)));
    }

    @Test
    public void whenPostPrices_thenReturnJsonPrices() throws Exception {
        Prices prices2 = new Prices(1401570,0.79,0.99,0.79,13.39);

        mockMvc.perform(post("/prices")
            .content(mapper.writeValueAsString(prices2))
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appId",is(1401570)))
                .andExpect(jsonPath("$.euro",is(0.79)))
                .andExpect(jsonPath("$.dollar",is(0.99)))
                .andExpect(jsonPath("$.pound",is(0.79)))
                .andExpect(jsonPath("$.peso",is(13.39)));
    }

    @Test
    public void givenPrices_whenPutPrices_thenReturnJsonPrices() throws Exception{
        Prices prices1 = new Prices(620,1.63,1.99,1.43,22.79);

        given(pricesRepository.findPricesByAppId(620)).willReturn(prices1);

        Prices updatedPrices = new Prices(620,2.63,2.99,2.43,28.79);

        mockMvc.perform(put("/prices")
        .content(mapper.writeValueAsString(updatedPrices))
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appId",is(620)))
                .andExpect(jsonPath("$.euro",is(2.63)))
                .andExpect(jsonPath("$.dollar",is(2.99)))
                .andExpect(jsonPath("$.pound",is(2.43)))
                .andExpect(jsonPath("$.peso",is(28.79)));
    }

    @Test
    public void givenPrices_whenDeletePrices_thenStatusOk() throws Exception {
        Prices pricesToBeDeleted = new Prices(999, 9.99, 9.99, 18.99, 99.99);

        given(pricesRepository.findPricesByAppId(999)).willReturn(pricesToBeDeleted);

        mockMvc.perform(delete("/prices/{appId}",999)
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoPrices_whenDeletePrices_thenStatusNotFound() throws Exception{
        given(pricesRepository.findPricesByAppId(888)).willReturn(null);

        mockMvc.perform(delete("/prices/{appId}",888)
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
