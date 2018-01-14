package com.apps.diego.autoconfig;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by DiegoAndres on 01/08/2014.
 */
public class SmsReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    SendSMS sms = new SendSMS();
    Archivo ar= new Archivo();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                // get sms objects
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus.length == 0) {
                    return;
                }
                // large message might be broken into many
                SmsMessage[] messages = new SmsMessage[pdus.length];
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    sb.append(messages[i].getMessageBody());
                }
                String sender = messages[0].getOriginatingAddress();
                String message = sb.toString();
                if(message.trim().toString().equalsIgnoreCase("location") && ar.recuperar("AutoConfigUser").split("-")[2].equals("1")){
                    sms.SendSMS(sender,ar.recuperar("sms"));
                }
                // prevent any other broadcast receivers from receiving broadcast
                // abortBroadcast();
            }
        }
    }

}
