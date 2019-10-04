package com.example.android.pruebas3;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

@IgnoreExtraProperties
public class login extends AppCompatActivity {

    private Button ingresar;
    private Button registrar;
    private EditText editDni;
    private EditText contrasena;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseAnalytics firebaseAnalytics;
    private FirebaseDatabase database;
    private DatabaseReference users;
    private String email;
    private String pass;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        ingresar = findViewById(R.id.btn1);
        registrar = findViewById(R.id.btn2);
        editDni = findViewById(R.id.editDni);
        contrasena = findViewById(R.id.editPass);
        checkBox = findViewById(R.id.checkBox1);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("usuarios");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        progressDialog = new ProgressDialog(this);

        cargarpreferencias();


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(login.this, registro2.class);
                Bundle bundle = new Bundle();
                bundle.putString("user", editDni.getText().toString());
                bundle.putString("passw", contrasena.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(editDni.getText().toString(), contrasena.getText().toString());
            }
        });


    }

    private void cargarpreferencias() {
        SharedPreferences sharedPreferences = getSharedPreferences
                ("credenciales",Context.MODE_PRIVATE);

        editDni.setText(sharedPreferences.getString("user",""));
        contrasena.setText(sharedPreferences.getString("pass",""));

        if (!editDni.equals("") && !contrasena.equals(""))
        {
            signIn(editDni.getText().toString(),contrasena.getText().toString());
        }
    }

    private void signIn(final String dni, final String password)
    {
        if (TextUtils.isEmpty(dni))
        {
            Toast.makeText(this,"Se debe ingresar un DNI",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Se debe ingresar una contraseña",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Ingresando...");
        progressDialog.show();
        users.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(dni).exists())
                {
                    if (!dni.isEmpty())
                    {
                        ObjetoUsuario login1 = dataSnapshot.child(dni).getValue(ObjetoUsuario.class);
                        if (login1.getPassword().equals(password))
                        {
                            if (checkBox.isChecked())
                            {
                                SharedPreferences sharedPreferences = getSharedPreferences
                                        ("credenciales", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user", login1.getDni().toString());
                                editor.putString("pass", login1.getPassword());
                                editor.commit();
                            }

                            Toast.makeText(login.this, "Logueado exitosamente", Toast.LENGTH_LONG).show();
                            Intent intent =
                                    new Intent(login.this, NavDrawer.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("user", login1.getNombre().toString());
                            bundle.putString("mail", login1.getEmail().toString());
                            bundle.putString("dni", login1.getDni().toString());
                            bundle.putString("div", login1.getSchool().toString());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else
                        {
                            Toast.makeText(login.this, "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                        }
                    }
                }else
                {
                    Toast.makeText(login.this, "Usuario no registrado", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(login.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
