package com.bws.userservice.model.entity;

import com.bws.userservice.model.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name ="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String userId;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB" ,name = "photo")
    private String photo;

    @Column(name = "active")
    private int active;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Balance> balance;

    @Column(name = "password")
    private String password;

    @Column(name = "account_create_date")
    private Timestamp accountCreateDate;

    @Column(name = "password_expire_date")
    private Timestamp passwordExpireDate;

    @Column(name = "password_last_changed_date")
    private Timestamp passwordLastChangedDate;

}
