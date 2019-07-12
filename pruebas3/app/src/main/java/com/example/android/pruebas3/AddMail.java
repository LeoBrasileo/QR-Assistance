package com.example.android.pruebas3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AddMail extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText mail;
    private Button validar;
    private FirebaseDatabase database;
    private DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mail);
        toolbar = findViewById(R.id.toolbar2);
        mail = findViewById(R.id.addMail1);
        validar = findViewById(R.id.btnAddEmail);
        database = FirebaseDatabase.getInstance();
        users = database.getReference("usuarios");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registrar un mail");

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarMail(mail.getText().toString());
            }
        });
    }

    private void RegistrarMail (final String mail)
    {
        if (TextUtils.isEmpty(mail))
        {
            Toast.makeText(this,"Ingrese un Email",Toast.LENGTH_LONG).show();
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registrando Email");
        progressDialog.show();
        Bundle bundle = getIntent().getExtras();
        final String dni = bundle.getString("dni");

        users.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                ObjetoUsuario addmail = dataSnapshot.child(dni).getValue(ObjetoUsuario.class);
                addmail.setEmail(mail);
                users.child(dni).setValue(addmail);
                Intent intent =
                        new Intent(AddMail.this,NavDrawer.class);
                Bundle bundle = new Bundle();
                bundle.putString("user", addmail.getNombre().toString());
                bundle.putString("mail", addmail.getEmail().toString());
                bundle.putString("dni", addmail.getDni().toString());
                intent.putExtras(bundle);
                progressDialog.dismiss();
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}
