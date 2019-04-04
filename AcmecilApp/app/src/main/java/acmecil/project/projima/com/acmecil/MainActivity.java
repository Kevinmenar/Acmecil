package acmecil.project.projima.com.acmecil;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ResourceBundle;

import acmecil.project.projima.com.acmecil.R;

import acmecil.project.projima.com.acmecil.Medicamentos.*;
import acmecil.project.projima.com.acmecil.login.LogInActivity;

public class MainActivity extends AbsRuntimePermission {

    private final int PERMISSIONS_REQUEST_MAP = 6546;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestAppPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                R.string.msg,
                PERMISSIONS_REQUEST_MAP);

        Controller.getInstance().setSessionRole(Controller.Role.COMMON_USER);

    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if(requestCode == PERMISSIONS_REQUEST_MAP){
            Controller.getInstance().setLocationAvailable(true);
        }
        startActivity(new Intent(getApplicationContext(), SearchResultMapActivity.class));
    }

    public void onclick(View view) {
        Intent ListSong = new Intent(getApplicationContext(), buscarActivity.class);
        startActivity(ListSong);
    }
}
