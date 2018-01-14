package com.apps.diego.autoconfig;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by DiegoAndres on 31/07/2014.
 */
public class Bateria {

    BatteryManager bm;
    Intent battery;
    public Bateria(Intent b){
        battery=b;
    }

    public int cargaBateria ()
    {
        try
        {
            int nivelBateria = battery.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            return nivelBateria;
        }
        catch (Exception e)
        {
            return 0;
        }
    }
}
