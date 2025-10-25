package com.inventario.controller;

import com.inventario.model.Usuario;
import com.inventario.model.Producto;
import com.inventario.model.Venta;
import com.inventario.model.ItemVenta;
import com.inventario.service.VentaService;
import com.inventario.service.ProductoService;
import com.inventario.view.VentaFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

/**
 * Controlador para la gestión de ventas
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class VentaController {
    
    private VentaFrame view;
    private VentaService ventaService;
    private ProductoService productoService;
    private Usuario usuarioActual;
    private Venta ventaActual;
    private List<Producto> productosDisponibles;
    private NumberFormat formatoMoneda;

    public VentaController(VentaFrame view, Usuario usuarioActual) {
        this.view = view;
        this.usuarioActual = usuarioActual;
        this.ventaService = new VentaService();
        this.productoService = new ProductoService();
        this.ventaActual = new Venta(usuarioActual);
        this.formatoMoneda = NumberFormat.getCurrencyInstance();
    }

    /**
     * Carga los productos disponibles en el combo
     */
    public void cargarProductosDisponibles() {
        try {
            productosDisponibles = productoService.obtenerProductosDisponibles();
            
            JComboBox<String> combo = view.getCmbProductos();
            combo.removeAllItems();
            
            if (productosDisponibles.isEmpty()) {
                combo.addItem("No hay productos disponibles");
                view.mostrarError("No hay productos en el inventario");
                return;
            }
            
            for (Producto producto : productosDisponibles) {
                String item = String.format("%s - %s (Stock: %d)", 
                    producto.getCodigo(),
                    producto.getNombre(), 
                    producto.getCantidad());
                combo.addItem(item);
            }
            
        } catch (Exception e) {
            view.mostrarError("Error al cargar productos: " + e.getMessage());
        }
    }

    /**
     * Agrega un producto al carrito
     */
    public void agregarProducto() {
        try {
            // Validar selección
            int indiceSeleccionado = view.getCmbProductos().getSelectedIndex();
            if (indiceSeleccionado < 0) {
                view.mostrarError("Por favor, seleccione un producto");
                return;
            }
            
            // Obtener producto de la lista inicial
            Producto productoInicial = productosDisponibles.get(indiceSeleccionado);
            
            // IMPORTANTE: Recargar el producto desde BD para tener stock actualizado
            Producto productoSeleccionado = productoService.obtenerProductoPorCodigo(productoInicial.getCodigo());
            
            if (productoSeleccionado == null) {
                view.mostrarError("El producto ya no está disponible");
                cargarProductosDisponibles(); // Recargar lista
                return;
            }
            
            int cantidadSolicitada = view.getCantidadSeleccionada();
            
            // Validar stock actualizado
            if (cantidadSolicitada > productoSeleccionado.getCantidad()) {
                view.mostrarError(String.format(
                    "Stock insuficiente.\n" +
                    "Disponible: %d unidades\n" +
                    "Solicitado: %d unidades",
                    productoSeleccionado.getCantidad(),
                    cantidadSolicitada
                ));
                cargarProductosDisponibles(); // Actualizar lista con stock real
                return;
            }
            
            // Validar stock considerando lo que ya está en el carrito
            int cantidadEnCarrito = obtenerCantidadEnCarrito(productoSeleccionado.getCodigo());
            int cantidadTotal = cantidadEnCarrito + cantidadSolicitada;
            
            if (cantidadTotal > productoSeleccionado.getCantidad()) {
                view.mostrarError(String.format(
                    "La cantidad total excede el stock disponible.\n\n" +
                    "Stock disponible: %d unidades\n" +
                    "En carrito: %d unidades\n" +
                    "Solicitando: %d unidades\n" +
                    "Total requerido: %d unidades",
                    productoSeleccionado.getCantidad(),
                    cantidadEnCarrito,
                    cantidadSolicitada,
                    cantidadTotal
                ));
                return;
            }
            
            // Verificar si el producto ya está en el carrito
            boolean productoExistente = false;
            DefaultTableModel modelo = view.getModeloTabla();
            
            for (int i = 0; i < modelo.getRowCount(); i++) {
                String codigoEnTabla = modelo.getValueAt(i, 0).toString().split(" - ")[0];
                if (codigoEnTabla.equals(productoSeleccionado.getCodigo())) {
                    // Actualizar cantidad existente
                    int cantidadActual = Integer.parseInt(modelo.getValueAt(i, 2).toString());
                    int nuevaCantidad = cantidadActual + cantidadSolicitada;
                    
                    BigDecimal subtotal = productoSeleccionado.getPrecio()
                        .multiply(BigDecimal.valueOf(nuevaCantidad));
                    
                    modelo.setValueAt(nuevaCantidad, i, 2);
                    modelo.setValueAt(formatoMoneda.format(subtotal), i, 3);
                    
                    // Actualizar en ventaActual
                    actualizarItemEnVenta(productoSeleccionado.getCodigo(), nuevaCantidad);
                    
                    productoExistente = true;
                    break;
                }
            }
            
            // Si no existe, agregarlo
            if (!productoExistente) {
                ItemVenta item = new ItemVenta(productoSeleccionado, cantidadSolicitada);
                ventaActual.agregarItem(item);
                
                Object[] fila = {
                    productoSeleccionado.getCodigo() + " - " + productoSeleccionado.getNombre(),
                    formatoMoneda.format(productoSeleccionado.getPrecio()),
                    cantidadSolicitada,
                    formatoMoneda.format(item.getSubtotal())
                };
                
                modelo.addRow(fila);
            }
            
            // Actualizar total
            calcularTotal();
            
            String mensajeExito = String.format(
                "✅ Producto agregado al carrito\n\n" +
                "%s\n" +
                "Cantidad: %d unidades\n" +
                "Stock restante: %d unidades",
                productoSeleccionado.getNombre(),
                cantidadSolicitada,
                productoSeleccionado.getCantidad() - cantidadTotal
            );
            view.mostrarMensaje(mensajeExito);
            
        } catch (Exception e) {
            view.mostrarError("Error al agregar producto: " + e.getMessage());
        }
    }

    /**
     * Quita el producto seleccionado del carrito
     */
    public void quitarProductoSeleccionado() {
        try {
            JTable tabla = view.getTablaCarrito();
            int filaSeleccionada = tabla.getSelectedRow();
            
            if (filaSeleccionada < 0) {
                view.mostrarError("Por favor, seleccione un producto del carrito");
                return;
            }
            
            if (view.confirmar("¿Desea quitar este producto del carrito?")) {
                DefaultTableModel modelo = view.getModeloTabla();
                modelo.removeRow(filaSeleccionada);
                
                // Actualizar la venta
                if (filaSeleccionada < ventaActual.getItems().size()) {
                    ventaActual.getItems().remove(filaSeleccionada);
                }
                
                calcularTotal();
                view.mostrarMensaje("Producto eliminado del carrito");
            }
            
        } catch (Exception e) {
            view.mostrarError("Error al quitar producto: " + e.getMessage());
        }
    }

    /**
     * Limpia todo el carrito
     */
    public void limpiarCarrito() {
        try {
            if (ventaActual.getItems().isEmpty()) {
                view.mostrarError("El carrito ya está vacío");
                return;
            }
            
            if (view.confirmar("¿Desea limpiar todo el carrito?")) {
                DefaultTableModel modelo = view.getModeloTabla();
                modelo.setRowCount(0);
                
                ventaActual = new Venta(usuarioActual);
                
                calcularTotal();
                view.mostrarMensaje("Carrito limpiado");
            }
            
        } catch (Exception e) {
            view.mostrarError("Error al limpiar carrito: " + e.getMessage());
        }
    }

    /**
     * Procesa la venta final
     */
    public void procesarVenta() {
        try {
            System.out.println("\n🛒 ===== VENTACONTROLLER: INICIANDO PROCESO =====");
            
            // Validar que hay items
            if (!ventaActual.tieneItems()) {
                view.mostrarError("El carrito está vacío");
                return;
            }
            
            System.out.println("📋 Información de la venta:");
            System.out.println("   Usuario: " + usuarioActual.getUsername());
            System.out.println("   Usuario ID: " + usuarioActual.getId());
            System.out.println("   Items en carrito: " + ventaActual.getCantidadItems());
            System.out.println("   Total: " + ventaActual.getTotal());
            
            System.out.println("\n📦 Items en la venta:");
            for (ItemVenta item : ventaActual.getItems()) {
                System.out.println("   - Producto: " + item.getProducto().getNombre());
                System.out.println("     ID: " + item.getProducto().getId());
                System.out.println("     Código: " + item.getProducto().getCodigo());
                System.out.println("     Cantidad: " + item.getCantidad());
                System.out.println("     Precio unitario: " + item.getPrecioUnitario());
                System.out.println("     Subtotal: " + item.getSubtotal());
            }
            
            // Confirmar venta
            String mensaje = String.format(
                "¿Confirmar venta?\n\n" +
                "Items: %d\n" +
                "Total: %s\n\n" +
                "Esta acción actualizará el inventario.",
                ventaActual.getCantidadItems(),
                formatoMoneda.format(ventaActual.getTotal())
            );
            
            if (!view.confirmar(mensaje)) {
                System.out.println("❌ Usuario canceló la venta");
                return;
            }
            
            System.out.println("\n✅ Usuario confirmó la venta. Llamando a VentaService...");
            
            // Procesar venta
            boolean exito = ventaService.procesarVenta(ventaActual);
            
            System.out.println("\n📊 Resultado de VentaService.procesarVenta(): " + exito);
            
            if (exito) {
                // Mostrar mensaje de éxito
                String mensajeExito = String.format(
                    "✅ ¡Venta procesada exitosamente!\n\n" +
                    "Venta #%d\n" +
                    "Total: %s\n" +
                    "Items: %d\n\n" +
                    "El inventario ha sido actualizado.",
                    ventaActual.getId(),
                    formatoMoneda.format(ventaActual.getTotal()),
                    ventaActual.getCantidadItems()
                );
                
                view.mostrarMensaje(mensajeExito);
                
                // Limpiar y recargar
                DefaultTableModel modelo = view.getModeloTabla();
                modelo.setRowCount(0);
                ventaActual = new Venta(usuarioActual);
                calcularTotal();
                cargarProductosDisponibles();
                
            } else {
                System.err.println("\n❌ VentaService retornó FALSE");
                System.err.println("Revisa los logs arriba para ver el motivo específico");
                view.mostrarError("No se pudo procesar la venta. Verifique el stock.\n\nRevisa la consola para más detalles.");
            }
            
        } catch (Exception e) {
            System.err.println("\n❌❌❌ EXCEPCIÓN EN VENTACONTROLLER ❌❌❌");
            System.err.println("Mensaje: " + e.getMessage());
            System.err.println("Tipo: " + e.getClass().getName());
            e.printStackTrace();
            view.mostrarError("Error al procesar venta: " + e.getMessage());
        }
    }

    /**
     * Calcula y actualiza el total de la venta
     */
    private void calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        DefaultTableModel modelo = view.getModeloTabla();
        
        for (int i = 0; i < modelo.getRowCount(); i++) {
            String subtotalStr = modelo.getValueAt(i, 3).toString();
            
            try {
                // Limpiar formato de moneda usando el método helper
                BigDecimal subtotal = limpiarFormatoMoneda(subtotalStr);
                total = total.add(subtotal);
            } catch (NumberFormatException e) {
                System.err.println("Error al parsear subtotal: " + subtotalStr);
                System.err.println("Error: " + e.getMessage());
            }
        }
        
        ventaActual.setTotal(total);
        view.setTotal(total);
    }
    
    /**
     * Limpia el formato de moneda y convierte a BigDecimal
     * Maneja diferentes formatos: $1.234,56 o $1,234.56 o 1234.56
     */
    private BigDecimal limpiarFormatoMoneda(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        // Remover símbolos de moneda y espacios
        String limpio = valor.trim()
            .replace("$", "")
            .replace("€", "")
            .replace("£", "")
            .replace("¥", "")
            .replace(" ", "")
            .replace("\u00A0", ""); // Non-breaking space
        
        // Detectar el formato basándose en la posición de coma y punto
        if (limpio.contains(",") && limpio.contains(".")) {
            // Formato: 1.234,56 (europeo) o 1,234.56 (americano)
            int posicionPunto = limpio.indexOf(".");
            int posicionComa = limpio.indexOf(",");
            
            if (posicionPunto < posicionComa) {
                // Formato europeo: 1.234,56 -> remover puntos, cambiar coma por punto
                limpio = limpio.replace(".", "").replace(",", ".");
            } else {
                // Formato americano: 1,234.56 -> solo remover comas
                limpio = limpio.replace(",", "");
            }
        } else if (limpio.contains(",")) {
            // Solo tiene comas
            // Determinar si es separador de miles o decimal
            int posicionComa = limpio.indexOf(",");
            int digitosDespuesComa = limpio.length() - posicionComa - 1;
            
            if (digitosDespuesComa <= 2) {
                // Probablemente es decimal: 1234,56
                limpio = limpio.replace(",", ".");
            } else {
                // Probablemente es separador de miles: 1,234
                limpio = limpio.replace(",", "");
            }
        }
        // Si solo tiene puntos, se asume formato americano (ya está bien)
        
        try {
            return new BigDecimal(limpio);
        } catch (NumberFormatException e) {
            System.err.println("No se pudo convertir: " + valor + " -> " + limpio);
            throw new NumberFormatException("Formato de moneda inválido: " + valor);
        }
    }
    
    /**
     * Obtiene la cantidad de un producto que ya está en el carrito
     * 
     * @param codigoProducto Código del producto
     * @return Cantidad en el carrito, 0 si no está
     */
    private int obtenerCantidadEnCarrito(String codigoProducto) {
        DefaultTableModel modelo = view.getModeloTabla();
        
        for (int i = 0; i < modelo.getRowCount(); i++) {
            String codigoEnTabla = modelo.getValueAt(i, 0).toString().split(" - ")[0];
            if (codigoEnTabla.equals(codigoProducto)) {
                return Integer.parseInt(modelo.getValueAt(i, 2).toString());
            }
        }
        
        return 0;
    }
    
    /**
     * Actualiza la cantidad de un item en la venta actual
     * 
     * @param codigoProducto Código del producto
     * @param nuevaCantidad Nueva cantidad
     */
    private void actualizarItemEnVenta(String codigoProducto, int nuevaCantidad) {
        for (ItemVenta item : ventaActual.getItems()) {
            if (item.getProducto().getCodigo().equals(codigoProducto)) {
                item.setCantidad(nuevaCantidad);
                break;
            }
        }
    }
}
