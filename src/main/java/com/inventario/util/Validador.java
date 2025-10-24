package com.inventario.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Clase de utilidades para validaciones comunes
 * Implementa validaciones reutilizables para el sistema
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class Validador {
    
    // Patrones de validación
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"
    );
    
    private static final Pattern USERNAME_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_]{3,20}$"
    );
    
    private static final Pattern NOMBRE_PATTERN = Pattern.compile(
        "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{2,50}$"
    );

    /**
     * Valida si un email tiene formato válido
     * 
     * @param email Email a validar
     * @return true si el email es válido
     */
    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Valida si un nombre de usuario tiene formato válido
     * 
     * @param username Nombre de usuario a validar
     * @return true si el username es válido
     */
    public static boolean validarUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return USERNAME_PATTERN.matcher(username.trim()).matches();
    }

    /**
     * Valida si un nombre tiene formato válido
     * 
     * @param nombre Nombre a validar
     * @return true si el nombre es válido
     */
    public static boolean validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        return NOMBRE_PATTERN.matcher(nombre.trim()).matches();
    }

    /**
     * Valida si una contraseña cumple con los requisitos mínimos
     * 
     * @param password Contraseña a validar
     * @return true si la contraseña es válida
     */
    public static boolean validarPassword(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        return true;
    }

    /**
     * Valida si un precio es válido (mayor a cero)
     * 
     * @param precio Precio a validar
     * @return true si el precio es válido
     */
    public static boolean validarPrecio(String precio) {
        if (precio == null || precio.trim().isEmpty()) {
            return false;
        }
        
        try {
            BigDecimal valor = new BigDecimal(precio.trim());
            return valor.compareTo(BigDecimal.ZERO) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Valida si una cantidad es válida (entero positivo)
     * 
     * @param cantidad Cantidad a validar
     * @return true si la cantidad es válida
     */
    public static boolean validarCantidad(String cantidad) {
        if (cantidad == null || cantidad.trim().isEmpty()) {
            return false;
        }
        
        try {
            int valor = Integer.parseInt(cantidad.trim());
            return valor >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Valida si un campo no está vacío
     * 
     * @param campo Campo a validar
     * @return true si el campo no está vacío
     */
    public static boolean validarCampoObligatorio(String campo) {
        return campo != null && !campo.trim().isEmpty();
    }

    /**
     * Valida si un campo tiene la longitud correcta
     * 
     * @param campo Campo a validar
     * @param longitudMinima Longitud mínima
     * @param longitudMaxima Longitud máxima
     * @return true si el campo tiene la longitud correcta
     */
    public static boolean validarLongitud(String campo, int longitudMinima, int longitudMaxima) {
        if (campo == null) {
            return false;
        }
        int longitud = campo.trim().length();
        return longitud >= longitudMinima && longitud <= longitudMaxima;
    }

    /**
     * Valida si un número está en un rango específico
     * 
     * @param numero Número a validar
     * @param minimo Valor mínimo
     * @param maximo Valor máximo
     * @return true si el número está en el rango
     */
    public static boolean validarRango(int numero, int minimo, int maximo) {
        return numero >= minimo && numero <= maximo;
    }

    /**
     * Valida si un número decimal está en un rango específico
     * 
     * @param numero Número a validar
     * @param minimo Valor mínimo
     * @param maximo Valor máximo
     * @return true si el número está en el rango
     */
    public static boolean validarRangoDecimal(BigDecimal numero, BigDecimal minimo, BigDecimal maximo) {
        if (numero == null || minimo == null || maximo == null) {
            return false;
        }
        return numero.compareTo(minimo) >= 0 && numero.compareTo(maximo) <= 0;
    }

    /**
     * Valida si una fecha está en un formato válido (dd/mm/yyyy)
     * 
     * @param fecha Fecha a validar
     * @return true si la fecha es válida
     */
    public static boolean validarFecha(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            return false;
        }
        
        try {
            String[] partes = fecha.trim().split("/");
            if (partes.length != 3) {
                return false;
            }
            
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int año = Integer.parseInt(partes[2]);
            
            return validarRango(dia, 1, 31) && 
                   validarRango(mes, 1, 12) && 
                   validarRango(año, 1900, 2100);
                   
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Valida si un teléfono tiene formato válido
     * 
     * @param telefono Teléfono a validar
     * @return true si el teléfono es válido
     */
    public static boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }
        
        // Remover espacios y guiones
        String telefonoLimpio = telefono.trim().replaceAll("[\\s-]", "");
        
        // Validar que solo contenga números y tenga entre 7 y 15 dígitos
        return telefonoLimpio.matches("^\\d{7,15}$");
    }

    /**
     * Valida si un código postal tiene formato válido
     * 
     * @param codigoPostal Código postal a validar
     * @return true si el código postal es válido
     */
    public static boolean validarCodigoPostal(String codigoPostal) {
        if (codigoPostal == null || codigoPostal.trim().isEmpty()) {
            return false;
        }
        
        String codigo = codigoPostal.trim();
        return codigo.matches("^\\d{5,6}$");
    }

    /**
     * Valida si una dirección es válida
     * 
     * @param direccion Dirección a validar
     * @return true si la dirección es válida
     */
    public static boolean validarDireccion(String direccion) {
        if (direccion == null || direccion.trim().isEmpty()) {
            return false;
        }
        
        return validarLongitud(direccion, 10, 100);
    }

    /**
     * Valida si un ID es válido (entero positivo)
     * 
     * @param id ID a validar
     * @return true si el ID es válido
     */
    public static boolean validarId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        
        try {
            int valor = Integer.parseInt(id.trim());
            return valor > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
