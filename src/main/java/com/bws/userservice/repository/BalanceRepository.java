package com.bws.userservice.repository;

import com.bws.userservice.model.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, String> {

    Balance findBalanceByUsername (String username);
    
}
