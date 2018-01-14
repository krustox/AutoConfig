package com.apps.diego.autoconfig;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by DiegoAndres on 30/07/2014.
 */
public class SendSMS {

    public void SendSMS(String phone,String sms){
        SmsManager msj= SmsManager.getDefault();
        msj.sendTextMessage(phone,null,sms,null,null);
    }

}
