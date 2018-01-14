package com.apps.diego.autoconfig;

/**
 * Created by DiegoAndres on 29/07/2014.
 */
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class httpHandler {


    //String host="http://autoconfig.webatu.com/";
   // String host="http://192.168.1.151/AutoConfig/Api.php?url=";
    String host="http://ec2-52-37-140-230.us-west-2.compute.amazonaws.com:8080/AutoConfig/Api.php?url=";
    public String post(String posturl,String usuario,String device, double lat, double lon, String fecha, double alti, float prec, String prov,int bat,String zona){

        try {
            posturl=host+posturl;
           HttpClient httpclient = new DefaultHttpClient();
/*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
           HttpPost httppost = new HttpPost(posturl);
/*El objeto HttpPost permite que enviemos una peticion de tipo POST a una URL especificada*/
            //AÑADIR PARAMETROS
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Usuario",usuario));
            params.add(new BasicNameValuePair("Device",device));
            params.add(new BasicNameValuePair("Latitud",lat+""));
            params.add(new BasicNameValuePair("Longitud",lon+""));
            params.add(new BasicNameValuePair("Fecha",fecha));
            params.add(new BasicNameValuePair("Altitud",alti+""));
            params.add(new BasicNameValuePair("Precision",prec+""));
            params.add(new BasicNameValuePair("Proveedor",prov));
            params.add(new BasicNameValuePair("Bat",bat+""));
            params.add(new BasicNameValuePair("Zona",zona));
		/*Una vez añadidos los parametros actualizamos la entidad de httppost, esto quiere decir en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor envien los datos que hemos añadido*/
            httppost.setEntity(new UrlEncodedFormEntity(params));

                  /*Finalmente ejecutamos enviando la info al server*/
            HttpResponse resp = httpclient.execute(httppost);
            HttpEntity ent = resp.getEntity();/*y obtenemos una respuesta*/

            String text = EntityUtils.toString(ent);

            return text;

        }
        catch(Exception e) { System.out.println(e.toString());return "error ";}

    }

    public String zonesearchpost(String posturl,String usuario, double lat, double lon){
        posturl=host+posturl;
        try {

            HttpClient httpclient = new DefaultHttpClient();
/*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
            HttpPost httppost = new HttpPost(posturl);
/*El objeto HttpPost permite que enviemos una peticion de tipo POST a una URL especificada*/
            //AÑADIR PARAMETROS
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Correo",usuario));
            params.add(new BasicNameValuePair("Latitud",lat+""));
            params.add(new BasicNameValuePair("Longitud",lon+""));

		/*Una vez añadidos los parametros actualizamos la entidad de httppost, esto quiere decir en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor envien los datos que hemos añadido*/
            httppost.setEntity(new UrlEncodedFormEntity(params));

                  /*Finalmente ejecutamos enviando la info al server*/
            HttpResponse resp = httpclient.execute(httppost);
            HttpEntity ent = resp.getEntity();/*y obtenemos una respuesta*/
            String text = EntityUtils.toString(ent);
            JSONObject answ2= new JSONObject(text);
            JSONObject answ=new JSONObject(answ2.getString("zonas"));
            String result=answ2.getString("estado")+";"+answ.getString("zona")+";"+answ.getString("wifi")+";"+answ.getString("bluetooth")+";"+answ.getString("movil")+";"+answ.getString("sonido");
            return result;

        }
        catch(Exception e) { System.out.println(e.toString());return "error ";}

    }

    public String zonelookpost(String posturl,String usuario){
        posturl=host+posturl;
        try {

            HttpClient httpclient = new DefaultHttpClient();
/*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
            HttpPost httppost = new HttpPost(posturl);
/*El objeto HttpPost permite que enviemos una peticion de tipo POST a una URL especificada*/
            //AÑADIR PARAMETROS
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Correo",usuario));

		/*Una vez añadidos los parametros actualizamos la entidad de httppost, esto quiere decir en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor envien los datos que hemos añadido*/
            httppost.setEntity(new UrlEncodedFormEntity(params));

                  /*Finalmente ejecutamos enviando la info al server*/
            HttpResponse resp = httpclient.execute(httppost);
            HttpEntity ent = resp.getEntity();/*y obtenemos una respuesta*/

            String text = EntityUtils.toString(ent);
            JSONObject answ= new JSONObject(text);
            String result=answ.getString("zonas");
            return result;

        }
        catch(Exception e) { System.out.println(e.toString());return "error ";}

    }

    public String zonedelpost(String posturl,String usuario,String borra){
        posturl=host+posturl;
        try {

            HttpClient httpclient = new DefaultHttpClient();
/*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
            HttpPost httppost = new HttpPost(posturl);
/*El objeto HttpPost permite que enviemos una peticion de tipo POST a una URL especificada*/
            //AÑADIR PARAMETROS
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Correo",usuario));
            params.add(new BasicNameValuePair("Borra",borra));

		/*Una vez añadidos los parametros actualizamos la entidad de httppost, esto quiere decir en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor envien los datos que hemos añadido*/
            httppost.setEntity(new UrlEncodedFormEntity(params));

                  /*Finalmente ejecutamos enviando la info al server*/
            HttpResponse resp = httpclient.execute(httppost);
            HttpEntity ent = resp.getEntity();/*y obtenemos una respuesta*/

            String text = EntityUtils.toString(ent);

            return text;

        }
        catch(Exception e) { System.out.println(e.toString());return "error ";}

    }

    public String zonapost(String posturl,String usuario, String lat, String lon,String zona, String prov,String wi,String bl,String mo,String son){
        posturl=host+posturl;
        try {

            HttpClient httpclient = new DefaultHttpClient();
/*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
            HttpPost httppost = new HttpPost(posturl);
/*El objeto HttpPost permite que enviemos una peticion de tipo POST a una URL especificada*/
            //AÑADIR PARAMETROS
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Usuario",usuario));
            params.add(new BasicNameValuePair("Latitud",lat+""));
            params.add(new BasicNameValuePair("Longitud",lon+""));
            params.add(new BasicNameValuePair("Zona",zona));
            params.add(new BasicNameValuePair("Proveedor",prov));
            params.add(new BasicNameValuePair("Wifi",wi));
            params.add(new BasicNameValuePair("Blue",bl));
            params.add(new BasicNameValuePair("Movil",mo));
            params.add(new BasicNameValuePair("Sonido",son));
		/*Una vez añadidos los parametros actualizamos la entidad de httppost, esto quiere decir en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor envien los datos que hemos añadido*/
            httppost.setEntity(new UrlEncodedFormEntity(params));

                  /*Finalmente ejecutamos enviando la info al server*/
            HttpResponse resp = httpclient.execute(httppost);
            HttpEntity ent = resp.getEntity();/*y obtenemos una respuesta*/

            String text = EntityUtils.toString(ent);

            return text;
        }
        catch(Exception e) { System.out.println(e.toString());return "error ";}

    }

    public String loginpost(String posturl,String usuario, String contrase){
        posturl=host+posturl;
        try {

            HttpClient httpclient = new DefaultHttpClient();
/*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
            HttpPost httppost = new HttpPost(posturl);
/*El objeto HttpPost permite que enviemos una peticion de tipo POST a una URL especificada*/
            //AÑADIR PARAMETROS
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Usuario",usuario));
            params.add(new BasicNameValuePair("Contrase",contrase));
		/*Una vez añadidos los parametros actualizamos la entidad de httppost, esto quiere decir en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor envien los datos que hemos añadido*/
            httppost.setEntity(new UrlEncodedFormEntity(params));

                  /*Finalmente ejecutamos enviando la info al server*/
            HttpResponse resp = httpclient.execute(httppost);
            HttpEntity ent = resp.getEntity();/*y obtenemos una respuesta*/

            String text = EntityUtils.toString(ent);
            JSONObject answ2= new JSONObject(text);
            JSONObject answ=new JSONObject(answ2.getString("login"));
            String result=answ2.getString("estado")+";"+answ.getString("nombre")+";"+answ.getString("correo")+";"+answ.getString("password")+";"+answ.getString("tracking")+";"+answ.getString("sms");
            return result;
        }
        catch(Exception e) { System.out.println(e.toString());return "error "+e.fillInStackTrace();}

    }

    public String registerpost(String posturl,String usuario, String correo, String contrase, int tracking,int sms){
        posturl=host+posturl;
        try {

            HttpClient httpclient = new DefaultHttpClient();
/*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
            HttpPost httppost = new HttpPost(posturl);
/*El objeto HttpPost permite que enviemos una peticion de tipo POST a una URL especificada*/
            //AÑADIR PARAMETROS
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Usuario",usuario));
            params.add(new BasicNameValuePair("Correo",correo));
            params.add(new BasicNameValuePair("Contrase",contrase));
            params.add(new BasicNameValuePair("Tracking", tracking+""));
            params.add(new BasicNameValuePair("Sms",sms+""));
		/*Una vez añadidos los parametros actualizamos la entidad de httppost, esto quiere decir en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor envien los datos que hemos añadido*/
            httppost.setEntity(new UrlEncodedFormEntity(params));

                  /*Finalmente ejecutamos enviando la info al server*/
            HttpResponse resp = httpclient.execute(httppost);
            HttpEntity ent = resp.getEntity();/*y obtenemos una respuesta*/

            String text = EntityUtils.toString(ent);

            return text;
        }
        catch(Exception e) { System.out.println(e.toString());return "error ";}

    }

}