package com.playket.util;

/**
 * Excepción propia de Playket.
 * Se lanza cuando algo falla en el acceso a la base de datos
 * para que el controlador pueda mostrar un mensaje claro al usuario.
 */
public class PlayketException extends RuntimeException {

    public PlayketException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public PlayketException(String mensaje) {
        super(mensaje);
    }
}
