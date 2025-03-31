package com.softuni.Restaurant.repository;

import com.softuni.Restaurant.model.Order;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @NotNull
    List<Order> findAll ();
    @NotNull
    Optional<Order> findById(@NotNull Long id);
}
