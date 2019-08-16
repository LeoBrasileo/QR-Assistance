package com.example.android.pruebas3;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PublicKey;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;

public class NavDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Fragment1.OnFragmentInteractionListener,User_config.OnFragmentInteractionListener,Horarios.OnFragmentInteractionListener,Inasistencias.OnFragmentInteractionListener {

    public TextView usertext;
    public TextView mailtext;
    public String User;
    public String mail;
    public Layout cabeza;
    private final int REQUEST_CAMERA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View navHeader = navigationView.getHeaderView(0);
        Bundle bundle = this.getIntent().getExtras();
        usertext = navHeader.findViewById(R.id.txtusername);
        mailtext = navHeader.findViewById(R.id.textMail);
        mail = bundle.getString("mail");
        User = bundle.getString("user");
        usertext.setText(User.toString());
        mailtext.setText(mail.toString());
        if (TextUtils.isEmpty(mail))
        {
            mailtext.setText("Asocie un mail a su cuenta");
        }
        Fragment fragment = null;
        fragment = new Fragment1();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }

        if (!drawer.isDrawerOpen(GravityCompat.START))
        {
            AlertDialog.Builder salir = new AlertDialog.Builder(NavDrawer.this);
            salir.setMessage("¿Desea salir de QR Assistance?")
                    .setCancelable(false)
                    .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveTaskToBack(true);
                            finish();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog titulo = salir.create();
            titulo.setTitle("Salir");
            titulo.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        boolean fragmentseleccionado = false;

        if (id == R.id.menu_seccion_1) {
            // Handle the camera action
            fragment = new Fragment1();
            fragmentseleccionado = true;
        } else if (id == R.id.nav_horarios) {
            fragment = new Horarios();
            fragmentseleccionado = true;
        } else if (id == R.id.nav_inasistencias) {
            fragment = new Inasistencias();
            fragmentseleccionado = true;
        } else if (id == R.id.nav_tools) {
            fragment = new User_config();
            fragmentseleccionado = true;

        } else if (id == R.id.logout) {
            SharedPreferences sharedPreferences = getSharedPreferences
                    ("credenciales", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user","");
            editor.putString("pass","");
            editor.commit();

            Intent intent =
                    new Intent(NavDrawer.this,login.class);
            startActivity(intent);
        }

        if (fragmentseleccionado==true)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if (requestCode == REQUEST_CAMERA)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permiso concedido",Toast.LENGTH_LONG).show();
            }else
            {
                Toast.makeText(this,"Permiso denegado",Toast.LENGTH_LONG).show();
            }
        }
    }

    static class AdapterConfigs extends ArrayAdapter<Configs_strings>
    {

        private Activity context;
        private ArrayList<Configs_strings> listConfigs;

        static class ViewHolder
        {
            TextView txtConfig;
            ImageView imageView;

        }

        AdapterConfigs(Activity context,ArrayList<Configs_strings> listConfigs)
        {
            super(context, R.layout.item_producto, listConfigs);
            this.context = context;
            this.listConfigs = listConfigs;
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View item = convertView;
            ViewHolder holder;

            if(item == null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
                item = inflater.inflate(R.layout.item_producto, null);

                holder = new ViewHolder();
                holder.txtConfig = item.findViewById(R.id.txtConf);
                holder.imageView = item.findViewById(R.id.imageView);
                item.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)item.getTag();
            }

            holder.txtConfig.setText(listConfigs.get(position).getConfiguracion());
            holder.imageView.setImageResource((listConfigs.get(position).getImagen()));
            return(item);
        }
    }

    static class AdapterHorarios extends ArrayAdapter<ObjetoHorariosMaterias>
    {

        private Activity context;
        private ArrayList<ObjetoHorariosMaterias> listHorarios;

        static class ViewHolder
        {
            TextView txtNumBloque;
            TextView txtHorarioReal;
            TextView txtNombreMateria;

        }

        AdapterHorarios(Activity context,ArrayList<ObjetoHorariosMaterias> listHorarios)
        {
            super(context, R.layout.item_subhorarios, listHorarios);
            this.context = context;
            this.listHorarios = listHorarios;
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View item = convertView;
            NavDrawer.AdapterHorarios.ViewHolder holder;

            if(item == null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
                item = inflater.inflate(R.layout.item_subhorarios, null);

                holder = new NavDrawer.AdapterHorarios.ViewHolder();
                holder.txtNumBloque = item.findViewById(R.id.textViewBloque);
                holder.txtHorarioReal = item.findViewById(R.id.textViewHoraReal);
                holder.txtNombreMateria = item.findViewById(R.id.textViewMateria);
                item.setTag(holder);
            }
            else
            {
                holder = (NavDrawer.AdapterHorarios.ViewHolder)item.getTag();
            }

            holder.txtNombreMateria.setText(listHorarios.get(position).getId());
            return(item);
        }
    }
}
