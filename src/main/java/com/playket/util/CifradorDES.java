package com.playket.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.util.Base64;

/**
 * Utilidad para cifrar y verificar contraseñas usando el algoritmo DES.
 * Las contraseñas se cifran con DES y se codifican en Base64 para
 * poder almacenarlas como texto en la base de datos.
 */
public class CifradorDES {

    // Clave DES de exactamente 8 bytes (64 bits, 56 efectivos)
    private static final String CLAVE_DES = "Pl4yk3t!";

    /**
     * Cifra una contraseña con DES y devuelve el resultado en Base64.
     *
     * @param password Contraseña en texto plano
     * @return Contraseña cifrada en Base64, o null si ocurre un error
     */
    public static String cifrar(String password) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
            DESKeySpec kspec = new DESKeySpec(CLAVE_DES.getBytes("UTF-8"));
            SecretKey ks = skf.generateSecret(kspec);

            Cipher cifrado = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cifrado.init(Cipher.ENCRYPT_MODE, ks);

            byte[] datosCifrados = cifrado.doFinal(password.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(datosCifrados);

        } catch (Exception e) {
            System.err.println("Error al cifrar contraseña: " + e.getMessage());
            return null;
        }
    }

    /**
     * Descifra una contraseña almacenada en Base64.
     *
     * @param passwordCifrada Contraseña cifrada en Base64
     * @return Contraseña en texto plano, o null si ocurre un error
     */
    public static String descifrar(String passwordCifrada) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
            DESKeySpec kspec = new DESKeySpec(CLAVE_DES.getBytes("UTF-8"));
            SecretKey ks = skf.generateSecret(kspec);

            Cipher cifrado = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cifrado.init(Cipher.DECRYPT_MODE, ks);

            byte[] datosDescifrados = cifrado.doFinal(Base64.getDecoder().decode(passwordCifrada));
            return new String(datosDescifrados, "UTF-8");

        } catch (Exception e) {
            System.err.println("Error al descifrar contraseña: " + e.getMessage());
            return null;
        }
    }

    /**
     * Comprueba si una contraseña en texto plano coincide con su versión cifrada.
     * Es el método que deben usar los controladores para verificar credenciales.
     *
     * @param passwordPlano   Contraseña introducida por el usuario
     * @param passwordCifrada Contraseña almacenada en la base de datos (cifrada)
     * @return true si coinciden, false en caso contrario
     */
    public static boolean verificar(String passwordPlano, String passwordCifrada) {
        String descifrada = descifrar(passwordCifrada);
        if (descifrada == null) return false;
        return descifrada.equals(passwordPlano);
    }
}