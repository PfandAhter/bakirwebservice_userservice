package com.bws.userservice.repository;

import com.bws.userservice.model.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller,String> {

    @Query("SELECT s FROM Seller s WHERE s.sellerName = ?1 AND  s.active = 1")
    Seller findSellerBySellerName(String sellerName);

    @Query("select s from Seller s where s.sellerId = ?1")
    Seller findSellerBySellerId (String sellerId);

    @Query("select s from Seller s where ?1 = 'all' or s.sellerId = ?1")
    List<Seller> findSellersBySellerId (String sellerId);

    @Query("select s From Seller s where s.sellerUsername = ?1")
    Seller findSellerBySellerUsername (String sellerUsername);



}
