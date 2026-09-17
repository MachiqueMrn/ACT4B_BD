package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // Las credenciales exactas que configuramos en Docker
    private static final String URL = "jdbc:postgresql://localhost:5432/sistema_tramites";
    private static final String USUARIO = "admin";
    private static final String PASSWORD = "password123";

    // Método que funciona como el puente de comunicación
    public static Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("¡Conexión exitosa a la Base de Datos!");
        } catch (SQLException e) {
            System.out.println("Error: No se pudo conectar a la Base de Datos. Verifique que el contenedor esté encendido.");
            System.out.println("Detalle técnico: " + e.getMessage());
        }
        return conexion;
    }
}