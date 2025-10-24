package com.inventario.view;

import com.inventario.controller.RegistroUsuarioController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Interfaz para el registro de nuevos usuarios
 * Implementa todos los componentes gráficos requeridos
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class RegistroUsuarioFrame extends JFrame {
    
    // Componentes gráficos requeridos
    private JLabel lblTitulo;
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
    private JButton btnCancelar;
    private JButton btnLimpiar;
    
    private JRadioButton rdbAdmin;
    private JRadioButton rdbVendedor;
    private ButtonGroup btnGroupRol;
    
    private JMenuBar menuBar;
    private JMenu menuArchivo;
    private JMenu menuAyuda;
    private JMenuItem menuItemSalir;
    private JMenuItem menuItemAcerca;
    
    private RegistroUsuarioController controller;

    public RegistroUsuarioFrame() {
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
        lblTitulo = new JLabel("Registro de Usuario", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 102, 204));
        
        lblUsername = new JLabel("Usuario:");
        lblPassword = new JLabel("Contraseña:");
        lblConfirmPassword = new JLabel("Confirmar Contraseña:");
        lblNombre = new JLabel("Nombre Completo:");
        lblEmail = new JLabel("Email:");
        lblRol = new JLabel("Tipo de Usuario:");
        
        // Text Fields
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        txtConfirmPassword = new JPasswordField(20);
        txtNombre = new JTextField(20);
        txtEmail = new JTextField(20);
        
        // Configurar tooltips
        txtUsername.setToolTipText("Ingrese un nombre de usuario único");
        txtPassword.setToolTipText("Mínimo 6 caracteres");
        txtConfirmPassword.setToolTipText("Repita la contraseña");
        txtEmail.setToolTipText("Ingrese un email válido");
        
        // Buttons
        btnRegistrar = new JButton("Registrar Usuario");
        btnRegistrar.setBackground(new Color(76, 175, 80));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorder(BorderFactory.createRaisedBevelBorder());
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(244, 67, 54));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorder(BorderFactory.createRaisedBevelBorder());
        
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBackground(new Color(158, 158, 158));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setBorder(BorderFactory.createRaisedBevelBorder());
        
        // Radio Buttons para rol
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
        gbc.anchor = GridBagConstraints.WEST;
        
        // Título
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(lblTitulo, gbc);
        
        // Username
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(lblUsername, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtUsername, gbc);
        
        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(lblPassword, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtPassword, gbc);
        
        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(lblConfirmPassword, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtConfirmPassword, gbc);
        
        // Nombre
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(lblNombre, gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtNombre, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(lblEmail, gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtEmail, gbc);
        
        // Rol
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(lblRol, gbc);
        
        JPanel panelRol = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRol.setBackground(new Color(248, 248, 248));
        panelRol.add(rdbAdmin);
        panelRol.add(rdbVendedor);
        
        gbc.gridx = 1; gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(panelRol, gbc);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(248, 248, 248));
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCancelar);
        
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(panelBotones, gbc);
        
        add(panelPrincipal, BorderLayout.CENTER);
    }

    /**
     * Configura los manejadores de eventos
     */
    private void setupEventHandlers() {
        controller = new RegistroUsuarioController(this);
        
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.registrarUsuario();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
        
        // Enter en confirm password field
        txtConfirmPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.registrarUsuario();
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
        menuItemSalir.addActionListener(e -> dispose());
        menuItemAcerca.addActionListener(e -> mostrarAcercaDe());
    }

    /**
     * Configura las propiedades del frame
     */
    private void configureFrame() {
        setTitle("Registro de Usuario - Sistema de Inventario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Muestra información acerca de la aplicación
     */
    private void mostrarAcercaDe() {
        JOptionPane.showMessageDialog(this,
            "Sistema de Inventario v1.0\n" +
            "Registro de Usuarios\n" +
            "Desarrollado para Construcción de Software I\n" +
            "Ingeniería de Software - Segundo Semestre\n\n" +
            "© 2024 Equipo de Desarrollo",
            "Acerca de",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Limpia el formulario
     */
    public void limpiarFormulario() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        txtNombre.setText("");
        txtEmail.setText("");
        rdbVendedor.setSelected(true);
        txtUsername.requestFocus();
    }

    // Getters para el controlador
    public String getUsername() {
        return txtUsername.getText().trim();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public String getConfirmPassword() {
        return new String(txtConfirmPassword.getPassword());
    }

    public String getNombre() {
        return txtNombre.getText().trim();
    }

    public String getEmail() {
        return txtEmail.getText().trim();
    }

    public boolean isAdminSelected() {
        return rdbAdmin.isSelected();
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
