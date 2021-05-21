package com.example.hazelcast.controller;

import com.example.hazelcast.model.Employee;
import com.example.hazelcast.service.EmployeeService;
import com.hazelcast.core.HazelcastInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    private final HazelcastInstance hazelcastInstance;

    @GetMapping("/hazelcast/{cacheName}")
    public List<String> hazelCastInstance(@PathVariable String cacheName) {
        return hazelcastInstance.getMap(cacheName).entrySet().stream().map(entry -> entry.getKey() + " => "
        + entry.getValue().toString()).collect(Collectors.toList());
    }

    @GetMapping("/hazelcast/invalidate/{cacheName}")
    public void invalidateCache(@PathVariable String cacheName) {
        hazelcastInstance.getMap(cacheName).evictAll();
    }

    @GetMapping
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee findOne(@PathVariable Long id) {
        return employeeService.findOne(id);
    }

    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        return employeeService.create(employee);
    }

    @PutMapping("/{id}")
    public Employee update(@RequestBody Employee employee, @PathVariable Long id) {
        return employeeService.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }



}
