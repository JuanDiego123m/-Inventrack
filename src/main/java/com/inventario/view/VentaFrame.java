package com.inventario.view;

import com.inventario.controller.VentaController;
import com.inventario.model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Interfaz para procesar ventas
 * Permite seleccionar productos y generar ventas
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class VentaFrame extends JFrame {
    
    // Colores modernos
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    
    // Componentes principales
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblProducto;
    private JLabel lblCantidad;
    private JLabel lblTotal;
    
    private JComboBox<String> cmbProductos;
    private JSpinner spnCantidad;
    private JButton btnAgregar;
    private JButton btnQuitar;
    private JButton btnProcesarVenta;
    private JButton btnCancelar;
    private JButton btnLimpiar;
    
    private JTable tablaCarrito;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPane;
    
    private JTextField txtTotal;
    private JTextArea txtResumen;
    
    private VentaController controller;
    private Usuario usuarioActual;
    private MainFrame mainFrame;
    private NumberFormat formatoMoneda;
    private JButton btnRegresar;

    public VentaFrame(Usuario usuario, MainFrame mainFrame) {
        this.usuarioActual = usuario;
        this.mainFrame = mainFrame;
        this.formatoMoneda = NumberFormat.getCurrencyInstance();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureFrame();
        cargarProductos();
    }

    /**
     * Inicializa todos los componentes
     */
    private void initializeComponents() {
        // Labels
        lblTitulo = new JLabel("🛒 Procesar Venta", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(PRIMARY_COLOR);
        
        lblUsuario = new JLabel("Vendedor: " + usuarioActual.getNombre());
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsuario.setForeground(new Color(52, 73, 94));
        
        lblProducto = new JLabel("Producto:");
        lblProducto.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        lblTotal = new JLabel("TOTAL:");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotal.setForeground(SUCCESS_COLOR);
        
        // ComboBox de productos
        cmbProductos = new JComboBox<>();
        cmbProductos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbProductos.setPreferredSize(new Dimension(300, 35));
        
        // Spinner de cantidad
        SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
        spnCantidad = new JSpinner(spinnerModel);
        spnCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        ((JSpinner.DefaultEditor) spnCantidad.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
        
        // Botones
        btnAgregar = createModernButton("➕ Agregar al Carrito", SUCCESS_COLOR);
        btnQuitar = createModernButton("➖ Quitar Seleccionado", DANGER_COLOR);
        btnProcesarVenta = createModernButton("💰 Procesar Venta", PRIMARY_COLOR);
        btnLimpiar = createModernButton("🔄 Limpiar Carrito", WARNING_COLOR);
        btnRegresar = createModernButton("⬅️ Regresar al Menú", new Color(52, 73, 94));
        btnCancelar = btnRegresar; // Alias para compatibilidad
        
        // Tabla del carrito
        String[] columnas = {"Producto", "Precio Unit.", "Cantidad", "Subtotal"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaCarrito = new JTable(modeloTabla);
        tablaCarrito.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaCarrito.setRowHeight(30);
        tablaCarrito.setGridColor(new Color(224, 224, 224));
        tablaCarrito.setSelectionBackground(PRIMARY_COLOR.brighter());
        tablaCarrito.setSelectionForeground(Color.WHITE);
        
        // Configurar encabezado de la tabla con renderizador personalizado
        javax.swing.table.JTableHeader header = tablaCarrito.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(PRIMARY_COLOR);
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
                label.setBackground(PRIMARY_COLOR);
                label.setForeground(Color.WHITE);
                label.setOpaque(true);
                label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 1, PRIMARY_COLOR.darker()),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
                ));
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        });
        
        // Implementar zebra striping (filas alternadas)
        tablaCarrito.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
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
                    // Zebra striping
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                    c.setForeground(new Color(52, 73, 94));
                }
                
                // Centrar contenido numérico
                if (column >= 2) { // Precio, Cantidad, Subtotal
                    ((javax.swing.JLabel) c).setHorizontalAlignment(javax.swing.JLabel.CENTER);
                } else {
                    ((javax.swing.JLabel) c).setHorizontalAlignment(javax.swing.JLabel.LEFT);
                }
                
                return c;
            }
        });
        
        tablaCarrito.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        scrollPane = new JScrollPane(tablaCarrito);
        scrollPane.setPreferredSize(new Dimension(700, 250));
        
        // Campo de total
        txtTotal = new JTextField("$0");
        txtTotal.setFont(new Font("Segoe UI", Font.BOLD, 24));
        txtTotal.setForeground(SUCCESS_COLOR);
        txtTotal.setHorizontalAlignment(JTextField.RIGHT);
        txtTotal.setEditable(false);
        txtTotal.setBackground(Color.WHITE);
        txtTotal.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SUCCESS_COLOR, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Área de resumen
        txtResumen = new JTextArea(3, 30);
        txtResumen.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        txtResumen.setEditable(false);
        txtResumen.setLineWrap(true);
        txtResumen.setWrapStyleWord(true);
        txtResumen.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        actualizarResumen();
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
     * Configura el layout
     */
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        panelSuperior.add(lblUsuario, BorderLayout.SOUTH);
        
        // Panel de selección de productos
        JPanel panelProductos = new JPanel();
        panelProductos.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelProductos.setBackground(Color.WHITE);
        panelProductos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            "Seleccionar Producto",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 13),
            PRIMARY_COLOR
        ));
        
        panelProductos.add(lblProducto);
        panelProductos.add(cmbProductos);
        panelProductos.add(lblCantidad);
        panelProductos.add(spnCantidad);
        panelProductos.add(btnAgregar);
        
        // Panel del carrito
        JPanel panelCarrito = new JPanel(new BorderLayout(5, 5));
        panelCarrito.setBackground(Color.WHITE);
        panelCarrito.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            "Carrito de Compras",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 13),
            PRIMARY_COLOR
        ));
        
        JPanel panelBotonesCarrito = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotonesCarrito.setBackground(Color.WHITE);
        panelBotonesCarrito.add(btnQuitar);
        panelBotonesCarrito.add(btnLimpiar);
        
        panelCarrito.add(scrollPane, BorderLayout.CENTER);
        panelCarrito.add(panelBotonesCarrito, BorderLayout.SOUTH);
        
        // Panel de total y resumen
        JPanel panelTotal = new JPanel();
        panelTotal.setLayout(new BoxLayout(panelTotal, BoxLayout.Y_AXIS));
        panelTotal.setBackground(Color.WHITE);
        panelTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel panelTotalInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelTotalInfo.setBackground(Color.WHITE);
        panelTotalInfo.add(lblTotal);
        panelTotalInfo.add(txtTotal);
        
        panelTotal.add(new JScrollPane(txtResumen));
        panelTotal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelTotal.add(panelTotalInfo);
        
        // Panel de botones finales
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.add(btnProcesarVenta);
        panelBotones.add(btnRegresar);
        
        // Panel central que agrupa todo
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCentral.add(panelProductos, BorderLayout.NORTH);
        panelCentral.add(panelCarrito, BorderLayout.CENTER);
        panelCentral.add(panelTotal, BorderLayout.EAST);
        
        // Agregar todo al frame
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Configura los eventos
     */
    private void setupEventHandlers() {
        controller = new VentaController(this, usuarioActual);
        
        btnAgregar.addActionListener(e -> controller.agregarProducto());
        btnQuitar.addActionListener(e -> controller.quitarProductoSeleccionado());
        btnLimpiar.addActionListener(e -> controller.limpiarCarrito());
        btnProcesarVenta.addActionListener(e -> controller.procesarVenta());
        btnRegresar.addActionListener(e -> regresarAlMenu());
    }

    /**
     * Configura el frame
     */
    private void configureFrame() {
        setTitle("Procesar Venta - Sistema de Inventario");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                regresarAlMenu();
            }
        });
        setSize(950, 700);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Abrir maximizada
        setResizable(true);
    }
    
    /**
     * Regresa al menú principal
     */
    private void regresarAlMenu() {
        this.dispose();
        mainFrame.mostrar();
    }

    /**
     * Carga los productos disponibles
     */
    private void cargarProductos() {
        controller.cargarProductosDisponibles();
    }

    /**
     * Actualiza el resumen de la venta
     */
    public void actualizarResumen() {
        int items = modeloTabla.getRowCount();
        int totalUnidades = 0;
        
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            totalUnidades += Integer.parseInt(modeloTabla.getValueAt(i, 2).toString());
        }
        
        txtResumen.setText(String.format(
            "📊 Resumen de Venta\n" +
            "Items diferentes: %d\n" +
            "Total de unidades: %d",
            items, totalUnidades
        ));
    }

    // Getters para el controlador
    public JComboBox<String> getCmbProductos() {
        return cmbProductos;
    }

    public int getCantidadSeleccionada() {
        return (Integer) spnCantidad.getValue();
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JTable getTablaCarrito() {
        return tablaCarrito;
    }

    public void setTotal(BigDecimal total) {
        txtTotal.setText(formatoMoneda.format(total));
        actualizarResumen();
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean confirmar(String mensaje) {
        return JOptionPane.showConfirmDialog(this, mensaje, "Confirmar", 
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
