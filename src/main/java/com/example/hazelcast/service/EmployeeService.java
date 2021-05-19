package com.example.hazelcast.service;

import com.example.hazelcast.model.Employee;
import com.example.hazelcast.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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

    @Cacheable(cacheNames = "employeeCache", key = "{#id}")
    public Employee findOne(Long id) {
        log.info("Fetch one employee, employeeId = {}", id);
        return employeeRepository.findById(id).get();
    }

    @CachePut(cacheNames = "employeeCache", key = "{#employee.id}")
    public Employee save(Employee employee) {
        log.info("Save one");
        return employeeRepository.save(employee);
    }

    @CacheEvict(cacheNames = "employeeCache", key = "{#id}")
    public void delete(Long id) {
        log.info("Delete one");
        employeeRepository.deleteById(id);
    }





}
