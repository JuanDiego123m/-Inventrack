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
public class LoginFrame extends JFrame {
    
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

    public LoginFrame() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupMenuBar();
        configureFrame();
    }

    /**
     * Inicializa todos los componentes gráficos
     */
    private void initializeComponents() {
        // Labels
        lblTitulo = new JLabel("Sistema de Inventario", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 102, 204));
        
        lblUsuario = new JLabel("Usuario:");
        lblPassword = new JLabel("Contraseña:");
        
        // Text Fields
        txtUsuario = new JTextField(15);
        txtPassword = new JPasswordField(15);
        
        // Buttons
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBackground(new Color(0, 102, 204));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(204, 0, 0));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        
        // Radio Buttons
        rdbRecordar = new JRadioButton("Recordar usuario");
        rdbAdmin = new JRadioButton("Administrador");
        rdbVendedor = new JRadioButton("Vendedor");
        
        btnGroupRol = new ButtonGroup();
        btnGroupRol.add(rdbAdmin);
        btnGroupRol.add(rdbVendedor);
        rdbVendedor.setSelected(true); // Por defecto vendedor
        
        // Menu Bar
        menuBar = new JMenuBar();
        menuArchivo = new JMenu("Archivo");
        menuAyuda = new JMenu("Ayuda");
        menuItemSalir = new JMenuItem("Salir");
        menuItemAcerca = new JMenuItem("Acerca de");
    }

    /**
     * Configura el layout de la interfaz
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(248, 248, 248));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
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
        panelRol.setBackground(new Color(248, 248, 248));
        panelRol.add(new JLabel("Tipo de usuario:"));
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
        panelBotones.setBackground(new Color(248, 248, 248));
        panelBotones.add(btnLogin);
        panelBotones.add(btnCancelar);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(panelBotones, gbc);
        
        add(panelPrincipal, BorderLayout.CENTER);
    }

    /**
     * Configura los manejadores de eventos
     */
    private void setupEventHandlers() {
        controller = new LoginController(this);
        
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.validarLogin();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        // Enter en password field
        txtPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.validarLogin();
            }
        });
    }

    /**
     * Configura la barra de menú
     */
    private void setupMenuBar() {
        menuArchivo.add(menuItemSalir);
        menuAyuda.add(menuItemAcerca);
        
        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);
        
        setJMenuBar(menuBar);
        
        // Eventos del menú
        menuItemSalir.addActionListener(e -> System.exit(0));
        menuItemAcerca.addActionListener(e -> mostrarAcercaDe());
    }

    /**
     * Configura las propiedades del frame
     */
    private void configureFrame() {
        setTitle("Sistema de Inventario - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Icono de la aplicación
        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
        } catch (Exception e) {
            // Si no hay icono, continuar sin él
        }
    }

    /**
     * Muestra información acerca de la aplicación
     */
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
        return rdbRecordar.isSelected();
    }

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

