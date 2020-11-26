package com.disenofundamentos.crudsqllite.componente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.disenofundamentos.crudsqllite.R;
import com.disenofundamentos.crudsqllite.componente.agregarContacto.AgregarContactoActividad;
import com.disenofundamentos.crudsqllite.componente.agregarContacto.EditarContactoActividad;
import com.disenofundamentos.crudsqllite.entidades.Contacto;
import com.disenofundamentos.crudsqllite.entidades.ContactoDao;

import java.util.ArrayList;

public class ListadoAdapter extends BaseAdapter {

    private ArrayList<Contacto> listadoContactos;
    private ContactoDao contactoDao;
    private Contacto contacto;
    private Activity activity;

    public ListadoAdapter(ArrayList<Contacto> listadoContactos, ContactoDao contactoDao, Activity activity) {
        this.listadoContactos = listadoContactos;
        this.contactoDao = contactoDao;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listadoContactos.size();
    }

    @Override
    public Contacto getItem(int i) {
        contacto = listadoContactos.get(i);
        return contacto;
    }

    @Override
    public long getItemId(int i) {
        return listadoContactos.get(i).getId();
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        View layout = view;
        if (layout == null) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = layoutInflater.inflate(R.layout.item_contacto, null);
        }

        contacto = new Contacto();
        contacto = listadoContactos.get(posicion);

        TextView tvNombre = (TextView) layout.findViewById(R.id.nombre_item);
        TextView tvEmail = (TextView) layout.findViewById(R.id.email_item);
        TextView tvTelefono = (TextView) layout.findViewById(R.id.telefono_item);
        TextView tvEdad = (TextView) layout.findViewById(R.id.edad_item);
        ImageButton btonEditar = (ImageButton) layout.findViewById(R.id.btn_editar);
        ImageButton btonEliminar = (ImageButton) layout.findViewById(R.id.btn_eliminar);

        tvNombre.setText(contacto.getNombre());
        tvEmail.setText(contacto.getEmail());
        tvTelefono.setText(contacto.getTelefono());
        tvEdad.setText(String.valueOf(contacto.getEdad()));

        btonEditar.setTag(posicion);
        btonEliminar.setTag(posicion);

        btonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = Integer.parseInt(view.getTag().toString());
                activity.startActivity(EditarContactoActividad.obtenerIntencion(activity.getBaseContext(), listadoContactos.get(pos)));
            }
        });


        btonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = Integer.parseInt(view.getTag().toString());
                contacto = listadoContactos.get(pos);
                final AlertDialog.Builder dialogoEliminar = new AlertDialog.Builder(activity);
                dialogoEliminar.setTitle("Eliminacion");
                dialogoEliminar.setMessage("¿Estas segudo de eliminar este registro?");
                dialogoEliminar.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        contactoDao.eliminar(contacto.getId());
                        listadoContactos = contactoDao.consultaListaContactos();
                        notifyDataSetChanged();
                    }
                });
                dialogoEliminar.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialogoEliminar.show();
            }
        });


        return layout;
    }
}
