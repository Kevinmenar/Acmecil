package acmecil.project.projima.com.acmecil;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EliminarUsuario extends AppCompatActivity {

    Button b1,b2 ,b3;
    EditText ed2;
    TextView tv1,tv2;
    private String correo;
    private String uid;
    private static final String TAG = "EliminarUsuario";

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
                if(!ed2.getText().toString().isEmpty()){
                    getUserEmail(ed2.getText().toString());
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser  ();
            }
        });

    }

    private boolean get_user(){
        if(!ed2.getText().toString().isEmpty()){
            // se llama al SP que obtiene los datos del usuario
            correo = "correo prueba";//se coge de DB
            return true;
        }else return false;
    }

    private void updateUser() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        ref.child("Usuarios").child(uid).child("state").setValue("False");

        Toast.makeText(getApplicationContext(), "Usuario eliminado",Toast.LENGTH_SHORT).show();
    }

    private void getUserEmail (final String pEmail) {
        System.out.println("pEmail " + pEmail);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        Query myTopPostsQuery = ref.child("Usuarios").orderByChild("email").equalTo(pEmail);

        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("dataSnapshot" + dataSnapshot);
                if(dataSnapshot.getValue()!=null){

                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        // TODO: handle the post
                        uid = postSnapshot.getKey();
                        correo = postSnapshot.child("email").getValue(String.class);

                        System.out.println("uid " + uid);
                        System.out.println("correo " + correo);

                        tv1.setText(correo);
                        tv1.setVisibility(View.VISIBLE);
                        b2.setEnabled(true);
                    }
                } else {
                    tv1.setText("No encontrado");
                    tv2.setText("No disponible");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
                tv1.setText("No encontrado");
                tv2.setText("No disponible");
            }
        });
    }
}