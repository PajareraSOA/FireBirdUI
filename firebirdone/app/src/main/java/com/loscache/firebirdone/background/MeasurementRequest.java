package com.loscache.firebirdone.background;

import android.os.AsyncTask;
import android.util.Log;

import com.loscache.firebirdone.data.DataReaderDbContext;
import com.loscache.firebirdone.data.MeasurementModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by ClaudioSaccella on 8/11/2017.
 */

public class MeasurementRequest extends AsyncTask<Integer, MeasurementModel, Integer> {

    private DataReaderDbContext dbContext;

    public MeasurementRequest(DataReaderDbContext dbContext){
        super();
        this.dbContext = dbContext;
    }

    @Override
    protected Integer doInBackground(Integer... integers) {

        Random rnd = new Random();

        try {
            while(!isCancelled()){

                    Log.i("REQUEST", "MeasurementRequest");

                    // Aca debería solicitar los datos al arduino
                    String temperature = (rnd.nextInt(10) + 30) + "." + rnd.nextInt(10) + "º";
                    String flame = rnd.nextBoolean() ? "Detectado" : "No detectado";
                    String smoke = rnd.nextBoolean() ? "Detectado" : "No detectado";
                    String food = rnd.nextBoolean() ? "Alto" : "Medio";
                    String water = rnd.nextBoolean() ? "Alto" : "Bajo";

                    // fin leer ultima medicion
                    publishProgress(
                            new MeasurementModel(
                                    Calendar.getInstance().getTime().toLocaleString(),
                                    temperature,
                                    smoke,
                                    flame,
                                    food,
                                    water,
                                    false));     // is not history
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    protected void onProgressUpdate(MeasurementModel... values) {

        if(isCancelled())
            return;

        // Get the value
        MeasurementModel measurementModel = values[0];

        // Store in db
        dbContext.insert(measurementModel);
        dbContext.clearMeasurements();

    }
}
