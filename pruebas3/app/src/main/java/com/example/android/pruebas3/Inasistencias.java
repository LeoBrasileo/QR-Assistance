package com.example.android.pruebas3;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.example.android.pruebas3.R.layout.item_inasistencias;

//por cada mas faltas el color en el textView se vuelve cada vez mas rojo
//a las 10 faltas se hace visible un Warning

public class Inasistencias extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    private FirebaseDatabase database;
    private DatabaseReference todo;
    private DatabaseReference faltas;
    private ListView listView;

    public Inasistencias() {
        // Required empty public constructor
    }

    public static Inasistencias newInstance(String param1, String param2) {
        Inasistencias fragment = new Inasistencias();
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
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inasistencias, container, false);

        listView = view.findViewById(R.id.listInasistencias);

        final Bundle bundle = getActivity().getIntent().getExtras();
        String div = bundle.getString("div");
        String dni = bundle.getString("dni");

        database = FirebaseDatabase.getInstance();
        todo = database.getReference();
        faltas = database.getReference("faltas").child(div).child(dni);

        faltas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map <String,Long> map = (Map<String,Long>) dataSnapshot.getValue();
                ArrayList<ObjetoFaltas> list = new ArrayList<ObjetoFaltas>();
                for (int i = 0;i<map.size();i++)
                {
                    ArrayList<String> nombresMaterias = new ArrayList<String>(map.keySet());
                    ArrayList<Long> faltasMaterias = new ArrayList<Long>(map.values());
                    ObjetoFaltas faltas = new ObjetoFaltas((nombresMaterias.get(i).toString()),faltasMaterias.get(i));
                    list.add(faltas);
                    AdapterInasistencias adapter = new AdapterInasistencias(getActivity(),list);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
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

    class AdapterInasistencias extends ArrayAdapter<ObjetoFaltas>
    {

        private Activity context;
        private ArrayList<ObjetoFaltas> listFaltas;

        class ViewHolder
        {
            TextView txtNombreMateria;
            TextView txtFaltasmateria;
        }

        AdapterInasistencias(Activity context,ArrayList<ObjetoFaltas> listFaltas)
        {
            super(context, R.layout.item_subhorarios, listFaltas);
            this.context = context;
            this.listFaltas = listFaltas;
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View item = convertView;
            NavDrawer.AdapterInasistencias.ViewHolder holder;

            if(item == null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
                item = inflater.inflate(item_inasistencias, null);

                holder = new NavDrawer.AdapterInasistencias.ViewHolder();
                holder.txtNombreMateria = item.findViewById(R.id.txtMateria);
                holder.txtFaltasmateria = item.findViewById(R.id.txtFaltas);
                item.setTag(holder);
            }
            else
            {
                holder = (NavDrawer.AdapterInasistencias.ViewHolder)item.getTag();
            }

            Long faltas = listFaltas.get(position).getNumeroFaltas();
            Double faltasReales = faltas.doubleValue();
            faltasReales = faltasReales / 2;

            holder.txtFaltasmateria.setText(faltasReales.toString());
            holder.txtNombreMateria.setText(listFaltas.get(position).getNombreMateria());
            return(item);
        }
    }
}
