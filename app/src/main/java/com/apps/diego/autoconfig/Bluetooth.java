package com.apps.diego.autoconfig;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by DiegoAndres on 30/07/2014.
 */
public class Bluetooth {

    BluetoothAdapter Ba;

    public Bluetooth(BluetoothAdapter blue){
        Ba=blue;
    }

    public boolean ChangeBluetooth(int bt){
        if(bt==0){
            if(Ba.isEnabled()) {
                Ba.disable();
                return false;
            }
        }
        if(bt==1){
            if(!Ba.isEnabled()) {
                Ba.enable();
                return true;
            }
        }
        return false;
    }

}
