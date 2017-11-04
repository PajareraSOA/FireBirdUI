package com.loscache.firebirdone;

/**
 * Created by cdsac on 22/10/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class GesturesFragment extends Fragment {

    ListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gestures, container, false);

        listview = (ListView) rootView.findViewById(R.id.gestures_listview);

        ArrayList<GestureRowObject> gesturesRowObject = new ArrayList<>();

        gesturesRowObject.add(new GestureRowObject("Verificación Lumínica", "Mover el dipositivo horizontalmente para comprobar el funcionamiento de la alerta lumínica en la pajarera."));
        gesturesRowObject.add(new GestureRowObject("Verificación Sonora", "Tapar el detector de proximidad para comprobar el funcionamiento de la alerta lumínica en la pajarera."));
        gesturesRowObject.add(new GestureRowObject("Verificación de Agua", "Girar el dispositivo para comprobar el funcionamiento de la alerta lumínica en la pajarera."));

        listview.setAdapter(
                new GesturesRowAdapter(this.getContext(),
                        gesturesRowObject));

        return rootView;
    }
}
