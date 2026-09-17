package modelo;

public class Requisito {
    
    // Atributos
    private String nombreDocumento;
    private boolean fueEntregado;

    // Constructor
    public Requisito (String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
        this.fueEntregado = false; // Por defecto, inicia sin entregarse.
    }

    // Método - Requisito
    public void marcarComoEntregado() {
        this.fueEntregado = true;
        System.out.println("\nLos documentos [" + nombreDocumento + "] han sido recibidos.");
    }

    // Método Getter
    public boolean getFueEntregado() { return fueEntregado; }
    
    // CÓDIGO EXTRA - BD
    public String getNombreDocumento() { return nombreDocumento; }
}