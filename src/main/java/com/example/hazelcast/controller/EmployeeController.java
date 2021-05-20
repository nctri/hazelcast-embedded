package com.example.hazelcast.controller;

import com.example.hazelcast.model.Employee;
import com.example.hazelcast.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    private final CacheManager cacheManager;

    @GetMapping
    public List<Employee> findAll() {
        cacheManager.getCache("allEmployeeCache");
        CacheProperties.EhCache cache = (CacheProperties.EhCache) cacheManager.getCache("allEmployeeCache").getNativeCache();
        return employeeService.findAll();
    }
/*
    public List<> findAllCacheValues() {
        Cache cache = cacheManager.getCache("allEmployeeCache");
    }*/

}
