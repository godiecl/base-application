/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.directorio.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.ViewModelProviders;

import org.apache.commons.lang3.time.StopWatch;

import cl.ucn.disc.dsm.directorio.R;
import cl.ucn.disc.dsm.directorio.adapters.PersonAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * Activity principal.
 *
 * @author Diego Urrutia-Astorga.
 */
@Slf4j
public final class MainActivity extends AppCompatActivity {

    /**
     * Person Adapter
     */
    protected PersonAdapter personAdapter;

    /**
     * @param savedInstanceState instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Chronos
        final StopWatch stopWatch = StopWatch.createStarted();

        setContentView(R.layout.activity_main);

        // Toolbar
        {
            final Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }

        // ListView of data
        {
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

                this.personAdapter = new PersonAdapter(getApplicationContext());

                if (people != null) {
                    this.personAdapter.setPeople(people);
                } else {
                    log.error("Can't find people !!");
                }

                listView.setAdapter(personAdapter);

            });
        }

        log.debug("onCreate() in {}.", stopWatch);

    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.toolbar_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // searchView.setActivated(true);
        searchView.setQueryHint("Búsqueda");
        // searchView.onActionViewExpanded();
        // searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            /**
             *
             * @param query to use
             * @return the bool
             */
            @Override
            public boolean onQueryTextSubmit(final String query) {

                return false;
            }

            /**
             *
             * @param newText to use
             * @return the bool
             */
            @Override
            public boolean onQueryTextChange(final String newText) {

                if (personAdapter != null) {
                    personAdapter.getFilter().filter(newText);
                    return true;
                }

                return false;
            }

        });


        return super.onCreateOptionsMenu(menu);
    }
}
