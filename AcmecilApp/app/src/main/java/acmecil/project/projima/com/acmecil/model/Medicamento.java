package acmecil.project.projima.com.acmecil.model;

public class Medicamento {
    public String nombre;
    public String marca;
    public String moneda;
    public float price;

    public Medicamento (String pNombre, String pMarca, String pMoneda, float pPrice) {
        this.nombre = pNombre;
        this.marca = pMarca;
        this.moneda = pMoneda;
        this.price = pPrice;
    }
}
