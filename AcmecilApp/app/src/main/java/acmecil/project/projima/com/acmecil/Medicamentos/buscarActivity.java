package acmecil.project.projima.com.acmecil.Medicamentos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import acmecil.project.projima.com.acmecil.R;
import acmecil.project.projima.com.acmecil.adapters.PublicityAdapter;


public class buscarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String vMarca, vMedication;
    int vRadio;
    Spinner spn_marca;
    Spinner spn_radio;
    ArrayAdapter<String>  aMarca;
    ArrayAdapter<Integer> aRadio;
    EditText txtMedication;
    Integer [] arrayRadio = new Integer[] {5, 15, 25, 35};
    String [] arrayMarca;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        arrayMarca = getArrayMarca();
        spn_marca = (Spinner) findViewById(R.id.spinnerMarca);
        spn_radio = (Spinner) findViewById(R.id.spinnerRadio);
        txtMedication = (EditText) findViewById(R.id.editTxtBusqueda);
        spn_radio.setOnItemSelectedListener(this);
        spn_marca.setOnItemSelectedListener(this);

        aMarca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayMarca);
        aRadio= new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, arrayRadio);
        spn_marca.setAdapter(aMarca);
        spn_radio.setAdapter(aRadio);

        //Lista de resultados???
        List<ImageResult> testlist = new ArrayList<>();
        for (int i = 0; i < 20 ; i++) {
            testlist.add(new ImageResult(String.format("j %d", i)));
        }

        PublicityAdapter adapter = new PublicityAdapter(testlist);
        RecyclerView recyclerView = findViewById(R.id.search_results_recycler_view);


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        recyclerView.setAdapter(adapter);


    }
    //Función que obtiene las marcas de los medicamentos existentes
    public String[] getArrayMarca(){
        //Esto se borra y se hace una llamada a la base de datos que retorne un arreglo con las marcas existentes
        //Esto es solo para prueba
        String [] marca = new String[] {"Panadol", "Alka-Seltzer", "Bicetil", "Alerlisin"};
        return marca;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void search(View view) {
        vMarca = spn_marca.getSelectedItem().toString();
        vRadio = Integer.valueOf(spn_radio.getSelectedItem().toString());
        vMedication = txtMedication.getText().toString();

    }
    //Función que llama al layout con los resultados del medicamento a buscar?????
    public void searchMedication(String pMarca, String pRadio, String pMedication){

    }
}