package com.loscache.firebirdone.gui;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.loscache.firebirdone.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Request Codes
    private static int REQUEST_ENABLE_BT = 1000;

    // Progress Dialog
    private ProgressDialog progressDialog;

    // Bluetooth
    private BluetoothAdapter mBluetoothAdapter;
    private ArrayList<BluetoothDevice> bluetoothDevices = new ArrayList<BluetoothDevice>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crear el progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Buscando Dispositivos.");

        // Obtener el adpatador de bluetooth
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }

        // Botón Conectarse
        Button btnConecction = (Button) findViewById(R.id.btn_connection);
        btnConecction.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*Intent i = new Intent(MainActivity.this, InfoActivity.class);
                        startActivity(i);*/
                        if (!mBluetoothAdapter.isEnabled()) {
                            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                        }
                    }
                }
        );

        // Register los broadcast que escucharemos
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
    }

    // Creamos el BroadcastReceiver para el Bluetooth.
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();


            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                bluetoothDevices.add((BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
            } else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                bluetoothDevices.clear();
                progressDialog.show();
            } else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                progressDialog.dismiss();
                Intent i = new Intent(MainActivity.this, DevicesActivity.class);
                i.putExtra("Devices", bluetoothDevices);
                ArrayList<BluetoothDevice> bluetoothPairedDevices = new ArrayList<BluetoothDevice>(mBluetoothAdapter.getBondedDevices());
                i.putExtra("PairedDevices", bluetoothPairedDevices);
                startActivity(i);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        if(mBluetoothAdapter != null) {
            mBluetoothAdapter.disable();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK){
            mBluetoothAdapter.startDiscovery();
        }
    }
}
