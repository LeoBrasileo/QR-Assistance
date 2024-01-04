package com.example.android.pruebas3;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class User_config extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public ListView listView;
    public AdapterConfigs adapterConfigs;

    private OnFragmentInteractionListener mListener;
    private FirebaseDatabase database;
    private DatabaseReference users;
    private DatabaseReference divisiones;
    private DatabaseReference faltas;

    public User_config() {
        // Required empty public constructor
    }

    public static User_config newInstance(String param1, String param2) {
        User_config fragment = new User_config();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState)
    {
        final View v = inflater.inflate(R.layout.fragment_user_config, container, false);
        listView = (ListView) v.findViewById(R.id.mainMenu);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("usuarios");
        divisiones = database.getReference("divisiones");
        faltas = database.getReference("faltas");
        final Bundle bundle = getActivity().getIntent().getExtras();
        final String dni = bundle.getString("dni");
        final String div = bundle.getString("div");

        final ArrayList<Configs_strings> configs_strings = new ArrayList<Configs_strings>();
        configs_strings.add(new Configs_strings(R.drawable.addmail,"Registrar Email",Color.TRANSPARENT));
        configs_strings.add((new Configs_strings(R.drawable.mailc,"Cambiar Email",Color.GRAY)));
        //configs_strings.add(new Configs_strings(R.drawable.userpic,"Foto de perfil (tal vez algun día lo programe)"));
        configs_strings.add(new Configs_strings(R.drawable.llave,"Cambiar contraseña",Color.TRANSPARENT));
        configs_strings.add(new Configs_strings(R.drawable.school,"Division",Color.GRAY));
        configs_strings.add(new Configs_strings(R.drawable.notificacion,"Notificaciones",Color.TRANSPARENT));
        configs_strings.add(new Configs_strings(R.drawable.tachoide, "Eliminar usuario",Color.GRAY));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0)
                {
                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ObjetoUsuario addmail = dataSnapshot.child(dni).getValue(ObjetoUsuario.class);
                            if (addmail.getEmail().equals(""))
                            {
                                Intent intent =
                                        new Intent(getActivity(),AddMail.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }else
                            {
                                Snackbar.make(v,"Ya se encuentra registrado un mail en esta cuenta", Snackbar.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                if (position == 1)
                {
                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ObjetoUsuario mail = dataSnapshot.child(dni).getValue(ObjetoUsuario.class);
                            if (mail.getEmail().equals(""))
                            {
                                Toast.makeText(getContext(),"No se encuentra ningun Email registrado en este usuario",Toast.LENGTH_LONG).show();
                            }else
                            {
                                Intent intent =
                                        new Intent(getActivity(),ChangeMail.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                if (position == 2)
                {
                    Intent intent =
                            new Intent(getActivity(),Change_pass.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("dni",dni);
                    intent.putExtras(bundle1);
                    startActivity(intent);
                }

                if (position == 3)
                {
                    Intent intent =
                            new Intent(getActivity(),ConfColegio.class);
                    bundle.putString("dni",dni);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                if (position == 4)
                {
                    Intent intent =
                            new Intent(getActivity(),ConfNotificaciones.class);
                    startActivity(intent);
                }

                if (position == 5)
                {
                    AlertDialog.Builder eliminar = new AlertDialog.Builder(getContext());
                    View view2 = getLayoutInflater().inflate(R.layout.alert_dialog_borrarusuario, null);
                    eliminar.setView(view2);
                    final EditText editPw = view2.findViewById(R.id.editPSW1);
                    eliminar.setMessage("Esta accion es permanente, perdera todos sus datos.")
                            .setCancelable(false)
                            .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //me conecto con la database y elimino rama usuario, de ahi a la activity .inicio
                                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            ObjetoUsuario userExist = dataSnapshot.child(dni).getValue(ObjetoUsuario.class);
                                            if (editPw.getText().toString().equals(userExist.getPassword().toString()))
                                            {
                                                if (userExist.getDni().equals(dni))
                                                {
                                                    users.child(String.valueOf(dni)).removeValue();
                                                    divisiones.child(div).child(dni).removeValue();
                                                    //faltas.child(div).child(dni).removeValue();
                                                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString("user", "");
                                                    editor.putString("pass", "");
                                                    editor.commit();
                                                    Intent intent =
                                                            new Intent(getActivity(),login.class);
                                                    startActivity(intent);
                                                }
                                            }else
                                            {
                                                Toast.makeText(getContext(),"Se debe escribir la contraseña correcta",Toast.LENGTH_LONG).show();
                                                editPw.setText("");
                                                return;
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog titulo = eliminar.create();
                    titulo.setTitle("¿Esta seguro que desea eliminar su usuario?");
                    titulo.show();
                }
            }
        });

        adapterConfigs = new AdapterConfigs((Activity) getContext(),configs_strings);
        listView.setAdapter(adapterConfigs);
        return  v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    class AdapterConfigs extends ArrayAdapter<Configs_strings>
    {

        private Activity context;
        private ArrayList<Configs_strings> listConfigs;

        class ViewHolder
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
            NavDrawer.AdapterConfigs.ViewHolder holder;

            if(item == null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
                item = inflater.inflate(R.layout.item_producto, null);

                holder = new NavDrawer.AdapterConfigs.ViewHolder();
                holder.txtConfig = item.findViewById(R.id.txtConf);
                holder.imageView = item.findViewById(R.id.imageView);
                item.setTag(holder);
            }
            else
            {
                holder = (NavDrawer.AdapterConfigs.ViewHolder)item.getTag();
            }

            item.setBackgroundColor(listConfigs.get(position).getColor());

            holder.txtConfig.setText(listConfigs.get(position).getConfiguracion());
            holder.imageView.setImageResource((listConfigs.get(position).getImagen()));
            return(item);
        }
    }
}
