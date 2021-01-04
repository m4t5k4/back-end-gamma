package com.example.postgresservice.repository;

import com.example.postgresservice.model.Prices;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PricesRepository extends CrudRepository<Prices, Long> {
    List<Prices> findAll();
    Prices findPricesByAppId(Integer appId);
}
