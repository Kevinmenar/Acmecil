package acmecil.project.projima.com.acmecil.login;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import acmecil.project.projima.com.acmecil.R;

public class RegisterActivity extends AppCompatActivity {

    EditText ed1,ed2, ed3;
    Button b1,b2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Intent intent = getIntent();

        ed1 = (EditText)findViewById(R.id.editText);
        ed2 = (EditText)findViewById(R.id.editText2);
        ed3 = (EditText)findViewById(R.id.editText8);

        b1 = (Button)findViewById(R.id.button2);

        b2 = (Button)findViewById(R.id.button4);
        b2.setPaintFlags(b2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ed1.getText().toString().isEmpty()&&!ed2.getText().toString().isEmpty()&&!ed3.getText().toString().isEmpty()){
                    if(ed2.getText().toString().equals(ed3.getText().toString())){
                        Toast.makeText(getApplicationContext(),"Creando usuario.",Toast.LENGTH_SHORT).show();//aqui se pone el create user
                    }else{
                        Toast.makeText(getApplicationContext(),"La contraseñas no concuerdan.",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Debe ingresar datos.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
