package com.alex;

import java.sql.SQLException;
import java.util.List;

public interface StudentProvider {

    List<Student> findAll(int offset, int limit) throws SQLException;

    long countAll() throws SQLException;

    Student findById(long id) throws SQLException;

    List<Student> findByAge(int age, int offset, int limit) throws SQLException;

    long countByAge(int age) throws SQLException;

    Student create(Student student) throws SQLException;

    Student update(Student student) throws SQLException, IllegalArgumentException;

    void delete(Student student) throws SQLException, IllegalArgumentException;

    void deleteById(long id) throws SQLException;
}
