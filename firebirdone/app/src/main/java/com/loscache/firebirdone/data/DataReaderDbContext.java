package com.loscache.firebirdone.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cdsac on 05/11/2017.
 */

public class DataReaderDbContext implements Serializable {

    // Object Db Helper
    private DataReaderDbHelper dbHelper = null;

    // Dependency Injection
    public DataReaderDbContext(DataReaderDbHelper db){
        this.dbHelper = db;
    }

    // Insert new row
    public long insert(MeasurementModel measurementModel){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DataReaderContract.DataMeasurements.COLUMN_NAME_TEMPERATURE, measurementModel.getTemperature());
        values.put(DataReaderContract.DataMeasurements.COLUMN_NAME_FLAME, measurementModel.getFlame());
        values.put(DataReaderContract.DataMeasurements.COLUMN_NAME_SMOKE, measurementModel.getSmoke());
        values.put(DataReaderContract.DataMeasurements.COLUMN_NAME_FOOD, measurementModel.getFood());
        values.put(DataReaderContract.DataMeasurements.COLUMN_NAME_WATER, measurementModel.getWater());
        values.put(DataReaderContract.DataMeasurements.COLUMN_NAME_DATE, measurementModel.getDate());

        // Insert the new row, returning the primary key value of the new row
        return db.insert(DataReaderContract.DataMeasurements.TABLE_NAME, null, values);
    }

    // Get all rows
    public ArrayList<MeasurementModel> getAll(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DataReaderContract.DataMeasurements._ID,
                DataReaderContract.DataMeasurements.COLUMN_NAME_TEMPERATURE,
                DataReaderContract.DataMeasurements.COLUMN_NAME_FLAME,
                DataReaderContract.DataMeasurements.COLUMN_NAME_SMOKE,
                DataReaderContract.DataMeasurements.COLUMN_NAME_FOOD,
                DataReaderContract.DataMeasurements.COLUMN_NAME_WATER,
                DataReaderContract.DataMeasurements.COLUMN_NAME_DATE
        };

        // Filter results WHERE "title" = 'My Title'
        //String selection = DataReaderContract.DataMeasurements.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DataReaderContract.DataMeasurements._ID + " DESC";

        Cursor c = db.query(
                DataReaderContract.DataMeasurements.TABLE_NAME,     // The table to query
                projection,                                         // The columns to return
                null,                                               // The columns for the WHERE clause
                null,                                               // The values for the WHERE clause
                null,                                               // don't group the rows
                null,                                               // don't filter by row groups
                sortOrder                                           // The sort order
        );
        // Save the result into an array
        ArrayList<MeasurementModel> mArray = new ArrayList<>();
        // Need to be sure that there is at least one value
        if (c.moveToFirst()) {
            // Move through the table until there are no more values
            do {
                MeasurementModel m = new MeasurementModel(
                    c.getString(c.getColumnIndexOrThrow(DataReaderContract.DataMeasurements.COLUMN_NAME_DATE)),
                    c.getString(c.getColumnIndexOrThrow(DataReaderContract.DataMeasurements.COLUMN_NAME_TEMPERATURE)),
                    c.getString(c.getColumnIndexOrThrow(DataReaderContract.DataMeasurements.COLUMN_NAME_SMOKE)),
                    c.getString(c.getColumnIndexOrThrow(DataReaderContract.DataMeasurements.COLUMN_NAME_FLAME)),
                    c.getString(c.getColumnIndexOrThrow(DataReaderContract.DataMeasurements.COLUMN_NAME_FOOD)),
                    c.getString(c.getColumnIndexOrThrow(DataReaderContract.DataMeasurements.COLUMN_NAME_WATER))
                );
                mArray.add(m);
            } while(c.moveToNext());
        }
        c.close();
        // Return the array
        return mArray;
    }

    // Delete row
    public void deleteLast(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Define 'where' part of query.
        String selection = DataReaderContract.DataMeasurements._ID +  "=" +
                "(SELECT MIN("+DataReaderContract.DataMeasurements._ID+") FROM " + DataReaderContract.DataMeasurements.TABLE_NAME + ")";;
/*        // Specify arguments in placeholder order.
        String[] selectionArgs = { "MyTitle" };*/
        // Issue SQL statement.
        db.delete(DataReaderContract.DataMeasurements.TABLE_NAME, selection, null);
    }

    // Count
    public int count(){
        return getAll().size();
    }

    // Close db
    public void close(){
        dbHelper.close();
    }
}
