/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.news.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cl.ucn.disc.dsm.news.R;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        }
    }
}
