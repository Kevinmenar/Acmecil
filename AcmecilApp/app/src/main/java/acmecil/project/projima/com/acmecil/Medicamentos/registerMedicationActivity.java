package acmecil.project.projima.com.acmecil.Medicamentos;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import acmecil.project.projima.com.acmecil.R;
import acmecil.project.projima.com.acmecil.model.Medicamento;

public class registerMedicationActivity extends AppCompatActivity {
    String name, marca;
    Float cost;
    private String tipoMoneda;
    Boolean estadoDolares, estadoColones;
    private EditText nameM;
    private EditText marcaM;
    private EditText costM;
    private Button enterM;
    private RadioButton colones;
    private  RadioButton dolares;
    private TextView result;
    private String titulo = "Ingresar medicamento";
    private Button cancelM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_medication);
        this.setTitle(titulo);

        nameM = (EditText) findViewById(R.id.txtNameMedication);
        marcaM = (EditText) findViewById(R.id.txtMarca);
        costM =(EditText) findViewById(R.id.txtPrecio);

        enterM =(Button) findViewById(R.id.btnIngresar);
        colones=(RadioButton) findViewById(R.id.rdoBtonColones);
        dolares= (RadioButton) findViewById(R.id.rdoBtonDolares);
        cancelM=(Button) findViewById(R.id.btonCancelar);


    }
    public void validarMedicamento(View v){
        if(nameM.getText().toString().isEmpty()||marcaM.getText().toString().isEmpty()||costM.toString().isEmpty()){
            final String text = "Todos los campos deben contener la información requerida";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
        else{
            if(colones.isChecked()){
                tipoMoneda = "colones";
            }
            if(dolares.isChecked()){
                tipoMoneda = "dolares";
            }
        }
        name= nameM.getText().toString();
        marca = marcaM.getText().toString();
        cost = Float.valueOf(costM.getText().toString());
        inscribirMedicamento(name,marca,cost, tipoMoneda, "IdPharmacy");
    }
    //Agregar a la base de datos el medicamento
    public void inscribirMedicamento(String pname, String pmarca, Float pcost, String pTipoMoneda, String pIdFarmacia){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        DatabaseReference postsRef = ref.child("Medicamentos");
        DatabaseReference newPostRef = postsRef.push();
        newPostRef.setValue(new Medicamento(pname, pmarca, pTipoMoneda, pcost, pIdFarmacia, "False"));

    }



    public void cancel(View view) {
        nameM.setText("");
        marcaM.setText("");
        costM.setText("");
    }
}