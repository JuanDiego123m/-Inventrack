package com.inventario.view;

import com.inventario.controller.LoginController;
import com.inventario.util.DesignConstants;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Interfaz de login moderna del sistema de inventario
 * Diseño profesional con elementos visuales elegantes
 * 
 * @author Equipo de Desarrollo
 * @version 4.0 - Actualizado con DesignConstants
 */
public class ModernLoginFrame extends JFrame implements ILoginView {
    
    // Usando constantes unificadas
    private static final Color PRIMARY_COLOR = DesignConstants.PRIMARY_COLOR;
    private static final Color PRIMARY_DARK = DesignConstants.PRIMARY_DARK;
    private static final Color ACCENT_COLOR = DesignConstants.SUCCESS_COLOR;
    private static final Color BACKGROUND_START = DesignConstants.BACKGROUND_COLOR;
    private static final Color BACKGROUND_END = DesignConstants.CARD_COLOR;
    private static final Color TEXT_PRIMARY = DesignConstants.TEXT_PRIMARY;
    private static final Color TEXT_SECONDARY = DesignConstants.TEXT_SECONDARY;
    private static final Color ERROR_COLOR = DesignConstants.DANGER_COLOR;
    
    // Componentes
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCancelar;
    private JRadioButton rdbAdmin;
    private JRadioButton rdbVendedor;
    private ButtonGroup btnGroupRol;
    private JCheckBox chkRecordar;
    
    private LoginController controller;

