/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.directorio.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.apache.commons.lang3.time.StopWatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import cl.ucn.disc.dsm.directorio.R;
import cl.ucn.disc.dsm.directorio.adapters.PersonAdapter;
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
     * Person Adapter
     */
    protected PersonAdapter personAdapter;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final StopWatch stopWatch = StopWatch.createStarted();

        setContentView(R.layout.activity_main);

        // Toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ListView of data
        final ListView listView = findViewById(android.R.id.list);

        final View empty = findViewById(android.R.id.empty);
        listView.setEmptyView(empty);

        // Model
        final PersonViewModel model = ViewModelProviders
                .of(this)
                .get(PersonViewModel.class);

        // Observador
        model.getPeople().observe(this, people -> {

            log.debug("getPeople() ready to go!");

            final PersonAdapter personAdapter = new PersonAdapter(getApplicationContext());

            if (people != null) {
                personAdapter.setPeople(people);
            } else {
                log.error("Can't find people !!");
            }

            listView.setAdapter(personAdapter);

        });

        log.debug("onCreate() in {}.", stopWatch);

    }

}
