/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.attack.model;

import lombok.Builder;
import lombok.Getter;

/**
 *
 */
@Builder
public final class Ship {

    @Getter
    private int file;

    @Getter
    private int column;

    @Getter
    private Type type;

}
