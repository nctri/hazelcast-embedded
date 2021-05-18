package com.example.hazelcast.service;

import com.example.hazelcast.model.Employee;
import com.example.hazelcast.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Cacheable(cacheNames = "allEmployeeCache")
    public List<Employee> findAll() {
        log.info("Fetch all employees");
        return employeeRepository.findAll();
    }



}
