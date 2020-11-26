package com.disenofundamentos.crudsqllite.componente.agregarContacto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.disenofundamentos.crudsqllite.R;
import com.disenofundamentos.crudsqllite.entidades.Contacto;
import com.disenofundamentos.crudsqllite.entidades.ContactoDao;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public
class EditarContactoActividad extends AppCompatActivity {


    private ContactoDao contactoDao;
    private Contacto contacto;
    private Activity activity;


    public static Intent obtenerIntencion(Context contexto, Contacto contacto) {
        Intent intencion = new Intent(contexto, EditarContactoActividad.class);
        intencion.putExtra("EXTRA_CONTACTO", contacto);
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

        contacto = (Contacto) getIntent().getSerializableExtra("EXTRA_CONTACTO");


        Button btnEditarContacto = (Button) findViewById(R.id.editar_contac);
        Button btnAgregarContacto = (Button) findViewById(R.id.agregar_contac);
        btnAgregarContacto.setVisibility(View.GONE);

        edNombre.setText(contacto.getNombre());
        edEmail.setText(contacto.getEmail());
        edTelefono.setText(String.valueOf(contacto.getTelefono()));
        edEdad.setText(String.valueOf(contacto.getEdad()));

        btnEditarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    contacto.setNombre(edNombre.getText().toString());
                    contacto.setEdad(Integer.parseInt(edEdad.getText().toString()));
                    contacto.setEmail(edEmail.getText().toString());
                    contacto.setTelefono(edTelefono.getText().toString());

                    contactoDao.editar(contacto);
                    EditarContactoActividad.this.finish();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
