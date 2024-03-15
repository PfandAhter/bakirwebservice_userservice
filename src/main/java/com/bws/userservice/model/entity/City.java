package com.bws.userservice.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "city")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long city_id;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "country_id")
    private Long country_id;

    /*@OneToOne(mappedBy = "cityName",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private Address address;*/
}
