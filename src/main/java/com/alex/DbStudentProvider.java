package com.alex;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbStudentProvider implements StudentProvider {
    private Connection connection;

    public DbStudentProvider(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Student> findAll(int offset, int limit) throws SQLException {
        String sql = "select * from students offset ? limit ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, limit);
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
    public List<Student> findByAge(int age, int offset, int limit) throws SQLException {
        String sql = "select * from students where age = ? offset ? limit ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, age);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit);
            try(ResultSet results = preparedStatement.executeQuery()) {
                return prepareData(results);
            }
        }
    }

    @Override
    public long countByAge(int age) throws SQLException {
        String sql = "select count(*) from students where age = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, age);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getLong("count");
            }
        }
    }

    @Override
    public Student create(Student student) throws SQLException {
        String sql = "insert into students (first_name, last_name, age) values(?,?,?) returning *";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getAge());
            try(ResultSet results = preparedStatement.executeQuery()) {
                results.next();
                return prepareStudent(results);
            }
        }

    }

    @Override
    public Student update(Student student) throws SQLException, IllegalArgumentException {
        if(student.getId() == null) {
            throw new IllegalArgumentException("Provided student does not have an id");
        }
        String sql = "update students set first_name = ?, last_name = ?, age = ? where id = ? returning *";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setLong(4, student.getId());
            try(ResultSet results = preparedStatement.executeQuery()) {
                results.next();
                return prepareStudent(results);
            }
        }
    }

    @Override
    public void delete(Student student) throws IllegalArgumentException, SQLException {
        if(student.getId() == null) {
            throw new IllegalArgumentException("Provided student does not have an id");
        }
        String sql = "delete from students where id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, student.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteById(long id) throws SQLException {
        String sql = "delete from students where id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
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
