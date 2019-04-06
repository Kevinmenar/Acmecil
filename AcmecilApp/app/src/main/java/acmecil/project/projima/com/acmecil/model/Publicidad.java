package acmecil.project.projima.com.acmecil.model;

public class Publicidad {
    public String owner;
    public String descripcion;
    public String medicacion;
    public String url;

    public Publicidad (String pOwner, String pDescripcion, String pMedicacion, String pUrl) {
        this.owner = pOwner;
        this.descripcion = pDescripcion;
        this.medicacion = pMedicacion;
        this.url = pUrl;
    }
}
