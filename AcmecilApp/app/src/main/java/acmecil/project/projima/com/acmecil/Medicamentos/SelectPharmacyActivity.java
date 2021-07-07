package acmecil.project.projima.com.acmecil.Medicamentos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import acmecil.project.projima.com.acmecil.R;
import acmecil.project.projima.com.acmecil.SearchResultMapActivity;

public class SelectPharmacyActivity extends AppCompatActivity {
    ListView listView;
    Button btnChoose;
    int pos;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pharmacy);
        listView=findViewById(R.id.listView);
        btnChoose=findViewById(R.id.btnChoose);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,setPharmacy());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos=position;
                id = setIdPharmacy(position);
                Intent intent= new Intent(getApplicationContext(),registerMedicationActivity.class);
                intent.putExtra("idPharmacy",id);
                startActivity(intent);
            }
        });
    }
    //Función que retorna un arreglo que el nombre de las farmacias sacado de la base de datos
    private String[] setPharmacy(){
        String[] farmacias = new String[]{" Farmacia Fischel Cartago Centro","Farmacia La Bomba Paraíso", "Farmacia Value Los Angeles","Farmacia AMPM","Farmacia Sucre"};
        return farmacias;
    }
    //Funcion que retorna el id del la farmacia que se encuentra en X posición
    private int setIdPharmacy(int position){
        //estos deberían ser los id's de las farmacias.
        int[] r = new int[]{1,2,3,4,5,6};
        int a = r[position];
        return a;
    }

    public void newPharmacy(View view) {
        startActivity(new Intent(getApplicationContext(), SearchResultMapActivity.class));
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