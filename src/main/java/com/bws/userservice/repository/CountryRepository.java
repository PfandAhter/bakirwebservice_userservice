package com.bws.userservice.repository;

import com.bws.userservice.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {
    Country findCountryByCountryName (String countryName);
}
