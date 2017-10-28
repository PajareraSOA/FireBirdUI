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

public class HistoryFragment extends Fragment {

    ListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        listview = (ListView) rootView.findViewById(R.id.history_listview);

        ArrayList<HistoryRowObject> historyRowObjects = new ArrayList<>();

        historyRowObjects.add(new HistoryRowObject(Calendar.getInstance().getTime(), "35.5º", "No detectado", "No detectado", "Alto", "Medio"));
        historyRowObjects.add(new HistoryRowObject(Calendar.getInstance().getTime(), "39.2º", "No detectado", "Detectado", "Alto", "Medio"));
        historyRowObjects.add(new HistoryRowObject(Calendar.getInstance().getTime(), "46.8º", "Detectado", "Detectado", "Alto", "Bajo"));

        listview.setAdapter(
                new HistoryRowAdapter(this.getContext(),
                        historyRowObjects));

        // How to add items to the list view
        //((HistoryRowAdapter) listview.getAdapter()).addItem(new HistoryRowObject(Calendar.getInstance().getTime(), "50.8º", "Detectado", "Detectado", "Alto", "Bajo") );

        return rootView;
    }

}
