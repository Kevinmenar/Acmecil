package acmecil.project.projima.com.acmecil.Medicamentos;

public class MedicineDTO {

    private int medicineCode;
    private int pharmacyCode;
    private String medicineName;
    private String pharmacyName;
    private int medicineLastPrice;
    private int medicineNewPrice;

    public MedicineDTO() {
    }

    public MedicineDTO(int medicineCode, int pharmacyCode, String medicineName, String pharmacyName, int medicineLastPrice, int medicineNewPrice) {
        updateMedicineInfo( medicineCode, pharmacyCode, medicineName, pharmacyName, medicineLastPrice, medicineNewPrice);
    }

    public void updateMedicineInfo(int medicineCode, int pharmacyCode, String medicineName, String pharmacyName, int medicineLastPrice, int medicineNewPrice) {
        this.medicineCode = medicineCode;
        this.pharmacyCode = pharmacyCode;
        this.medicineName = medicineName;
        this.pharmacyName = pharmacyName;
        this.medicineLastPrice = medicineLastPrice;
        this.medicineNewPrice = medicineNewPrice;
    }

    public void setMedicineCode(int medicineCode) {
        this.medicineCode = medicineCode;
    }

    public void setPharmacyCode(int pharmacyCode) {
        this.pharmacyCode = pharmacyCode;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public void setMedicineLastPrice(int medicineLastPrice) {
        this.medicineLastPrice = medicineLastPrice;
    }

    public void setMedicineNewPrice(int medicineNewPrice) {
        this.medicineNewPrice = medicineNewPrice;
    }

    public int getMedicineCode() {
        return medicineCode;
    }

    public int getPharmacyCode() {
        return pharmacyCode;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public int getMedicineLastPrice() {
        return medicineLastPrice;
    }

    public int getMedicineNewPrice() {
        return medicineNewPrice;
    }
}
