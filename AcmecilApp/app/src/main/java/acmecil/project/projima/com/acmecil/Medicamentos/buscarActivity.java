package acmecil.project.projima.com.acmecil.Medicamentos;

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
 //   String [] arrayMarca;
    ArrayList<String> list = new ArrayList<>(2);
    private static final String TAG = "buscarActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
     //   arrayMarca = getArrayMarca();
        getMedicinasMarcas();
        spn_marca = (Spinner) findViewById(R.id.spinnerMarca);
        spn_radio = (Spinner) findViewById(R.id.spinnerRadio);
        txtMedication = (EditText) findViewById(R.id.editTxtBusqueda);
        spn_radio.setOnItemSelectedListener(this);
        spn_marca.setOnItemSelectedListener(this);

        aMarca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
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
    private void getMedicinasMarcas() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        Query myTopPostsQuery = ref.child("Medicamentos");

        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    System.out.println("Values: " + postSnapshot.child("marca").getValue());
                    String marca = postSnapshot.child("marca").getValue(String.class);

                    if(!(list.indexOf(marca) > 0)) {
                        list.add(marca);
                    }
                    // TODO: handle the post
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
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
        }

    }
    //Función que llama al layout con los resultados del medicamento a buscar????
    public double distance(double lat1, double lat2, double lon1, double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        return distance;
    }

    //Función que llama al layout con los resultados del medicamento a buscar????
    public void searchMedication(final String pMarca, double pRadio, String pMedication, final double pLatitud, final double pLongitud){
        final DatabaseReference QueryAssociazioni = FirebaseDatabase.getInstance().getReference();
        Query zonesQuery = QueryAssociazioni.child("Medicamentos").orderByChild("nombre").equalTo(pMedication);

        zonesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("Info Gotit ");
                Log.w(TAG, dataSnapshot.toString());
                for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
                    System.out.println("The value is: ");
                    Log.i(TAG, zoneSnapshot.child("nombre").getValue(String.class));

                    Map<String, Object> valuesMap = (HashMap<String, Object>) dataSnapshot.getValue();

                    // Get push id value.
                    String marca = (String) valuesMap.get("marca");
                    if (marca == pMarca) {
                        String keyIdFarmacia = (String) valuesMap.get("idFarmacia");
                        Query Famacias = QueryAssociazioni.child("Medicamentos").child(keyIdFarmacia);

                        Famacias.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
                                    Map<String, Object> valuesMap = (HashMap<String, Object>) dataSnapshot.getValue();

                                    double latitud = (double) valuesMap.get("latitud");
                                    double longitud = (double) valuesMap.get("longitud");

                                    double distance = distance(pLatitud, latitud, pLongitud, longitud);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });
    }
}