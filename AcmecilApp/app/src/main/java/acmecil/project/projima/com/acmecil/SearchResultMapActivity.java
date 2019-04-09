package acmecil.project.projima.com.acmecil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.ArrayList;
import java.util.List;

import acmecil.project.projima.com.acmecil.Medicamentos.SearchResult;
import acmecil.project.projima.com.acmecil.Medicamentos.buscarActivity;
import acmecil.project.projima.com.acmecil.adapters.ResultListAdapter;
import acmecil.project.projima.com.acmecil.model.Farmacia;

public class SearchResultMapActivity extends AppCompatActivity implements PermissionsListener{


    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private static final String TAG = "SearchResultMapActivity";
    private List<SearchResult> testlist = new ArrayList<>();
    private RecyclerView recyclerView;
    private ResultListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_search_result_map);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        //Recupera la lista de resultados
        double logitud = getIntent().getDoubleExtra("log" , 0.0);
        double lat = getIntent().getDoubleExtra("lat" , 0.0);
        String marca = getIntent().getStringExtra("marca");
        int radio = getIntent().getIntExtra("radio" , 0);
        String medi = getIntent().getStringExtra("Medication");

        searchMedication(marca, radio, medi, latitude, logitud);

        // Create supportMapFragment
        SupportMapFragment mapFragment;
        if (savedInstanceState == null) {

            // Create fragment
            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Build mapboxMap
            MapboxMapOptions options = new MapboxMapOptions();
            options.camera(new CameraPosition.Builder()
                    .target(new LatLng(9.856903,-83.9146553))
                    .zoom(14)
                    .build());

            // Create map fragment
            mapFragment = SupportMapFragment.newInstance(options);


            transaction.add(R.id.container, mapFragment, "com.mapbox.map");
            transaction.commit();
        } else {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentByTag("com.mapbox.map");
        }

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                SearchResultMapActivity.this.mapboxMap = mapboxMap;
                mapboxMap.setStyle(Style.OUTDOORS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        //enableLocationComponent(style);
                        style.addImage("marker-icon-id",
                                BitmapFactory.decodeResource(
                                        SearchResultMapActivity.this.getResources(), R.drawable.mapbox_marker_icon_default));

                        GeoJsonSource geoJsonSource = new GeoJsonSource("source-id", Feature.fromGeometry(
                                Point.fromLngLat(9.8560621,-83.9112765)));
                        style.addSource(geoJsonSource);

                        SymbolLayer symbolLayer = new SymbolLayer("layer-id", "source-id");
                        symbolLayer.withProperties(
                                PropertyFactory.iconImage("marker-icon-id")
                        );
                        style.addLayer(symbolLayer);
                    }
                });
            }
        });

        
    }

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

                    System.out.println("Marca " + marca);
                    System.out.println("pMarca " + pMarca);

                    if (marca.equals(pMarca)) {
                        String keyIdFarmacia = zoneSnapshot.child("idFarmacia").getValue(String.class);
                        System.out.println("keyIdFarmacia " + keyIdFarmacia);
                        DatabaseReference QueryFarmacia = FirebaseDatabase.getInstance().getReference();
                        Query Famacias = QueryFarmacia.child("Pharmacy").child(keyIdFarmacia);

                        Famacias.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                System.out.println("dataSnapshot " + dataSnapshot );
                                if(dataSnapshot!=null){
                                    String nombre = dataSnapshot.child("nombre").getValue(String.class);
                                    String direccion = dataSnapshot.child("direccion").getValue(String.class);
                                    double latitud = dataSnapshot.child("latitud").getValue(double.class);
                                    double longitud = dataSnapshot.child("longitud").getValue(double.class);
                                    Farmacia farmacia = new Farmacia(nombre, latitud, longitud, direccion);
                                    SearchResult searchResult = new SearchResult("", "", "", nombre , Integer.valueOf(precio), farmacia);
                                    testlist.add(searchResult);
                                    double distance = distance(pLatitud, latitud, pLongitud, longitud);
                                    System.out.println("distance " + distance);

                                    if(pRadio*1000>=distance) {
                                        adapter = new ResultListAdapter(testlist, getApplicationContext());
                                        recyclerView = findViewById(R.id.search_results_recycler_view);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                        recyclerView.setAdapter(adapter);
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });
    }


    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(this, loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.NORMAL);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.mapbox_attributionTelemetryMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.mapbox_attributionTelemetryMessage, Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
