package config;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Conexion {
    Connection con;

    public Conexion(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/analisis", "root", "");
            System.out.println("conecta");

        } catch (Exception e) {
            System.err.println("No se pudo establecer la coneccion" + e);
        }
    }

    public Connection getConnection() {
        return con;    }
}
