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
        ((TextView) vi.findViewById(R.id.txt_time)).setText(data.get(position).getTime());
        ((TextView) vi.findViewById(R.id.txt_temperature_value)).setText(data.get(position).getTemperature());
        ((TextView) vi.findViewById(R.id.txt_smoke_value)).setText(data.get(position).getSmoke());
        ((TextView) vi.findViewById(R.id.txt_flame_value)).setText(data.get(position).getFlame());
        ((TextView) vi.findViewById(R.id.txt_food_value)).setText(data.get(position).getFood());
        ((TextView) vi.findViewById(R.id.txt_water_value)).setText(data.get(position).getWater());
        return vi;
    }

    public void addItem(HistoryRowObject item){
        data.add(item);
    }
}