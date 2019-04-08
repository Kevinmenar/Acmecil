package acmecil.project.projima.com.acmecil.Medicamentos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import acmecil.project.projima.com.acmecil.AddPharmacyActivity;
import acmecil.project.projima.com.acmecil.R;
import acmecil.project.projima.com.acmecil.SearchResultMapActivity;
import acmecil.project.projima.com.acmecil.model.Farmacia;

public class SelectPharmacyActivity extends AppCompatActivity {
    ListView listView;
    Button btnChoose;
    int pos;
    String idFarmacia;
    private static final String TAG = "SelectPharmacyActivity";
    private ArrayList<String> farmacias = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pharmacy);

        listView=findViewById(R.id.listView);
        btnChoose=findViewById(R.id.btnChoose);

        getPharmacies();

    }
    //Función que retorna un arreglo que el nombre de las farmacias sacado de la base de datos
    private String[] setPharmacy(){
        String[] farmacias = new String[]{"1","2", "3","4","5","6"};
        return farmacias;
    }
    //Funcion que retorna el id del la farmacia que se encuentra en X posición
    private String setIdPharmacy(int position){
        //estos deberían ser los id's de las farmacias.

        String a = ids.get(position);
        return a;
    }

    public void newPharmacy(View view) {
            startActivity(new Intent(getApplicationContext(), AddPharmacyActivity.class));
    }


    private void getPharmacies() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        Query myTopPostsQuery = ref.child("Pharmacy");

        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String nombre = postSnapshot.child("nombre").getValue(String.class);
                    String id = postSnapshot.getKey();

                    farmacias.add(nombre);
                    ids.add(id);

                    //Farmacia farmacia = new Farmacia(nombre, id);
                    //farmacias.add(farmacia);
                }
                farmaciasNameId();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }

    private void farmaciasNameId() {
        // Logica aqui
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,farmacias);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos=position;
                idFarmacia = setIdPharmacy(position);
                Intent intent= new Intent(getApplicationContext(),registerMedicationActivity.class);
                intent.putExtra("idPharmacy",idFarmacia);
                startActivity(intent);
            }
        });
    }
}
/*listView = findViewById(R.id.list_view_songs);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        currentSong = position;
        isPlaying = false;
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), songIds[currentSong]);
        setSongInfo(songNames[position]);
        songDuration = mediaPlayer.getDuration();
        prgrss = mediaPlayer.getCurrentPosition();
        progressSeekBar.setMax(songDuration);
        progressSeekBar.setProgress(prgrss);
        playSong(view);
        }
        })*/