package com.example.hazelcast.controller;

import com.example.hazelcast.caching.service.EmployeeCacheService;
import com.example.hazelcast.model.Employee;
import com.hazelcast.core.HazelcastInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employeeCache")
@RequiredArgsConstructor
public class EmployeeCacheController {

    private final HazelcastInstance hazelcastInstance;

    private final EmployeeCacheService employeeCacheService;

    @GetMapping("/{cacheName}")
    public List<String> hazelCastInstance(@PathVariable String cacheName) {
        return hazelcastInstance.getSet(cacheName).stream().map(Object::toString).collect(Collectors.toList());
    }

    @GetMapping("/invalidate/{cacheName}")
    public String invalidateCache(@PathVariable String cacheName) {
        Set<Employee> set = hazelcastInstance.getSet(cacheName);
        set.clear();
        return "Clear cache successfully";
    }

    @GetMapping("/refresh")
    public String refreshCache() {
        employeeCacheService.init();
        return "Refresh cache successfully";
    }

}
