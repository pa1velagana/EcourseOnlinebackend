package com.example.ecourseonline.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecourseonline.entity.MyUser;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Integer> {

	boolean existsByUserName(String userName);

	boolean existsByUserEmail(String userEmail);

	MyUser findByUserEmail(String userEmail);

	Optional<MyUser> findByUserName(String username);

	


}
