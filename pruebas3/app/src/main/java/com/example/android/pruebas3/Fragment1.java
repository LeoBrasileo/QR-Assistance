package com.example.android.pruebas3;

import android.content.Context;
import android.content.Intent;
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
    private DatabaseReference division;
    private DatabaseReference diaactual;
    private DatabaseReference materiaact1;
    private DatabaseReference fecha;
    private String horariosmaterias = "";

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
        final Bundle bundle = getActivity().getIntent().getExtras();
        final String div = bundle.getString("div");

        database = FirebaseDatabase.getInstance();
        qrs = database.getReference("qrs");
        hora = database.getReference("hora");
        día = database.getReference("día");
        division = database.getReference(div);
        fecha = database.getReference("fecha");
        qrs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                ObjetoQR qr = dataSnapshot.getValue(ObjetoQR.class);

                if (qr.getNumeros().equals(result.toString()))
                {
                    //Toast.makeText(getContext(),"ESTO FUNCIONA (Estas presente)",Toast.LENGTH_LONG).show();

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat formathora = new SimpleDateFormat("HH");
                    SimpleDateFormat formatminutos = new SimpleDateFormat("mm");
                    SimpleDateFormat formatsec = new SimpleDateFormat("ss");
                    String hora1 = formathora.format(calendar.getTime());
                    String min1 = formatminutos.format(calendar.getTime());
                    String sec1 = formatsec.format(calendar.getTime());
                    String horatotal = hora1 + ":" + min1 + ":" + sec1;
                    String[] dias = new String[] { "sabado", "domingo", "lunes", "martes", "miercoles", "jueves", "viernes" };
                    final String day = dias[calendar.get(Calendar.DAY_OF_WEEK)];
                    int ifecha = calendar.get(Calendar.DAY_OF_MONTH);
                    fecha.setValue(ifecha);

                    String horamin1 = hora1 + min1;
                    int horamin = Integer.valueOf(horamin1);

                    hora.setValue(horatotal);
                    día.setValue(day);

                    if (horamin > 0745 && horamin < 905)
                    {
                        horariosmaterias = "1";
                    }else if (horamin > 905 && horamin < 1040)
                    {
                        horariosmaterias = "2";
                    }else if (horamin > 1040 && horamin < 1215)
                    {
                        horariosmaterias = "3";
                    }else if (horamin > 1310 && horamin < 1430)
                    {
                        horariosmaterias = "4";
                    }else if (horamin > 1430 && horamin < 1600)
                    {
                        horariosmaterias = "5";
                    }else if (horamin > 1600 && horamin < 1730)
                    {
                        horariosmaterias = "6";
                    }else if (horamin > 1730 && horamin < 2359)
                    {
                        horariosmaterias = "prueba";
                    }


                    //descargo los datos de div (5MA) y despues descargo el child del día actual, y segun el horario agreg al usuario loguado a la materia que se este dando en ese horario

                    division.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            diaactual = division.child(day);
                            diaactual.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    //if (dataSnapshot.child(horariosmaterias).exists())
                                    //{
                                        materiaact1 = diaactual.child(horariosmaterias);
                                        materiaact1.addListenerForSingleValueEvent(new ValueEventListener()
                                        {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                            {
                                                ObjetoPresensia presentes = dataSnapshot.getValue(ObjetoPresensia.class);
                                                //ObjetoPresensia presensia = new ObjetoPresensia(bundle.getString("user"));
                                                presentes.setAlumno(bundle.getString("user").toString());
                                                materiaact1.setValue(presentes);
                                                Toast.makeText(getContext(),"Presencia tomada",Toast.LENGTH_LONG).show();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) { }
                                        });
                                    //}
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) { }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });

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