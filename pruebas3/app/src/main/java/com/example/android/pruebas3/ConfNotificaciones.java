package com.example.android.pruebas3;
import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class ConfNotificaciones extends AppCompatActivity {

    Switch switchButton;
    public boolean Notificaciones = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_button_example);

        switchButton = (Switch) findViewById(R.id.switchButton);


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