package io.aditya.kam.repository;

import io.aditya.kam.entity.KeyAccountManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * This is used to perform CRUD from the database.
 */
@Repository
public interface KeyAccountManagerRepository extends JpaRepository<KeyAccountManagerEntity, String> {
}
