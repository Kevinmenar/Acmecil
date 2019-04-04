package acmecil.project.projima.com.acmecil;

import acmecil.project.projima.com.acmecil.Medicamentos.MedicineDTO;

public class Controller {

    private Role sessionRole;
    private MedicineDTO medicineDTO;
    private Boolean isLocationAvailable;

    private static final Controller ourInstance = new Controller();

    private Controller() {
        medicineDTO = new MedicineDTO();
    }

    public boolean reportPrice(){
        //report price
        return false;
    }

    public boolean changePrice(){
        //changePrice
        return false;
    }

    public void updateMedicineInfo(int medicineCode, int pharmacyCode, String medicineName, String pharmacyName, int medicineLastPrice, int medicineNewPrice){
        medicineDTO.updateMedicineInfo( medicineCode, pharmacyCode, medicineName, pharmacyName, medicineLastPrice, medicineNewPrice);
    }

    //

    public static Controller getInstance() {
        return ourInstance;
    }

    public Role getSessionRole() {
        return sessionRole;
    }

    public void setSessionRole(Role sessionRole) {
        this.sessionRole = sessionRole;
    }

    public MedicineDTO getMedicineDTO(){ return medicineDTO; }

    public void setLocationAvailable(boolean b) {
        isLocationAvailable = b;
    }

    public enum Role{
        ADMINISTRATOR, COMMON_USER;
    }

}
