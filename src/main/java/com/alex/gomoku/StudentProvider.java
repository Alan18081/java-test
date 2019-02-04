package com.alex.gomoku;

import java.sql.SQLException;
import java.util.List;

public interface StudentProvider {

    List<Student> findAll(int offset, int limit) throws SQLException;

    long countAll() throws SQLException;

    Student findById(long id) throws SQLException;

    List<Student> findByAge(int age, int offset, int limit);

    long countByAge(int age);

    Student create(Student student);

    Student update(Student student);

    void delete(Student student);

    void deleteById(long id);
}
