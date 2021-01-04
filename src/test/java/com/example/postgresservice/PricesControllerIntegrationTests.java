package com.example.postgresservice;

import com.example.postgresservice.model.Prices;
import com.example.postgresservice.repository.PricesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PricesControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PricesRepository pricesRepository;

    private Prices prices1 = new Prices(620,1.63,1.99,1.43,22.79);

    @BeforeEach
    public void beforeAllTests(){
        pricesRepository.deleteAll();
        pricesRepository.save(prices1);
    }

    @AfterEach
    public void afterAllTests(){
        pricesRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenPrices_whenGetAll_thenReturnJsonPrices() throws Exception{
        mockMvc.perform(get("/prices"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].appId", is(620)))
                .andExpect(jsonPath("$[0].euro", is(1.63)))
                .andExpect(jsonPath("$[0].dollar", is(1.99)))
                .andExpect(jsonPath("$[0].pound", is(1.43)))
                .andExpect(jsonPath("$[0].peso", is(22.79)));
    }

    @Test
    public void givenPrices_whenGetPricesByAppId_thenReturnJsonPrices() throws Exception{
        mockMvc.perform(get("/prices/{appId}", 620))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appId", is(620)))
                .andExpect(jsonPath("$.euro", is(1.63)))
                .andExpect(jsonPath("$.dollar", is(1.99)))
                .andExpect(jsonPath("$.pound", is(1.43)))
                .andExpect(jsonPath("$.peso", is(22.79)));
    }

    @Test
    public void whenPostPrices_thenReturnJsonPrices() throws Exception{
        Prices prices2 = new Prices(1145360, 16.79, 19.99, 15.59, 187.19);

        mockMvc.perform(post("/prices")
        .content(mapper.writeValueAsString(prices2))
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appId", is(1145360)))
                .andExpect(jsonPath("$.euro", is(16.79)))
                .andExpect(jsonPath("$.dollar", is(19.99)))
                .andExpect(jsonPath("$.pound", is(15.59)))
                .andExpect(jsonPath("$.peso", is(187.19)));
    }

    @Test
    public void givenPrices_whenPutPrices_thenReturnJsonPrices() throws Exception {
        Prices updatedPrices = new Prices(620,1.99,1.99,1.43,22.79);

        mockMvc.perform(put("/prices")
            .content(mapper.writeValueAsString(updatedPrices))
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appId", is(620)))
                .andExpect(jsonPath("$.euro", is(1.99)))
                .andExpect(jsonPath("$.dollar", is(1.99)))
                .andExpect(jsonPath("$.pound", is(1.43)))
                .andExpect(jsonPath("$.peso", is(22.79)));
    }

    @Test
    public void givenPrices_whenDeletePrices_thenStatusOk() throws Exception {
        mockMvc.perform(delete("/prices/{appId}", 620)
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoPrices_whenDeletePrices_thenStatusNotFound() throws Exception {
        mockMvc.perform(delete("/prices/{appId}", 999)
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
