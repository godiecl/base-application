/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.news;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public final class MainApplication extends Application {




    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     *
     * <p>Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.</p>
     *
     */
    @Override
    public void onCreate() {
        super.onCreate();

        // Inicializacion de fresco
        Fresco.initialize(this);

    }
}
