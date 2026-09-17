// Declaración de paquete
package main;

import java.util.Scanner;
import modelo.Ciudadano;
import modelo.Requisito;
import modelo.SolicitudLicencia;
import dao.CiudadanoDAO;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        boolean salir = false;

        System.out.println("=".repeat(50));
        System.out.println("    VENTANILLA DE TRÁMITES CIUDADANOS");
        System.out.println("=".repeat(50));

        while (!salir) {
            System.out.println("\n");
            System.out.println("=".repeat(50));
            System.out.println("    MENÚ PRINCIPAL");
            System.out.println("=".repeat(50));
            System.out.println("1. Registrar nuevo trámite de licencia");
            System.out.println("2. Consultar historial de trámites");
            System.out.println("3. Salir del sistema");
            System.out.print("Elija una opción (1-3): ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            if (opcion == 1) {
                System.out.println("\n");
                System.out.println("=".repeat(50));
                System.out.println("    A. Datos del Ciudadano");
                System.out.println("=".repeat(50));
                System.out.print("Nombre completo: ");
                String nombre = scanner.nextLine();
                System.out.print("CURP: ");
                String curp = scanner.nextLine();
                System.out.print("Teléfono: ");
                String telefono = scanner.nextLine();
                
                Ciudadano ciudadano = new Ciudadano(nombre, curp, telefono);
                
                System.out.println("\n");
                System.out.println("=".repeat(50));
                System.out.println("    B. Datos del Trámite");
                System.out.println("=".repeat(50));
                
                System.out.println("Tipo de licencia:");
                System.out.println("[1] Primera Vez.\n[2] Renovación\nElija una opción (1-2):");
                int opLicencia = scanner.nextInt();
                scanner.nextLine();
                
                String tipoLicencia = "";
                if (opLicencia == 1) {
                    tipoLicencia = "Primera Vez";
                } else if (opLicencia == 2) {
                    tipoLicencia = "Renovación";
                } else {
                    tipoLicencia = "No Especificado";
                }
                
                Requisito requisito = new Requisito("Identificación Oficial y Comprobante de domicilio");
                System.out.println("\n¿El ciudadano entregó todos los documentos?\n[1] Sí.\n[2] No.\nElija una opción (1-2):");
                int entrego = scanner.nextInt();
                scanner.nextLine();
                
                if (entrego == 1) {
                    requisito.marcarComoEntregado();
                }
                
                SolicitudLicencia nuevaSolicitud = new SolicitudLicencia(0, tipoLicencia, ciudadano, requisito);
                nuevaSolicitud.procesarSolicitud();
                
                CiudadanoDAO ciudadanoDAO = new CiudadanoDAO();
                ciudadanoDAO.registrarCiudadano(ciudadano, nuevaSolicitud, requisito);
                
            } else if (opcion == 2) {
                CiudadanoDAO ciudadanoDAO = new CiudadanoDAO();
                ciudadanoDAO.consultarHistorial();
                
            } else if (opcion == 3) {
                salir = true;
                System.out.println("\nCerrando la ventanilla... ¡Hasta pronto!");
            } else {
                System.out.println("\nOpción no válida. Por favor, intente de nuevo.");
            }
        }

        scanner.close();
    }
}