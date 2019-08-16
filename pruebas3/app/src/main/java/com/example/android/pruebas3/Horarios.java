package com.example.android.pruebas3;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
        View v = inflater.inflate(R.layout.fragment_horarios, container, false);

        final Bundle bundle = getActivity().getIntent().getExtras();
        final String div = bundle.getString("div");

        bottomNavigationView = v.findViewById(R.id.nav_view);
        listView = v.findViewById(R.id.listHorarios);
        bienvDiv = v.findViewById(R.id.textViewBienvDiv);

        bienvDiv.setText("Lista de horarios de " + div);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        // Configurar la vista
        return v;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_lunes:
                    Toast.makeText(getContext(),"casa",Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_martes:
                    Toast.makeText(getContext(),"cuadrados",Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_miercoles:
                    Toast.makeText(getContext(),"campanas",Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_jueves:
                    Toast.makeText(getContext(),"campanas",Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_viernes:
                    Toast.makeText(getContext(),"campanas",Toast.LENGTH_LONG).show();
                    return true;
            }
            return false;
        }
    };

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
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
            return(item);
        }
    }
}
