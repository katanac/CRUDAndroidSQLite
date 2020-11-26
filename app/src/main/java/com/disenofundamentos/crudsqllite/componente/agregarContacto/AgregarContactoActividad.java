package com.disenofundamentos.crudsqllite.componente.agregarContacto;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import com.disenofundamentos.crudsqllite.R;
import com.disenofundamentos.crudsqllite.entidades.Contacto;
import com.disenofundamentos.crudsqllite.entidades.ContactoDao;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class AgregarContactoActividad extends AppCompatActivity {

    private ContactoDao contactoDao;
    private Contacto contacto;
    private Activity activity;


    public static Intent obtenerIntencion(Context contexto) {
        Intent intencion = new Intent(contexto, AgregarContactoActividad.class);
        return intencion;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogo);

        contactoDao = new ContactoDao(this);

        final EditText edNombre = (EditText) findViewById(R.id.nombre);
        final EditText edTelefono = (EditText) findViewById(R.id.telefono);
        final EditText edEmail = (EditText) findViewById(R.id.email);
        final EditText edEdad = (EditText) findViewById(R.id.edad);


        Button btnAgregarContacto = (Button) findViewById(R.id.agregar_contac);
        Button btnEditarContacto = (Button) findViewById(R.id.editar_contac);
        btnEditarContacto.setVisibility(View.GONE);



        btnAgregarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contacto = new Contacto();
                try {
                    contacto.setNombre(edNombre.getText().toString());
                    contacto.setEdad(Integer.parseInt(edEdad.getText().toString()));
                    contacto.setEmail(edEmail.getText().toString());
                    contacto.setTelefono(edTelefono.getText().toString());

                    contactoDao.insertar(contacto);

                    AgregarContactoActividad.this.finish();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
