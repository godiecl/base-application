package cl.ucn.disc.dsm.base.activities;

import android.app.ListActivity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import cl.ucn.disc.dsm.base.adapters.PersonaAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * Actividad con un listado de Personas.
 */
@Slf4j
public final class PersonaListActivity extends ListActivity {

    /**
     * Adaptador de Personas
     */
    private PersonaAdapter personaAdapter;

    /**
     * Called when the activity is starting.  This is where most initialization
     * should go: calling {@link #setContentView(int)} to inflate the
     * activity's UI, using {@link #findViewById} to programmatically interact
     * with widgets in the UI, calling
     * {@link #managedQuery(Uri, String[], String, String[], String)} to retrieve
     * cursors for data being displayed, etc.
     *
     * <p>You can call {@link #finish} from within this function, in
     * which case onDestroy() will be immediately called after {@link #onCreate} without any of the
     * rest of the activity lifecycle ({@link #onStart}, {@link #onResume}, {@link #onPause}, etc)
     * executing.
     *
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     * @see #onStart
     * @see #onSaveInstanceState
     * @see #onRestoreInstanceState
     * @see #onPostCreate
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Si el adaptador es null, no se ha construido
        if (this.personaAdapter == null) {

            // .. lo contruyo ..
            this.personaAdapter = new PersonaAdapter(this);

            this.personaAdapter.load();

        }

        // Adaptador a utilizar para mostrar los datos
        super.setListAdapter(this.personaAdapter);

    }


    /**
     * Called after {@link #onCreate} &mdash; or after {@link #onRestart} when
     * the activity had been stopped, but is now again being displayed to the
     * user.  It will be followed by {@link #onResume}.
     *
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onCreate
     * @see #onStop
     * @see #onResume
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Si el adaptador no es null y no hay datos ..
        if (this.personaAdapter != null && this.personaAdapter.isEmpty()) {

            // Mensaje para mostrar que se estan cargando los datos
            Snackbar.make(findViewById(android.R.id.content), "Loading data ..", Snackbar.LENGTH_LONG).show();

            // Ejecuto en segundo plano ..
            AsyncTask.execute(() -> {

                log.debug("Loading data ..");
                personaAdapter.load();

                // .. ejecuto en el hilo principal
                runOnUiThread(() -> {

                    // Notifico que cambio el conjunto de datos.
                    personaAdapter.notifyDataSetChanged();

                });

            });
        }
    }
}
