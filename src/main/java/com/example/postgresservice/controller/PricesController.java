package com.example.postgresservice.controller;

import com.example.postgresservice.model.Prices;
import com.example.postgresservice.repository.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class PricesController {

    @Autowired
    private PricesRepository pricesRepository;

    @PostConstruct
    public void fillDB(){
        if (pricesRepository.count()==0){
            pricesRepository.save(new Prices(620,1.63,1.99,1.43,22.79));
        }
    }

    @GetMapping("/prices")
    public List<Prices> getPrices() {
        return pricesRepository.findAll();
    }
}
