package acmecil.project.projima.com.acmecil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.List;

import acmecil.project.projima.com.acmecil.model.Farmacia;

public class AddPharmacyActivity extends AppCompatActivity implements PermissionsListener {

    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    public EditText pharmacyNameEditTex;
    public EditText pharmacyAdressEditTex;
    public Button insertPharmacyButton;
    boolean pNameIsEmpty, pAdressIsEmpty;
    double lat, longi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_add_pharmacy);

        pharmacyNameEditTex = findViewById(R.id.add_pharmacy_name_edit_text);
        pharmacyAdressEditTex = findViewById(R.id.add_pharmacy_adress_edit_text);
        insertPharmacyButton = findViewById(R.id.add_pharmacy_button);
        pNameIsEmpty = pAdressIsEmpty = true;

        insertPharmacyButton.setEnabled(false);

        pharmacyNameEditTex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().trim().length()==0){
                    pNameIsEmpty = true;
                }else {
                    pNameIsEmpty = false;
                }
                changeButton();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pharmacyAdressEditTex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().trim().length()==0){
                    pAdressIsEmpty = true;
                }else {
                    pAdressIsEmpty = false;
                }
                changeButton();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();


        SupportMapFragment mapFragment;
        if (savedInstanceState == null) {

            // Create fragment
            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Build mapboxMap
            MapboxMapOptions options = new MapboxMapOptions();
            options.camera(new CameraPosition.Builder()
                    .target(new LatLng(latitude,longitude))
                    .zoom(14)
                    .build());

            // Create map fragment
            mapFragment = SupportMapFragment.newInstance(options);


            transaction.add(R.id.container, mapFragment, "com.mapbox.map");
            transaction.commit();
        } else {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentByTag("com.mapbox.map");
        }


        //
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                AddPharmacyActivity.this.mapboxMap = mapboxMap;
                mapboxMap.setStyle(Style.OUTDOORS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        //enableLocationComponent(style);
                        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        double longitude = location.getLongitude();
                        double latitude = location.getLatitude();
                        longi = longitude;
                        lat = latitude;
                        style.addImage("marker-icon-id",
                                BitmapFactory.decodeResource(
                                        AddPharmacyActivity.this.getResources(), R.drawable.mapbox_marker_icon_default));

                        GeoJsonSource geoJsonSource = new GeoJsonSource("source-id", Feature.fromGeometry(
                                Point.fromLngLat(longitude,latitude)));
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

    private void changeButton(){
        if (!pNameIsEmpty && !pAdressIsEmpty){
            Log.i("Input", "Aceptado");
            insertPharmacyButton.setEnabled(true);
            insertPharmacyButton.setBackgroundColor(Color.parseColor("#C4C4C4"));
        }else{
            Log.i("Input", "nel");
            insertPharmacyButton.setEnabled(false);
            insertPharmacyButton.setBackgroundColor(Color.parseColor("#C4C4C4"));
        }
    }

    public void insertPharmacy(View view){
        // TODO: obtener localización del pin, obtener datos

        String nombre = pharmacyNameEditTex.getText().toString();
        String direccion = pharmacyAdressEditTex.getText().toString();
        crearFarmacia(nombre, lat, longi, direccion);
    }



    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

    }

    private void crearFarmacia(String name, double pLatitud, double pLongitud, String direccion) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        double latitud = pLatitud;
        double longitud = pLongitud;
        DatabaseReference postsRef = ref.child("Pharmacy");
        DatabaseReference newPostRef = postsRef.push();
        newPostRef.setValue(new Farmacia(name, latitud, longitud, direccion));
    }

}
