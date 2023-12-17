package com.civilMarriageSystem.Repositories;

import java.util.List;
import java.util.Optional;

import com.civilMarriageSystem.Domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> findByEmail(String email);
    List<User> findByRoles(String roles);

    @Query(value ="SELECT * from User WHERE roles='ROLE_USER'", nativeQuery = true)
    List<User> getCitizens();
    
}