package com.apps.diego.autoconfig;

/**
 * Created by DiegoAndres on 29/07/2014.
 */
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class GPSTracker extends Service implements LocationListener {

    private static Volume vol;
    private static Wifi wif;
    private static Bluetooth blue;
    private static SendSMS sms;
    private static httpHandler handler;
    public static RedCelular gprs;
    public static Phone ph;
    public static Bateria b;
    public static Archivo ar;

    Context context;
    double latitud;
    double longitud;
    float precision;
    double altitud;
    String proveedor;
    static String fecha;
    static String txtsms=" ";
    static String zon="",wifi="",bluetooth="",audio="",movil="";

    public void onCreate() {
        super.onCreate();
        //class
        ar = new Archivo(this.getBaseContext().getApplicationContext());
        vol= new Volume((AudioManager) this.getBaseContext().getSystemService(AUDIO_SERVICE));
        sms= new SendSMS();
        handler = new httpHandler();
        wif = new Wifi((WifiManager) this.getBaseContext().getSystemService(WIFI_SERVICE));
        blue= new Bluetooth((BluetoothAdapter.getDefaultAdapter()));
        gprs = new RedCelular((ConnectivityManager) this.getBaseContext().getSystemService(CONNECTIVITY_SERVICE));
        ph = new Phone((TelephonyManager) this.getBaseContext().getSystemService(TELEPHONY_SERVICE));
        IntentFilter batIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        b=new Bateria(this.registerReceiver(null, batIntentFilter));
        context=this.getBaseContext();
    }

    @Override
    public void onLocationChanged(Location location) {
        // Initialize the location fields
        latitud = location.getLatitude();
        longitud = location.getLongitude();
        precision = location.getAccuracy();
        altitud = location.getAltitude();
        proveedor = location.getProvider();
        fecha = new Date().toString();
        txtsms=fecha;
        configuracion(latitud,longitud,fecha,altitud,precision,proveedor);
        Config.GPS(zon,wifi,bluetooth,audio,movil);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void configuracion(double latitude, double longitude, String fecha, double altitude, float precisione, String provider){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String us= ph.getPhoneNumber()+" "+ph.getDeviceId();
        int bat= b.cargaBateria();
        String zona=handler.zonesearchpost("BuscarZona",ar.recuperar("AutoConfigUser").split("-")[0],latitude,longitude);
        //Toast.makeText(context,zona,Toast.LENGTH_LONG).show();
        //String zonas=zona;
        if(!zona.contains("OK")){zona= "UNKNOWN";}else {
            String[] zonab = zona.split(";");
            zona=zonab[1];
            int volu=vol.ChangeVolume(Integer.parseInt(zonab[5]));
            switch (volu){
                case 0: audio="Modo Vibrar Activado";
                    break;
                case 1: audio="Modo Sonido Activado";
                    break;
                case 2: audio="Modo Silencio Activado";
                    break;
            }
            if(wif.ChangeWifiStatus(Integer.parseInt(zonab[2]))) {
                wifi="Wifi Activado";
            }else{
               wifi="Wifi Desactivado";
            }
            if(blue.ChangeBluetooth(Integer.parseInt(zonab[3]))){
                bluetooth="Bluetooth Activado";
            }else{
               bluetooth="Bluetooth Desactivado";
            }
            boolean gp = true;
            if (zonab[4].equals(0)) {
                gp = false;
            }
            if(gprs.setMobileDataEnabled(gp)){
                movil="Red Movil Activada";
            }else{
               movil="Red Movil Desactivada";
            }

        }
        zon=zona;
        txtsms += " Bateria: "+bat+"% Zona: " + zona + " https://www.google.com/maps/@+" + latitude + "," + longitude + ",18z";
        ar.grabar("sms",txtsms);
        if(ar.recuperar("AutoConfigUser").split("-")[1].equals("1")) {
            String txt = handler.post("InsertarPosicion",ar.recuperar("AutoConfigUser").split("-")[0], us, latitude, longitude, fecha, altitude, precisione, provider, bat, zona);
        }
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public float getPrecision() {
        return precision;
    }

    public double getAltitud() {
        return altitud;
    }

    public String getProveedor() {
        return proveedor;
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //Toast.makeText(MainActivity.this, provider + "'s status changed to "+status +"!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        //Toast.makeText(MainActivity.this, "Provider " + provider + " enabled!",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        //Toast.makeText(MainActivity.this, "Provider " + provider + " disabled!",Toast.LENGTH_SHORT).show();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}