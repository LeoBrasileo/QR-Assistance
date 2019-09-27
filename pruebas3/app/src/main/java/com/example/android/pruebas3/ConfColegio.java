package com.example.android.pruebas3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfColegio extends AppCompatActivity
{
    private FirebaseDatabase database;
    private DatabaseReference divisiones;
    private DatabaseReference users;
    private Button validar;
    private Spinner colegios;
    private Toolbar toolbar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf_colegio);
        validar = findViewById(R.id.btnColegios1);
        colegios = findViewById(R.id.spinnerColegios2);
        toolbar = findViewById(R.id.toolbar2);
        progressDialog = new ProgressDialog(this);
        database = FirebaseDatabase.getInstance();
        users = database.getReference("usuarios");
        divisiones = database.getReference("divisiones");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registrar / Cambiar division");

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDivision(colegios.getSelectedItem().toString());
            }
        });
    }

    private void setDivision (final String school)
    {
        if (school.equals("Seleccione su division"))
        {
            Toast.makeText(this,"Ingrese su division",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        final Bundle bundle = getIntent().getExtras();
        final String dni = bundle.getString("dni");

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ObjetoUsuario chschool = dataSnapshot.child(dni).getValue(ObjetoUsuario.class);
                String school1 = school.replace("°","");
                chschool.setSchool(school1);
                users.child(dni).setValue(chschool);
                divisiones.child(school).child(dni).setValue(bundle.getString("user"));
                Intent intent =
                        new Intent(ConfColegio.this,NavDrawer.class);
                progressDialog.dismiss();
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        progressDialog.dismiss();
    }
}
