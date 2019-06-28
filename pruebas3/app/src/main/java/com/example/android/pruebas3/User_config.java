package com.example.android.pruebas3;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link User_config.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link User_config#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    public User_config() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment User_config.
     */
    // TODO: Rename and change types and number of parameters
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
                             Bundle savedInstanceState) {
        listView = (ListView) User_config.findViewById(R.id.mainMenu);

        //Trabajar por aca. error por aca

        String[] menuItems = {"Cambiar nombre",
                              "Registrar/cambiar email",
                              "Cambiar contraseña"};

        int[] menuIcons = {
                R.drawable.listviewtest
        };

        ArrayList<Configs_strings> configs_strings = new ArrayList<Configs_strings>();
        configs_strings.add(new Configs_strings(R.drawable.listviewtest,"Cambiar contraseña"));

        adapterConfigs = new AdapterConfigs((Activity) getContext(),configs_strings);
        listView.setAdapter(adapterConfigs);

        /*ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menuItems
        );*/

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_config, container, false);
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
