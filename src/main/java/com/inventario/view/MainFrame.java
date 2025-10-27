package com.inventario.view;

import com.inventario.model.Usuario;
import com.inventario.util.DesignConstants;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del sistema de inventario - Diseño Profesional
 * 
 * @author Equipo de Desarrollo
 * @version 3.0 - Actualizado con DesignConstants
 */
public class MainFrame extends JFrame {
    
    // Usando constantes unificadas
    private static final Color PRIMARY_COLOR = DesignConstants.PRIMARY_COLOR;
    private static final Color SECONDARY_COLOR = DesignConstants.SECONDARY_COLOR;
    private static final Color SUCCESS_COLOR = DesignConstants.SUCCESS_COLOR;
    private static final Color WARNING_COLOR = DesignConstants.WARNING_COLOR;
    private static final Color DANGER_COLOR = DesignConstants.DANGER_COLOR;
    private static final Color INFO_COLOR = DesignConstants.INFO_COLOR;
    private static final Color BACKGROUND_COLOR = DesignConstants.BACKGROUND_COLOR;
    private static final Color CARD_COLOR = DesignConstants.CARD_COLOR;
    
    // Tipografía consistente
    private static final Font TITLE_FONT = DesignConstants.FONT_TITLE;
    private static final Font SUBTITLE_FONT = new Font(DesignConstants.FONT_FAMILY, Font.PLAIN, 16);
    private static final Font BUTTON_FONT = DesignConstants.FONT_BUTTON;
    
    private Usuario usuarioActual;
    private JMenuBar menuBar;
    private JMenu menuProductos, menuVentas, menuReportes, menuUsuario;
    private JMenuItem menuItemRegistrarProducto, menuItemListarProductos;
    private JMenuItem menuItemNuevaVenta, menuItemGenerarFactura;
    private JMenuItem menuItemReporteInventario, menuItemReporteVentas;
    private JMenuItem menuItemRegistrarUsuario, menuItemCambiarPassword;
    private JMenuItem menuItemCerrarSesion, menuItemSalir;
    
    private JPanel panelPrincipal;
    private JLabel lblTitulo, lblSubtitulo;
    private JButton btnProductos, btnVentas, btnFacturas, btnReportes;

    public MainFrame(Usuario usuario) {
        this.usuarioActual = usuario;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureFrame();
    }

    private void initializeComponents() {
        // Panel principal con fondo moderno
        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(BACKGROUND_COLOR);
        
        // Título del sistema
        lblTitulo = new JLabel("Sistema de Inventario", JLabel.CENTER);
        lblTitulo.setFont(TITLE_FONT);
        lblTitulo.setForeground(SECONDARY_COLOR);
        
        // Subtítulo con nombre de usuario
        lblSubtitulo = new JLabel("Bienvenido, " + usuarioActual.getNombre() + " | " + 
                                   usuarioActual.getRol().getDescripcion(), JLabel.CENTER);
        lblSubtitulo.setFont(SUBTITLE_FONT);
        lblSubtitulo.setForeground(PRIMARY_COLOR);
        
        // Crear botones del menú principal
        btnProductos = crearBotonModerno("📦 Gestión de Productos", PRIMARY_COLOR);
        btnVentas = crearBotonModerno("🛒 Procesar Ventas", SUCCESS_COLOR);
        btnFacturas = crearBotonModerno("📄 Generar Facturas", INFO_COLOR);
        btnReportes = crearBotonModerno("📊 Reportes", WARNING_COLOR);
        
        setupMenuBar();
    }

