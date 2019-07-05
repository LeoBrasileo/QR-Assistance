package com.example.android.pruebas3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;


public class Change_pass extends AppCompatActivity
{
    private Toolbar toolbar;
    private Button validar;
    private EditText oldpass;
    private EditText newpass1;
    private EditText newpass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        toolbar = findViewById(R.id.toolbar2);
        oldpass = findViewById(R.id.editPass4);
        newpass1 = findViewById(R.id.editPass5);
        newpass2 = findViewById(R.id.editPass6);
        validar = findViewById(R.id.validar4);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cambiar contraseña");
    }
}
