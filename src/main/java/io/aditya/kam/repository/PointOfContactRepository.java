package io.aditya.kam.repository;

import io.aditya.kam.entity.PointOfContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PointOfContactRepository extends JpaRepository<PointOfContactEntity, Integer> {


  
}
