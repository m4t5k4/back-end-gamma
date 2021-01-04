package com.example.postgresservice.controller;

import com.example.postgresservice.model.Prices;
import com.example.postgresservice.repository.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            pricesRepository.save(new Prices(1145360, 16.79, 19.99, 15.59, 187.19));
            pricesRepository.save(new Prices(427520, 25, 30, 21, 250));
            pricesRepository.save(new Prices(292030, 8.99, 11.99, 7.49, 104.99));
            pricesRepository.save(new Prices(105600, 4.99, 4.99, 3.49, 54.99));
        }
    }

    @GetMapping("/prices")
    public List<Prices> getPrices() {
        return pricesRepository.findAll();
    }

    @GetMapping("/prices/{appId}")
    public Prices getPricesByAppId(@PathVariable Integer appId){
        return pricesRepository.findPricesByAppId(appId);
    }

    @PostMapping("/prices")
    public Prices addPrices(@RequestBody Prices prices) {
        pricesRepository.save(prices);
        return prices;
    }

    @PutMapping("/prices")
    public Prices updatePrices(@RequestBody Prices updatedPrices) {
        Prices retrievedPrices = pricesRepository.findPricesByAppId(updatedPrices.getAppId());

        retrievedPrices.setEuro(updatedPrices.getEuro());
        retrievedPrices.setDollar(updatedPrices.getDollar());
        retrievedPrices.setPound(updatedPrices.getPound());
        retrievedPrices.setPeso(updatedPrices.getPeso());

        pricesRepository.save(retrievedPrices);
        return retrievedPrices;
    }

    @DeleteMapping("/prices/{appId}")
    public  ResponseEntity deletePrices(@PathVariable Integer appId){
        Prices prices = pricesRepository.findPricesByAppId(appId);
        if (prices!=null){
            pricesRepository.delete(prices);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
