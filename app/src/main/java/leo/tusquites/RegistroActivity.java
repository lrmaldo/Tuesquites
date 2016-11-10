package leo.tusquites;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import leo.tusquites.modelos.usuarios;

public class RegistroActivity extends AppCompatActivity {


    private EditText inputEmail, inputPassword, nombre;
    private Button btnSignIn, btnSignUp, btnRestaurar;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        /// /Obtener  instancia Firebase
        auth = FirebaseAuth.getInstance();
        nombre =(EditText) findViewById(R.id.nombre_re);
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnRestaurar = (Button) findViewById(R.id.btn_reset_password);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnRestaurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistroActivity.this, RestuararActivity.class));
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(RegistroActivity.this, PrincipalActivity.class));
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nom = nombre.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(nom)){
                    Toast.makeText(getApplicationContext(), "Falta el nombre", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Falta email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Falta contraseña!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Contraseña corta, minimo 6 caracteres!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegistroActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegistroActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    /// obtiee el registro de usuario nuevo y luego inserta en la base de datos
                                   //RegistroBD(task.getResult().getUser());
                                    String username = nom;

                                    // Write new user
                                    InsertarUsuario(task.getResult().getUser().getUid(), username, task.getResult().getUser().getEmail());

                                    Toast.makeText(RegistroActivity.this, "Cierra sesión y Vuelve a iniciar sesión." ,
                                            Toast.LENGTH_SHORT).show();
                                    // Go to MainActivity
                                    startActivity(new Intent(RegistroActivity.this, PrincipalActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });


    }

    private void RegistroBD(FirebaseUser user) {
        String username = user.getEmail();

        // Write new user
        InsertarUsuario(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        startActivity(new Intent(RegistroActivity.this, PrincipalActivity.class));
        finish();
    }
private  void InsertarUsuario(String userID, String nombre, String email){
    usuarios user = new usuarios(nombre,email,"false");
   mDatabase.child("usuarios").child(userID).setValue(user);
   // auth.signOut();

}
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