    public ModernLoginFrame() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureFrame();
    }

    /**
     * Inicializa todos los componentes con diseño profesional
     */
    private void initializeComponents() {
        // Text Fields con diseño moderno
        txtUsuario = createModernTextField("Usuario");
        txtPassword = createModernPasswordField("Contraseña");
        
        // Botones con diseño moderno
        btnLogin = createModernButton("🔓 INICIAR SESIÓN", PRIMARY_COLOR);
        btnCancelar = createModernButton("✖ SALIR", ERROR_COLOR);
        
        // Radio Buttons con mejor estilo
        rdbAdmin = new JRadioButton("👔 Administrador");
        rdbAdmin.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rdbAdmin.setForeground(TEXT_PRIMARY);
        rdbAdmin.setBackground(Color.WHITE);
        rdbAdmin.setFocusPainted(false);
        
        rdbVendedor = new JRadioButton("🛒 Vendedor");
        rdbVendedor.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rdbVendedor.setForeground(TEXT_PRIMARY);
        rdbVendedor.setBackground(Color.WHITE);
        rdbVendedor.setFocusPainted(false);
        rdbVendedor.setSelected(true);
        
        btnGroupRol = new ButtonGroup();
        btnGroupRol.add(rdbAdmin);
        btnGroupRol.add(rdbVendedor);
        
        // Checkbox (oculto por ahora, no se usa)
        chkRecordar = new JCheckBox("Recordar usuario");
        chkRecordar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkRecordar.setForeground(TEXT_SECONDARY);
        chkRecordar.setBackground(Color.WHITE);
        chkRecordar.setFocusPainted(false);
        chkRecordar.setVisible(false); // Oculto por ahora
    }

    /**
     * Crea un text field moderno con placeholder
     */
    private JTextField createModernTextField(String placeholder) {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224), 2),
            new EmptyBorder(12, 16, 12, 16)
        ));
        field.setToolTipText(placeholder);
        field.setBackground(new Color(250, 250, 250));
        
        // Efecto focus
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                    new EmptyBorder(12, 16, 12, 16)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(224, 224, 224), 2),
                    new EmptyBorder(12, 16, 12, 16)
                ));
            }
        });
        
        return field;
    }

    /**
     * Crea un password field moderno
     */
    private JPasswordField createModernPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224), 2),
            new EmptyBorder(12, 16, 12, 16)
        ));
        field.setToolTipText(placeholder);
        field.setBackground(new Color(250, 250, 250));
        
        // Efecto focus
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                    new EmptyBorder(12, 16, 12, 16)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(224, 224, 224), 2),
                    new EmptyBorder(12, 16, 12, 16)
                ));
            }
        });
        
        return field;
    }

    /**
     * Crea un botón moderno con efecto hover y sombra
     */
    private JButton createModernButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(14, 40, 14, 40));
        
        // Efecto hover con transición suave
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (color == PRIMARY_COLOR) {
                    button.setBackground(PRIMARY_DARK);
                } else {
                    button.setBackground(color.darker());
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }

    /**
     * Configura el layout con diseño profesional
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel principal con fondo gradiente suave
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, BACKGROUND_START, 0, h, BACKGROUND_END);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        
        // Panel de login con sombra elegante
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(50, 50, 50, 50)
        ));
        
        // Título con icono mejorado
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblIcon = new JLabel("🏢");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblTitulo = new JLabel("Sistema de Inventario");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(PRIMARY_COLOR);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitulo = new JLabel("Gestión Profesional de Inventarios");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(TEXT_SECONDARY);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titlePanel.add(lblIcon);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        titlePanel.add(lblTitulo);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 8)));
        titlePanel.add(lblSubtitulo);
        
        // Separador visual
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setForeground(new Color(230, 230, 230));
        
        // Panel de campos con mejor espaciado
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBackground(Color.WHITE);
        fieldsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Usuario con icono
        JLabel lblUsuario = new JLabel("👤 Usuario");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUsuario.setForeground(TEXT_PRIMARY);
        lblUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        txtUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        
        // Contraseña con icono
        JLabel lblPassword = new JLabel("🔒 Contraseña");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPassword.setForeground(TEXT_PRIMARY);
        lblPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        txtPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        
        fieldsPanel.add(lblUsuario);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        fieldsPanel.add(txtUsuario);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        fieldsPanel.add(lblPassword);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        fieldsPanel.add(txtPassword);
        
        // Panel de roles con mejor diseño
        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        rolePanel.setBackground(Color.WHITE);
        rolePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rolePanel.add(rdbAdmin);
        rolePanel.add(rdbVendedor);
        
        // Panel de información de usuarios de prueba
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(245, 248, 250));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 230, 240), 1),
            new EmptyBorder(12, 15, 12, 15)
        ));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblInfo = new JLabel("ℹ️ Usuarios de Prueba:");
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblInfo.setForeground(TEXT_PRIMARY);
        lblInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblUser1 = new JLabel("• superadmin / superadmin123");
        lblUser1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblUser1.setForeground(TEXT_SECONDARY);
        lblUser1.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblUser2 = new JLabel("• admin / admin123");
        lblUser2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblUser2.setForeground(TEXT_SECONDARY);
        lblUser2.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblUser3 = new JLabel("• vendedor / vendedor123");
        lblUser3.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblUser3.setForeground(TEXT_SECONDARY);
        lblUser3.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        infoPanel.add(lblInfo);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        infoPanel.add(lblUser1);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        infoPanel.add(lblUser2);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        infoPanel.add(lblUser3);
        
        // Panel de botones con mejor espaciado
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCancelar);
        
        // Ensamblar todo con mejor espaciado
        loginPanel.add(titlePanel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 35)));
        loginPanel.add(separator);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        loginPanel.add(fieldsPanel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(rolePanel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(infoPanel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        loginPanel.add(buttonPanel);
        
        mainPanel.add(loginPanel);
        add(mainPanel, BorderLayout.CENTER);
        
        // Footer mejorado
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(BACKGROUND_START);
        footerPanel.setBorder(new EmptyBorder(12, 10, 12, 10));
        JLabel lblFooter = new JLabel("© 2025 Sistema de Inventario - Construcción de Software I");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFooter.setForeground(TEXT_SECONDARY);
        footerPanel.add(lblFooter);
        add(footerPanel, BorderLayout.SOUTH);
    }

    /**
     * Configura los eventos
     */
    private void setupEventHandlers() {
        controller = new LoginController(this);
        
        btnLogin.addActionListener(e -> controller.validarLogin());
        btnCancelar.addActionListener(e -> System.exit(0));
        txtPassword.addActionListener(e -> controller.validarLogin());
    }

    /**
     * Configura el frame con diseño profesional
     */
    private void configureFrame() {
        setTitle("Sistema de Inventario - Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Icono de la aplicación (opcional)
        try {
            setIconImage(null); // Aquí se puede agregar un icono personalizado
        } catch (Exception e) {
            // Ignorar si no es soportado
        }
    }

    // Getters para el controlador
    public String getUsuario() {
        return txtUsuario.getText().trim();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public boolean isAdminSelected() {
        return rdbAdmin.isSelected();
    }

    public boolean isRecordarUsuario() {
        return chkRecordar.isSelected();
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtPassword.setText("");
        txtUsuario.requestFocus();
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
