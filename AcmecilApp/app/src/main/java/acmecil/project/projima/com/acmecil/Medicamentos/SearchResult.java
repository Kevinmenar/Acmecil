package acmecil.project.projima.com.acmecil.Medicamentos;

public class SearchResult {

    private String pharmacyName;
    private String pharmacyAdress;
    private int price;
    private String imagePath;
    private String medicineName;


    public SearchResult(String pharmacyName, String pharmacyAdress, String imagePath, String medicineName ,int price){
        this.imagePath = imagePath;
        this.pharmacyAdress =pharmacyAdress;
        this.price = price;
        this.pharmacyName = pharmacyName;
        this.medicineName = medicineName;
    }


    public String getPharmacyAdress() {
        return pharmacyAdress;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }
}
