package acmecil.project.projima.com.acmecil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import acmecil.project.projima.com.acmecil.Medicamentos.MedicineDTO;

public class ChangePriceActivity extends AppCompatActivity {

    private EditText newPriceEditText;
    private TextView titleView;
    private TextView lastPriceTextView;
    private TextView pharmacyName;
    private Button confirmButton;
    private String medID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_price);

        newPriceEditText = findViewById(R.id.change_price_medicine_edit_text);
        titleView = findViewById(R.id.change_price_medicine_name);
        confirmButton =findViewById(R.id.change_price_medicine_confirm_button);
        lastPriceTextView = findViewById(R.id.change_price_medicine_last_price);
        pharmacyName = findViewById(R.id.change_price_pharmacy_name_text_view);

        Bundle bundle = getIntent().getExtras();
        titleView.setText(bundle.getString("medicineName"));
        lastPriceTextView.setText(String.format("¢ %d",bundle.getInt("lastPrice")));
        pharmacyName.setText(bundle.getString("pharmacyName"));
        medID = bundle.getString("medicineID");
        switch (Controller.getInstance().getSessionRole()){
            case ADMINISTRATOR:{
                confirmButton.setText("Confirmar");
                break;
            }
            case COMMON_USER:{
                confirmButton.setText("Reportar");
                break;
            }
        }




    }


    public void OnButtonChangePrice(View view){
        String content = newPriceEditText.getText().toString();
        String lastPrice = lastPriceTextView.getText().toString();
        float precio = Float.parseFloat(content);
        if(content.matches("") || content.equals(lastPrice)){
            Toast.makeText(this,"Debe introducir un nuevo precio",Toast.LENGTH_SHORT).show();
            return;
        }
        switch (Controller.getInstance().getSessionRole()){
            case COMMON_USER:{
                update(Controller.Role.COMMON_USER, medID, precio);
                break;
            }
            case ADMINISTRATOR:{
                update(Controller.Role.ADMINISTRATOR, medID, precio);
                break;
            }
        }
    }

    private void update(Controller.Role User, String medID, float precio) {
        switch (User){
            case COMMON_USER:{
                updateMedicamentoEstado(medID);
                break;
            }
            case ADMINISTRATOR:{
                updateMedicamentoPrecio(medID,precio);
                break;
            }
        }
    }


    private void updateMedicamentoEstado(String pMedecineId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        DatabaseReference refMedState = ref.child("Medicamentos").child(pMedecineId).child("state");
        refMedState.setValue("False");
    }

    private void updateMedicamentoPrecio(String pMedecineId, float pPrecio) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        DatabaseReference refMedState = ref.child("Medicamentos").child(pMedecineId).child("state");
        refMedState.setValue("True");

        DatabaseReference refMedPrecio = ref.child("Medicamentos").child(pMedecineId).child("price");
        refMedState.setValue(pPrecio);
    }


    private void fillDTO(){
        String newPriceString = newPriceEditText.getText().toString();
        int price = Integer.parseInt(newPriceString);
        String medicineName = titleView.getText().toString();
        MedicineDTO temporalDTOAccess = Controller.getInstance().getMedicineDTO();
        temporalDTOAccess.setMedicineNewPrice(price);
        temporalDTOAccess.setMedicineName(medicineName);
        //Agregar campos pertinentes
    }

}
