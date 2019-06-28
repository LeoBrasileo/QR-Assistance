package com.example.android.pruebas3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class registro2 extends AppCompatActivity {

    private Button registrar;
    private EditText editMail;
    private EditText editDni2;
    private EditText editNombre;
    private EditText editApellido;
    private EditText repPass1;
    private EditText contrasena;
    private ProgressDialog progressDialog;
    private Spinner spinnerColegios;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference users;
    private String nombre;
    private String dou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);

        registrar = findViewById(R.id.btn3);
        editMail = findViewById(R.id.editMail);
        editNombre = findViewById(R.id.editNombre);
        editApellido = findViewById(R.id.editApellido);
        editDni2 = findViewById(R.id.editDni2);
        contrasena = findViewById(R.id.editPass3);
        repPass1 = findViewById(R.id.editPass4);
        spinnerColegios = findViewById(R.id.spinner1);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("usuarios");
        progressDialog = new ProgressDialog(this);

        Bundle bundle = this.getIntent().getExtras();
        editDni2.setText(bundle.getString("user"));
        contrasena.setText(bundle.getString("passw"));

        final String nombre1 = editNombre.getText().toString();
        final String apellido2 = editApellido.getText().toString();
        final String nombre3 = nombre1 + " " + apellido2;

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registroDatabase(editDni2.getText().toString(),contrasena.getText().toString(),repPass1.getText().toString(),editNombre.getText().toString(),editApellido.getText().toString(),editMail.getText().toString(),spinnerColegios.getSelectedItem().toString());
            }
        });
    }

    private void registroDatabase (final String dni, final String password, final String repPass, final String nombre, final String apellido, final String email, String school)
    {

        if (TextUtils.isEmpty(dni)) {
            Toast.makeText(this, "Se debe ingresar un DNI", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(nombre)) {
            Toast.makeText(this, "Indique su nombre", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(apellido)) {
            Toast.makeText(this, "Indique su apellido", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Se debe ingresar una contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(repPass)) {
            Toast.makeText(this, "Porfavor repita la contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        final String nombre1;
        nombre1 = nombre + " " + apellido;
        if (school.equals("Seleccione un colegio"))
        {
            final String school1 = "";
            school = school1;
        }
        progressDialog.setMessage("Creando Usuario...");
        progressDialog.show();
        if (password.equals(repPass))
        {
            final String finalSchool = school;
            users.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(dni).exists())
                    {
                        if (!dni.isEmpty())
                        {
                            ObjetoUsuario userExist = dataSnapshot.child(dni).getValue(ObjetoUsuario.class);
                            if (userExist.getDni().equals(dni))
                            {
                                Toast.makeText(registro2.this, "Ese usuario ya existe", Toast.LENGTH_LONG).show();
                            }
                        }
                    }else
                    {
                        ObjetoUsuario usuario = new ObjetoUsuario(nombre1, password, dni, email, finalSchool);
                        users.child(String.valueOf(dni)).setValue(usuario);
                        Toast.makeText(registro2.this,"Usuario correctamente registrado",Toast.LENGTH_LONG).show();
                        //estaria bueno un delay aca
                        progressDialog.dismiss();
                        //El usuario entra a la activity .login para loguearse devuelta
                        Intent intent =
                                new Intent(registro2.this,login.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            //aca va un delay
            progressDialog.dismiss();
        }else
        {
            Toast.makeText(this,"Las contraseñas no coinciden",Toast.LENGTH_LONG).show();
        }
        progressDialog.dismiss();
    }

}
