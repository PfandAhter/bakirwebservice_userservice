package com.bws.userservice.repository;

import com.bws.userservice.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    @Query("SELECT a FROM Address a where a.username = ?1")
    Address findAddressByUsername (String username);
}
