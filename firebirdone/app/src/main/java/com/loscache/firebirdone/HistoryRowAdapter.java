package com.loscache.firebirdone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cdsac on 23/10/2017.
 */

public class HistoryRowAdapter  extends BaseAdapter {

    Context context;
    ArrayList<HistoryRowObject> data;
    private static LayoutInflater inflater = null;

    public HistoryRowAdapter(Context context, ArrayList<HistoryRowObject> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.history_row, null);
        HistoryRowObject hrObject = data.get(position);
        ((TextView) vi.findViewById(R.id.txt_time)).setText(hrObject.getTime());
        ((TextView) vi.findViewById(R.id.txt_temperature_value)).setText(hrObject.getTemperature());
        ((TextView) vi.findViewById(R.id.txt_smoke_value)).setText(hrObject.getSmoke());
        ((TextView) vi.findViewById(R.id.txt_flame_value)).setText(hrObject.getFlame());
        ((TextView) vi.findViewById(R.id.txt_food_value)).setText(hrObject.getFood());
        ((TextView) vi.findViewById(R.id.txt_water_value)).setText(hrObject.getWater());
        return vi;
    }

    public void addItem(HistoryRowObject item){
        data.add(item);
    }
}