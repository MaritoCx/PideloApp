package com.example.maritocx.pideloapp.Admin;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maritocx.pideloapp.Login;
import com.example.maritocx.pideloapp.R;
import com.example.maritocx.pideloapp.classes.Connection;

import com.squareup.picasso.Picasso;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class RegistroUNO extends AppCompatActivity {
TextView txtusuario;
ImageView btn;
LinearLayout layout;
    private class AsyncCallWS extends AsyncTask<String, Void, String> {
          @Override
          protected String doInBackground(String... params) {

              layout.addView(AddControls("getKindofRestaurant",getApplicationContext()));
        return "Cargando...";
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(RegistroUNO.this,result,Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_uno);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtusuario=(TextView) findViewById(R.id.usu);
        txtusuario.setText("Bienvenido "+Login.user);
        layout=(LinearLayout) findViewById(R.id.scroll);
        btn=(ImageView) findViewById(R.id.addTip);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncCallWS task = new AsyncCallWS();
                task.execute();
            }
        });
    }

    public ScrollView AddControls(String method, Context context){
        ScrollView scrollV= new ScrollView(context);

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



        return scrollV;
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

}
