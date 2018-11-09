package cl.ucn.disc.dsm.base.model;


import lombok.Builder;
import lombok.Getter;

/**
 * Clase que representa el contacto de una Persona.
 */
@Builder
public final class Persona {

    /**
     *
     */
    @Getter
    private String nombre;

    /**
     *
     */
    @Getter
    private String apellidos;

    /**
     *
     */
    @Getter
    private String numero;

    /**
     *
     */
    @Getter
    private String foto;

    /**
     *
     */
    @Getter
    private String email;

}
