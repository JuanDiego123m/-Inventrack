package com.inventario.view;

import com.inventario.controller.RegistroUsuarioController;
import com.inventario.model.Usuario;
import com.inventario.util.DesignConstants;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Interfaz moderna para el registro de nuevos usuarios
 * Diseño profesional con DesignConstants
 * 
 * @author Equipo de Desarrollo
 * @version 2.0 - Rediseño Profesional
 */
public class RegistroUsuarioFrame extends JFrame {
    
    // Componentes gráficos
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblConfirmPassword;
    private JLabel lblNombre;
    private JLabel lblEmail;
    private JLabel lblRol;
    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JTextField txtNombre;
    private JTextField txtEmail;
    
    private JButton btnRegistrar;
    private JButton btnLimpiar;
    private JButton btnCancelar;
    private JButton btnRegresar;
    
    private JRadioButton rdbAdmin;
    private JRadioButton rdbVendedor;
    private ButtonGroup btnGroupRol;
    
    private RegistroUsuarioController controller;
    private Usuario usuarioActual;

    public RegistroUsuarioFrame(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureFrame();
    }

    /**
     * Inicializa todos los componentes gráficos con diseño moderno
     */
    private void initializeComponents() {
        // Título principal
        lblTitulo = new JLabel("Registro de Usuario", JLabel.CENTER);
        lblTitulo.setFont(DesignConstants.FONT_TITLE);
        lblTitulo.setForeground(DesignConstants.PRIMARY_COLOR);
        
        // Subtítulo
        lblSubtitulo = new JLabel("Complete la información del nuevo usuario", JLabel.CENTER);
        lblSubtitulo.setFont(DesignConstants.FONT_SMALL);
        lblSubtitulo.setForeground(DesignConstants.TEXT_SECONDARY);
        
        // Labels con diseño mejorado
        lblUsername = crearLabel("Usuario:");
        lblPassword = crearLabel("Contraseña:");
        lblConfirmPassword = crearLabel("Confirmar Contraseña:");
        lblNombre = crearLabel("Nombre Completo:");
        lblEmail = crearLabel("Correo Electrónico:");
        lblRol = crearLabel("Tipo de Usuario:");
        
        // Text Fields con diseño moderno
        txtUsername = crearTextField("Ej: juan_perez");
        txtPassword = crearPasswordField("Mínimo 6 caracteres");
        txtConfirmPassword = crearPasswordField("Repita la contraseña");
        txtNombre = crearTextField("Ej: Juan Pérez");
        txtEmail = crearTextField("ejemplo@correo.com");
        
        // Botones con diseño moderno y tamaño más compacto
        btnRegistrar = crearBoton("✓ Registrar", DesignConstants.SUCCESS_COLOR, DesignConstants.SUCCESS_DARK);
        btnLimpiar = crearBoton("⟲ Limpiar", DesignConstants.WARNING_COLOR, DesignConstants.WARNING_DARK);
        btnCancelar = crearBoton("✕ Cancelar", DesignConstants.DANGER_COLOR, DesignConstants.DANGER_DARK);
        btnRegresar = crearBoton("← Regresar", DesignConstants.SECONDARY_COLOR, DesignConstants.SECONDARY_LIGHT);
        
        // Radio Buttons con diseño mejorado
        rdbAdmin = new JRadioButton("👤 Administrador");
        rdbAdmin.setFont(DesignConstants.FONT_INPUT);
        rdbAdmin.setForeground(DesignConstants.TEXT_PRIMARY);
        rdbAdmin.setBackground(Color.WHITE);
        rdbAdmin.setFocusPainted(false);
        rdbAdmin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        rdbVendedor = new JRadioButton("🛒 Vendedor");
        rdbVendedor.setFont(DesignConstants.FONT_INPUT);
        rdbVendedor.setForeground(DesignConstants.TEXT_PRIMARY);
        rdbVendedor.setBackground(Color.WHITE);
        rdbVendedor.setFocusPainted(false);
        rdbVendedor.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnGroupRol = new ButtonGroup();
        btnGroupRol.add(rdbAdmin);
        btnGroupRol.add(rdbVendedor);
        rdbVendedor.setSelected(true);
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
     * Crea un text field con diseño moderno
     */
    private JTextField crearTextField(String placeholder) {
        JTextField textField = new JTextField(20);
        textField.setFont(DesignConstants.FONT_INPUT);
        textField.setBorder(DesignConstants.createInputBorder());
        textField.setPreferredSize(new Dimension(300, DesignConstants.INPUT_HEIGHT));
        
        // Placeholder text
        textField.setForeground(DesignConstants.TEXT_DISABLED);
        textField.setText(placeholder);
        
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(DesignConstants.TEXT_PRIMARY);
                }
                textField.setBorder(DesignConstants.createInputBorderFocus());
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(DesignConstants.TEXT_DISABLED);
                }
                textField.setBorder(DesignConstants.createInputBorder());
            }
        });
        
        return textField;
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
        button.setPreferredSize(new Dimension(130, 36)); // Botones más compactos
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
        
        // Header - Título y subtítulo
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(0, 0, DesignConstants.SPACING_LARGE, 0));
        
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(lblTitulo);
        headerPanel.add(Box.createVerticalStrut(DesignConstants.SPACING_SMALL));
        headerPanel.add(lblSubtitulo);
        
        // Panel del formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(DesignConstants.SPACING_SMALL, DesignConstants.SPACING_MEDIUM, 
                               DesignConstants.SPACING_SMALL, DesignConstants.SPACING_MEDIUM);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Username
        agregarCampo(formPanel, gbc, lblUsername, txtUsername, row++);
        
        // Password
        agregarCampo(formPanel, gbc, lblPassword, txtPassword, row++);
        
        // Confirm Password
        agregarCampo(formPanel, gbc, lblConfirmPassword, txtConfirmPassword, row++);
        
        // Nombre
        agregarCampo(formPanel, gbc, lblNombre, txtNombre, row++);
        
        // Email
        agregarCampo(formPanel, gbc, lblEmail, txtEmail, row++);
        
        // Separador visual
        JSeparator separator = new JSeparator();
        separator.setForeground(DesignConstants.BORDER_COLOR);
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(DesignConstants.SPACING_MEDIUM, 0, DesignConstants.SPACING_MEDIUM, 0);
        formPanel.add(separator, gbc);
        
        // Rol
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(DesignConstants.SPACING_SMALL, DesignConstants.SPACING_MEDIUM, 
                               DesignConstants.SPACING_SMALL, DesignConstants.SPACING_MEDIUM);
        formPanel.add(lblRol, gbc);
        
        JPanel panelRol = new JPanel(new FlowLayout(FlowLayout.LEFT, DesignConstants.SPACING_MEDIUM, 0));
        panelRol.setBackground(Color.WHITE);
        panelRol.add(rdbVendedor);
        panelRol.add(rdbAdmin);
        
        gbc.gridx = 1;
        gbc.gridy = row++;
        formPanel.add(panelRol, gbc);
        
        // Panel de botones reorganizado en dos filas
        JPanel botonesPanel = new JPanel();
        botonesPanel.setLayout(new BoxLayout(botonesPanel, BoxLayout.Y_AXIS));
        botonesPanel.setBackground(Color.WHITE);
        botonesPanel.setBorder(new EmptyBorder(DesignConstants.SPACING_LARGE, 0, 0, 0));
        
        // Primera fila - Acción principal
        JPanel filaPrincipal = new JPanel(new FlowLayout(FlowLayout.CENTER, DesignConstants.SPACING_SMALL, 0));
        filaPrincipal.setBackground(Color.WHITE);
        filaPrincipal.add(btnRegistrar);
        filaPrincipal.add(btnLimpiar);
        
        // Segunda fila - Acciones secundarias
        JPanel filaSecundaria = new JPanel(new FlowLayout(FlowLayout.CENTER, DesignConstants.SPACING_SMALL, 0));
        filaSecundaria.setBackground(Color.WHITE);
        filaSecundaria.add(btnRegresar);
        filaSecundaria.add(btnCancelar);
        
        botonesPanel.add(filaPrincipal);
        botonesPanel.add(Box.createVerticalStrut(DesignConstants.SPACING_SMALL));
        botonesPanel.add(filaSecundaria);
        
        // Ensamblar el card
        cardPanel.add(headerPanel);
        cardPanel.add(formPanel);
        cardPanel.add(botonesPanel);
        
        // Panel contenedor con margen
        JPanel containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setBackground(DesignConstants.BACKGROUND_COLOR);
        containerPanel.add(cardPanel);
        
        add(containerPanel, BorderLayout.CENTER);
        
        // Footer con información
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
     * Crea el panel de footer
     */
    private JPanel crearFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(DesignConstants.SECONDARY_COLOR);
        footer.setBorder(new EmptyBorder(DesignConstants.SPACING_SMALL, 0, DesignConstants.SPACING_SMALL, 0));
        
        JLabel lblFooter = new JLabel("Sistema de Inventario v2.0 | Solo Super Administradores pueden crear usuarios");
        lblFooter.setFont(DesignConstants.FONT_SMALL);
        lblFooter.setForeground(Color.WHITE);
        footer.add(lblFooter);
        
        return footer;
    }

    /**
     * Configura los manejadores de eventos
     */
    private void setupEventHandlers() {
        controller = new RegistroUsuarioController(this, usuarioActual);
        
        btnRegistrar.addActionListener(e -> controller.registrarUsuario());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnRegresar.addActionListener(e -> dispose());
        btnCancelar.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea cancelar?\nSe perderán los datos ingresados.",
                "Confirmar Cancelación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (confirmacion == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
        
        // Enter para registrar
        txtConfirmPassword.addActionListener(e -> controller.registrarUsuario());
    }

    /**
     * Configura las propiedades del frame
     */
    private void configureFrame() {
        setTitle("Registro de Usuario - Sistema de Inventario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 700);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Limpia el formulario
     */
    public void limpiarFormulario() {
        // Limpiar y restaurar placeholders
        restaurarPlaceholder(txtUsername, "Ej: juan_perez");
        restaurarPlaceholder(txtNombre, "Ej: Juan Pérez");
        restaurarPlaceholder(txtEmail, "ejemplo@correo.com");
        
        // Passwords
        txtPassword.setText("");
        txtPassword.setEchoChar((char) 0);
        txtPassword.setForeground(DesignConstants.TEXT_DISABLED);
        txtPassword.setText("Mínimo 6 caracteres");
        
        txtConfirmPassword.setText("");
        txtConfirmPassword.setEchoChar((char) 0);
        txtConfirmPassword.setForeground(DesignConstants.TEXT_DISABLED);
        txtConfirmPassword.setText("Repita la contraseña");
        
        rdbVendedor.setSelected(true);
        txtUsername.requestFocus();
    }

    /**
     * Restaura el placeholder de un campo
     */
    private void restaurarPlaceholder(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(DesignConstants.TEXT_DISABLED);
    }

    // ========== Getters para el controlador ==========
    
    public String getUsername() {
        String text = txtUsername.getText().trim();
        return text.equals("Ej: juan_perez") ? "" : text;
    }

    public String getPassword() {
        String text = String.valueOf(txtPassword.getPassword());
        return text.equals("Mínimo 6 caracteres") ? "" : text;
    }

    public String getConfirmPassword() {
        String text = String.valueOf(txtConfirmPassword.getPassword());
        return text.equals("Repita la contraseña") ? "" : text;
    }

    public String getNombre() {
        String text = txtNombre.getText().trim();
        return text.equals("Ej: Juan Pérez") ? "" : text;
    }

    public String getEmail() {
        String text = txtEmail.getText().trim();
        return text.equals("ejemplo@correo.com") ? "" : text;
    }

    public boolean isAdminSelected() {
        return rdbAdmin.isSelected();
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
