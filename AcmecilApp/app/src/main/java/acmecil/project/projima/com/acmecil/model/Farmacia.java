package acmecil.project.projima.com.acmecil.model;

public class Farmacia {
    public String nombre;
    public double latitud;
    public double longitud;
    public String id;
    public String direccion;

    public Farmacia (String pNombre, double pLatitud, double pLongitud, String pDireccion) {
        this.nombre = pNombre;
        this.latitud = pLatitud;
        this.longitud = pLongitud;
        this.direccion = pDireccion;
    }

    public Farmacia (String pNombre, String pId) {
        this.nombre = pNombre;
        this.id = pId;
    }
}