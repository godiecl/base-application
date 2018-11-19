/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.directorio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.ucn.disc.dsm.directorio.R;
import cl.ucn.disc.dsm.directorio.model.Person;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Adaptador de Personas
 */
@Slf4j
public final class PersonAdapter extends BaseAdapter {

    /**
     * Listado de Personas.
     */
    private List<Person> people = new ArrayList<>();

    /**
     * Hash de md5(email)
     */
    private Map<String, String> md5Email = new HashMap<>();

    /**
     * Inflater
     */
    private LayoutInflater inflater;

    /**
     * @param context para obtener el inflater.
     */
    public PersonAdapter(@NonNull final Context context) {

        this.inflater = LayoutInflater.from(context);

    }

    /**
     * Add all the people.
     *
     * @param people to add.
     */
    public void setPeople(final List<Person> people) {

        this.people.addAll(people);

    }

    /**
     * Cargar las personas desde alguna fuente (url, texto, etc.)
     */
    public void load() {


        // Read from json

        // Ordamiento por apellidos
        // Collections.sort(this.people, (p1, p2) -> p1.getApellidos().compareTo(p2.getApellidos()));

    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return this.people.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Person getItem(int position) {
        return this.people.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Contenedor
        ViewHolder holder;

        // Si la fila es null, la inflo
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.row_person, parent, false);

            // Instancio y almaceno
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {

            // Obtengo el holder desde el view.
            holder = (ViewHolder) convertView.getTag();
        }

        // Persona a ser mostrada
        final Person persona = this.people.get(position);

        // Valores
        holder.nombre.setText(persona.getNombre());
        holder.cargo.setText(persona.getCargo());
        holder.unidad.setText(persona.getUnidad());

        holder.email.setText(persona.getEmail());
        holder.telefono.setText(persona.getTelefono());
        holder.oficina.setText(persona.getOficina());

        /*
        // Obtengo el md5 desde el map
        String md5 = md5Email.get(persona.getEmail());

        // Si no existe, lo calculo y lo guardo
        if (md5 == null) {
            md5 = Md5.encode(persona.getEmail());
            md5Email.put(persona.getEmail(), md5);
        }

        // El gran fb nos ayuda
        Uri uri = Uri.parse("https://www.gravatar.com/avatar/" + md5);
        holder.foto.setImageURI(uri);
        */

        return convertView;
    }

    /**
     * Clase interna.
     */
    private static class ViewHolder {

        SimpleDraweeView foto;

        TextView nombre;
        TextView cargo;
        TextView unidad;

        TextView email;
        TextView telefono;
        TextView oficina;

        ViewHolder(View view) {

            foto = view.findViewById(R.id.rp_iv_foto);

            nombre = view.findViewById(R.id.rp_tv_nombre);
            cargo = view.findViewById(R.id.rp_tv_cargo);
            unidad = view.findViewById(R.id.rp_tv_unidad);

            email = view.findViewById(R.id.rp_tv_email);
            telefono = view.findViewById(R.id.rp_tv_telefono);
            oficina = view.findViewById(R.id.rp_tv_oficina);
        }

    }
}
