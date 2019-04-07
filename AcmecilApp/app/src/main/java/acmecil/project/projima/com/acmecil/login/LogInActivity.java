package acmecil.project.projima.com.acmecil.login;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import acmecil.project.projima.com.acmecil.MainActivity;
import acmecil.project.projima.com.acmecil.R;
import acmecil.project.projima.com.acmecil.model.Publicidad;

public class LogInActivity extends AppCompatActivity {

    Button b1,b3;
    EditText ed1,ed2;
    private FirebaseAuth mAuth;
    private static final String TAG = "LogInActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        // FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
        setContentView(R.layout.activity_log_in);

        b1 = (Button)findViewById(R.id.button);
        ed1 = (EditText)findViewById(R.id.editText);
        ed2 = (EditText)findViewById(R.id.editText2);

        b3 = (Button)findViewById(R.id.button3);
        b3.setPaintFlags(b3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean respuesta = log(ed1.getText().toString(),ed2.getText().toString() );
                //if( ed1.getText().toString().equals("admin") && ed2.getText().toString().equals("admin")) {
                if(respuesta){
                    Toast.makeText(getApplicationContext(),"Redirecting...",Toast.LENGTH_SHORT).show();
                    //Intent i = new Intent(LogInActivity.this, MainActivity.class);
                }else{
                    Toast.makeText(getApplicationContext(), "WrongCredentials",Toast.LENGTH_SHORT).show();

                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), SelectPharmacyActivity.class));
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
    private boolean log(String correo, String password){
        //Aqui se llama a base de datos con el email y la contraseña para verificar si coinciden
        //getMedicinasErrorPrice();
        logInByEmail(correo, password);
        return true;
    }

    private void updateUI(FirebaseUser currentUser) {
        String uid = currentUser.getUid();
        System.out.println("Uid" + uid);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        Query myTopPostsQuery = ref.child("Usuarios").child(uid);

        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String state = dataSnapshot.child("state").getValue(String.class);
                String nombre = dataSnapshot.child("nombre").getValue(String.class);
                String rol = dataSnapshot.child("rol").getValue(String.class);

                if(state.equals("True")) {
                    System.out.println("Finish intent");
                    if(rol.equals("User")) {
                        Intent i = new Intent(LogInActivity.this, MainActivity.class);
                        startActivity(i);
                        System.out.println("Finish intent");
                    } else {
                        // call admin view
                    }
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

    private void createPublicity(String pOwner, String pDescription, String pMedication, String pUrl) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        DatabaseReference postsRef = ref.child("Ad");
        DatabaseReference newPostRef = postsRef.push();
        newPostRef.setValue(new Publicidad(pOwner, pDescription, pMedication, pUrl));
    }

    private void logInByEmail(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

}
