package com.loscache.firebirdone.background;

import android.os.AsyncTask;
import android.util.Log;

import com.loscache.firebirdone.data.DataReaderDbContext;
import com.loscache.firebirdone.data.MeasurementModel;
import com.loscache.firebirdone.gui.HistoryRowAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by cdsac on 06/11/2017.
 */

public class HistorySaver extends AsyncTask<Integer, MeasurementModel, Integer> {

    public DataReaderDbContext dbContext;
    public HistoryRowAdapter historyRowAdapter;

    @Override
    protected Integer doInBackground(Integer... integers) {

            Random rnd = new Random();

            try {
                while(true){
                    String temperature = (rnd.nextInt(10) + 30) + "." + rnd.nextInt(10) + "º";
                    String flame = rnd.nextBoolean() ? "Detectado" : "No detectado";
                    String smoke = rnd.nextBoolean() ? "Detectado" : "No detectado";
                    String food = rnd.nextBoolean() ? "Alto" : "Medio";
                    String water = rnd.nextBoolean() ? "Alto" : "Bajo";
                    publishProgress(new MeasurementModel(Calendar.getInstance().getTime().toLocaleString(), temperature, smoke, flame, food, water));
                    Thread.sleep(10000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return 0;
    }

    @Override
    protected void onProgressUpdate(MeasurementModel... values) {

        // Store in db
        if(dbContext.count() >= 3){
            dbContext.deleteLast();
            historyRowAdapter.removeLastItem();
        }
        dbContext.insert(values[0]);

        // Show in list view
       historyRowAdapter.addItem(values[0]);
        historyRowAdapter.notifyDataSetChanged();

    }
}
