package com.example.maritocx.pideloapp.classes;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class pruebaconreoles {
    /*
    public void generar() {
        LinearLayout inicio;
        ArrayList<imagen> Imgcategorias = new ArrayList<imagen>();
        Imgcategorias.add(new imagen(1, "img1"));
        Imgcategorias.add(new imagen(2, "img2"));
        Imgcategorias.add(new imagen(3, "img3"));
        Imgcategorias.add(new imagen(4, "img4"));
        for (imagen img : Imgcategorias) {
            final ImageView image = new ImageView(getApplicationContext());
            image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 143));
            image.setId(img.id);
            image.setImageDrawable(getDrawable(R.drawable.logo));
            inicio.addView(image);
            image.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             EVENTO CLIC PARA CADA IMAGEN
                                         }
                                     }
            );
        }
        ArrayList<boton> categorias = new ArrayList<boton>();
        categorias.add(new boton(1, "boton1"));
        categorias.add(new boton(2, "boton2"));
        categorias.add(new boton(3, "boton3"));
        categorias.add(new boton(4, "boton4"));
        for (boton btn : categorias) {
            final Button btncat = new Button(this);
            btncat.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            btncat.setText(btn.nombre);
            btncat.setId(btn.cod);
            inicio.addView(btncat);
            btncat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        EVENTO CLIC PARA CADA BOTON
                }
            });
        }
    }
    */
    class imagen{
        public int id;
        public String nombree;
        public imagen(int id, String nombree) {
            this.id = id;
            this.nombree = nombree;
        }
    }
    class boton{
        public int  cod;
        public String nombre;
        public boton(int cod, String nombre) {
            this.cod = cod;
            this.nombre = nombre;
        }
    }
}
