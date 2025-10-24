package com.inventario.view;

import com.inventario.model.Usuario;
import com.inventario.controller.RegistroUsuarioController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana principal del sistema de inventario
 * Implementa la navegación principal y el menú del sistema
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class MainFrame extends JFrame {
    
    private Usuario usuarioActual;
    private JMenuBar menuBar;
    private JMenu menuProductos;
    private JMenu menuVentas;
    private JMenu menuReportes;
    private JMenu menuUsuario;
    private JMenuItem menuItemRegistrarProducto;
    private JMenuItem menuItemListarProductos;
    private JMenuItem menuItemNuevaVenta;
    private JMenuItem menuItemGenerarFactura;
    private JMenuItem menuItemReporteInventario;
    private JMenuItem menuItemReporteVentas;
    private JMenuItem menuItemRegistrarUsuario;
    private JMenuItem menuItemCambiarPassword;
    private JMenuItem menuItemCerrarSesion;
    private JMenuItem menuItemSalir;
    
    private JPanel panelPrincipal;
    private JLabel lblBienvenida;
    private JButton btnProductos;
    private JButton btnVentas;
    private JButton btnFacturas;
    private JButton btnReportes;

    public MainFrame(Usuario usuario) {
        this.usuarioActual = usuario;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureFrame();
    }

    /**
     * Inicializa todos los componentes de la ventana principal
     */
    private void initializeComponents() {
        // Panel principal
        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(248, 248, 248));
        
        // Label de bienvenida
        lblBienvenida = new JLabel("Bienvenido: " + usuarioActual.getNombre(), JLabel.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));
        lblBienvenida.setForeground(new Color(0, 102, 204));
        
        // Botones principales
        btnProductos = crearBotonPrincipal("Gestión de Productos", "📦", new Color(0, 150, 136));
        btnVentas = crearBotonPrincipal("Procesar Ventas", "🛒", new Color(255, 152, 0));
        btnFacturas = crearBotonPrincipal("Generar Facturas", "🧾", new Color(156, 39, 176));
        btnReportes = crearBotonPrincipal("Reportes", "📊", new Color(76, 175, 80));
        
        // Configurar menú
        setupMenuBar();
    }

    /**
     * Crea un botón principal con estilo personalizado
     */
    private JButton crearBotonPrincipal(String texto, String icono, Color color) {
        JButton boton = new JButton("<html><center>" + icono + "<br>" + texto + "</center></html>");
        boton.setPreferredSize(new Dimension(150, 100));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
            }
        });
        
        return boton;
    }

    /**
     * Configura la barra de menú
     */
    private void setupMenuBar() {
        menuBar = new JMenuBar();
        
        // Menú Productos
        menuProductos = new JMenu("Productos");
        menuItemRegistrarProducto = new JMenuItem("Registrar Producto");
        menuItemListarProductos = new JMenuItem("Listar Productos");
        menuProductos.add(menuItemRegistrarProducto);
        menuProductos.add(menuItemListarProductos);
        
        // Menú Ventas
        menuVentas = new JMenu("Ventas");
        menuItemNuevaVenta = new JMenuItem("Nueva Venta");
        menuItemGenerarFactura = new JMenuItem("Generar Factura");
        menuVentas.add(menuItemNuevaVenta);
        menuVentas.add(menuItemGenerarFactura);
        
        // Menú Reportes
        menuReportes = new JMenu("Reportes");
        menuItemReporteInventario = new JMenuItem("Reporte de Inventario");
        menuItemReporteVentas = new JMenuItem("Reporte de Ventas");
        menuReportes.add(menuItemReporteInventario);
        menuReportes.add(menuItemReporteVentas);
        
        // Menú Usuario
        menuUsuario = new JMenu("Usuario");
        menuItemRegistrarUsuario = new JMenuItem("Registrar Usuario");
        menuItemCambiarPassword = new JMenuItem("Cambiar Contraseña");
        menuItemCerrarSesion = new JMenuItem("Cerrar Sesión");
        menuItemSalir = new JMenuItem("Salir");
        menuUsuario.add(menuItemRegistrarUsuario);
        menuUsuario.addSeparator();
        menuUsuario.add(menuItemCambiarPassword);
        menuUsuario.addSeparator();
        menuUsuario.add(menuItemCerrarSesion);
        menuUsuario.add(menuItemSalir);
        
        // Agregar menús a la barra
        menuBar.add(menuProductos);
        menuBar.add(menuVentas);
        menuBar.add(menuReportes);
        menuBar.add(menuUsuario);
        
        setJMenuBar(menuBar);
    }

    /**
     * Configura el layout de la ventana
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel superior con bienvenida
        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.setBackground(new Color(248, 248, 248));
        panelSuperior.add(lblBienvenida);
        
        // Panel central con botones
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(new Color(248, 248, 248));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Botones en grid 2x2
        gbc.gridx = 0; gbc.gridy = 0;
        panelCentral.add(btnProductos, gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        panelCentral.add(btnVentas, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panelCentral.add(btnFacturas, gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        panelCentral.add(btnReportes, gbc);
        
        // Panel inferior con información
        JPanel panelInferior = new JPanel(new FlowLayout());
        panelInferior.setBackground(new Color(240, 240, 240));
        panelInferior.add(new JLabel("Sistema de Inventario v1.0 - " + usuarioActual.getRol()));
        
        // Agregar paneles al frame
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    /**
     * Configura los manejadores de eventos
     */
    private void setupEventHandlers() {
        // Eventos de botones principales
        btnProductos.addActionListener(e -> abrirGestionProductos());
        btnVentas.addActionListener(e -> abrirProcesarVentas());
        btnFacturas.addActionListener(e -> abrirGenerarFacturas());
        btnReportes.addActionListener(e -> abrirReportes());
        
        // Eventos del menú
        menuItemRegistrarProducto.addActionListener(e -> abrirRegistrarProducto());
        menuItemListarProductos.addActionListener(e -> abrirListarProductos());
        menuItemNuevaVenta.addActionListener(e -> abrirNuevaVenta());
        menuItemGenerarFactura.addActionListener(e -> abrirGenerarFactura());
        menuItemReporteInventario.addActionListener(e -> abrirReporteInventario());
        menuItemReporteVentas.addActionListener(e -> abrirReporteVentas());
        menuItemRegistrarUsuario.addActionListener(e -> abrirRegistrarUsuario());
        menuItemCambiarPassword.addActionListener(e -> abrirCambiarPassword());
        menuItemCerrarSesion.addActionListener(e -> cerrarSesion());
        menuItemSalir.addActionListener(e -> salirSistema());
    }

    /**
     * Configura las propiedades del frame
     */
    private void configureFrame() {
        setTitle("Sistema de Inventario - " + usuarioActual.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    // Métodos para abrir diferentes ventanas (implementar según necesidad)
    private void abrirGestionProductos() {
        JOptionPane.showMessageDialog(this, "Abriendo gestión de productos...");
        // TODO: Implementar ProductoFrame
    }

    private void abrirProcesarVentas() {
        JOptionPane.showMessageDialog(this, "Abriendo procesar ventas...");
        // TODO: Implementar VentaFrame
    }

    private void abrirGenerarFacturas() {
        JOptionPane.showMessageDialog(this, "Abriendo generar facturas...");
        // TODO: Implementar FacturaFrame
    }

    private void abrirReportes() {
        JOptionPane.showMessageDialog(this, "Abriendo reportes...");
        // TODO: Implementar ReporteFrame
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
        abrirReportes();
    }

    private void abrirReporteVentas() {
        abrirReportes();
    }

    private void abrirRegistrarUsuario() {
        // Verificar permisos: solo SUPER_ADMIN puede crear usuarios
        if (!usuarioActual.esSuperAdministrador()) {
            JOptionPane.showMessageDialog(this, 
                "No tiene permisos para crear usuarios.\n" +
                "Solo el Super Administrador puede registrar nuevos usuarios.",
                "Sin Permisos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        SwingUtilities.invokeLater(() -> {
            RegistroUsuarioFrame registroFrame = new RegistroUsuarioFrame();
            // Pasar el usuario actual al controlador
            RegistroUsuarioController controller = new RegistroUsuarioController(registroFrame, usuarioActual);
            registroFrame.setVisible(true);
        });
    }

    private void abrirCambiarPassword() {
        JOptionPane.showMessageDialog(this, "Funcionalidad de cambiar contraseña en desarrollo...");
    }

    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro que desea cerrar sesión?", 
            "Cerrar Sesión", 
            JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            dispose();
            // Volver a la ventana de login
            SwingUtilities.invokeLater(() -> {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            });
        }
    }

    private void salirSistema() {
        int opcion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro que desea salir del sistema?", 
            "Salir", 
            JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}

