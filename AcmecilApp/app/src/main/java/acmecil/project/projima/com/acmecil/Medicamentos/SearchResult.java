package acmecil.project.projima.com.acmecil.Medicamentos;

public class SearchResult {

    private String pharmacyName;
    private String pharmacyAdress;
    private int price;
    private String imagePath;


    public SearchResult(String pharmacyName, String pharmacyAdress, String imagePath, int price){
        this.imagePath = imagePath;
        this.pharmacyAdress =pharmacyAdress;
        this.price = price;
        this.pharmacyName = pharmacyName;
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
}
