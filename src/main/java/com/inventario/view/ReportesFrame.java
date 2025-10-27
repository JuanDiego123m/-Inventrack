package com.inventario.view;

import com.inventario.controller.ReportesController;
import com.inventario.model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Interfaz para visualizar reportes y estadísticas
 * Muestra información sobre ventas, productos e inventario
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class ReportesFrame extends JFrame {
    
    // Colores modernos
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color INFO_COLOR = new Color(52, 152, 219);
    
    // Componentes principales
    private JTabbedPane tabbedPane;
    private Usuario usuarioActual;
    private MainFrame mainFrame;
    private ReportesController controller;
    private JButton btnRegresar;
    
    // Panel de estadísticas generales
    private JPanel panelEstadisticas;
    private JLabel lblTotalVentas;
    private JLabel lblVentasHoy;
    private JLabel lblProductosStock;
    private JLabel lblProductosBajoStock;
    private JLabel lblIngresoTotal;
    private JLabel lblPromedioVenta;
    
    // Panel de ventas
    private JPanel panelVentas;
    private JTable tablaVentas;
    private DefaultTableModel modeloVentas;
    private JScrollPane scrollVentas;
    private JButton btnActualizarVentas;
    private JButton btnExportarVentas;
    
    // Panel de productos
    private JPanel panelProductos;
    private JTable tablaProductos;
    private DefaultTableModel modeloProductos;
    private JScrollPane scrollProductos;
    private JButton btnActualizarProductos;
    private JButton btnExportarProductos;
    
    // Panel de inventario
    private JPanel panelInventario;
    private JTable tablaInventario;
    private DefaultTableModel modeloInventario;
    private JScrollPane scrollInventario;
    private JButton btnActualizarInventario;
    private JProgressBar progressStock;
    
    // Panel de productos más vendidos
    private JPanel panelTopProductos;
    private JTable tablaTopProductos;
    private DefaultTableModel modeloTopProductos;
    private JScrollPane scrollTopProductos;

    public ReportesFrame(Usuario usuario, MainFrame mainFrame) {
        this(usuario, mainFrame, 0); // Por defecto muestra Dashboard
    }
    
    /**
     * Constructor con pestaña específica
     * @param usuario Usuario actual
     * @param mainFrame Frame principal
     * @param tabIndex Índice de la pestaña a mostrar (0=Dashboard, 1=Ventas, 2=Productos, 3=Inventario, 4=Top Ventas)
     */
    public ReportesFrame(Usuario usuario, MainFrame mainFrame, int tabIndex) {
        this.usuarioActual = usuario;
        this.mainFrame = mainFrame;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureFrame();
        
        // Seleccionar la pestaña especificada
        if (tabIndex >= 0 && tabIndex < tabbedPane.getTabCount()) {
            tabbedPane.setSelectedIndex(tabIndex);
        }
        
        cargarDatos();
    }

    /**
     * Inicializa todos los componentes
     */
    private void initializeComponents() {
        // TabbedPane principal
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        // Inicializar paneles
        initPanelEstadisticas();
        initPanelVentas();
        initPanelProductos();
        initPanelInventario();
        initPanelTopProductos();
    }

    /**
     * Inicializa el panel de estadísticas generales
     */
    private void initPanelEstadisticas() {
        panelEstadisticas = new JPanel();
        panelEstadisticas.setLayout(new GridLayout(3, 2, 20, 20));
        panelEstadisticas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelEstadisticas.setBackground(Color.WHITE);
        
        // Crear cards de estadísticas
        lblTotalVentas = createStatCard("📊 Total Ventas", "0", SUCCESS_COLOR);
        lblVentasHoy = createStatCard("📅 Ventas Hoy", "0", INFO_COLOR);
        lblProductosStock = createStatCard("📦 Productos en Stock", "0", PRIMARY_COLOR);
        lblProductosBajoStock = createStatCard("⚠️ Productos Bajo Stock", "0", WARNING_COLOR);
        lblIngresoTotal = createStatCard("💰 Ingreso Total", "$0", SUCCESS_COLOR);
        lblPromedioVenta = createStatCard("📈 Promedio por Venta", "$0", INFO_COLOR);
        
        panelEstadisticas.add(createStatPanel(lblTotalVentas, SUCCESS_COLOR));
        panelEstadisticas.add(createStatPanel(lblVentasHoy, INFO_COLOR));
        panelEstadisticas.add(createStatPanel(lblProductosStock, PRIMARY_COLOR));
        panelEstadisticas.add(createStatPanel(lblProductosBajoStock, WARNING_COLOR));
        panelEstadisticas.add(createStatPanel(lblIngresoTotal, SUCCESS_COLOR));
        panelEstadisticas.add(createStatPanel(lblPromedioVenta, INFO_COLOR));
    }

    /**
     * Crea un card de estadística
     */
    private JLabel createStatCard(String titulo, String valor, Color color) {
        JLabel label = new JLabel(String.format("<html><div style='text-align: center;'>" +
            "<div style='font-size: 14px; color: #7f8c8d;'>%s</div>" +
            "<div style='font-size: 32px; font-weight: bold; color: rgb(%d,%d,%d); margin-top: 10px;'>%s</div>" +
            "</div></html>", titulo, color.getRed(), color.getGreen(), color.getBlue(), valor));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    /**
     * Crea un panel para una estadística
     */
    private JPanel createStatPanel(JLabel label, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(20, 10, 20, 10)
        ));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Inicializa el panel de ventas
     */
    private void initPanelVentas() {
        panelVentas = new JPanel(new BorderLayout(10, 10));
        panelVentas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelVentas.setBackground(Color.WHITE);
        
        // Tabla de ventas
        String[] columnasVentas = {"ID", "Fecha", "Vendedor", "Items", "Total"};
        modeloVentas = new DefaultTableModel(columnasVentas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaVentas = new JTable(modeloVentas);
        tablaVentas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaVentas.setRowHeight(30);
        tablaVentas.setGridColor(new Color(224, 224, 224));
        tablaVentas.setSelectionBackground(PRIMARY_COLOR.brighter());
        tablaVentas.setSelectionForeground(Color.WHITE);
        
        // Aplicar renderizador personalizado al encabezado
        aplicarRenderizadorEncabezado(tablaVentas, PRIMARY_COLOR);
        
        // Aplicar zebra striping
        aplicarZebraStriping(tablaVentas);
        
        scrollVentas = new JScrollPane(tablaVentas);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(Color.WHITE);
        
        btnActualizarVentas = createModernButton("🔄 Actualizar", PRIMARY_COLOR);
        btnExportarVentas = createModernButton("📄 Exportar", SUCCESS_COLOR);
        
        panelBotones.add(btnActualizarVentas);
        panelBotones.add(btnExportarVentas);
        
        panelVentas.add(scrollVentas, BorderLayout.CENTER);
        panelVentas.add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Inicializa el panel de productos
     */
    private void initPanelProductos() {
        panelProductos = new JPanel(new BorderLayout(10, 10));
        panelProductos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelProductos.setBackground(Color.WHITE);
        
        // Tabla de productos
        String[] columnasProductos = {"Código", "Nombre", "Categoría", "Stock", "Precio", "Valor Total"};
        modeloProductos = new DefaultTableModel(columnasProductos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaProductos = new JTable(modeloProductos);
        tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaProductos.setRowHeight(30);
        tablaProductos.setGridColor(new Color(224, 224, 224));
        tablaProductos.setSelectionBackground(PRIMARY_COLOR.brighter());
        tablaProductos.setSelectionForeground(Color.WHITE);
        
        // Aplicar renderizador personalizado al encabezado
        aplicarRenderizadorEncabezado(tablaProductos, PRIMARY_COLOR);
        
        // Aplicar zebra striping
        aplicarZebraStriping(tablaProductos);
        
        scrollProductos = new JScrollPane(tablaProductos);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(Color.WHITE);
        
        btnActualizarProductos = createModernButton("🔄 Actualizar", PRIMARY_COLOR);
        btnExportarProductos = createModernButton("📄 Exportar", SUCCESS_COLOR);
        
        panelBotones.add(btnActualizarProductos);
        panelBotones.add(btnExportarProductos);
        
        panelProductos.add(scrollProductos, BorderLayout.CENTER);
        panelProductos.add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Inicializa el panel de inventario
     */
    private void initPanelInventario() {
        panelInventario = new JPanel(new BorderLayout(10, 10));
        panelInventario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelInventario.setBackground(Color.WHITE);
        
        // Tabla de inventario
        String[] columnasInventario = {"Código", "Producto", "Stock", "Estado", "Acción Sugerida"};
        modeloInventario = new DefaultTableModel(columnasInventario, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaInventario = new JTable(modeloInventario);
        tablaInventario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaInventario.setRowHeight(30);
        tablaInventario.setGridColor(new Color(224, 224, 224));
        tablaInventario.setSelectionBackground(PRIMARY_COLOR.brighter());
        tablaInventario.setSelectionForeground(Color.WHITE);
        
        // Aplicar renderizador personalizado al encabezado
        aplicarRenderizadorEncabezado(tablaInventario, PRIMARY_COLOR);
        
        // Aplicar zebra striping
        aplicarZebraStriping(tablaInventario);
        
        scrollInventario = new JScrollPane(tablaInventario);
        
        // Panel superior con barra de progreso
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JLabel lblProgreso = new JLabel("Estado General del Inventario:");
        lblProgreso.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        progressStock = new JProgressBar(0, 100);
        progressStock.setStringPainted(true);
        progressStock.setForeground(SUCCESS_COLOR);
        progressStock.setPreferredSize(new Dimension(400, 30));
        
        panelSuperior.add(lblProgreso, BorderLayout.WEST);
        panelSuperior.add(progressStock, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(Color.WHITE);
        
        btnActualizarInventario = createModernButton("🔄 Actualizar", PRIMARY_COLOR);
        
        panelBotones.add(btnActualizarInventario);
        
        panelInventario.add(panelSuperior, BorderLayout.NORTH);
        panelInventario.add(scrollInventario, BorderLayout.CENTER);
        panelInventario.add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Inicializa el panel de productos más vendidos
     */
    private void initPanelTopProductos() {
        panelTopProductos = new JPanel(new BorderLayout(10, 10));
        panelTopProductos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelTopProductos.setBackground(Color.WHITE);
        
        // Título
        JLabel lblTitulo = new JLabel("🏆 Top 10 Productos Más Vendidos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(PRIMARY_COLOR);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Tabla de top productos
        String[] columnasTop = {"#", "Código", "Producto", "Unidades Vendidas", "Ingresos Generados"};
        modeloTopProductos = new DefaultTableModel(columnasTop, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaTopProductos = new JTable(modeloTopProductos);
        tablaTopProductos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaTopProductos.setRowHeight(35);
        tablaTopProductos.setGridColor(new Color(224, 224, 224));
        tablaTopProductos.setSelectionBackground(SUCCESS_COLOR.brighter());
        tablaTopProductos.setSelectionForeground(Color.WHITE);
        
        // Aplicar renderizador personalizado al encabezado (color verde)
        aplicarRenderizadorEncabezado(tablaTopProductos, SUCCESS_COLOR);
        
        // Aplicar zebra striping
        aplicarZebraStriping(tablaTopProductos);
        
        scrollTopProductos = new JScrollPane(tablaTopProductos);
        
        panelTopProductos.add(lblTitulo, BorderLayout.NORTH);
        panelTopProductos.add(scrollTopProductos, BorderLayout.CENTER);
    }

    /**
     * Crea un botón moderno
     */
    private JButton createModernButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }

    /**
     * Aplica zebra striping (filas alternadas) a la tabla
     */
    private void aplicarZebraStriping(JTable tabla) {
        tabla.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table, Object value, boolean isSelected, 
                    boolean hasFocus, int row, int column) {
                
                java.awt.Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
                
                if (isSelected) {
                    c.setBackground(PRIMARY_COLOR.brighter());
                    c.setForeground(Color.WHITE);
                } else {
                    // Zebra striping: filas pares blancas, impares gris claro
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                    c.setForeground(new Color(52, 73, 94));
                }
                
                // Centrar contenido numérico
                if (value instanceof Number) {
                    ((javax.swing.JLabel) c).setHorizontalAlignment(javax.swing.JLabel.CENTER);
                } else {
                    ((javax.swing.JLabel) c).setHorizontalAlignment(javax.swing.JLabel.LEFT);
                }
                
                return c;
            }
        });
    }
    
    /**
     * Aplica renderizador personalizado al encabezado de la tabla
     */
    private void aplicarRenderizadorEncabezado(JTable tabla, Color colorFondo) {
        javax.swing.table.JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(colorFondo);
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setPreferredSize(new Dimension(0, 40));
        
        // Renderizador personalizado para asegurar que los colores se apliquen
        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table, Object value, boolean isSelected, 
                    boolean hasFocus, int row, int column) {
                
                JLabel label = new JLabel(value != null ? value.toString() : "");
                label.setFont(new Font("Segoe UI", Font.BOLD, 13));
                label.setBackground(colorFondo);
                label.setForeground(Color.WHITE);
                label.setOpaque(true);
                label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 1, colorFondo.darker()),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
                ));
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        });
    }

    /**
     * Configura el layout
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel superior con título
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel lblTitulo = new JLabel("📊 Reportes y Estadísticas", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(PRIMARY_COLOR);
        
        JLabel lblUsuario = new JLabel("Usuario: " + usuarioActual.getNombre());
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUsuario.setForeground(new Color(52, 73, 94));
        
        // Botón de regresar
        btnRegresar = new JButton("⬅️ Regresar al Menú");
        btnRegresar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRegresar.setBackground(new Color(52, 73, 94));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setBorderPainted(false);
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(e -> regresarAlMenu());
        
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        panelSuperior.add(lblUsuario, BorderLayout.SOUTH);
        panelSuperior.add(btnRegresar, BorderLayout.EAST);
        
        // Agregar pestañas
        tabbedPane.addTab("📊 Dashboard", panelEstadisticas);
        tabbedPane.addTab("💰 Ventas", panelVentas);
        tabbedPane.addTab("📦 Productos", panelProductos);
        tabbedPane.addTab("📋 Inventario", panelInventario);
        tabbedPane.addTab("🏆 Top Ventas", panelTopProductos);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Configura los eventos
     */
    private void setupEventHandlers() {
        controller = new ReportesController(this, usuarioActual);
        
        btnActualizarVentas.addActionListener(e -> controller.cargarReporteVentas());
        btnExportarVentas.addActionListener(e -> controller.exportarVentas());
        
        btnActualizarProductos.addActionListener(e -> controller.cargarReporteProductos());
        btnExportarProductos.addActionListener(e -> controller.exportarProductos());
        
        btnActualizarInventario.addActionListener(e -> controller.cargarReporteInventario());
        
        // Evento de cambio de pestaña
        tabbedPane.addChangeListener(e -> controller.onTabChanged(tabbedPane.getSelectedIndex()));
    }

    /**
     * Configura el frame
     */
    private void configureFrame() {
        setTitle("Reportes y Estadísticas - Sistema de Inventario");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                regresarAlMenu();
            }
        });
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Abrir maximizada
        setResizable(true);
    }

    /**
     * Carga los datos iniciales
     */
    private void cargarDatos() {
        controller.cargarEstadisticasGenerales();
    }
    
    /**
     * Regresa al menú principal
     */
    private void regresarAlMenu() {
        this.dispose();
        mainFrame.mostrar();
    }

    // Getters para el controlador
    public JLabel getLblTotalVentas() { return lblTotalVentas; }
    public JLabel getLblVentasHoy() { return lblVentasHoy; }
    public JLabel getLblProductosStock() { return lblProductosStock; }
    public JLabel getLblProductosBajoStock() { return lblProductosBajoStock; }
    public JLabel getLblIngresoTotal() { return lblIngresoTotal; }
    public JLabel getLblPromedioVenta() { return lblPromedioVenta; }
    
    public DefaultTableModel getModeloVentas() { return modeloVentas; }
    public DefaultTableModel getModeloProductos() { return modeloProductos; }
    public DefaultTableModel getModeloInventario() { return modeloInventario; }
    public DefaultTableModel getModeloTopProductos() { return modeloTopProductos; }
    
    public JProgressBar getProgressStock() { return progressStock; }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
