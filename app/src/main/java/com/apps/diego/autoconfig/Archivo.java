package com.apps.diego.autoconfig;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by DiegoAndres on 08/08/2014.
 */
public class Archivo {

    Context context;


    public Archivo(){}
    public Archivo(Context context){
        this.context=context;
    }

    public void grabar(String nombre, String content) {
        String nomarchivo = nombre;
        String contenido = content;
        try {
            File tarjeta = Environment.getExternalStorageDirectory();
            File file = new File(tarjeta.getAbsolutePath(), nomarchivo);
            OutputStreamWriter osw = new OutputStreamWriter(
                    new FileOutputStream(file));
            osw.write(contenido);
            osw.flush();
            osw.close();
           if(context!=null) {
               Toast.makeText(context, "Los datos fueron grabados correctamente", Toast.LENGTH_SHORT).show();
           }
        } catch (IOException ioe) {
        }
    }

    public String recuperar(String nombre) {
        String nomarchivo = nombre;
        String todo = "";
        File tarjeta = Environment.getExternalStorageDirectory();
        File file = new File(tarjeta.getAbsolutePath(), nomarchivo);
        try {
            FileInputStream fIn = new FileInputStream(file);
            InputStreamReader archivo = new InputStreamReader(fIn);
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();
            while (linea != null) {
                todo = todo + linea ;
                linea = br.readLine();
            }
            br.close();
            archivo.close();

        } catch (IOException e) {
        }
        return todo;
    }

    public boolean Borrar(String nombre){
        File tarjeta = Environment.getExternalStorageDirectory();
        File file = new File(tarjeta.getAbsolutePath(), nombre);
        boolean deleted = file.delete();
        if(context!=null && deleted) {
            Toast.makeText(context, "El archivo "+nombre+" ha sido eliminado", Toast.LENGTH_SHORT).show();
        }
        return deleted;
    }
}
