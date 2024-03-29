package acmecil.project.projima.com.acmecil.Medicamentos;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import acmecil.project.projima.com.acmecil.R;
import acmecil.project.projima.com.acmecil.model.Farmacia;
import acmecil.project.projima.com.acmecil.model.Publicidad;

public class PublicityActivity extends AppCompatActivity {
    String nameOwner, description, medication, url;
    private EditText txt_nameOwner;
    private EditText txt_description;
    private EditText txt_medication;
    private EditText txt_url;
    private Uri path;
    private Button btn_confirmar;
    private Button btn_cancel;
    private static final String TAG = "PublicityActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicity);

        txt_nameOwner = (EditText) findViewById(R.id.txtOwner);
        txt_description =(EditText) findViewById(R.id.txtDescription);
        txt_medication =(EditText) findViewById(R.id.txtMedication);
        txt_url=(EditText) findViewById(R.id.txtUrl);
        btn_confirmar=(Button) findViewById(R.id.btonConfirma);
        btn_cancel=(Button) findViewById(R.id.btonCancelar);

    }
    public void validarPublicidad(View v){
        if(txt_nameOwner.getText().toString().isEmpty()||txt_description.toString().isEmpty()||txt_medication.toString().isEmpty()){
            final String text = "Todos los campos deben contener la información requerida";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
        if(path==null){
            final String text= "Debe cargar una imagen";
            Toast.makeText(this,text,Toast.LENGTH_LONG).show();
        }
        if(txt_url.getText().toString().isEmpty()){
            url=null;
        }
        else{
            url=txt_url.getText().toString();
        }
        nameOwner = txt_nameOwner.getText().toString();
        description =txt_description.getText().toString();
        medication = txt_medication.getText().toString();
        registrarPublicidad(nameOwner,description,medication);
    }
    //Ingresar el la base de datos la publicidad
    public void registrarPublicidad(String powner, String pdescription, String pmedication){

    }


    public void clickImage(View view) {
        cargarImagen();
    }

    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione una imagen"),10);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==RESULT_OK){
            path=data.getData();
        }

    }

    private void createPublicity(String pOwner, String pDescription, String pMedication, String pUrl) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        DatabaseReference postsRef = ref.child("Ad");
        DatabaseReference newPostRef = postsRef.push();
        newPostRef.setValue(new Publicidad(pOwner, pDescription, pMedication, pUrl));
    }

    private void crearFarmacia(String name, double pLatitud, double pLongitud) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        double latitud = pLatitud;
        double longitud = pLongitud;
        DatabaseReference postsRef = ref.child("Pharmacy");
        DatabaseReference newPostRef = postsRef.push();
        newPostRef.setValue(new Farmacia(name, latitud, longitud));
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

    private ArrayList<String> setArrayMarcas(ArrayList<String> pMarcas){
        return null;
    }

    private void getMedicinasErrorPrice() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        Query myTopPostsQuery = ref.child("Medicamentos").orderByChild("state").equalTo("True");

        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
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

    private void getPharmacies() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        Query myTopPostsQuery = ref.child("Pharmacy");

        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Farmacia> farmacias = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String nombre = postSnapshot.child("nombre").getValue(String.class);
                    String id = postSnapshot.getKey();
                    Farmacia farmacia = new Farmacia(nombre, id);
                    farmacias.add(farmacia);
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

    private ArrayList<Farmacia> farmaciasNameId(ArrayList<Farmacia> pFarmacias) {
        // Logica aqui
        return null;
    }

    private void getUsers() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        Query myTopPostsQuery = ref.child("Usuarios");

        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
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

    private void updateMedicamentoEstado(String pMedecineId) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        DatabaseReference refMedState = ref.child("Medicamentos").child(pMedecineId).child("state");
        refMedState.setValue("False");
    }

    private void updateMedicamentoPrecio(String pMedecineId, float pPrecio) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        DatabaseReference refMedState = ref.child("Medicamentos").child(pMedecineId).child("state");
        refMedState.setValue("True");

        DatabaseReference refMedPrecio = ref.child("Medicamentos").child(pMedecineId).child("price");
        refMedState.setValue(pPrecio);
    }

    private void deleteUser(String userId) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        DatabaseReference refMedFarm = ref.child("Usuarios").child("state");

        refMedFarm.setValue("False");
    }

    public void cancel(View view) {
        txt_nameOwner.setText("");
        txt_url.setText("");
        txt_medication.setText("");
        txt_description.setText("");
    }
}