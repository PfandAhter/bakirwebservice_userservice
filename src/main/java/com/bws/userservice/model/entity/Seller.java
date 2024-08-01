package com.bws.userservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "sellers")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "seller_id")
    private String sellerId;

    @Column(name = "seller_name")
    private String sellerName;

    @Column(name = "seller_username")
    private String sellerUsername;

    @Column(name = "active")
    private int active;

}
