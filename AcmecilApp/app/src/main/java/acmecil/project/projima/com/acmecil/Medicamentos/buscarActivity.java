package acmecil.project.projima.com.acmecil.Medicamentos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import acmecil.project.projima.com.acmecil.MainActivity;
import acmecil.project.projima.com.acmecil.R;
import acmecil.project.projima.com.acmecil.SearchResultMapActivity;
import acmecil.project.projima.com.acmecil.adapters.PublicityAdapter;
import acmecil.project.projima.com.acmecil.login.LogInActivity;
import acmecil.project.projima.com.acmecil.model.Farmacia;


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
    private static final String TAG = "buscarActivity";
    ArrayList<String> localListMarcas = new ArrayList<>();
    ArrayList<SearchResult> searchResultsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        getMedicinasMarcas();

        //arrayMarca = getArrayMarca();
        spn_marca = (Spinner) findViewById(R.id.spinnerMarca);
        spn_radio = (Spinner) findViewById(R.id.spinnerRadio);
        txtMedication = (EditText) findViewById(R.id.editTxtBusqueda);
        spn_radio.setOnItemSelectedListener(this);
        spn_marca.setOnItemSelectedListener(this);


        aMarca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, localListMarcas);
        aRadio= new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, arrayRadio);


        aMarca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aRadio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


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
        if(vMarca.isEmpty()||spn_radio.getSelectedItem().toString().isEmpty()||vMedication.isEmpty()){
            final String text = "Todos los campos deben contener la información requerida";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        } else {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();


            Intent i = new Intent(buscarActivity.this, SearchResultMapActivity.class);
            i.putExtra("log", longitude);
            i.putExtra("lat", latitude);
            i.putExtra("marca", vMarca);
            i.putExtra("radio", vRadio);
            i.putExtra("Medication", vMedication);
            startActivity(i);

        }

    }
    //Función que llama al layout con los resultados del medicamento a buscar????

    private void getMedicinasMarcas() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        Query myTopPostsQuery = ref.child("Medicamentos");

        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> list = new ArrayList<>(2);
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    System.out.println("Values: " + postSnapshot.child("marca").getValue());
                    String marca = postSnapshot.child("marca").getValue(String.class);

                    if(!(list.indexOf(marca) > 0)) {
                        list.add(marca);
                    }
                    // TODO: handle the post
                }
                setArrayMarcas(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }

    private void setArrayMarcas(ArrayList<String> pMarcas){
        aMarca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pMarcas);
        spn_marca.setAdapter(aMarca);
    }
}