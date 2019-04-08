package acmecil.project.projima.com.acmecil.model;

public class Usuario {
    public String rol;
    public String nombre;
    public String state;
    public String email;

    public Usuario(String pRol, String pNombre, String pState, String pEmail) {
        this.email = pEmail;
        this.rol = pRol;
        this.nombre = pNombre;
        this.state = pState;
    }
}