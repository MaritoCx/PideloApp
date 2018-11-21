package com.example.maritocx.pideloapp.classes;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.maritocx.pideloapp.Admin.AdminUNO;
import com.example.maritocx.pideloapp.Admin.RegistroUNO;
import com.example.maritocx.pideloapp.Login;
import com.squareup.picasso.Picasso;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class GenerarTipo {

    public ScrollView AddControls(String method, Context context){

        LayoutInflater inflater = LayoutInflater.from(context);
    ScrollView layout= (ScrollView) inflater.inflate('1', null, false);
    String result;
    String[] list;
    ArrayList<String> imgList= new ArrayList<>();
    ArrayList<String> nameList= new ArrayList<>();
    int size=0;

        Connection con=new Connection();

        SoapObject request=new SoapObject(con.NAMESPACE, method);

        SoapSerializationEnvelope envelope =
                new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=false;
        envelope.setOutputSoapObject(request);

        HttpTransportSE ht = new HttpTransportSE(con.URL);

        try{
            ht.call(con.SOAP_ACTION+method,envelope);
            SoapPrimitive response =(SoapPrimitive) envelope.getResponse();

            list = new String[response.getAttributeCount()];


            for (int i=0;i<response.getAttributeCount();i++){
                list[i]=response.getAttributeAsString(i).toString();
                String[] split=list[i].split(",");
                imgList.add(split[1]);
                nameList.add(split[2]);
                size=list.length;
            }


        }catch (Exception e){
            e.printStackTrace();
            result="Error occurred";
        }
        ArrayList<imagen> imgTipo = new ArrayList<imagen>();
        for (int i = 0; i < size; i++) {
            imgTipo.add(new imagen(i,nameList.get(i),imgList.get(i)));
        }

        for (imagen img : imgTipo) {
            final ImageView image = new ImageView(context);
            image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 143));
            image.setId(img.id);
            loadImageFromUrl(img.url, context,image);
            layout.addView(image);
            image.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             //EVENTO CLIC PARA CADA IMAGEN

                                         }
                                     }
            );
        }


        return layout;
    }

    private static void loadImageFromUrl(String url, Context context, ImageView img) {
        Picasso.with(context).load(url).into(img, new com.squareup.picasso.Callback(){
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
    }

    /*private class AsyncCallWS extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
        *//*Result=login(txtUsser.getText().toString(),txtPass.getText().toString());
        return Result;*//*
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
        *//*switch(result) {
            case "Usuario correcto":
                user=txtUsser.getText().toString();
                Toast.makeText(Login.this,"iniciando...",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getApplicationContext(), AdminUNO.class);
                startActivity(intent1);
                break;
            case "Login correcto":
                user=txtUsser.getText().toString();
                Toast.makeText(Login.this,"iniciando...",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getApplicationContext(), RegistroUNO.class);
                startActivity(intent2);
                break;
            default:
                Toast.makeText(Login.this,"Fallo el inicio de sesion",Toast.LENGTH_LONG).show();
        }*//*
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }*/
}

class imagen{
    public int id;
    public String nombree;
    public String url;
    public imagen(int id, String nombree, String url) {
        this.id = id;
        this.nombree = nombree;
        this.url=url;
    }
}


