package com.example.hazelcast.config;

import com.example.hazelcast.model.Employee;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;

public class EmployeeStreamSerializer implements StreamSerializer<Employee> {

    @Override
    public void write(ObjectDataOutput out, Employee employee) throws IOException {
        out.writeString(employee.getFirstName());
        out.writeString(employee.getLastName());
        out.writeString(employee.getEmail());
        out.writeLong(employee.getId());
    }

    @Override
    public Employee read(ObjectDataInput in) throws IOException {
        return Employee.builder()
                .firstName(in.readString())
                .lastName(in.readString())
                .email(in.readString())
                .id(in.readLong())
                .build();
    }

    @Override
    public int getTypeId() {
        return 1;
    }
}
