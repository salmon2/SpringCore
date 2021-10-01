package com.sparta.springcore.repository;

import com.sparta.springcore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByUserId(Long userId, Pageable pageable);
}