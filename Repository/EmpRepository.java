package com.example.Employee.Details.Repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Employee.Details.Entity.EmpEntity;

@Repository
public interface EmpRepository extends JpaRepository<EmpEntity, Long> {
  @Modifying

  List<EmpEntity> findByUsername(String username);

  List<EmpEntity> findAll();

}
