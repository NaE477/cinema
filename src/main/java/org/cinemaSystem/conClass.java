package org.cinemaSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conClass {

    private static conClass conClass;
    private Connection connection;

    private conClass() throws SQLException{
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.
                    getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "postgres");
        }
        catch (SQLException e){
            System.out.println("SQL Exception");
        }
        catch (ClassNotFoundException e){
            System.out.println("Class not found Exception");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static conClass getInstance() throws SQLException {
        try {
            if (conClass == null) {
                conClass = new conClass();
            } else if (conClass.getConnection().isClosed()) {
                conClass = new conClass();
            }
            return conClass;
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return conClass;
    }

}
