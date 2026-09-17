package modelo;

public class SolicitudLicencia {
    // Atributos
    private int folio;
    private String tipoLicencia;
    private boolean aprobada;

    // Asociaciones
    private Ciudadano titular;
    private Requisito requisito;

    // Constructor
    public SolicitudLicencia(int folio, String tipoLicencia, Ciudadano titular, Requisito requisito) {
        this.folio = folio;
        this.tipoLicencia = tipoLicencia;
        this.titular = titular;
        this.requisito = requisito;
        this.aprobada = false;
    }

    // Métodos
    public void procesarSolicitud() {
        if (requisito.getFueEntregado()) {
            this.aprobada = true;
            System.out.println("\n>> LA SOLICITUD CON FOLIO " + folio + " HA SIDO APROBADA.");
        } else {
            System.out.println("\n>> LA SOLICITUD CON FOLIO " + folio + " HA SIDO RECHAZADA.\n>> FALTAN DOCUMENTOS.");
        }
    }

    public void consultarEstado() {
        System.out.println("=".repeat(50));
        System.out.println("    Estado del Trámite");
        System.out.println("=".repeat(50));
        titular.mostrarInfo();
        System.out.println("Tipo de licencia: " + tipoLicencia);
        System.out.println("Aprobado: " + (aprobada ? "Sí" : "No"));
    }

    // Métodos Getters 
    public String getTipoLicencia() { return tipoLicencia; }

    // CÓDIGO EXTRA - BD
    public boolean getEstaAprobada() { return aprobada; }
}