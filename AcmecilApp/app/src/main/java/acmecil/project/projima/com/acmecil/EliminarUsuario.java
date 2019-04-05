package acmecil.project.projima.com.acmecil;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EliminarUsuario extends AppCompatActivity {

    Button b1,b2 ,b3;
    EditText ed2;
    TextView tv1,tv2;
    private String correo;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminate_user);

        b1 = (Button)findViewById(R.id.button5);
        b2 = (Button)findViewById(R.id.button6);
        b3 = (Button)findViewById(R.id.button7);
        ed2 = (EditText)findViewById(R.id.editText31);
        tv1 = (TextView)findViewById(R.id.textView5);
        tv2= (TextView)findViewById(R.id.textView6);
        tv1.setVisibility(View.INVISIBLE);
        tv2.setVisibility(View.INVISIBLE);
        b2.setEnabled(false);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(get_user()){
                    tv1.setText(correo);
                    tv2.setText(date);
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setVisibility(View.VISIBLE);
                    b2.setEnabled(true);

                }else{
                    tv1.setText("No encontrado");
                    tv2.setText("No disponible");

                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private boolean get_user(){
        if(!ed2.getText().toString().isEmpty()){
            // se llama al SP que obtiene los datos del usuario
            correo = "correo prueba";//se coge de DB
            date = "15/1/19";//se coge de DB
            return true;
        }else return false;
    }
}