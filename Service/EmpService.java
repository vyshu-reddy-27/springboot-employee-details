package com.example.Employee.Details.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Employee.Details.Entity.EmpEntity;
import com.example.Employee.Details.Repository.EmpRepository;

@Service
public class EmpService {

  @Autowired
  private EmpRepository repository;

  public List<EmpEntity> getAllEmployees() {
    return repository.findAll();
  }

}
