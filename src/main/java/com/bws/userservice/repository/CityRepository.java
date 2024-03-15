package com.bws.userservice.repository;

import com.bws.userservice.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {
    City findCityByCityName (String cityname);
}
