package com.apps.diego.autoconfig;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by DiegoAndres on 29/07/2014.
 */
public class IniciadorServicio extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent servicio = new Intent();
        servicio.setAction("GPSTracker");
        context.startService(servicio);
    }
}