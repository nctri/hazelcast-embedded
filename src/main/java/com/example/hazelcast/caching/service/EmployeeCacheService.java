package com.example.hazelcast.caching.service;

import com.example.hazelcast.model.Employee;
import com.example.hazelcast.repository.EmployeeRepository;
import com.hazelcast.collection.ISet;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class EmployeeCacheService extends HazelcastSetService<Long, Employee> {

    private final EmployeeRepository employeeRepository;

    public EmployeeCacheService(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance,
                                EmployeeRepository employeeRepository) {
        super(hazelcastInstance);
        this.employeeRepository = employeeRepository;
    }

    @PostConstruct
    public void init() {
        ISet<Employee> set = getSet();
        set.clear();
        set.addAll(employeeRepository.findAll());
    }

    @Override
    protected Long getKey(Employee value) {
        return value.getId();
    }

    @Override
    protected String getCacheName() {
        return "employeeCache";
    }
}
