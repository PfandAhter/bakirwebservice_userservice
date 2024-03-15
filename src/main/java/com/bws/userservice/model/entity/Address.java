package com.bws.userservice.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "address")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long address_id;

    @Column(name = "username")
    private String username;

    @Column(name = "address")
    private String address;

    @Column(name = "district")
    private String district;

    /*@OneToOne(cascade = CascadeType.ALL,
    orphanRemoval = true)
    @JoinColumn(name = "city_id" , referencedColumnName = "city_id")
    private City cityName;*/

    @Column(name = "city_id")
    private Long city_id;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "phone")
    private String phone;
}
