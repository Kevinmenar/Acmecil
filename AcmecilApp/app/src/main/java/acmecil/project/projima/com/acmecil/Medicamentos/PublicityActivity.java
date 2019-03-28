package acmecil.project.projima.com.acmecil.Medicamentos;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import acmecil.project.projima.com.acmecil.R;

public class PublicityActivity extends AppCompatActivity {
    String nameOwner, description, medication, url;
    private EditText txt_nameOwner;
    private EditText txt_description;
    private EditText txt_medication;
    private EditText txt_url;
    private Uri path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicity);

        txt_nameOwner = (EditText) findViewById(R.id.txtOwner);
        txt_description =(EditText) findViewById(R.id.txtDescription);
        txt_medication =(EditText) findViewById(R.id.txtMedication);
        txt_url=(EditText) findViewById(R.id.txtUrl);

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
}
