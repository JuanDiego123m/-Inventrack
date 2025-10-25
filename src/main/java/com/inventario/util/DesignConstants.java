package com.inventario.util;

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * Constantes de diseño unificadas para toda la aplicación
 * Garantiza consistencia visual en todos los módulos
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class DesignConstants {
    
    // ========== PALETA DE COLORES UNIFICADA ==========
    
    // Colores Primarios
    public static final Color PRIMARY_COLOR = new Color(41, 128, 185);      // Azul profesional
    public static final Color PRIMARY_DARK = new Color(25, 118, 210);       // Azul oscuro
    public static final Color PRIMARY_LIGHT = new Color(52, 152, 219);      // Azul claro
    
    // Colores Secundarios
    public static final Color SECONDARY_COLOR = new Color(52, 73, 94);      // Gris oscuro
    public static final Color SECONDARY_LIGHT = new Color(127, 140, 141);   // Gris medio
    
    // Colores Semánticos
    public static final Color SUCCESS_COLOR = new Color(46, 204, 113);      // Verde
    public static final Color SUCCESS_DARK = new Color(39, 174, 96);        // Verde oscuro
    public static final Color WARNING_COLOR = new Color(241, 196, 15);      // Amarillo
    public static final Color WARNING_DARK = new Color(243, 156, 18);       // Amarillo oscuro
    public static final Color DANGER_COLOR = new Color(231, 76, 60);        // Rojo
    public static final Color DANGER_DARK = new Color(192, 57, 43);         // Rojo oscuro
    public static final Color INFO_COLOR = new Color(52, 152, 219);         // Azul info
    
    // Colores de Fondo
    public static final Color BACKGROUND_COLOR = new Color(236, 240, 241);  // Gris muy claro
    public static final Color CARD_COLOR = Color.WHITE;                     // Blanco para cards
    public static final Color HOVER_COLOR = new Color(248, 249, 250);       // Gris hover
    public static final Color SELECTED_COLOR = new Color(174, 214, 241);    // Azul selección
    
    // Colores de Texto
    public static final Color TEXT_PRIMARY = new Color(33, 33, 33);         // Casi negro
    public static final Color TEXT_SECONDARY = new Color(117, 117, 117);    // Gris medio
    public static final Color TEXT_DISABLED = new Color(189, 195, 199);     // Gris claro
    
    // Colores de Bordes
    public static final Color BORDER_COLOR = new Color(224, 224, 224);      // Gris borde
    public static final Color BORDER_FOCUS = PRIMARY_COLOR;                 // Azul focus
    
    // Colores para Tablas
    public static final Color TABLE_HEADER_BG = PRIMARY_COLOR;              // Azul
    public static final Color TABLE_HEADER_FG = Color.WHITE;                // Blanco
    public static final Color TABLE_ROW_EVEN = Color.WHITE;                 // Blanco
    public static final Color TABLE_ROW_ODD = new Color(248, 249, 250);     // Gris claro (zebra)
    public static final Color TABLE_SELECTION_BG = PRIMARY_LIGHT;           // Azul claro
    public static final Color TABLE_SELECTION_FG = Color.WHITE;             // Blanco
    public static final Color TABLE_GRID = new Color(224, 224, 224);        // Gris grid
    
    // ========== TIPOGRAFÍA UNIFICADA ==========
    
    // Fuente base
    public static final String FONT_FAMILY = "Segoe UI";
    
    // Tamaños de fuente
    public static final Font FONT_TITLE = new Font(FONT_FAMILY, Font.BOLD, 28);
    public static final Font FONT_SUBTITLE = new Font(FONT_FAMILY, Font.BOLD, 20);
    public static final Font FONT_HEADING = new Font(FONT_FAMILY, Font.BOLD, 16);
    public static final Font FONT_LABEL = new Font(FONT_FAMILY, Font.BOLD, 13);
    public static final Font FONT_BUTTON = new Font(FONT_FAMILY, Font.BOLD, 14);
    public static final Font FONT_INPUT = new Font(FONT_FAMILY, Font.PLAIN, 13);
    public static final Font FONT_TABLE_HEADER = new Font(FONT_FAMILY, Font.BOLD, 13);
    public static final Font FONT_TABLE_CELL = new Font(FONT_FAMILY, Font.PLAIN, 12);
    public static final Font FONT_SMALL = new Font(FONT_FAMILY, Font.PLAIN, 11);
    
    // ========== DIMENSIONES Y ESPACIADO ==========
    
    // Sistema de espaciado (múltiplos de 8)
    public static final int SPACING_TINY = 4;
    public static final int SPACING_SMALL = 8;
    public static final int SPACING_MEDIUM = 16;
    public static final int SPACING_LARGE = 24;
    public static final int SPACING_XLARGE = 32;
    
    // Tamaños de componentes
    public static final int BUTTON_HEIGHT = 40;
    public static final int INPUT_HEIGHT = 36;
    public static final int TABLE_ROW_HEIGHT = 32;
    public static final int TABLE_HEADER_HEIGHT = 40;
    
    // Bordes redondeados
    public static final int BORDER_RADIUS_SMALL = 4;
    public static final int BORDER_RADIUS_MEDIUM = 8;
    public static final int BORDER_RADIUS_LARGE = 12;
    
    // ========== BORDES Y SOMBRAS ==========
    
    /**
     * Crea un borde con sombra sutil para cards
     */
    public static Border createCardBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(SPACING_MEDIUM, SPACING_MEDIUM, SPACING_MEDIUM, SPACING_MEDIUM)
        );
    }
    
    /**
     * Crea un borde con sombra para botones elevados
     */
    public static Border createElevatedBorder(Color color) {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 3, 0, color.darker()),
            BorderFactory.createLineBorder(color, 1)
        );
    }
    
    /**
     * Crea un borde para campos de entrada
     */
    public static Border createInputBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(SPACING_SMALL, SPACING_MEDIUM, SPACING_SMALL, SPACING_MEDIUM)
        );
    }
    
    /**
     * Crea un borde para campos de entrada con foco
     */
    public static Border createInputBorderFocus() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_FOCUS, 2),
            BorderFactory.createEmptyBorder(SPACING_SMALL, SPACING_MEDIUM, SPACING_SMALL, SPACING_MEDIUM)
        );
    }
    
    /**
     * Crea una sombra simulada (efecto drop shadow)
     */
    public static Border createShadowBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 2, new Color(0, 0, 0, 30)),
            BorderFactory.createLineBorder(BORDER_COLOR, 1)
        );
    }
    
    /**
     * Crea un borde para paneles de sección
     */
    public static Border createSectionBorder(String title) {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                title,
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                FONT_LABEL,
                SECONDARY_COLOR
            ),
            BorderFactory.createEmptyBorder(SPACING_SMALL, SPACING_MEDIUM, SPACING_SMALL, SPACING_MEDIUM)
        );
    }
    
    // ========== UTILIDADES ==========
    
    /**
     * Oscurece un color en un porcentaje
     */
    public static Color darken(Color color, double factor) {
        return new Color(
            Math.max((int)(color.getRed() * (1 - factor)), 0),
            Math.max((int)(color.getGreen() * (1 - factor)), 0),
            Math.max((int)(color.getBlue() * (1 - factor)), 0),
            color.getAlpha()
        );
    }
    
    /**
     * Aclara un color en un porcentaje
     */
    public static Color lighten(Color color, double factor) {
        return new Color(
            Math.min((int)(color.getRed() + (255 - color.getRed()) * factor), 255),
            Math.min((int)(color.getGreen() + (255 - color.getGreen()) * factor), 255),
            Math.min((int)(color.getBlue() + (255 - color.getBlue()) * factor), 255),
            color.getAlpha()
        );
    }
    
    /**
     * Crea un color con transparencia
     */
    public static Color withAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
}


