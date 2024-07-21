package com.poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
