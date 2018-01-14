package com.apps.diego.autoconfig;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Config extends Activity {

    static TextView nota;
    static TextView latitud;
    static TextView longitud;
    static TextView proveedor;
    static TextView precision;
    static TextView altitud;
    static TextView hora;
    static EditText zona;
    static TextView showzona;
    static Button add;
    static Button erase;
    static Bundle bundle;
    static Context context;

    private LocationManager locationManager;
    private Criteria criteria;
    private String provider;
    private static GPSTracker mylistener;
    public static Archivo ar;
    public static String mjs;
    static String user="", track="";
    static String lat="", lon="",z="";

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        bundle = getIntent().getExtras();
        user=bundle.getString("usuario");
        inicializacion();
        mjs=bundle.getString("sms");
        track = bundle.getString("track");

        startService(new Intent(this, GPSTracker.class));
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        criteria.setCostAllowed(false);
        provider = locationManager.getBestProvider(criteria, false);
        locationManager.requestLocationUpdates(provider, 1000 * 60 * 20, 1, mylistener);
        add.setEnabled(false);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ejecutar(v, zone.class);
            }
        });
        erase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ejecutar(v,DeleteZone.class);
            }
        });

    }

    public void inicializacion(){

        //variables
        nota = (TextView) findViewById(R.id.nota);
        latitud = (TextView) findViewById(R.id.latitud);
        longitud = (TextView) findViewById(R.id.longitud);
        proveedor = (TextView) findViewById(R.id.proveedor);
        precision = (TextView) findViewById(R.id.precision);
        altitud = (TextView) findViewById(R.id.altitud);
        hora = (TextView) findViewById(R.id.hora);
        add = (Button) findViewById(R.id.add);
        erase = (Button) findViewById(R.id.erase);
        zona = (EditText) findViewById(R.id.zonename);
        showzona = (TextView) findViewById(R.id.showzona);
        context = getApplicationContext();

        //class
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        mylistener = new GPSTracker();

    }

    public static void GPS(String zona,String wifi,String bluetooth,String audio,String movil){

                double latitude = mylistener.getLatitud();
                double longitude = mylistener.getLongitud();
                String provider = mylistener.getProveedor();
                float precisione = mylistener.getPrecision();
                double altitude = mylistener.getAltitud();
                String fecha = new java.util.Date()+"";

                nota.setText("Nota: GPS OK.");
                latitud.setText("Latitud: " + latitude);
                longitud.setText("Longitud: " + longitude);
                proveedor.setText("Proveedor: " + provider);
                altitud.setText("Altitud: " + altitude);
                precision.setText("Precision: " + precisione);
                hora.setText("Fecha: " + fecha);
                lat= latitude+"";
                lon= longitude+"";
                if(bundle.getString("usuario").equals("")){
                    user=ar.recuperar("AutoConfigUser").split("-")[0];
                }
                if(bundle.getString("track").equals("")){
                    track=ar.recuperar("AutoConfigUser").split("-")[1];
                }

                showzona.setText("Zona: " + zona);
                if(!z.equalsIgnoreCase(zona)) {
                    Toast.makeText(context, zona, Toast.LENGTH_SHORT).show();
                    if (zona.equalsIgnoreCase("UNKNOWN")) {
                        add.setEnabled(true);
                    } else {
                        add.setEnabled(false);
                        Toast.makeText(context, wifi, Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, bluetooth, Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, audio, Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, movil, Toast.LENGTH_SHORT).show();
                    }
                }
        z=zona;

    }

    public void ejecutar(View view,  Class<?> cls) {
        Intent i = new Intent(this,cls);
        i.putExtra("usuario", user);
        i.putExtra("nombre", zona.getText().toString() );
        i.putExtra("latitud", lat);
        i.putExtra("longitud", lon);
        i.putExtra("poveedor", proveedor.getText().toString());
        i.putExtra("track",bundle.getString("track"));
        i.putExtra("sms",bundle.getString("sms"));

        startActivity(i);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK :
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("EXIT", true);

                    startActivity(intent);
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
