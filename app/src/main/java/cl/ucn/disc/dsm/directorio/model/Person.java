/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.directorio.model;

import lombok.Builder;
import lombok.Getter;

@Builder
public final class Person {

    @Getter
    private int id;

    @Getter
    private String nombre;

    @Getter
    private String cargo;

    @Getter
    private String unidad;

    @Getter
    private String email;

    @Getter
    private String telefono;

    @Getter
    private String oficina;

}
