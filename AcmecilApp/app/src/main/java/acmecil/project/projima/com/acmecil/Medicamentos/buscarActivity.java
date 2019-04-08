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

        //aMarca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, localListMarcas);
        aRadio= new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, arrayRadio);

        spn_radio.setAdapter(aRadio);

        //Lista de resultados???
        List<ImageResult> testlist = new ArrayList<>();
        for (int i = 0; i < 20 ; i++) {
            testlist.add(new ImageResult(String.format("j %d", i)));
        }

        PublicityAdapter adapter = new PublicityAdapter(testlist,this);
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

            searchMedication(vMarca, vRadio, vMedication, latitude, longitude);

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
    public void searchMedication(final String pMarca, final int pRadio, String pMedication, final double pLatitud, final double pLongitud){
        DatabaseReference QueryAssociazioni = FirebaseDatabase.getInstance().getReference();
        Query zonesQuery = QueryAssociazioni.child("Medicamentos").orderByChild("nombre").equalTo(pMedication);

        zonesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("Info Gotit ");
                Log.w(TAG, dataSnapshot.toString());
                for (final DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
                    System.out.println("The value is: " + zoneSnapshot);
                    Log.i(TAG, zoneSnapshot.child("nombre").getValue(String.class));

                    // Get push id value.
                    final String marca = zoneSnapshot.child("marca").getValue(String.class);
                    final String precio = zoneSnapshot.child("price").getValue(String.class);
                    final String nombre = zoneSnapshot.child("nombre").getValue(String.class);

                    if (marca == pMarca) {
                        String keyIdFarmacia = zoneSnapshot.child("idFarmacia").getValue(String.class);
                        System.out.println("keyIdFarmacia " + keyIdFarmacia);
                        DatabaseReference QueryFarmacia = FirebaseDatabase.getInstance().getReference();
                        Query Famacias = QueryFarmacia.child("Pharmacy").child(keyIdFarmacia);

                        Famacias.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if(dataSnapshot!=null){
                                    String nombre = dataSnapshot.child("nombre").getValue(String.class);
                                    String direccion = dataSnapshot.child("direccion").getValue(String.class);
                                    double latitud = dataSnapshot.child("latitud").getValue(double.class);
                                    double longitud = dataSnapshot.child("longitud").getValue(double.class);
                                    Farmacia farmacia = new Farmacia(nombre, latitud, longitud, direccion);
                                    SearchResult searchResult = new SearchResult("", "", "", nombre , Integer.valueOf(precio), farmacia);

                                    double distance = distance(pLatitud, latitud, pLongitud, longitud);
                                    System.out.println("distance " + distance);

                                    if(pRadio>=distance) {
                                        searchResultsList.add(searchResult);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }

                //  Logic here
                Bundle bundle = new Bundle();
                bundle.putSerializable("List", searchResultsList);
                Intent i = new Intent(buscarActivity.this, SearchResultMapActivity.class);
                i.putExtra("x", bundle);
                startActivity(i);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

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