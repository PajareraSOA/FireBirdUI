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

public class HistoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        ListView listview = (ListView) rootView.findViewById(R.id.history_listview);
        listview.setAdapter(new HistoryRowAdapter(this.getContext(), new String[] { "34.3º",
                "31.7º" }));
        return rootView;
    }
}
