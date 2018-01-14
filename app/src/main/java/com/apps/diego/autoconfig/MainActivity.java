package com.apps.diego.autoconfig;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by DiegoAndres on 01/08/2014.
 */
public class MainActivity extends Activity {

    Button button;
    Button register;
    EditText user;
    EditText pass;
    Cursor cursor;
    httpHandler handler;
    String resp1="";
    public Archivo ar;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        button = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        user = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.Password);
        ar= new Archivo(getApplicationContext());


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handler  = new httpHandler();
                resp1=handler.loginpost("Login",user.getText().toString(),pass.getText().toString());
                //Toast.makeText(getApplicationContext(),resp1,Toast.LENGTH_LONG).show();
                if(resp1.contains("OK")){
                String resp=resp1.split(";")[0];
                    if(resp.trim().equals("OK") || cursor.getCount()==1) {
                        Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_SHORT).show();
                        ar.grabar("AutoConfigUser",user.getText().toString()+"-"+resp1.split(";")[4]+"-"+resp1.split(";")[5]);
                        ejecutar(v, Config.class);
                    }else{
                        Toast.makeText(getApplicationContext(),"ERROR de Autenticación",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"ERROR de Autenticación",Toast.LENGTH_SHORT).show();
                }
        }});

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ejecutar(v, Register.class);
            }
        });
    }

    public void ejecutar(View view, Class<?> cls) {
        Intent i = new Intent(this,cls);
        i.putExtra("usuario", user.getText().toString());
        if(resp1.contains("OK")) {
            i.putExtra("track", resp1.split(";")[4]);
            i.putExtra("sms", resp1.split(";")[5]);
        }
        startActivity(i);
    }

}
