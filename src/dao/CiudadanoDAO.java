package dao;

import modelo.Ciudadano;
import modelo.SolicitudLicencia;
import modelo.Requisito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CiudadanoDAO {

    // Método público dedicado exclusivamente a guardar en la base de datos
    public void registrarCiudadano(Ciudadano ciudadano, SolicitudLicencia solicitud, Requisito requisito) {
        // Consultas para cada una de tus tres tablas
        String sqlCiudadano = "INSERT INTO Ciudadano (nombre, curp, telefono) VALUES (?, ?, ?)";
        String sqlSolicitud = "INSERT INTO SolicitudLicencia (id_ciudadano, tipo_licencia, esta_aprobada) VALUES (?, ?, ?)";
        String sqlRequisito = "INSERT INTO Requisito (folio_solicitud, nombre_documento, fue_entregado) VALUES (?, ?, ?)";

        try {
            Connection conexion = ConexionBD.conectar();
            if (conexion != null) {
                // 1. Guardar Ciudadano y obtener su ID automático
                PreparedStatement pstmtCiudadano = conexion.prepareStatement(sqlCiudadano, java.sql.Statement.RETURN_GENERATED_KEYS);
                pstmtCiudadano.setString(1, ciudadano.getNombre());
                pstmtCiudadano.setString(2, ciudadano.getCurp());
                pstmtCiudadano.setString(3, ciudadano.getTelefono());
                pstmtCiudadano.executeUpdate();

                // Recuperar el id_ciudadano generado por PostgreSQL
                java.sql.ResultSet rsCiudadano = pstmtCiudadano.getGeneratedKeys();
                int idCiudadanoGenerado = -1;
                if (rsCiudadano.next()) {
                    idCiudadanoGenerado = rsCiudadano.getInt(1);
                }

                // 2. Guardar Solicitud utilizando el ID del Ciudadano
                PreparedStatement pstmtSolicitud = conexion.prepareStatement(sqlSolicitud, java.sql.Statement.RETURN_GENERATED_KEYS);
                pstmtSolicitud.setInt(1, idCiudadanoGenerado); // Relacionamos con la tabla Ciudadano (FK)
                pstmtSolicitud.setString(2, solicitud.getTipoLicencia());
                pstmtSolicitud.setBoolean(3, solicitud.getEstaAprobada());
                pstmtSolicitud.executeUpdate();

                // Recuperar el folio generado por PostgreSQL
                java.sql.ResultSet rsSolicitud = pstmtSolicitud.getGeneratedKeys();
                int folioGenerado = -1;
                if (rsSolicitud.next()) {
                    folioGenerado = rsSolicitud.getInt(1);
                }

                // 3. Guardar Requisito utilizando el folio de la Solicitud
                PreparedStatement pstmtRequisito = conexion.prepareStatement(sqlRequisito);
                pstmtRequisito.setInt(1, folioGenerado); // Relacionamos con la tabla SolicitudLicencia (FK)
                pstmtRequisito.setString(2, requisito.getNombreDocumento());
                pstmtRequisito.setBoolean(3, requisito.getFueEntregado());
                pstmtRequisito.executeUpdate();

                System.out.println("\n>> ¡Éxito! Trámite guardado de forma permanente en PostgreSQL.");

                // Cerramos las herramientas de base de datos
                pstmtCiudadano.close();
                pstmtSolicitud.close();
                pstmtRequisito.close();
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println("\n>> Error al guardar en la base de datos: " + e.getMessage());
        }
    }

    // CÓDIGO EXTRA - BD: Método para consultar el historial de ciudadanos
    public void consultarHistorial() {
        // La consulta "mágica" que une las 3 tablas
        String sql = "SELECT c.id_ciudadano, c.nombre, c.curp, c.telefono, " +
                     "s.folio, s.tipo_licencia, s.esta_aprobada, " +
                     "r.nombre_documento, r.fue_entregado " +
                     "FROM Ciudadano c " +
                     "INNER JOIN SolicitudLicencia s ON c.id_ciudadano = s.id_ciudadano " +
                     "INNER JOIN Requisito r ON s.folio = r.folio_solicitud";

        try {
            Connection conexion = ConexionBD.conectar();
            if (conexion != null) {
                java.sql.Statement stmt = conexion.createStatement();
                java.sql.ResultSet rs = stmt.executeQuery(sql);

                System.out.println("\n");
                System.out.println("=".repeat(50));
                System.out.println("    HISTORIAL DE TRÁMITES");
                System.out.println("=".repeat(50));
                
                int contador = 0;
                
                while (rs.next()) {
                    contador++;
                    System.out.println("\nRegistro #" + rs.getInt("id_ciudadano") + ":");
                    System.out.println("=".repeat(50));
                    System.out.println("    Estado del Trámite (Folio: " + rs.getInt("folio") + ")");
                    System.out.println("=".repeat(50));
                    System.out.println("Ciudadano: " + rs.getString("nombre"));
                    System.out.println("CURP: " + rs.getString("curp"));
                    System.out.println("Tel: " + rs.getString("telefono"));
                    System.out.println("Tipo de licencia: " + rs.getString("tipo_licencia"));
                    System.out.println("Requisito (" + rs.getString("nombre_documento") + ") entregado: " + (rs.getBoolean("fue_entregado") ? "Sí" : "No"));
                    System.out.println("Aprobado: " + (rs.getBoolean("esta_aprobada") ? "Sí" : "No"));
                }
                
                if(contador == 0) {
                     System.out.println("\n>> Aún no hay ningún trámite registrado en la base de datos.");
                }

                rs.close();
                stmt.close();
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println("\n>> Error al consultar el historial: " + e.getMessage());
        }
    }
}