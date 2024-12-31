package io.aditya.kam.repository;


import io.aditya.kam.entity.InteractionEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InteractionRepository extends JpaRepository<InteractionEntity, String> {
}
