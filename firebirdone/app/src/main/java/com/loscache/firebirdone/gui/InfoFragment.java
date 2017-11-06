package com.loscache.firebirdone.gui;

/**
 * Created by cdsac on 22/10/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loscache.firebirdone.R;
import com.loscache.firebirdone.data.DataReaderDbContext;

public class InfoFragment extends Fragment {

    // Db Context
    private DataReaderDbContext dbContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);
        return rootView;
    }

}