    /**
     * Crea un botón con diseño moderno y profesional
     */
    private JButton crearBotonModerno(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(BUTTON_FONT);
        boton.setPreferredSize(new Dimension(280, 120));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setOpaque(true); // ← IMPORTANTE: Asegura que el fondo se pinte
        boton.setContentAreaFilled(true); // ← IMPORTANTE: Asegura que el área se rellene
        
        // Bordes con efecto de sombra (elevación)
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 4, 4, DesignConstants.withAlpha(Color.BLACK, 40)),
                BorderFactory.createLineBorder(color.darker(), 2)
            ),
            BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            private Color originalColor = color;
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.darker());
                // Sombra más pronunciada en hover
                boton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 6, 6, DesignConstants.withAlpha(Color.BLACK, 60)),
                        BorderFactory.createLineBorder(color.darker().darker(), 3)
                    ),
                    BorderFactory.createEmptyBorder(15, 25, 15, 25)
                ));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(originalColor);
                // Sombra normal
                boton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 4, 4, DesignConstants.withAlpha(Color.BLACK, 40)),
                        BorderFactory.createLineBorder(originalColor.darker(), 2)
                    ),
                    BorderFactory.createEmptyBorder(15, 25, 15, 25)
                ));
            }
        });
        
        return boton;
    }

    private void setupMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBackground(PRIMARY_COLOR); // Azul profesional para mejor contraste
        menuBar.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        
        // Menú Productos
        menuProductos = crearMenu("Productos");
        menuItemRegistrarProducto = crearMenuItem("Registrar Producto");
        menuItemListarProductos = crearMenuItem("Listar Productos");
        menuProductos.add(menuItemRegistrarProducto);
        menuProductos.add(menuItemListarProductos);
        
        // Menú Ventas
        menuVentas = crearMenu("Ventas");
        menuItemNuevaVenta = crearMenuItem("Nueva Venta");
        menuItemGenerarFactura = crearMenuItem("Generar Factura");
        menuVentas.add(menuItemNuevaVenta);
        menuVentas.add(menuItemGenerarFactura);
        
        // Menú Reportes
        menuReportes = crearMenu("Reportes");
        menuItemReporteInventario = crearMenuItem("Reporte de Inventario");
        menuItemReporteVentas = crearMenuItem("Reporte de Ventas");
        menuReportes.add(menuItemReporteInventario);
        menuReportes.add(menuItemReporteVentas);
        
        // Menú Usuario
        menuUsuario = crearMenu("Usuario");
        menuItemRegistrarUsuario = crearMenuItem("Registrar Usuario");
        menuItemCambiarPassword = crearMenuItem("Cambiar Contraseña");
        menuItemCerrarSesion = crearMenuItem("Cerrar Sesión");
        menuItemSalir = crearMenuItem("Salir");
        
        menuUsuario.add(menuItemRegistrarUsuario);
        menuUsuario.addSeparator();
        menuUsuario.add(menuItemCambiarPassword);
        menuUsuario.addSeparator();
        menuUsuario.add(menuItemCerrarSesion);
        menuUsuario.add(menuItemSalir);
        
        menuBar.add(menuProductos);
        menuBar.add(menuVentas);
        menuBar.add(menuReportes);
        menuBar.add(menuUsuario);
        
        setJMenuBar(menuBar);
    }

    private JMenu crearMenu(String texto) {
        JMenu menu = new JMenu(texto);
        menu.setForeground(Color.WHITE);
        menu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menu.setOpaque(true);
        menu.setBackground(PRIMARY_COLOR);
        
        // Padding para mejor visualización
        menu.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        
        // Efecto hover
        menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menu.setBackground(PRIMARY_COLOR.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menu.setBackground(PRIMARY_COLOR);
            }
        });
        
        return menu;
    }

    private JMenuItem crearMenuItem(String texto) {
        JMenuItem menuItem = new JMenuItem(texto);
        menuItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        menuItem.setBackground(Color.WHITE);
        menuItem.setForeground(SECONDARY_COLOR);
        menuItem.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        
        // Efecto hover para menu items
        menuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuItem.setBackground(PRIMARY_COLOR);
                menuItem.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuItem.setBackground(Color.WHITE);
                menuItem.setForeground(SECONDARY_COLOR);
            }
        });
        
        return menuItem;
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel superior con títulos y sombra
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(CARD_COLOR);
        panelSuperior.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 3, 0, PRIMARY_COLOR),
                BorderFactory.createMatteBorder(0, 0, 3, 0, DesignConstants.withAlpha(Color.BLACK, 30))
            ),
            BorderFactory.createEmptyBorder(25, 20, 25, 20)
        ));
        
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelSuperior.add(lblTitulo);
        panelSuperior.add(Box.createRigidArea(new Dimension(0, 10)));
        panelSuperior.add(lblSubtitulo);
        
        // Panel central con botones en grid
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(BACKGROUND_COLOR);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // Primera fila
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCentral.add(crearCardBoton(btnProductos), gbc);
        
        gbc.gridx = 1;
        panelCentral.add(crearCardBoton(btnVentas), gbc);
        
        // Segunda fila
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCentral.add(crearCardBoton(btnFacturas), gbc);
        
        gbc.gridx = 1;
        panelCentral.add(crearCardBoton(btnReportes), gbc);
        
        // Panel inferior con información
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setBackground(SECONDARY_COLOR);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel lblFooter = new JLabel("Sistema de Inventario v2.0 | © 2025");
        lblFooter.setForeground(Color.WHITE);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        panelInferior.add(lblFooter);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    /**
     * Crea un panel card para envolver un botón
     */
    private JPanel crearCardBoton(JButton boton) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.add(boton, BorderLayout.CENTER);
        return card;
    }

    private void setupEventHandlers() {
        // Botones principales
        btnProductos.addActionListener(e -> abrirGestionProductos());
        btnVentas.addActionListener(e -> abrirProcesarVentas());
        btnFacturas.addActionListener(e -> abrirGenerarFacturas());
        btnReportes.addActionListener(e -> abrirReportes());
        
        // Menú items
        menuItemRegistrarProducto.addActionListener(e -> abrirRegistrarProducto());
        menuItemListarProductos.addActionListener(e -> abrirListarProductos());
        menuItemNuevaVenta.addActionListener(e -> abrirNuevaVenta());
        menuItemGenerarFactura.addActionListener(e -> abrirGenerarFactura());
        menuItemReporteInventario.addActionListener(e -> abrirReporteInventario());
        menuItemReporteVentas.addActionListener(e -> abrirReporteVentas());
        menuItemRegistrarUsuario.addActionListener(e -> abrirRegistrarUsuario());
        menuItemCambiarPassword.addActionListener(e -> abrirCambiarPassword());
        menuItemCerrarSesion.addActionListener(e -> cerrarSesion());
        menuItemSalir.addActionListener(e -> salir());
    }

    private void configureFrame() {
        setTitle("Sistema de Inventario - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    // Métodos de navegación
    private void abrirGestionProductos() {
        SwingUtilities.invokeLater(() -> {
            ProductoFrame productoFrame = new ProductoFrame(this);
            productoFrame.setVisible(true);
            this.setVisible(false);
        });
    }

    private void abrirProcesarVentas() {
        SwingUtilities.invokeLater(() -> {
            VentaFrame ventaFrame = new VentaFrame(usuarioActual, this);
            ventaFrame.setVisible(true);
            this.setVisible(false);
        });
    }

    private void abrirGenerarFacturas() {
        SwingUtilities.invokeLater(() -> {
            FacturaFrame facturaFrame = new FacturaFrame(usuarioActual, this);
            facturaFrame.setVisible(true);
            this.setVisible(false);
        });
    }

    private void abrirReportes() {
        abrirReportes(0); // Por defecto abre Dashboard
    }
    
    /**
     * Abre la ventana de reportes en una pestaña específica
     * @param tabIndex Índice de la pestaña (0=Dashboard, 1=Ventas, 2=Productos, 3=Inventario, 4=Top Ventas)
     */
    private void abrirReportes(int tabIndex) {
        SwingUtilities.invokeLater(() -> {
            ReportesFrame reportesFrame = new ReportesFrame(usuarioActual, this, tabIndex);
            reportesFrame.setVisible(true);
            this.setVisible(false);
        });
    }

    private void abrirRegistrarProducto() {
        abrirGestionProductos();
    }

    private void abrirListarProductos() {
        abrirGestionProductos();
    }

    private void abrirNuevaVenta() {
        abrirProcesarVentas();
    }

    private void abrirGenerarFactura() {
        abrirGenerarFacturas();
    }

    private void abrirReporteInventario() {
        abrirReportes(3); // Índice 3 = Pestaña de Inventario
    }

    private void abrirReporteVentas() {
        abrirReportes(1); // Índice 1 = Pestaña de Ventas
    }

    private void abrirRegistrarUsuario() {
        if (!usuarioActual.esSuperAdministrador()) {
            JOptionPane.showMessageDialog(this,
                "Solo el Super Administrador puede registrar nuevos usuarios.",
                "Acceso Denegado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        SwingUtilities.invokeLater(() -> {
            RegistroUsuarioFrame registroFrame = new RegistroUsuarioFrame(usuarioActual);
            registroFrame.setVisible(true);
        });
    }

    private void abrirCambiarPassword() {
        SwingUtilities.invokeLater(() -> {
            CambiarPasswordFrame cambiarPasswordFrame = new CambiarPasswordFrame(usuarioActual);
            cambiarPasswordFrame.setVisible(true);
        });
    }

    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de cerrar sesión?",
            "Cerrar Sesión",
            JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            dispose();
            SwingUtilities.invokeLater(() -> {
                ModernLoginFrame loginFrame = new ModernLoginFrame();
                loginFrame.setVisible(true);
            });
        }
    }

    private void salir() {
        int opcion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de salir del sistema?",
            "Salir",
            JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    /**
     * Muestra el frame principal en modo maximizado (usado por botón regresar)
     */
    public void mostrar() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }
}