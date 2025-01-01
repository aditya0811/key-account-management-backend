package io.aditya.kam.repository;

import io.aditya.kam.entity.PointOfContact;
import io.aditya.kam.entity.PointOfContactEntity;
import io.aditya.kam.entity.PointOfContactEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PointOfContactRepository extends JpaRepository<PointOfContactEntity, Integer> {


  
}
