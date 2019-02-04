package com.alex.gomoku;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbStudentProvider implements StudentProvider {
    private Connection connection;
    private DbStudentProvider() {}

    public StudentProvider getInstance(Connection connection) {
        this.connection = connection;
        return new DbStudentProvider();
    }

    @Override
    public List<Student> findAll(int offset, int limit) throws SQLException {
        String sql = "select * from students offset ? limit ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, offset);
            try(ResultSet results = preparedStatement.executeQuery()) {
                return prepareData(results);
            }
        }
    }

    @Override
    public long countAll() throws SQLException {
        String sql = "select count(*) from students";
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return resultSet.getLong("count");
        }
    }

    @Override
    public Student findById(long id) throws SQLException {
        String sql = "select * from students where id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try(ResultSet results = preparedStatement.executeQuery()) {
                results.next();
                return prepareStudent(results);
            }
        }
    }

    @Override
    public List<Student> findByAge(int age, int offset, int limit) {
        return null;
    }

    @Override
    public long countByAge(int age) {
        return 0;
    }

    @Override
    public Student create(Student student) {
        return null;
    }

    @Override
    public Student update(Student student) {
        return null;
    }

    @Override
    public void delete(Student student) {

    }

    @Override
    public void deleteById(long id) {

    }

    private List<Student> prepareData(ResultSet results) throws SQLException {
        List<Student> list = new ArrayList<>();
        while(results.next()) {
            list.add(prepareStudent(results));
        }

        return list;
    }

    private Student prepareStudent(ResultSet results) throws SQLException {
        Student student = new Student(results.getString("first_name"), results.getString("last_name"), results.getInt("age"));
        student.setId(results.getLong("id"));
        return student;
    }
}
