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

import acmecil.project.projima.com.acmecil.R;
import acmecil.project.projima.com.acmecil.model.Usuario;

public class RegisterActivity extends AppCompatActivity {

    EditText ed1,ed2, ed3;
    Button b1,b2;

    private FirebaseAuth mAuth;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

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
                        createUserByEmail(ed1.getText().toString(), ed2.getText().toString());
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

    private void updateUI(FirebaseUser pUser, String pEmail) {
        String userId = pUser.getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        ref.child("Usuarios").child(userId).setValue(new Usuario("User", "Nombre", "True", pEmail));
    }

    private void createUserByEmail(final String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user, email);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                    }
                });

    }
}
