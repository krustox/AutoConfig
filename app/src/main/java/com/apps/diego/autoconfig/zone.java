package com.apps.diego.autoconfig;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by DiegoAndres on 01/08/2014.
 */
public class zone extends Activity {

    Button crear;
    Button cancelar;
    EditText zona;
    EditText latitud;
    EditText longitud;
    CheckBox wifi;
    CheckBox blue;
    CheckBox movil;
    RadioButton vibrar;
    RadioButton sonido;
    RadioButton silencio;
    Bundle bundle;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zone);
        bundle = getIntent().getExtras();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        crear = (Button) findViewById(R.id.zcrear);
        cancelar = (Button) findViewById(R.id.zcancelar);
        zona = (EditText) findViewById(R.id.znombre);
        latitud = (EditText) findViewById(R.id.zlat);
        longitud = (EditText) findViewById(R.id.zlon);
        wifi = (CheckBox) findViewById(R.id.chkwifi);
        blue = (CheckBox) findViewById(R.id.chkblue);
        movil = (CheckBox) findViewById(R.id.chkmovil);
        vibrar = (RadioButton) findViewById(R.id.radiovibrar);
        sonido = (RadioButton) findViewById(R.id.radiosonido);
        silencio = (RadioButton) findViewById(R.id.radiosilencio);
        zona.setText(bundle.getString("nombre"));
        latitud.setText(bundle.getString("latitud"));
        longitud.setText(bundle.getString("longitud"));

        crear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                httpHandler handler = new httpHandler();
                String wi="",bl="",mo="",son="";
                if(wifi.isChecked()){wi="1";}else{wi="0";}
                if(blue.isChecked()){bl="1";}else{bl="0";}
                if(movil.isChecked()){mo="true";}else{mo="false";}
                if(vibrar.isChecked()){son="0";}else{if(sonido.isChecked()){son="1";}else{if(silencio.isChecked()){son="2";}}}
                String resp = handler.zonapost("CrearZona", bundle.getString("usuario"),latitud.getText().toString(),longitud.getText().toString(),zona.getText().toString(),bundle.getString("poveedor"),wi,bl,mo,son);
                if (resp.contains("OK")) {
                    Toast.makeText(getApplicationContext(), "Zona Creada", Toast.LENGTH_SHORT).show();
                    ejecutar(v, Config.class);
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });
            cancelar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ejecutar(v, Config.class);
                }
            });
    }

    public void ejecutar(View view,  Class<?> cls) {
        Intent i = new Intent(this,cls);
        i.putExtra("usuario", bundle.getString("usuario"));
        i.putExtra("track",bundle.getString("track"));
        i.putExtra("sms",bundle.getString("sms"));
        startActivity(i);
    }

}
