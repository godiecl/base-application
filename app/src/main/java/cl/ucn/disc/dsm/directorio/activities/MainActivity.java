/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.directorio.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import cl.ucn.disc.dsm.directorio.R;
import cl.ucn.disc.dsm.directorio.adapters.PersonAdapter;
import cl.ucn.disc.dsm.directorio.model.Person;
import lombok.extern.slf4j.Slf4j;

/**
 * Activity principal.
 *
 * @author Diego Urrutia-Astorga.
 */
@Slf4j
public class MainActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_AUTO);
    }

    /**
     * Json un-serializer
     */
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    /**
     * Person Adapter
     */
    protected PersonAdapter personAdapter;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView listView = findViewById(android.R.id.list);

        final TextView empty = findViewById(android.R.id.empty);
        listView.setEmptyView(empty);

        // Adapter!
        if (this.personAdapter == null) {
            this.personAdapter = new PersonAdapter(this);
            listView.setAdapter(this.personAdapter);
        }

    }

    /**
     *
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Si existe el adaptador y no tiene datos
        if (this.personAdapter != null && this.personAdapter.getCount() == 0) {

            // Mensaje para mostrar que se estan cargando los datos
            Snackbar.make(findViewById(android.R.id.content), "Loading data ..", Snackbar.LENGTH_LONG).show();

            // Ejecuto en segundo plano ..
            AsyncTask.execute(() -> {

                log.debug("Loading data ..");

                try (InputStream is = getAssets().open("ucn.json")) {

                    // Tipo de la lista
                    final Type listType = new TypeToken<List<Person>>() {
                    }.getType();

                    // Lector
                    final Reader reader = new InputStreamReader(is);

                    // People!
                    final List<Person> people = GSON.fromJson(reader, listType);

                    personAdapter.setPeople(people);

                } catch (IOException ex) {
                    log.error("Error, ex");
                    return;
                }

                // .. ejecuto en el hilo principal
                runOnUiThread(() -> {

                    // Notifico que cambio el conjunto de datos.
                    personAdapter.notifyDataSetChanged();

                });

            });

        }

    }
}
