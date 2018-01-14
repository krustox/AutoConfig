package com.apps.diego.autoconfig;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.widget.Toast;

/**
 * Created by DiegoAndres on 30/07/2014.
 */
public class Wifi {

    WifiManager wm;

    public Wifi(WifiManager w){
        wm=w;
    }

    public boolean ChangeWifiStatus(int wifi){
        if(wifi==0){
            if(wm.isWifiEnabled()) {
                wm.setWifiEnabled(false);
                return false;
            }
        }
        if(wifi==1) {
            if(!wm.isWifiEnabled()) {
                wm.setWifiEnabled(true);
                return true;
            }
        }
        return false;
    }
}
