package com.alex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String... args) {
//        String url       = "jdbc:postgresql://localhost:5432/postgres";
//        String user      = "postgres";
//        String password  = "qwerty1";
//
//        try(Connection connection = DriverManager.getConnection(url, user, password)) {
//            StudentProvider studentProvider = new DbStudentProvider(connection);
//            studentProvider.deleteById(1);
//        } catch (SQLException | IllegalArgumentException e) {
//            e.printStackTrace();
//        }
        LOGGER.info("Some log message");
    }
}