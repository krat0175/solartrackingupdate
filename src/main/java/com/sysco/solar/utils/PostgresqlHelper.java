package com.sysco.solar.utils;

import java.sql.*;

public class PostgresqlHelper {
    private String dbUrl;
    private String userName;
    private String password;

    public PostgresqlHelper(String dbUrl, String dbUserName, String dbPassword) {
        this.dbUrl = dbUrl;
        this.userName = dbUserName;
        this.password = dbPassword;
    }
    //create postgresql connection
    public Connection getPostgreSqlConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(dbUrl, userName, password);
        } catch (Exception e) {
            System.out.println("DB Connection Error!");
        }
        return DriverManager.getConnection(dbUrl, userName, password);
    }
    //execute query in postgresql
    public ResultSet executeQuery(String query) throws SQLException {
        Connection connection = getPostgreSqlConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    //execute insert in postgresql
    public void executeInsert(String query) throws SQLException {
        Connection connection = getPostgreSqlConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }
    //close connection
    public void closeConnection() throws SQLException {
        Connection connection = getPostgreSqlConnection();
        connection.close();
    }


}
