package acmecil.project.projima.com.acmecil.Medicamentos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import acmecil.project.projima.com.acmecil.R;

public class registerMedicationActivity extends AppCompatActivity {
    String name, marca;
    Float cost;
    Boolean estadoDolares, estadoColones;
    private EditText nameM;
    private EditText marcaM;
    private EditText costM;
    private Button enterM;
    private RadioButton colones;
    private  RadioButton dolares;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_medication);

        nameM = (EditText) findViewById(R.id.txtNameMedication);
        marcaM = (EditText) findViewById(R.id.txtMarca);
        costM =(EditText) findViewById(R.id.txtPrecio);

        enterM =(Button) findViewById(R.id.btnIngresar);
        colones=(RadioButton) findViewById(R.id.rdoBtonColones);
        dolares= (RadioButton) findViewById(R.id.rdoBtonDolares);


    }
    public void validarMedicamento(View v){
        if(nameM.getText().toString().isEmpty()||marcaM.getText().toString().isEmpty()||costM.toString().isEmpty()){
            final String text = "Todos los campos deben contener la información requerida";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
        else{
            if(colones.isChecked()){
                estadoColones = true;
                estadoDolares = false;
            }
            if(dolares.isChecked()){
                estadoDolares =true;
                estadoColones = false;
            }
        }
        name= nameM.getText().toString();
        marca = marcaM.getText().toString();
        cost = Float.valueOf(costM.getText().toString());
        inscribirMedicamento(name,marca,cost, estadoColones,estadoDolares);
    }
    //Agregar a la base de datos el medicamento
    public void inscribirMedicamento(String pname, String pmarca, Float pcost, Boolean pcolones, Boolean pdolares){

    }
}
