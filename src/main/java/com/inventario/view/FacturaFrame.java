package com.inventario.view;

import com.inventario.controller.FacturaController;
import com.inventario.model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Interfaz para generar y visualizar facturas
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class FacturaFrame extends JFrame {
    
    // Colores modernos
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color INFO_COLOR = new Color(52, 152, 219);
    
    // Componentes principales
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    
    // Panel izquierdo - Ventas
    private JPanel panelVentas;
    private JTable tablaVentas;
    private DefaultTableModel modeloVentas;
    private JScrollPane scrollVentas;
    private JButton btnActualizar;
    private JButton btnGenerarFactura;
    
    // Panel derecho - Vista previa
    private JPanel panelVistaPrevia;
    private JTextArea txtVistaPrevia;
    private JScrollPane scrollVistaPrevia;
    private JButton btnGuardarFactura;
    private JButton btnImprimirFactura;
    private JButton btnLimpiar;
    
    // Panel de datos del cliente
    private JPanel panelCliente;
    private JLabel lblClienteNombre;
    private JLabel lblClienteDocumento;
    private JTextField txtClienteNombre;
    private JTextField txtClienteDocumento;
    private JCheckBox chkIncluirIVA;
    
    private FacturaController controller;
    private Usuario usuarioActual;
    private MainFrame mainFrame;
    private JButton btnRegresar;

    public FacturaFrame(Usuario usuario, MainFrame mainFrame) {
        this.usuarioActual = usuario;
        this.mainFrame = mainFrame;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureFrame();
        cargarVentas();
    }

    /**
     * Inicializa todos los componentes
     */
    private void initializeComponents() {
        // Labels principales
        lblTitulo = new JLabel("📄 Generar Facturas", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(PRIMARY_COLOR);
        
        lblUsuario = new JLabel("Usuario: " + usuarioActual.getNombre());
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsuario.setForeground(new Color(52, 73, 94));
        
        // Panel de ventas
        initPanelVentas();
        
        // Panel de cliente
        initPanelCliente();
        
        // Panel de vista previa
        initPanelVistaPrevia();
    }

    /**
     * Inicializa el panel de ventas
     */
    private void initPanelVentas() {
        panelVentas = new JPanel(new BorderLayout(10, 10));
        panelVentas.setBackground(Color.WHITE);
        panelVentas.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
            "Ventas Disponibles",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 14),
            PRIMARY_COLOR
        ));
        
        // Tabla de ventas
        String[] columnas = {"Seleccionar", "ID", "Fecha", "Vendedor", "Items", "Total"};
        modeloVentas = new DefaultTableModel(columnas, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : Object.class;
            }
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Solo la columna de checkbox es editable
            }
        };
        
        tablaVentas = new JTable(modeloVentas);
        tablaVentas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaVentas.setRowHeight(30);
        tablaVentas.setGridColor(new Color(224, 224, 224));
        tablaVentas.setSelectionBackground(PRIMARY_COLOR.brighter());
        tablaVentas.setSelectionForeground(Color.WHITE);
        
        // Configurar encabezado de la tabla con renderizador personalizado
        javax.swing.table.JTableHeader header = tablaVentas.getTableHeader();
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
        tablaVentas.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table, Object value, boolean isSelected, 
                    boolean hasFocus, int row, int column) {
                
                // No aplicar zebra striping a la columna de checkbox (columna 0)
                if (column == 0) {
                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
                
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
                if (column == 1 || column >= 4) { // ID y Total
                    ((javax.swing.JLabel) c).setHorizontalAlignment(javax.swing.JLabel.CENTER);
                } else {
                    ((javax.swing.JLabel) c).setHorizontalAlignment(javax.swing.JLabel.LEFT);
                }
                
                return c;
            }
        });
        
        tablaVentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        scrollVentas = new JScrollPane(tablaVentas);
        scrollVentas.setPreferredSize(new Dimension(600, 400));
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotones.setBackground(Color.WHITE);
        
        btnActualizar = createModernButton("🔄 Actualizar", PRIMARY_COLOR);
        btnGenerarFactura = createModernButton("📄 Generar Factura", SUCCESS_COLOR);
        
        panelBotones.add(btnActualizar);
        panelBotones.add(btnGenerarFactura);
        
        panelVentas.add(scrollVentas, BorderLayout.CENTER);
        panelVentas.add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Inicializa el panel de cliente
     */
    private void initPanelCliente() {
        panelCliente = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCliente.setBackground(Color.WHITE);
        panelCliente.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(INFO_COLOR, 2),
            "Datos del Cliente",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 13),
            INFO_COLOR
        ));
        
        lblClienteNombre = new JLabel("Nombre:");
        lblClienteNombre.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        txtClienteNombre = new JTextField("Cliente General");
        txtClienteNombre.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        lblClienteDocumento = new JLabel("Documento:");
        lblClienteDocumento.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        txtClienteDocumento = new JTextField("N/A");
        txtClienteDocumento.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        chkIncluirIVA = new JCheckBox("Incluir IVA (19%)", true);
        chkIncluirIVA.setFont(new Font("Segoe UI", Font.BOLD, 12));
        chkIncluirIVA.setBackground(Color.WHITE);
        
        panelCliente.add(lblClienteNombre);
        panelCliente.add(txtClienteNombre);
        panelCliente.add(lblClienteDocumento);
        panelCliente.add(txtClienteDocumento);
        panelCliente.add(chkIncluirIVA);
        panelCliente.add(new JLabel()); // Espacio vacío
    }

    /**
     * Inicializa el panel de vista previa
     */
    private void initPanelVistaPrevia() {
        panelVistaPrevia = new JPanel(new BorderLayout(10, 10));
        panelVistaPrevia.setBackground(Color.WHITE);
        panelVistaPrevia.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(SUCCESS_COLOR, 2),
            "Vista Previa de Factura",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 14),
            SUCCESS_COLOR
        ));
        
        // Área de texto para vista previa
        txtVistaPrevia = new JTextArea();
        txtVistaPrevia.setFont(new Font("Courier New", Font.PLAIN, 11));
        txtVistaPrevia.setEditable(false);
        txtVistaPrevia.setLineWrap(false);
        txtVistaPrevia.setText("Seleccione una venta y haga clic en 'Generar Factura'\n" +
            "para ver la vista previa aquí.");
        
        scrollVistaPrevia = new JScrollPane(txtVistaPrevia);
        scrollVistaPrevia.setPreferredSize(new Dimension(500, 400));
        
        // Panel de botones
        JPanel panelBotonesPrevia = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotonesPrevia.setBackground(Color.WHITE);
        
        btnGuardarFactura = createModernButton("💾 Guardar", SUCCESS_COLOR);
        btnImprimirFactura = createModernButton("🖨️ Imprimir", PRIMARY_COLOR);
        btnLimpiar = createModernButton("🔄 Limpiar", DANGER_COLOR);
        btnRegresar = createModernButton("⬅️ Menú", new Color(52, 73, 94));
        
        btnGuardarFactura.setEnabled(false);
        btnImprimirFactura.setEnabled(false);
        
        panelBotonesPrevia.add(btnGuardarFactura);
        panelBotonesPrevia.add(btnImprimirFactura);
        panelBotonesPrevia.add(btnLimpiar);
        panelBotonesPrevia.add(btnRegresar);
        
        panelVistaPrevia.add(scrollVistaPrevia, BorderLayout.CENTER);
        panelVistaPrevia.add(panelBotonesPrevia, BorderLayout.SOUTH);
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
        
        // Panel izquierdo (ventas + cliente)
        JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));
        panelIzquierdo.setBackground(Color.WHITE);
        panelIzquierdo.add(panelVentas, BorderLayout.CENTER);
        panelIzquierdo.add(panelCliente, BorderLayout.SOUTH);
        
        // Panel central con split
        JSplitPane splitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            panelIzquierdo,
            panelVistaPrevia
        );
        splitPane.setDividerLocation(650);
        splitPane.setResizeWeight(0.5);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.add(splitPane, BorderLayout.CENTER);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(panelPrincipal, BorderLayout.CENTER);
    }

    /**
     * Configura los eventos
     */
    private void setupEventHandlers() {
        controller = new FacturaController(this, usuarioActual);
        
        btnActualizar.addActionListener(e -> controller.cargarVentas());
        btnGenerarFactura.addActionListener(e -> controller.generarVistaPrevia());
        btnGuardarFactura.addActionListener(e -> controller.guardarFactura());
        btnImprimirFactura.addActionListener(e -> controller.imprimirFactura());
        btnLimpiar.addActionListener(e -> controller.limpiarVistaPrevia());
        btnRegresar.addActionListener(e -> regresarAlMenu());
    }

    /**
     * Configura el frame
     */
    private void configureFrame() {
        setTitle("Generar Facturas - Sistema de Inventario");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                regresarAlMenu();
            }
        });
        setSize(1200, 700);
        setLocationRelativeTo(null);
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
     * Carga las ventas disponibles
     */
    private void cargarVentas() {
        controller.cargarVentas();
    }

    // Getters para el controlador
    public DefaultTableModel getModeloVentas() {
        return modeloVentas;
    }

    public JTable getTablaVentas() {
        return tablaVentas;
    }

    public JTextArea getTxtVistaPrevia() {
        return txtVistaPrevia;
    }

    public String getClienteNombre() {
        return txtClienteNombre.getText().trim();
    }

    public String getClienteDocumento() {
        return txtClienteDocumento.getText().trim();
    }

    public boolean isIncluirIVA() {
        return chkIncluirIVA.isSelected();
    }

    public JButton getBtnGuardarFactura() {
        return btnGuardarFactura;
    }

    public JButton getBtnImprimirFactura() {
        return btnImprimirFactura;
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
