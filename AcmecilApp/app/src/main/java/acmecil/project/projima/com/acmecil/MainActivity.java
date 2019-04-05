package acmecil.project.projima.com.acmecil;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ResourceBundle;

import acmecil.project.projima.com.acmecil.R;

import acmecil.project.projima.com.acmecil.Medicamentos.*;
import acmecil.project.projima.com.acmecil.login.LogInActivity;

public class MainActivity extends AbsRuntimePermission {

    private final int PERMISSIONS_REQUEST_MAP = 6546;
    ListView listView;
    String [] choose = new String[]{"Buscar medicamento","Inscribir medicamento","Registrar publicidad","Eliminar usuario"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        requestAppPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                R.string.msg,
                PERMISSIONS_REQUEST_MAP);
        Controller.getInstance().setSessionRole(Controller.Role.COMMON_USER);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,choose);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });

    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if(requestCode == PERMISSIONS_REQUEST_MAP){
            Controller.getInstance().setLocationAvailable(true);
        }
       // startActivity(new Intent(getApplicationContext(), SearchResultMapActivity.class));
    }

    public void onclick(View view) {
        Intent ListSong = new Intent(getApplicationContext(), registerMedicationActivity.class);
        startActivity(ListSong);
    }
}
