package com.apps.diego.autoconfig;

import android.telephony.TelephonyManager;

/**
 * Created by DiegoAndres on 31/07/2014.
 */
public class Phone {

    TelephonyManager mTelephonyManager;

    public Phone(TelephonyManager TM){
        mTelephonyManager=TM;
    }


    public String getPhoneNumber(){
        return mTelephonyManager.getLine1Number();
    }

    public String getDeviceId(){
        return mTelephonyManager.getDeviceId();
    }
}
