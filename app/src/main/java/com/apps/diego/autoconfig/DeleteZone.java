package com.apps.diego.autoconfig;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by DiegoAndres on 02/08/2014.
 */
public class DeleteZone extends Activity {

    Bundle bundle;
    Button delZone;
    Button cancelar;
    private static httpHandler handler;
    LinearLayout ll;
    CheckBox[] z;
    Cursor cursor;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_zone);
        bundle = getIntent().getExtras();
        handler = new httpHandler();
        ll = (LinearLayout)findViewById(R.id.elimzone);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String zona = handler.zonelookpost("VerZona",bundle.getString("usuario"));
        //Toast.makeText(this, zona, Toast.LENGTH_LONG).show();
        String []zonab =zona.split(";");
        z = new CheckBox[zonab.length];
        for(int i=0;i<zonab.length;i++){
            z[i] = new CheckBox(getApplicationContext());
            z[i].setText(zonab[i].toString());
            z[i].setTextColor(Color.BLACK);
            ll.addView(z[i]);
        }
        delZone = (Button) findViewById(R.id.delzone);
        cancelar = (Button) findViewById(R.id.delzonecan);
        delZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String borra="";
                for(int i=0;i<z.length;i++){
                    if(z[i].isChecked()){
                        borra+=z[i].getText().toString()+";";
                    }
                }
                String zona = handler.zonedelpost("BorrarZona",bundle.getString("usuario"),borra);
                if(zona.contains("OK")){
                    Toast.makeText(getApplicationContext(), "Zona Eliminada", Toast.LENGTH_SHORT).show();
                    ejecutar(v,Config.class);
                }else {
                    Toast.makeText(getApplicationContext(), "ERROR ", Toast.LENGTH_SHORT).show();
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
