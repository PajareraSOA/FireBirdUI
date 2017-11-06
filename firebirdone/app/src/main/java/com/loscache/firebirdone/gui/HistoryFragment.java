package com.loscache.firebirdone.gui;

/**
 * Created by cdsac on 22/10/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loscache.firebirdone.R;
import com.loscache.firebirdone.background.HistorySaver;
import com.loscache.firebirdone.data.DataReaderDbContext;
import com.loscache.firebirdone.data.MeasurementModel;

import java.util.ArrayList;
import java.util.Calendar;

public class HistoryFragment extends Fragment {

    private ListView listview;

    // Db Context
    public DataReaderDbContext dbContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        listview = (ListView) rootView.findViewById(R.id.history_listview);

/*        // Get db context
        Bundle arguments = getArguments();

        if(arguments != null && arguments.size() > 0){
            dbContext = (DataReaderDbContext) getArguments().getSerializable("dbcontext");
           *//* String pepe = getArguments().getString("dbcontext");
            Log.i("CICLE",pepe);*//*
        }*/

        ArrayList<MeasurementModel> historyRowObjects = new ArrayList<>();

        if(dbContext != null){
           historyRowObjects = dbContext.getAll();
        }



   /*     historyRowObjects.add(new MeasurementModel(Calendar.getInstance().getTime().toLocaleString(), "35.5º", "No detectado", "No detectado", "Alto", "Medio"));
        historyRowObjects.add(new MeasurementModel(Calendar.getInstance().getTime().toLocaleString(), "39.2º", "No detectado", "Detectado", "Alto", "Medio"));
        historyRowObjects.add(new MeasurementModel(Calendar.getInstance().getTime().toLocaleString(), "46.8º", "Detectado", "Detectado", "Alto", "Bajo"));*/

        listview.setAdapter(
                new HistoryRowAdapter(this.getContext(),
                        historyRowObjects));

        HistorySaver hs = new HistorySaver();
        hs.historyRowAdapter = (HistoryRowAdapter) listview.getAdapter();
        hs.dbContext = dbContext;
        hs.execute();

        // How to add items to the list view
        //((HistoryRowAdapter) listview.getAdapter()).addItem(new HistoryRowObject(Calendar.getInstance().getTime(), "50.8º", "Detectado", "Detectado", "Alto", "Bajo") );

        return rootView;
    }

}
