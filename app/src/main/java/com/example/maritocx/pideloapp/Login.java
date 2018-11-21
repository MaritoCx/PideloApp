package com.example.maritocx.pideloapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maritocx.pideloapp.Admin.AdminUNO;
import com.example.maritocx.pideloapp.Admin.RegistroUNO;
import com.example.maritocx.pideloapp.classes.Connection;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class Login extends AppCompatActivity {

    private EditText txtUsser, txtPass;
    private TextView txtPrincipal,txtForget;
    private Button btnAcept;
    private static String Result=null;
    public static String user;

    //public String user, pass, url, result;

    //private static String NAMESPACE="http://webservices/";
    //private static String URL="http://192.168.0.16:8080/PideloAppWS/PideloAppWS?wsdl";
    //private static String SOAP_ACTION="http://webservices/";

    private class AsyncCallWS extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            Result=login(txtUsser.getText().toString(),txtPass.getText().toString());
            return Result;
        }

        @Override
        protected void onPostExecute(String result) {
            switch(result) {
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
            }
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
        setContentView(R.layout.activity_login);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        txtPrincipal=(TextView) findViewById(R.id.txt1);
        txtUsser=(EditText) findViewById(R.id.txtUsser);
        txtPass=(EditText) findViewById(R.id.txtPass);
        txtForget =(TextView)findViewById(R.id.txtForget);
        btnAcept=(Button) findViewById(R.id.btnAcept);

        btnAcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncCallWS task = new AsyncCallWS();
                task.execute();
            }
        });

        String text = txtForget.getText().toString();
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        txtForget.setText(content);

        txtForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),forgetPass.class);
                startActivity(intent);
                /*Intent intent3 = new Intent(getApplicationContext(), RegistroUNO.class);
                startActivity(intent3);*/
            }
        });
    }

    public static String login(String usu, String contra){
        String txtResult=null;
        Connection con=new Connection();

        SoapObject request=new SoapObject(con.NAMESPACE, "Login");
        //PropertyInfo paramN = new PropertyInfo();
        /*paramN.setName("usuario");
        paramN.setValue(usu);
        paramN.setType(String.class);
        request.addProperty(paramN);
        PropertyInfo paramP = new PropertyInfo();
        paramP.setName("contrasenia");
        paramP.setValue(contra);
        paramP.setType(String.class);
        request.addProperty(paramP);*/

        request.addProperty("usuario", usu);
        request.addProperty("contrasenia", contra);

        SoapSerializationEnvelope envelope =
                new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=false;
        envelope.setOutputSoapObject(request);

        HttpTransportSE ht = new HttpTransportSE(con.URL);
        //ht.debug=true;
        try{
            ht.call(con.SOAP_ACTION+"Login",envelope);
            SoapPrimitive response =(SoapPrimitive) envelope.getResponse();
            txtResult=response.toString();
        }catch (Exception e){
            e.printStackTrace();
            txtResult="Error occurred";
        }

        return txtResult;

    }


}


