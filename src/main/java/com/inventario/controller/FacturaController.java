package com.inventario.controller;

import com.inventario.model.Usuario;
import com.inventario.model.Venta;
import com.inventario.model.Factura;
import com.inventario.service.VentaService;
import com.inventario.service.FacturaService;
import com.inventario.view.FacturaFrame;

import javax.swing.table.DefaultTableModel;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controlador para la generación de facturas
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class FacturaController {
    
    private FacturaFrame view;
    private FacturaService facturaService;
    private VentaService ventaService;
    private Usuario usuarioActual;
    private Factura facturaActual;
    private NumberFormat formatoMoneda;
    private DateTimeFormatter formatoFecha;

    public FacturaController(FacturaFrame view, Usuario usuarioActual) {
        this.view = view;
        this.usuarioActual = usuarioActual;
        this.facturaService = new FacturaService();
        this.ventaService = new VentaService();
        this.formatoMoneda = NumberFormat.getCurrencyInstance();
        this.formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }

    /**
     * Carga las ventas disponibles
     */
    public void cargarVentas() {
        try {
            List<Venta> ventas = ventaService.obtenerTodasVentas();
            DefaultTableModel modelo = view.getModeloVentas();
            modelo.setRowCount(0);
            
            for (Venta venta : ventas) {
                Object[] fila = {
                    false, // Checkbox
                    venta.getId(),
                    venta.getFechaVenta().format(formatoFecha),
                    venta.getUsuario().getNombre(),
                    venta.getCantidadItems(),
                    formatoMoneda.format(venta.getTotal())
                };
                modelo.addRow(fila);
            }
            
        } catch (Exception e) {
            view.mostrarError("Error al cargar ventas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Genera la vista previa de la factura
     */
    public void generarVistaPrevia() {
        try {
            System.out.println("\n📄 ===== GENERANDO FACTURA =====");
            
            // Obtener venta seleccionada
            int ventaId = obtenerVentaSeleccionada();
            if (ventaId == -1) {
                view.mostrarError("Por favor, seleccione una venta de la tabla");
                return;
            }
            
            System.out.println("✅ Venta seleccionada ID: " + ventaId);
            
            // Obtener datos
            Venta venta = ventaService.obtenerVentaPorId(ventaId);
            if (venta == null) {
                System.err.println("❌ No se pudo cargar la venta con ID: " + ventaId);
                view.mostrarError("No se pudo cargar la venta seleccionada");
                return;
            }
            
            System.out.println("✅ Venta cargada:");
            System.out.println("   ID: " + venta.getId());
            System.out.println("   Usuario: " + venta.getUsuario().getNombre());
            System.out.println("   Total: " + venta.getTotal());
            System.out.println("   Fecha: " + venta.getFechaVenta());
            System.out.println("   Items: " + (venta.getItems() != null ? venta.getItems().size() : "NULL"));
            
            if (venta.getItems() != null && !venta.getItems().isEmpty()) {
                System.out.println("\n📦 Items de la venta:");
                for (int i = 0; i < venta.getItems().size(); i++) {
                    var item = venta.getItems().get(i);
                    System.out.println("   " + (i+1) + ". " + item.getProducto().getNombre());
                    System.out.println("      Cantidad: " + item.getCantidad());
                    System.out.println("      Precio unitario: " + item.getPrecioUnitario());
                    System.out.println("      Subtotal: " + item.getSubtotal());
                }
            } else {
                System.err.println("⚠️ La venta NO tiene items cargados");
            }
            
            // Obtener datos del cliente
            String clienteNombre = view.getClienteNombre();
            String clienteDocumento = view.getClienteDocumento();
            boolean incluirIVA = view.isIncluirIVA();
            
            System.out.println("\n👤 Datos del cliente:");
            System.out.println("   Nombre: " + clienteNombre);
            System.out.println("   Documento: " + clienteDocumento);
            System.out.println("   Incluir IVA: " + incluirIVA);
            
            // Validar datos
            if (clienteNombre.isEmpty()) {
                view.mostrarError("Ingrese el nombre del cliente");
                return;
            }
            
            // Crear factura
            System.out.println("\n📝 Creando factura...");
            facturaActual = new Factura(venta);
            facturaActual.setClienteNombre(clienteNombre);
            facturaActual.setClienteDocumento(clienteDocumento);
            
            System.out.println("✅ Factura creada:");
            System.out.println("   Número: " + facturaActual.getNumeroFactura());
            System.out.println("   Subtotal: " + facturaActual.getSubtotal());
            System.out.println("   IVA: " + facturaActual.getIva());
            System.out.println("   Total: " + facturaActual.getTotal());
            
            // Generar vista previa
            System.out.println("\n🖨️ Generando texto de factura...");
            String vistaPrevia = facturaService.generarFacturaTexto(
                facturaActual, 
                incluirIVA
            );
            
            System.out.println("✅ Texto generado (" + vistaPrevia.length() + " caracteres)");
            
            // Mostrar en la vista
            view.getTxtVistaPrevia().setText(vistaPrevia);
            
            // Habilitar botones
            view.getBtnGuardarFactura().setEnabled(true);
            view.getBtnImprimirFactura().setEnabled(true);
            
            System.out.println("✅✅✅ FACTURA GENERADA EXITOSAMENTE ✅✅✅\n");
            
        } catch (Exception e) {
            System.err.println("\n❌❌❌ ERROR AL GENERAR FACTURA ❌❌❌");
            System.err.println("Mensaje: " + e.getMessage());
            System.err.println("Tipo: " + e.getClass().getName());
            e.printStackTrace();
            System.err.println("=========================================\n");
            view.mostrarError("Error al generar vista previa: " + e.getMessage());
        }
    }

    /**
     * Obtiene el ID de la venta seleccionada
     */
    private int obtenerVentaSeleccionada() {
        DefaultTableModel modelo = view.getModeloVentas();
        
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Boolean seleccionado = (Boolean) modelo.getValueAt(i, 0);
            if (seleccionado != null && seleccionado) {
                return (Integer) modelo.getValueAt(i, 1); // Columna ID
            }
        }
        
        return -1;
    }

    /**
     * Guarda la factura en un archivo permitiendo al usuario elegir la ubicación
     */
    public void guardarFactura() {
        try {
            if (facturaActual == null) {
                view.mostrarError("Primero genere la vista previa de la factura");
                return;
            }
            
            // Crear el selector de archivos
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Guardar Factura");
            fileChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
            
            // Sugerir nombre de archivo
            String nombreSugerido = facturaService.generarNombreArchivo(facturaActual);
            fileChooser.setSelectedFile(new java.io.File(nombreSugerido));
            
            // Agregar filtro de archivos
            javax.swing.filechooser.FileNameExtensionFilter filtro = 
                new javax.swing.filechooser.FileNameExtensionFilter("Archivos de texto (*.txt)", "txt");
            fileChooser.setFileFilter(filtro);
            
            // Mostrar diálogo
            int resultado = fileChooser.showSaveDialog(null);
            
            if (resultado == javax.swing.JFileChooser.APPROVE_OPTION) {
                java.io.File archivoSeleccionado = fileChooser.getSelectedFile();
                String rutaCompleta = archivoSeleccionado.getAbsolutePath();
                
                // Asegurar que el archivo tenga extensión .txt
                if (!rutaCompleta.toLowerCase().endsWith(".txt")) {
                    rutaCompleta += ".txt";
                }
                
                // Guardar en archivo
                boolean incluirIVA = view.isIncluirIVA();
                facturaService.guardarFacturaArchivo(facturaActual, incluirIVA, rutaCompleta);
                
                // Marcar como generada
                facturaActual.setGenerada(true);
                
                view.mostrarMensaje(String.format(
                    "✅ Factura guardada exitosamente!\n\n" +
                    "Número: %s\n" +
                    "Ubicación: %s\n" +
                    "Total: %s",
                    facturaActual.getNumeroFactura(),
                    rutaCompleta,
                    formatoMoneda.format(facturaActual.getTotal())
                ));
                
                // Limpiar vista previa
                limpiarVistaPrevia();
                
                // Recargar ventas
                cargarVentas();
            }
            
        } catch (Exception e) {
            view.mostrarError("Error al guardar factura: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Imprime la factura - Genera archivo y lo abre para imprimir
     */
    public void imprimirFactura() {
        try {
            if (facturaActual == null) {
                view.mostrarError("Primero genere la vista previa de la factura");
                return;
            }
            
            if (!view.confirmar("¿Desea abrir la factura para imprimir?\n\n" +
                "Se abrirá el archivo con el programa predeterminado\n" +
                "y podrá imprimirlo desde allí.")) {
                return;
            }
            
            // Generar archivo temporal HTML en la carpeta Temp del sistema
            String carpetaTemp = System.getProperty("java.io.tmpdir");
            String nombreArchivo = facturaService.generarNombreArchivo(facturaActual);
            // Cambiar extensión a .html
            nombreArchivo = nombreArchivo.replace(".txt", ".html");
            String rutaCompleta = carpetaTemp + java.io.File.separator + nombreArchivo;
            
            // Guardar factura en formato HTML (mejor para imprimir)
            boolean incluirIVA = view.isIncluirIVA();
            facturaService.guardarFacturaHTML(facturaActual, incluirIVA, rutaCompleta);
            
            // Abrir el archivo con el programa predeterminado
            java.io.File archivo = new java.io.File(rutaCompleta);
            
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                
                if (desktop.isSupported(java.awt.Desktop.Action.OPEN)) {
                    desktop.open(archivo);
                    
                    view.mostrarMensaje(String.format(
                        "🖨️ Factura abierta para imprimir!\n\n" +
                        "Número: %s\n" +
                        "Archivo: %s\n\n" +
                        "El archivo se ha abierto con el programa predeterminado.\n" +
                        "Use Ctrl+P o el menú Archivo → Imprimir para imprimir.",
                        facturaActual.getNumeroFactura(),
                        nombreArchivo
                    ));
                } else {
                    view.mostrarError("No se puede abrir archivos automáticamente en este sistema.\n" +
                        "Archivo guardado en: " + rutaCompleta);
                }
            } else {
                view.mostrarError("Función de apertura automática no soportada.\n" +
                    "Archivo guardado en: " + rutaCompleta);
            }
            
        } catch (Exception e) {
            view.mostrarError("Error al preparar factura para imprimir: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Limpia la vista previa
     */
    public void limpiarVistaPrevia() {
        view.getTxtVistaPrevia().setText(
            "Seleccione una venta y haga clic en 'Generar Factura'\n" +
            "para ver la vista previa aquí."
        );
        
        // Desmarcar todas las ventas
        DefaultTableModel modelo = view.getModeloVentas();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.setValueAt(false, i, 0);
        }
        
        // Deshabilitar botones
        view.getBtnGuardarFactura().setEnabled(false);
        view.getBtnImprimirFactura().setEnabled(false);
        
        // Limpiar factura actual
        facturaActual = null;
    }
}
