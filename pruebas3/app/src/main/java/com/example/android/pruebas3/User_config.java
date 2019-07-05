package com.example.android.pruebas3;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
        View v = inflater.inflate(R.layout.fragment_user_config, container, false);
        listView = (ListView) v.findViewById(R.id.mainMenu);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("usuarios");
        Bundle bundle = getActivity().getIntent().getExtras();
        final String dni = bundle.getString("dni");

        final ArrayList<Configs_strings> configs_strings = new ArrayList<Configs_strings>();
        configs_strings.add(new Configs_strings(R.drawable.addmail,"Registrar email"));
        configs_strings.add((new Configs_strings(R.drawable.mailc,"Cambiar Email")));
        configs_strings.add(new Configs_strings(R.drawable.userpic,"Foto de perfil (tal vez algun día lo programe)"));
        configs_strings.add(new Configs_strings(R.drawable.llave,"Cambiar contraseña"));
        configs_strings.add(new Configs_strings(R.drawable.school,"Colegio"));
        configs_strings.add(new Configs_strings(R.drawable.notificacion,"Notificaciones"));
        configs_strings.add(new Configs_strings(R.drawable.tachoide, "Eliminar usuario"));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 3)
                {
                    Intent intent =
                            new Intent(getActivity(),Change_pass.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("dni",dni);
                    intent.putExtras(bundle1);
                    startActivity(intent);
                }

                if (position == 6)
                {
                    AlertDialog.Builder eliminar = new AlertDialog.Builder(getContext());
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
                                            if (userExist.getDni().equals(dni))
                                            {
                                                users.child(String.valueOf(dni)).removeValue();
                                                SharedPreferences sharedPreferences = getContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("user", "");
                                                editor.putString("pass", "");
                                                editor.commit();
                                                Intent intent =
                                                        new Intent(getActivity(),login.class);
                                                startActivity(intent);
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

        /*ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menuItems
        );*/

        // Inflate the layout for this fragment
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

            holder.txtConfig.setText(listConfigs.get(position).getConfiguracion());
            holder.imageView.setImageResource((listConfigs.get(position).getImagen()));
            return(item);
        }
    }
}
