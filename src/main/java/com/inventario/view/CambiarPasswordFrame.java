package com.inventario.view;

import com.inventario.controller.CambiarPasswordController;
import com.inventario.model.Usuario;
import com.inventario.util.DesignConstants;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Interfaz moderna para cambiar la contraseña del usuario
 * Diseño profesional con DesignConstants
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class CambiarPasswordFrame extends JFrame {
    
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JLabel lblPasswordActual;
    private JLabel lblPasswordNueva;
    private JLabel lblConfirmarPassword;
    private JLabel lblUsuario;
    
    private JPasswordField txtPasswordActual;
    private JPasswordField txtPasswordNueva;
    private JPasswordField txtConfirmarPassword;
    
    private JButton btnCambiar;
    private JButton btnCancelar;
    private JCheckBox chkMostrarPassword;
    
    private CambiarPasswordController controller;
    private Usuario usuarioActual;

    public CambiarPasswordFrame(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureFrame();
    }

    /**
     * Inicializa todos los componentes gráficos
     */
    private void initializeComponents() {
        // Título principal
        lblTitulo = new JLabel("Cambiar Contraseña", JLabel.CENTER);
        lblTitulo.setFont(DesignConstants.FONT_TITLE);
        lblTitulo.setForeground(DesignConstants.PRIMARY_COLOR);
        
        // Subtítulo
        lblSubtitulo = new JLabel("Actualice su contraseña de acceso al sistema", JLabel.CENTER);
        lblSubtitulo.setFont(DesignConstants.FONT_SMALL);
        lblSubtitulo.setForeground(DesignConstants.TEXT_SECONDARY);
        
        // Información del usuario
        lblUsuario = new JLabel("Usuario: " + usuarioActual.getUsername(), JLabel.CENTER);
        lblUsuario.setFont(DesignConstants.FONT_LABEL);
        lblUsuario.setForeground(DesignConstants.INFO_COLOR);
        
        // Labels
        lblPasswordActual = crearLabel("Contraseña Actual:");
        lblPasswordNueva = crearLabel("Nueva Contraseña:");
        lblConfirmarPassword = crearLabel("Confirmar Nueva:");
        
        // Password Fields
        txtPasswordActual = crearPasswordField("Ingrese su contraseña actual");
        txtPasswordNueva = crearPasswordField("Mínimo 6 caracteres");
        txtConfirmarPassword = crearPasswordField("Repita la nueva contraseña");
        
        // Checkbox para mostrar contraseñas
        chkMostrarPassword = new JCheckBox("Mostrar contraseñas");
        chkMostrarPassword.setFont(DesignConstants.FONT_SMALL);
        chkMostrarPassword.setForeground(DesignConstants.TEXT_SECONDARY);
        chkMostrarPassword.setBackground(Color.WHITE);
        chkMostrarPassword.setFocusPainted(false);
        chkMostrarPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Botones con tamaño completo
        btnCambiar = crearBoton("🔒 Cambiar Contraseña", DesignConstants.SUCCESS_COLOR, DesignConstants.SUCCESS_DARK);
        btnCancelar = crearBoton("← Regresar", DesignConstants.SECONDARY_COLOR, DesignConstants.SECONDARY_LIGHT);
    }

    /**
     * Crea un label con estilo consistente
     */
    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(DesignConstants.FONT_LABEL);
        label.setForeground(DesignConstants.TEXT_PRIMARY);
        return label;
    }

    /**
     * Crea un password field con diseño moderno
     */
    private JPasswordField crearPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(DesignConstants.FONT_INPUT);
        passwordField.setBorder(DesignConstants.createInputBorder());
        passwordField.setPreferredSize(new Dimension(300, DesignConstants.INPUT_HEIGHT));
        passwordField.setEchoChar((char) 0);
        passwordField.setForeground(DesignConstants.TEXT_DISABLED);
        passwordField.setText(placeholder);
        
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setEchoChar('●');
                    passwordField.setForeground(DesignConstants.TEXT_PRIMARY);
                }
                passwordField.setBorder(DesignConstants.createInputBorderFocus());
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setEchoChar((char) 0);
                    passwordField.setText(placeholder);
                    passwordField.setForeground(DesignConstants.TEXT_DISABLED);
                }
                passwordField.setBorder(DesignConstants.createInputBorder());
            }
        });
        
        return passwordField;
    }

    /**
     * Crea un botón con estilo moderno y efectos hover
     */
    private JButton crearBoton(String texto, Color bgColor, Color hoverColor) {
        JButton button = new JButton(texto);
        button.setFont(DesignConstants.FONT_BUTTON);
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        button.setMinimumSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(200, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    /**
     * Configura el layout con diseño tipo card
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(DesignConstants.BACKGROUND_COLOR);
        
        // Panel principal tipo card
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(DesignConstants.SPACING_XLARGE, DesignConstants.SPACING_XLARGE, 
                           DesignConstants.SPACING_XLARGE, DesignConstants.SPACING_XLARGE),
            DesignConstants.createShadowBorder()
        ));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(0, 0, DesignConstants.SPACING_LARGE, 0));
        
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(lblTitulo);
        headerPanel.add(Box.createVerticalStrut(DesignConstants.SPACING_SMALL));
        headerPanel.add(lblSubtitulo);
        headerPanel.add(Box.createVerticalStrut(DesignConstants.SPACING_SMALL));
        headerPanel.add(lblUsuario);
        
        // Panel del formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(DesignConstants.SPACING_SMALL, DesignConstants.SPACING_MEDIUM, 
                               DesignConstants.SPACING_SMALL, DesignConstants.SPACING_MEDIUM);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Contraseña actual
        agregarCampo(formPanel, gbc, lblPasswordActual, txtPasswordActual, row++);
        
        // Separador
        JSeparator separator1 = new JSeparator();
        separator1.setForeground(DesignConstants.BORDER_COLOR);
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(DesignConstants.SPACING_MEDIUM, 0, DesignConstants.SPACING_MEDIUM, 0);
        formPanel.add(separator1, gbc);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(DesignConstants.SPACING_SMALL, DesignConstants.SPACING_MEDIUM, 
                               DesignConstants.SPACING_SMALL, DesignConstants.SPACING_MEDIUM);
        
        // Nueva contraseña
        agregarCampo(formPanel, gbc, lblPasswordNueva, txtPasswordNueva, row++);
        
        // Confirmar contraseña
        agregarCampo(formPanel, gbc, lblConfirmarPassword, txtConfirmarPassword, row++);
        
        // Checkbox mostrar contraseñas
        gbc.gridx = 1;
        gbc.gridy = row++;
        gbc.gridwidth = 1;
        formPanel.add(chkMostrarPassword, gbc);
        
        // Panel de botones reorganizado
        JPanel botonesPanel = new JPanel();
        botonesPanel.setLayout(new BoxLayout(botonesPanel, BoxLayout.Y_AXIS));
        botonesPanel.setBackground(Color.WHITE);
        botonesPanel.setBorder(new EmptyBorder(DesignConstants.SPACING_LARGE, 0, DesignConstants.SPACING_MEDIUM, 0));
        
        // Primera fila - Botón principal
        JPanel filaBtnPrincipal = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        filaBtnPrincipal.setBackground(Color.WHITE);
        filaBtnPrincipal.add(btnCambiar);
        
        // Segunda fila - Botón secundario
        JPanel filaBtnSecundario = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        filaBtnSecundario.setBackground(Color.WHITE);
        filaBtnSecundario.add(btnCancelar);
        
        botonesPanel.add(filaBtnPrincipal);
        botonesPanel.add(Box.createVerticalStrut(DesignConstants.SPACING_SMALL));
        botonesPanel.add(filaBtnSecundario);
        
        // Ensamblar el card
        cardPanel.add(headerPanel);
        cardPanel.add(Box.createVerticalStrut(DesignConstants.SPACING_SMALL));
        cardPanel.add(formPanel);
        cardPanel.add(botonesPanel);
        
        // Panel de advertencia con más espacio
        JPanel warningPanel = crearPanelAdvertencia();
        cardPanel.add(Box.createVerticalStrut(DesignConstants.SPACING_LARGE));
        cardPanel.add(warningPanel);
        cardPanel.add(Box.createVerticalStrut(DesignConstants.SPACING_MEDIUM));
        
        // Panel contenedor con margen
        JPanel containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setBackground(DesignConstants.BACKGROUND_COLOR);
        containerPanel.add(cardPanel);
        
        add(containerPanel, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = crearFooter();
        add(footerPanel, BorderLayout.SOUTH);
    }

    /**
     * Agrega un campo al formulario
     */
    private void agregarCampo(JPanel panel, GridBagConstraints gbc, JLabel label, JComponent campo, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 1;
        panel.add(campo, gbc);
    }

    /**
     * Crea el panel de advertencia
     */
    private JPanel crearPanelAdvertencia() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(DesignConstants.lighten(DesignConstants.WARNING_COLOR, 0.8));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DesignConstants.WARNING_COLOR, 2),
            new EmptyBorder(DesignConstants.SPACING_MEDIUM, DesignConstants.SPACING_LARGE, 
                           DesignConstants.SPACING_MEDIUM, DesignConstants.SPACING_LARGE)
        ));
        panel.setMaximumSize(new Dimension(600, 140));
        
        JLabel lblIcono = new JLabel("⚠️ Requisitos de Seguridad", JLabel.CENTER);
        lblIcono.setFont(DesignConstants.FONT_HEADING);
        lblIcono.setForeground(DesignConstants.WARNING_DARK);
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblInfo1 = new JLabel("• Mínimo 6 caracteres");
        lblInfo1.setFont(DesignConstants.FONT_INPUT);
        lblInfo1.setForeground(DesignConstants.TEXT_PRIMARY);
        lblInfo1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblInfo2 = new JLabel("• Debe ser diferente a la contraseña actual");
        lblInfo2.setFont(DesignConstants.FONT_INPUT);
        lblInfo2.setForeground(DesignConstants.TEXT_PRIMARY);
        lblInfo2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblInfo3 = new JLabel("• Recuerde su nueva contraseña para futuros accesos");
        lblInfo3.setFont(DesignConstants.FONT_INPUT);
        lblInfo3.setForeground(DesignConstants.TEXT_PRIMARY);
        lblInfo3.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(lblIcono);
        panel.add(Box.createVerticalStrut(DesignConstants.SPACING_SMALL));
        panel.add(lblInfo1);
        panel.add(Box.createVerticalStrut(2));
        panel.add(lblInfo2);
        panel.add(Box.createVerticalStrut(2));
        panel.add(lblInfo3);
        
        return panel;
    }

    /**
     * Crea el panel de footer
     */
    private JPanel crearFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(DesignConstants.SECONDARY_COLOR);
        footer.setBorder(new EmptyBorder(DesignConstants.SPACING_SMALL, 0, DesignConstants.SPACING_SMALL, 0));
        
        JLabel lblFooter = new JLabel("Sistema de Inventario v2.0 | Seguridad de Cuentas");
        lblFooter.setFont(DesignConstants.FONT_SMALL);
        lblFooter.setForeground(Color.WHITE);
        footer.add(lblFooter);
        
        return footer;
    }

    /**
     * Configura los manejadores de eventos
     */
    private void setupEventHandlers() {
        controller = new CambiarPasswordController(this, usuarioActual);
        
        btnCambiar.addActionListener(e -> controller.cambiarPassword());
        btnCancelar.addActionListener(e -> dispose());
        
        // Enter para cambiar
        txtConfirmarPassword.addActionListener(e -> controller.cambiarPassword());
        
        // Checkbox para mostrar/ocultar contraseñas
        chkMostrarPassword.addActionListener(e -> {
            char echoChar = chkMostrarPassword.isSelected() ? (char) 0 : '●';
            
            // Solo cambiar si no es un placeholder
            if (!String.valueOf(txtPasswordActual.getPassword()).equals("Ingrese su contraseña actual") &&
                txtPasswordActual.getPassword().length > 0) {
                txtPasswordActual.setEchoChar(echoChar);
            }
            
            if (!String.valueOf(txtPasswordNueva.getPassword()).equals("Mínimo 6 caracteres") &&
                txtPasswordNueva.getPassword().length > 0) {
                txtPasswordNueva.setEchoChar(echoChar);
            }
            
            if (!String.valueOf(txtConfirmarPassword.getPassword()).equals("Repita la nueva contraseña") &&
                txtConfirmarPassword.getPassword().length > 0) {
                txtConfirmarPassword.setEchoChar(echoChar);
            }
        });
    }

    /**
     * Configura las propiedades del frame
     */
    private void configureFrame() {
        setTitle("Cambiar Contraseña - Sistema de Inventario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 750);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    /**
     * Limpia el formulario
     */
    public void limpiarFormulario() {
        restaurarPlaceholder(txtPasswordActual, "Ingrese su contraseña actual");
        restaurarPlaceholder(txtPasswordNueva, "Mínimo 6 caracteres");
        restaurarPlaceholder(txtConfirmarPassword, "Repita la nueva contraseña");
        chkMostrarPassword.setSelected(false);
        txtPasswordActual.requestFocus();
    }

    /**
     * Restaura el placeholder de un campo de contraseña
     */
    private void restaurarPlaceholder(JPasswordField field, String placeholder) {
        field.setEchoChar((char) 0);
        field.setText(placeholder);
        field.setForeground(DesignConstants.TEXT_DISABLED);
    }

    // ========== Getters para el controlador ==========
    
    public String getPasswordActual() {
        String text = String.valueOf(txtPasswordActual.getPassword());
        return text.equals("Ingrese su contraseña actual") ? "" : text;
    }

    public String getPasswordNueva() {
        String text = String.valueOf(txtPasswordNueva.getPassword());
        return text.equals("Mínimo 6 caracteres") ? "" : text;
    }

    public String getConfirmarPassword() {
        String text = String.valueOf(txtConfirmarPassword.getPassword());
        return text.equals("Repita la nueva contraseña") ? "" : text;
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void cerrar() {
        dispose();
    }
}

