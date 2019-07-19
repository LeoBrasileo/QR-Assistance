package com.example.android.pruebas3;

import android.content.Context;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Fragment1 extends Fragment implements ZXingScannerView.ResultHandler
{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseDatabase database;
    private DatabaseReference qrs;
    private DatabaseReference hora;
    private DatabaseReference día;

    private String mParam1;
    private String mParam2;
    private static final String LOGTAG = "LogsAndroid";

    private OnFragmentInteractionListener mListener;
    private ZXingScannerView zXingScannerView;

    @Override
    public void onResume()
    {
        super.onResume();
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    public Fragment1() {}

    public static Fragment1 newInstance(String param1, String param2)
    {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        zXingScannerView = new ZXingScannerView(getActivity());
        return zXingScannerView;
    }

    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        } else
            {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void handleResult(final Result result) {
        //Toast.makeText(getActivity(), "Contents = " + result.getText() +
         //       ", Format = " + result.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();

        database = FirebaseDatabase.getInstance();
        qrs = database.getReference("qrs");
        hora = database.getReference("hora");
        día = database.getReference("día");

        qrs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                ObjetoQR qr = dataSnapshot.getValue(ObjetoQR.class);

                if (qr.getNumeros().equals(result.toString()))
                {
                    Toast.makeText(getContext(),"ESTO FUNCIONA (Estas presente)",Toast.LENGTH_LONG).show();
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    String hora1 = format.format(calendar.getTime());
                    String[] dias = new String[] { "sabado", "domingo", "lunes", "martes", "miercoles", "jueves", "viernes" };
                    String day = dias[calendar.get(Calendar.DAY_OF_WEEK)];

                    hora.setValue(hora1);
                    día.setValue(day);

                }else
                {
                    Toast.makeText(getContext(),"Error en el escaneo, vuelva a intentar",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });


        //Toda la parte tecnica de la app se va a programar aca




        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

                zXingScannerView.resumeCameraPreview(Fragment1.this);
            }
        }, 2000);
    }
    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(Uri uri);
    }
}