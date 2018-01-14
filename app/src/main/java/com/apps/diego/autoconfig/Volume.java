package com.apps.diego.autoconfig;

import android.content.Context;
import android.media.AudioManager;
import android.widget.Toast;

/**
 * Created by DiegoAndres on 30/07/2014.
 */
public class Volume {

    AudioManager am;
    public Volume(AudioManager a){
        am =a;
    }

    public int ChangeVolume(int volume){

        if(volume==0){
            if(am.getRingerMode()!= AudioManager.RINGER_MODE_VIBRATE) {
                am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            }
        }
        if(volume==1){
            if(am.getRingerMode()!= AudioManager.RINGER_MODE_NORMAL) {
                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        }
        if(volume==2){
            if(am.getRingerMode()!= AudioManager.RINGER_MODE_SILENT) {
                am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }
        }
        return volume;
    }

}
