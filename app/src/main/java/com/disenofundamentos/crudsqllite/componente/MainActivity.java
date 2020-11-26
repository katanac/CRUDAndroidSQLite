package com.disenofundamentos.crudsqllite.componente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.disenofundamentos.crudsqllite.R;
import com.disenofundamentos.crudsqllite.componente.agregarContacto.AgregarContactoActividad;
import com.disenofundamentos.crudsqllite.entidades.Contacto;
import com.disenofundamentos.crudsqllite.entidades.ContactoDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ContactoDao contactoDao;
    private ListadoAdapter adaptador;
    private ArrayList<Contacto> listadoContactos;
    private Contacto contacto;
    private Activity activity;
    private View viewHoldingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listViewContactos = (ListView) findViewById(R.id.listado_contactos);
        CardView cardAgregarContacto = (CardView) findViewById(R.id.btn_agregar_contacto);
        FloatingActionButton fbtRefrsh = (FloatingActionButton) findViewById(R.id.refresh_list);

        activity = this;
        contactoDao = new ContactoDao(this);
        listadoContactos = new ArrayList<>();
        viewHoldingDialog = null;
        listadoContactos.clear();
        listadoContactos = contactoDao.consultaListaContactos();

        adaptador = new ListadoAdapter(listadoContactos, contactoDao, this);
        listViewContactos.setAdapter(adaptador);


        cardAgregarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.startActivity(AgregarContactoActividad.obtenerIntencion(activity.getBaseContext()));
            }
        });

        fbtRefrsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listadoContactos.clear();
                listadoContactos = contactoDao.consultaListaContactos();
                adaptador = new ListadoAdapter(listadoContactos, contactoDao, MainActivity.this);
                adaptador.notifyDataSetChanged();
            }
        });
    }

}