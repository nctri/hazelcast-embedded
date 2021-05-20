package com.example.hazelcast.config;

import com.example.hazelcast.model.Employee;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;

public class EmployeeStreamSerializer implements StreamSerializer<Employee> {

    @Override
    public void write(ObjectDataOutput out, Employee employee) throws IOException {
        out.writeUTF(employee.getFirstName());
        out.writeUTF(employee.getLastName());
        out.writeUTF(employee.getEmail());
        out.writeLong(employee.getId());
    }

    @Override
    public Employee read(ObjectDataInput in) throws IOException {
        return Employee.builder()
                .firstName(in.readUTF())
                .lastName(in.readUTF())
                .email(in.readUTF())
                .id(in.readLong())
                .build();
    }

    @Override
    public int getTypeId() {
        return 1;
    }

    @Override
    public void destroy() {

    }
}
