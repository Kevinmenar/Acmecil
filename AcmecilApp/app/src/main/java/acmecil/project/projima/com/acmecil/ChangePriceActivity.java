package acmecil.project.projima.com.acmecil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import acmecil.project.projima.com.acmecil.Medicamentos.MedicineDTO;

public class ChangePriceActivity extends AppCompatActivity {

    private EditText newPriceEditText;
    private TextView titleView;
    private TextView lastPriceTextView;
    private TextView pharmacyName;
    private Button confirmButton;

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
        lastPriceTextView.setText(bundle.getInt("lastPrice"));
        pharmacyName.setText(bundle.getString("pharmacyName"));
        switch (Controller.getInstance().getSessionRole()){
            case COMMON_USER:{
                confirmButton.setText("Confirmar");
                break;
            }
            case ADMINISTRATOR:{
                confirmButton.setText("Reportar");
                break;
            }
        }
    }


    public void OnButtonChangePrice(View view){
        String content = newPriceEditText.getText().toString();
        String lastPrice = lastPriceTextView.getText().toString();
        if(content.matches("") || content.equals(lastPrice)){
            Toast.makeText(this,"Debe introducir un nuevo precio",Toast.LENGTH_SHORT).show();
            return;
        }
        fillDTO();
        switch (Controller.getInstance().getSessionRole()){
            case COMMON_USER:{
                Controller.getInstance().reportPrice();
                break;
            }
            case ADMINISTRATOR:{
                Controller.getInstance().changePrice();
                break;
            }
        }
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
