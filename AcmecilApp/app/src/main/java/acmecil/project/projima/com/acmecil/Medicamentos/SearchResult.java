package acmecil.project.projima.com.acmecil.Medicamentos;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import acmecil.project.projima.com.acmecil.model.Farmacia;

public class SearchResult implements Serializable{

    private String pharmacyName;
    private String pharmacyAdress;
    private int price;
    private String imagePath;
    private String medicineName;
    private Farmacia farmacia;
    private String medID;


    public SearchResult(String pharmacyName, String pharmacyAdress, String imagePath, String medicineName ,int price, Farmacia pFarmacia, String medID){
        this.imagePath = imagePath;
        this.pharmacyAdress =pharmacyAdress;
        this.price = price;
        this.pharmacyName = pharmacyName;
        this.medicineName = medicineName;
        this.farmacia = pFarmacia;
        this.medID = medID;
    }


    protected SearchResult(Parcel in) {
        pharmacyName = in.readString();
        pharmacyAdress = in.readString();
        price = in.readInt();
        imagePath = in.readString();
        medicineName = in.readString();
    }


    public String getPharmacyAdress() {
        return farmacia.direccion;
    }

    public String getPharmacyName() {
        return farmacia.nombre;
    }

    public double getLatitud() {
        return farmacia.latitud;
    }

    public double getLongitud() {
        return farmacia.longitud;
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

    public String getMedID() {
        return medID;
    }
}
