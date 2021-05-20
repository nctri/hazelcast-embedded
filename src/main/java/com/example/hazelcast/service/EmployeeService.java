package com.example.hazelcast.service;

import com.example.hazelcast.constant.CacheName;
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

    @Cacheable(cacheNames = CacheName.ALL_EMPLOYEE_CACHE)
    public List<Employee> findAll() {
        log.info("Fetch all employees");
        return employeeRepository.findAll();
    }

    @Cacheable(cacheNames = CacheName.EMPLOYEE_CACHE, key = "{#id}")
    public Employee findOne(Long id) {
        log.info("Fetch one employee, employeeId = {}", id);
        return employeeRepository.findById(id).get();
    }

    @Cacheable(cacheNames = CacheName.EMPLOYEE_CACHE, key = "{#employee.id}")
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @CachePut(cacheNames = CacheName.EMPLOYEE_CACHE, key = "{#employee.id}")
    public Employee update(Long id, Employee employee) {
        Employee entity = employeeRepository.findById(employee.getId()).get();
        entity.setEmail(employee.getEmail());
        entity.setFirstName(employee.getFirstName());
        entity.setLastName(employee.getLastName());
        return employeeRepository.save(entity);
    }

    @CacheEvict(cacheNames = CacheName.EMPLOYEE_CACHE, key = "{#id}")
    public void delete(Long id) {
        log.info("Delete one");
        employeeRepository.deleteById(id);
    }

    @CacheEvict(cacheNames = CacheName.EMPLOYEE_CACHE, allEntries = true)
    public void invalidateCache() {

    }





}
