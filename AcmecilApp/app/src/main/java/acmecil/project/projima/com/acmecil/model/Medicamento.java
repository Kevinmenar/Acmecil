package acmecil.project.projima.com.acmecil.model;

public class Medicamento {
    public String nombre;
    public String marca;
    public String moneda;
    public float price;
    public String idFarmacia;
    public String state;
    public  String id;

    public Medicamento (String pNombre, String pMarca, String pMoneda, float pPrice, String pIdFarmacia, String pState) {
        this.nombre = pNombre;
        this.marca = pMarca;
        this.moneda = pMoneda;
        this.price = pPrice;
        this.idFarmacia = pIdFarmacia;
        this.state = pState;
    }

    public Medicamento (String pNombre, String pMarca, String pMoneda, float pPrice, String pIdFarmacia, String pState, String pId) {
        this.nombre = pNombre;
        this.marca = pMarca;
        this.moneda = pMoneda;
        this.price = pPrice;
        this.idFarmacia = pIdFarmacia;
        this.state = pState;
        this.id = pId;
    }
}