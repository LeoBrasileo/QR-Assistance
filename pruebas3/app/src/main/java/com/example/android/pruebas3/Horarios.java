package com.example.android.pruebas3;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Horarios.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Horarios#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Horarios extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private BottomNavigationView bottomNavigationView;
    private TextView bienvDiv;
    private ListView listView;
    private AdapterHorarios adapter;
    private FirebaseDatabase database;
    private DatabaseReference todo;
    private DatabaseReference division;
    private DatabaseReference dia;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Horarios() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Horarios.
     */
    // TODO: Rename and change types and number of parameters
    public static Horarios newInstance(String param1, String param2) {
        Horarios fragment = new Horarios();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {

            final Bundle bundle = getActivity().getIntent().getExtras();
            final String div = bundle.getString("div");
            division = todo.child(div);

            switch (item.getItemId()) {
                case R.id.navigation_lunes:
                    dia = division.child("lunes");
                    dia.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ObjetoHorariosMaterias mat1 = dataSnapshot.child("1").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat2 = dataSnapshot.child("2").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat3 = dataSnapshot.child("3").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat4 = dataSnapshot.child("4").getValue(ObjetoHorariosMaterias.class);
                            //ObjetoHorariosMaterias mat5 = dataSnapshot.child("5").getValue(ObjetoHorariosMaterias.class);
                            //ObjetoHorariosMaterias mat6 = dataSnapshot.child("6").getValue(ObjetoHorariosMaterias.class);

                            ArrayList<ObjetoHorariosMaterias> materias = new ArrayList<ObjetoHorariosMaterias>();
                            adapter = new AdapterHorarios(getActivity(),materias);
                            listView.setAdapter(adapter);

                            materias.clear();
                            materias.add(new ObjetoHorariosMaterias("","                 Lunes", ""));
                            materias.add(mat1);
                            materias.add(mat2);
                            materias.add(mat3);
                            materias.add(mat4);
                            //materias.add(mat5);
                            //materias.add(mat6);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    return true;
                case R.id.navigation_martes:
                    dia = division.child("martes");
                    dia.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ObjetoHorariosMaterias mat1 = dataSnapshot.child("1").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat2 = dataSnapshot.child("2").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat3 = dataSnapshot.child("3").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat4 = dataSnapshot.child("4").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat5 = dataSnapshot.child("5").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat6 = dataSnapshot.child("6").getValue(ObjetoHorariosMaterias.class);

                            ArrayList<ObjetoHorariosMaterias> materias = new ArrayList<ObjetoHorariosMaterias>();
                            adapter = new AdapterHorarios(getActivity(),materias);
                            listView.setAdapter(adapter);

                            materias.clear();
                            materias.add(new ObjetoHorariosMaterias("","                 Martes", ""));
                            materias.add(mat1);
                            materias.add(mat2);
                            materias.add(mat3);
                            materias.add(mat4);
                            materias.add(mat5);
                            materias.add(mat6);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    return true;
                case R.id.navigation_miercoles:
                    dia = division.child("miercoles");
                    dia.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ObjetoHorariosMaterias mat1 = dataSnapshot.child("1").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat2 = dataSnapshot.child("2").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat3 = dataSnapshot.child("3").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat4 = dataSnapshot.child("4").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat5 = dataSnapshot.child("5").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat6 = dataSnapshot.child("6").getValue(ObjetoHorariosMaterias.class);

                            ArrayList<ObjetoHorariosMaterias> materias = new ArrayList<ObjetoHorariosMaterias>();
                            adapter = new AdapterHorarios(getActivity(),materias);
                            listView.setAdapter(adapter);

                            materias.clear();
                            materias.add(new ObjetoHorariosMaterias("","                 Miercoles", ""));
                            materias.add(mat1);
                            materias.add(mat2);
                            materias.add(mat3);
                            materias.add(mat4);
                            materias.add(mat5);
                            materias.add(mat6);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    return true;
                case R.id.navigation_jueves:
                    dia = division.child("jueves");
                    dia.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ObjetoHorariosMaterias mat1 = dataSnapshot.child("1").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat2 = dataSnapshot.child("2").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat3 = dataSnapshot.child("3").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat4 = dataSnapshot.child("4").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat5 = dataSnapshot.child("5").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat6 = dataSnapshot.child("6").getValue(ObjetoHorariosMaterias.class);

                            ArrayList<ObjetoHorariosMaterias> materias = new ArrayList<ObjetoHorariosMaterias>();
                            adapter = new AdapterHorarios(getActivity(),materias);
                            listView.setAdapter(adapter);

                            materias.clear();
                            materias.add(new ObjetoHorariosMaterias("","                 Jueves", ""));
                            materias.add(mat1);
                            materias.add(mat2);
                            materias.add(mat3);
                            materias.add(mat4);
                            materias.add(mat5);
                            materias.add(mat6);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    return true;
                case R.id.navigation_viernes:
                    dia = division.child("viernes");
                    dia.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ObjetoHorariosMaterias mat1 = dataSnapshot.child("1").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat2 = dataSnapshot.child("2").getValue(ObjetoHorariosMaterias.class);
                            ObjetoHorariosMaterias mat3 = dataSnapshot.child("3").getValue(ObjetoHorariosMaterias.class);
                            //ObjetoHorariosMaterias mat4 = dataSnapshot.child("4").getValue(ObjetoHorariosMaterias.class);
                            //ObjetoHorariosMaterias mat5 = dataSnapshot.child("5").getValue(ObjetoHorariosMaterias.class);
                            //ObjetoHorariosMaterias mat6 = dataSnapshot.child("6").getValue(ObjetoHorariosMaterias.class);

                            ArrayList<ObjetoHorariosMaterias> materias = new ArrayList<ObjetoHorariosMaterias>();
                            adapter = new AdapterHorarios(getActivity(),materias);
                            listView.setAdapter(adapter);

                            materias.clear();
                            materias.add(new ObjetoHorariosMaterias("","                 Viernes", ""));
                            materias.add(mat1);
                            materias.add(mat2);
                            materias.add(mat3);
                            //materias.add(mat4);
                            //materias.add(mat5);
                            //materias.add(mat6);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    return true;
            }
            return false;
        }
    };

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
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_horarios, container, false);

        final Bundle bundle = getActivity().getIntent().getExtras();
        final String div = bundle.getString("div");

        database = FirebaseDatabase.getInstance();
        todo = database.getReference();

        bottomNavigationView = v.findViewById(R.id.nav_view);
        listView = v.findViewById(R.id.listHorarios);
        bienvDiv = v.findViewById(R.id.textViewBienvDiv);

        bienvDiv.setText("Lista de horarios de " + div);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Configurar la vista
        return v;
    }

    public void getupdates(DataSnapshot dataSnapshot)
    {

        ArrayList<ObjetoHorariosMaterias> materias = new ArrayList<ObjetoHorariosMaterias>();
        adapter = new AdapterHorarios(getActivity(),materias);
        listView.setAdapter(adapter);

        materias.clear();
        materias.add(new ObjetoHorariosMaterias("Materia:","Horario:", ""));

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            ObjetoHorariosMaterias d = new ObjetoHorariosMaterias();
            d.setHorario(ds.getValue(ObjetoHorariosMaterias.class).getHorario());
            d.setId(ds.getValue(ObjetoHorariosMaterias.class).getId());
            d.setNumero(ds.getValue(ObjetoHorariosMaterias.class).getNumero());
            materias.add(d);
        }
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

    class AdapterHorarios extends ArrayAdapter<ObjetoHorariosMaterias>
    {

        private Activity context;
        private ArrayList<ObjetoHorariosMaterias> listHorarios;

        class ViewHolder
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
            holder.txtHorarioReal.setText(listHorarios.get(position).getHorario());
            holder.txtNumBloque.setText(listHorarios.get(position).getNumero());
            return(item);
        }
    }
}
