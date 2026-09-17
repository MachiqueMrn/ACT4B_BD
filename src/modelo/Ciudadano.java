package modelo;

public class Ciudadano {
    
    // Atributos
    private String nombre;
    private String curp;
    private String telefono;

    // Constructor
    public Ciudadano (String nombre, String curp, String telefono) {
        this.nombre = nombre;
        this.curp = curp;
        this.telefono = telefono;
    }

    // Método -> Ciudadano
    public void mostrarInfo() {
        System.out.println(
            "Ciudadano: " + nombre + // Ciudadano: Carlos Machique
          "\nCURP: " + curp +        // CURP: MAMCXXXXX
          "\nTel: " + telefono       // Tel: 962123456
        );
    }

    // Método Getter
    public String getNombre() { return nombre; }

    // CÓDIGO EXTRA - BD
    public String getCurp() { return curp; }
    public String getTelefono() { return telefono; }
}