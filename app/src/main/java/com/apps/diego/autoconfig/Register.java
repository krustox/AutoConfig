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
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by DiegoAndres on 01/08/2014.
 */
public class Register extends Activity {

    Button aceptar;
    Button cancelar;
    EditText user;
    EditText pass;
    EditText correo;
    CheckBox tracking;
    CheckBox sms;
    int track=0,mjs=0;
    public Archivo ar;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        aceptar = (Button) findViewById(R.id.regaceptar);
        cancelar = (Button) findViewById(R.id.regcancel);
        user = (EditText) findViewById(R.id.reguser);
        correo = (EditText) findViewById(R.id.regcorreo);
        pass = (EditText) findViewById(R.id.regpass);
        tracking = (CheckBox) findViewById(R.id.tracking);
        sms = (CheckBox) findViewById(R.id.sms);
        ar = new Archivo(getApplicationContext());

        aceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                httpHandler handler = new httpHandler();

                if(tracking.isChecked()){track=1;}
                if(sms.isChecked()){mjs=1;}
                String resp =handler.registerpost("Registro", user.getText().toString(), correo.getText().toString(), pass.getText().toString(),track,mjs);
                if (resp.trim().contains("OK")) {
                    Toast.makeText(getApplicationContext(), "Registro Exitoso ", Toast.LENGTH_SHORT).show();
                    ar.grabar("AutoConfigUser",user.getText().toString()+"-"+track+"-"+mjs);
                    ejecutar(v, Config.class);
                } else {
                    Toast.makeText(getApplicationContext(), "El Correo Ya Esta Registrado ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ejecutar(v, MainActivity.class);
            }
        });
    }

    public void ejecutar(View view,  Class<?> cls) {
        Intent i = new Intent(this,cls);
        i.putExtra("usuario", correo.getText().toString());
        i.putExtra("track",track+"");
        i.putExtra("sms",mjs+"");
        startActivity(i);
    }
}
