package com.example.android.pruebas3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

public class ConfNotificaciones extends AppCompatActivity {
private Switch Sw;

String str1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Switch sb = new Switch(this);
        Sw = findViewById(R.id. switch1);
        sb.setTextOff("OFF");
        sb.setTextOn("ON");
        sb.setChecked(true);



        if (Sw. isChecked()){
            str1 = Sw.getTextOn ().toString();
            Toast.makeText(getApplicationContext(), "Notificaciones prendidas",Toast.LENGTH_SHORT).show();
        }
        else {
            str1 = Sw.getTextOff().toString();
            Toast.makeText(getApplicationContext(), "Notificaciones apagadas",Toast.LENGTH_SHORT).show();
        }



    }
}