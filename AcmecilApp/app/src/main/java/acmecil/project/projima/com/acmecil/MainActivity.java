package acmecil.project.projima.com.acmecil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import acmecil.project.projima.com.acmecil.R;

import acmecil.project.projima.com.acmecil.Medicamentos.*;
import acmecil.project.projima.com.acmecil.login.LogInActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(getApplicationContext(), LogInActivity.class));
    }

    public void onclick(View view) {
        Intent ListSong = new Intent(getApplicationContext(), buscarActivity.class);
        startActivity(ListSong);
    }
}
