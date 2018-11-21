package com.example.maritocx.pideloapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maritocx.pideloapp.classes.Connection;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class forgetPass extends AppCompatActivity {

    EditText txtUsu, txtMail, txtPassw;

    Button btnAcep, btnCancel;
    private static String Result=null;

    private static String METHOD="ChangePassword";
    private class AsyncCallWS extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            Result=changePass(txtUsu.getText().toString(),txtMail.getText().toString(),txtPassw.getText().toString());
            return Result;
        }

        @Override
        protected void onPostExecute(String result) {

            if (result.equals("La contraseña se modifico correctamente :)")){
                Toast.makeText(forgetPass.this,Result,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(forgetPass.this,Result,Toast.LENGTH_LONG).show();
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
        setContentView(R.layout.activity_forget_pass);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        txtUsu=(EditText) findViewById(R.id.txtUsser);
        txtMail=(EditText) findViewById(R.id.txtEmail);
        txtPassw=(EditText)findViewById(R.id.txtPass);
        btnAcep=(Button) findViewById(R.id.btnAcept);
        btnCancel=(Button)findViewById(R.id.btnCancel);

        btnAcep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtUsu!=null && txtMail!=null && txtPassw!=null) {// Use the Builder class for convenient dialog construction
                    AlertDialog.Builder builder = new AlertDialog.Builder(forgetPass.this);
                    builder.setMessage("¿Seguro que desea cambiar su contraseña?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    forgetPass.AsyncCallWS task = new forgetPass.AsyncCallWS();
                                    task.execute();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });

                    builder.create();
                    builder.show();
                }
                else
                {
                    Intent intent=new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                }


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   if (txtUsu!=null && txtMail!=null && txtPassw!=null) {// Use the Builder class for convenient dialog construction
                       AlertDialog.Builder builder = new AlertDialog.Builder(forgetPass.this);
                       builder.setMessage("¿Seguro que desea cancelar?")
                               .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int id) {
                                       Intent intent=new Intent(getApplicationContext(),Login.class);
                                       startActivity(intent);
                                   }
                               })
                               .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int id) {

                                   }
                               });

                       builder.create();
                       builder.show();
                   }
                   else
                   {
                       Intent intent=new Intent(getApplicationContext(),Login.class);
                       startActivity(intent);
                   }

            }
        });
    }

    public static String changePass(String usu, String mail, String pass){
        String txtResult=null;

        Connection con=new Connection();

        SoapObject request=new SoapObject(con.NAMESPACE, METHOD);

        request.addProperty("user", usu);
        request.addProperty("email", mail);
        request.addProperty("newPassword", pass);

        SoapSerializationEnvelope envelope =
                new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=false;
        envelope.setOutputSoapObject(request);

        HttpTransportSE ht = new HttpTransportSE(con.URL);
        //ht.debug=true;
        try{
            ht.call(con.SOAP_ACTION+METHOD,envelope);
            SoapPrimitive response =(SoapPrimitive) envelope.getResponse();
            txtResult=response.toString();
        }catch (Exception e){
            e.printStackTrace();
            txtResult="Error occurred";
        }

        return txtResult;
    }
}
