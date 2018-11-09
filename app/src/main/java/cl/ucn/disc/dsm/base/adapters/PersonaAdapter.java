package cl.ucn.disc.dsm.base.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

import cl.ucn.disc.dsm.base.R;
import cl.ucn.disc.dsm.base.model.Persona;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Adaptador de Personas
 */
@Slf4j
public final class PersonaAdapter extends BaseAdapter {

    /**
     * Listado de Personas.
     */
    private List<Persona> personas = new ArrayList<>();

    /**
     * Inflater
     */
    private LayoutInflater inflater;

    /**
     * @param context para obtener el inflater.
     */
    public PersonaAdapter(@NonNull final Context context) {

        this.inflater = LayoutInflater.from(context);
    }

    /**
     * Cargar las personas desde alguna fuente (url, texto, etc.)
     */
    public void load() {

        final Faker faker = new Faker();

        for (int i = 0; i < 500; i++) {

            personas.add(Persona.builder()
                    .nombre(faker.name().firstName())
                    .apellidos(faker.name().lastName())
                    .email(faker.internet().emailAddress())
                    .numero(faker.phoneNumber().cellPhone())
                    .build());

            log.debug("Persona: {}", personas.get(i));

        }

    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return this.personas.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Persona getItem(int position) {
        return this.personas.get(position);
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

            convertView = inflater.inflate(R.layout.row_persona, parent, false);

            // Instancio y almaceno
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {

            // Obtengo el holder desde el view.
            holder = (ViewHolder) convertView.getTag();
        }

        // Persona a ser mostrada
        final Persona persona = this.personas.get(position);

        // Valores
        holder.nombre.setText(persona.getNombre());

        return convertView;
    }

    /**
     * Clase interna.
     */
    private static class ViewHolder {

        TextView nombre;

        ViewHolder(View view) {
            nombre = view.findViewById(R.id.rp_tv_nombre);
        }

    }
}
