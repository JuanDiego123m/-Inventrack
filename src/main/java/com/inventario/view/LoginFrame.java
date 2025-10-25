package com.inventario.view;

import com.inventario.controller.LoginController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Interfaz de login del sistema de inventario
 * Implementa todos los componentes gráficos requeridos
 *
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class LoginFrame extends JFrame implements ILoginView {

    // Componentes gráficos requeridos
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblPassword;

    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    private JButton btnLogin;
    private JButton btnCancelar;

    private JRadioButton rdbRecordar;
    private JRadioButton rdbAdmin;
    private JRadioButton rdbVendedor;
    private ButtonGroup btnGroupRol;

    private JMenuBar menuBar;
    private JMenu menuArchivo;
    private JMenu menuAyuda;
    private JMenuItem menuItemSalir;
    private JMenuItem menuItemAcerca;

    private LoginController controller;

    // Colores accesibles y modernos
    private static final Color AZUL_BASE = new Color(33, 103, 183);
    private static final Color AZUL_HOVER = new Color(22, 76, 135);
    private static final Color ROJO_BASE = new Color(200, 40, 40);
    private static final Color ROJO_HOVER = new Color(142, 0, 0);
    private static final Color FONDO = new Color(245, 247, 250);
    private static final Color FONDO_PANEL = new Color(255, 255, 255);
    private static final Color TEXTO_PRIMARIO = new Color(44, 44, 44);

    public LoginFrame() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupMenuBar();
        configureFrame();
    }

    private void initializeComponents() {
        // Labels
        lblTitulo = new JLabel("Sistema de Inventario", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(AZUL_BASE);

        lblUsuario = new JLabel("Usuario:");
        lblUsuario.setForeground(TEXTO_PRIMARIO);
        lblPassword = new JLabel("Contraseña:");
        lblPassword.setForeground(TEXTO_PRIMARIO);

        // Text Fields
        txtUsuario = new JTextField(15);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtUsuario.setBackground(new Color(235, 240, 250));

        txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtPassword.setBackground(new Color(235, 240, 250));

        // Botones
        btnLogin = new JButton("Iniciar Sesión");
        estilizarBoton(btnLogin, AZUL_BASE, AZUL_HOVER);

        btnCancelar = new JButton("Cancelar");
        estilizarBoton(btnCancelar, ROJO_BASE, ROJO_HOVER);

        // Radio Buttons
        rdbRecordar = new JRadioButton("Recordar usuario");
        rdbRecordar.setForeground(TEXTO_PRIMARIO);

        rdbAdmin = new JRadioButton("Administrador");
        rdbAdmin.setForeground(TEXTO_PRIMARIO);
        rdbVendedor = new JRadioButton("Vendedor");
        rdbVendedor.setForeground(TEXTO_PRIMARIO);

        btnGroupRol = new ButtonGroup();
        btnGroupRol.add(rdbAdmin);
        btnGroupRol.add(rdbVendedor);
        rdbVendedor.setSelected(true);

        // Menu Bar
        menuBar = new JMenuBar();
        menuArchivo = new JMenu("Archivo");
        menuAyuda = new JMenu("Ayuda");
        menuItemSalir = new JMenuItem("Salir");
        menuItemAcerca = new JMenuItem("Acerca de");
    }

    // Botones planos, contrastados y con hover
    private void estilizarBoton(JButton boton, Color base, Color hover) {
        boton.setBackground(base);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        boton.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Hover manual
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { boton.setBackground(hover); }
            public void mouseExited(java.awt.event.MouseEvent evt) { boton.setBackground(base); }
        });
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createLineBorder(new Color(182, 189, 200), 2, true));
        panelPrincipal.setBackground(FONDO_PANEL);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        // Título
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(lblTitulo, gbc);

        // Usuario
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(lblUsuario, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtUsuario, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(lblPassword, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtPassword, gbc);

        // Radio buttons para rol
        JPanel panelRol = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRol.setBackground(FONDO_PANEL);
        JLabel lblRol = new JLabel("Tipo de usuario:");
        lblRol.setForeground(TEXTO_PRIMARIO);
        panelRol.add(lblRol);
        panelRol.add(rdbAdmin);
        panelRol.add(rdbVendedor);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(panelRol, gbc);

        // Recordar usuario
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(rdbRecordar, gbc);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(FONDO_PANEL);
        panelBotones.add(btnLogin);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(panelBotones, gbc);

        add(panelPrincipal, BorderLayout.CENTER);
        getContentPane().setBackground(FONDO);
    }

    private void setupEventHandlers() {
        controller = new LoginController(this);

        btnLogin.addActionListener(e -> controller.validarLogin());

        btnCancelar.addActionListener(e -> System.exit(0));

        // Enter en password field
        txtPassword.addActionListener(e -> controller.validarLogin());
    }

    private void setupMenuBar() {
        menuArchivo.add(menuItemSalir);
        menuAyuda.add(menuItemAcerca);

        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);

        setJMenuBar(menuBar);

        menuItemSalir.addActionListener(e -> System.exit(0));
        menuItemAcerca.addActionListener(e -> mostrarAcercaDe());
    }

    private void configureFrame() {
        setTitle("Sistema de Inventario - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(430, 370);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
        } catch (Exception e) {
            // Si no hay icono, continuar sin él
        }
    }

    private void mostrarAcercaDe() {
        JOptionPane.showMessageDialog(this,
                "Sistema de Inventario v1.0\n" +
                        "Desarrollado para Construcción de Software I\n" +
                        "Ingeniería de Software - Segundo Semestre\n\n" +
                        "© 2024 Equipo de Desarrollo",
                "Acerca de",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Getters para el controlador
    public String getUsuario() { return txtUsuario.getText().trim(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public boolean isAdminSelected() { return rdbAdmin.isSelected(); }
    public boolean isRecordarUsuario() { return rdbRecordar.isSelected(); }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtPassword.setText("");
        txtUsuario.requestFocus();
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}

