package io.aditya.kam.repository;


import io.aditya.kam.entity.CustomerOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrderEntity, Integer> {
}
