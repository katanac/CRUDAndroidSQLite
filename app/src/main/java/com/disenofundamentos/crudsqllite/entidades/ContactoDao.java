package com.disenofundamentos.crudsqllite.entidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ContactoDao {

    private SQLiteDatabase conexion;
    private ArrayList<Contacto> listacontactos;
    private Contacto contacto;
    private Context contexto;
    private String nombreBDD;
    private String tablaContacto;


    public ContactoDao(Context contexto) {
        this.contexto = contexto;
        this.nombreBDD = "BDContactos";
        this.tablaContacto = "create table if not exists contacto(id integer primary key autoincrement, nombre text, telefono text, email text, edad integer)";
        conexion = contexto.openOrCreateDatabase(nombreBDD, Context.MODE_PRIVATE, null);
        conexion.execSQL(tablaContacto);
    }

    public boolean insertar(Contacto contacto) {
        ContentValues contenedorContacto = new ContentValues();
        contenedorContacto.put("nombre", contacto.getNombre());
        contenedorContacto.put("telefono", contacto.getTelefono());
        contenedorContacto.put("email", contacto.getEmail());
        contenedorContacto.put("edad", contacto.getEdad());
        return (conexion.insert("contacto", null, contenedorContacto)) > 0;
    }


    public boolean eliminar(int idContacto) {

        return (conexion.delete("contacto",  "id=" + idContacto , null)) > 0;
    }


    public boolean editar(Contacto contacto) {

        ContentValues contenedorContacto = new ContentValues();
        contenedorContacto.put("nombre", contacto.getNombre());
        contenedorContacto.put("telefono", contacto.getTelefono());
        contenedorContacto.put("email", contacto.getEmail());
        contenedorContacto.put("edad", contacto.getEdad());
        return (conexion.update("contacto", contenedorContacto, "id=" + contacto.getId() , null)) > 0;
    }


    public ArrayList<Contacto> consultaListaContactos() {
        listacontactos = new ArrayList<>();
        listacontactos.clear();
        Cursor cursor = conexion.rawQuery("select * from contacto", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                listacontactos.add(new Contacto(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)));
            } while (cursor.moveToNext());
        }
        return listacontactos;
    }

    public Contacto consultaContacto(int posicion) {
        Cursor cursor = conexion.rawQuery("select * from contacto", null);
        cursor.moveToPosition(posicion);
        contacto = new Contacto(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4));

        return contacto;
    }


}
