package com.bws.userservice.repository;

import com.bws.userservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String>  {

    User findByEmail(String email);

    User findByUsername (String username);


}
