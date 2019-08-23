package com.example.android.pruebas3;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class ConfNotificaciones extends AppCompatActivity {

    Switch switchButton;
    public boolean Notificaciones = false;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);

        switchButton = (Switch) findViewById(R.id.switchButton);

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Configurar notificaciones");


        switchButton.setChecked(true);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (switchButton.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Notificaciones prendidas",Toast.LENGTH_SHORT).show();
                    Notificaciones = true;
                    switchButton.setChecked(true);
                }

                else {
                    Toast.makeText(getApplicationContext(), "Notificaciones apagadas",Toast.LENGTH_SHORT).show();
                    Notificaciones = false;
                    switchButton.setChecked(false);
                }

            }
        });



    }

}