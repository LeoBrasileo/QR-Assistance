package com.example.android.pruebas3;
//hola kevin

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Change_pass extends AppCompatActivity
{
    private Toolbar toolbar;
    private Button validar;
    private EditText oldpass;
    private EditText newpass1;
    private EditText newpass2;
    private FirebaseDatabase database;
    private DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("usuarios");

        toolbar = findViewById(R.id.toolbar2);
        oldpass = findViewById(R.id.editPass4);
        newpass1 = findViewById(R.id.editPass5);
        newpass2 = findViewById(R.id.editPass6);
        validar = findViewById(R.id.validar4);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cambiar contraseña");

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassChange(oldpass.getText().toString(),newpass1.getText().toString(),newpass2.getText().toString());
            }
        });
    }

    private void PassChange (final String OldPass, final String NewPass, String NewPass2)
    {
        if (TextUtils.isEmpty(OldPass))
        {
            Toast.makeText(this,"Falta ingresar su contraseña actual",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(NewPass))
        {
            Toast.makeText(this,"Se debe ingresar una contraseña",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(NewPass2))
        {
            Toast.makeText(this,"Porfavor repita la contraseña",Toast.LENGTH_LONG).show();
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cambiando de contraseña");
        progressDialog.show();
        Bundle bundle1 = getIntent().getExtras();
        final String dni = bundle1.getString("dni");
        if (NewPass.equals(NewPass2))
        {
            users.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    ObjetoUsuario chPass = dataSnapshot.child(dni).getValue(ObjetoUsuario.class);
                    if (chPass.getPassword().equals(OldPass))
                    {
                        ObjetoUsuario usuario = new ObjetoUsuario();
                        chPass.setPassword(NewPass);
                        users.child(dni).setValue(chPass);
                        Intent intent =
                                new Intent(Change_pass.this,NavDrawer.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("user", chPass.getNombre().toString());
                        bundle.putString("mail", chPass.getEmail().toString());
                        bundle.putString("dni", chPass.getDni().toString());
                        intent.putExtras(bundle);
                        SharedPreferences sharedPreferences = getSharedPreferences
                                ("credenciales", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("pass", chPass.getPassword());
                        editor.commit();
                        Toast.makeText(Change_pass.this,"Contraseña cambiada correctamente",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        startActivity(intent);
                    }else
                    {
                        Toast.makeText(Change_pass.this,"Contraseña incorrecta",Toast.LENGTH_LONG).show();
                        oldpass.setText("");
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    Toast.makeText(Change_pass.this, "Error", Toast.LENGTH_LONG).show();
                }
            });
        }else
        {
            Toast.makeText(Change_pass.this,"Las contraseñas no coinciden",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }
}
