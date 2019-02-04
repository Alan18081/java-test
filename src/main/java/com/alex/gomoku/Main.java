package com.alex.gomoku;

import java.sql.*;

public class Main {
    public static void main(String... args) {
        String url       = "jdbc:postgresql://localhost:5432/postgres";
        String user      = "postgres";
        String password  = "qwerty1";

        try(Connection connection = DriverManager.getConnection(url, user, password)) {
            try(Statement statement = connection.createStatement()) {
                String sql = "create table if not exists products ( id serial primary key, name varchar(100), description text)";
                statement.executeUpdate(sql);
            }
            try(Statement statement = connection.createStatement()) {
                String sql = "insert into products (name, description) values('Falcon 9', 'Rocket booster')";
                statement.execute(sql);
            }
            try(Statement statement = connection.createStatement()) {
                String sql = "select * from products";
                try(ResultSet resultSet = statement.executeQuery(sql)) {
                    ResultSetMetaData rm = resultSet.getMetaData();
                    for(int i = 1; i < rm.getColumnCount(); i++) {
                        System.out.print(rm.getColumnLabel(i) + (i != rm.getColumnCount() ? " | " : ""));
                    }

                }
            }
//
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}