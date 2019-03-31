package acmecil.project.projima.com.acmecil;

public class Controller {

    private Role sessionRole;
    private static final Controller ourInstance = new Controller();

    private Controller() {

    }

    public static Controller getInstance() {
        return ourInstance;
    }

    public Role getSessionRole() {
        return sessionRole;
    }

    public void setSessionRole(Role sessionRole) {
        this.sessionRole = sessionRole;
    }

    public enum Role{
        ADMINISTRATOR, COMMON_USER;
    }

}
