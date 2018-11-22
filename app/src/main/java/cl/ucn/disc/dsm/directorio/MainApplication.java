/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.directorio;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.apache.commons.lang3.time.StopWatch;

import androidx.appcompat.app.AppCompatDelegate;
import lombok.extern.slf4j.Slf4j;

/**
 * Aplicacion base
 */
@Slf4j
public final class MainApplication extends Application {

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     *
     */
    @Override
    public void onCreate() {
        super.onCreate();

        StopWatch stopWatch = StopWatch.createStarted();

        // Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);

        // Inicializacion de fresco
        Fresco.initialize(this);

        log.debug("Initialized in {}.", stopWatch);

    }
}
