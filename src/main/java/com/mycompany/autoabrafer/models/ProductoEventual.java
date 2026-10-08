package com.mycompany.autoabrafer.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Un producto cotizado en "Eventuales". Es inmutable: se crea desde
 * CotizadorEventual.crearProducto(...) con todo el cálculo ya hecho.
 */
public class ProductoEventual {

    private final String descripcion;
    private final BigDecimal cantidad;
    private final BigDecimal costoUnitario;   // en $, con el costo extra / descuento ya aplicado
    private final BigDecimal markUpPct;       // % de mark up aplicado
    private final BigDecimal ivaPct;          // 10.5 o 21
    private final BigDecimal precioUnitario;  // sin IVA, 3 decimales

    public ProductoEventual(String descripcion, BigDecimal cantidad, BigDecimal costoUnitario,
                            BigDecimal markUpPct, BigDecimal ivaPct, BigDecimal precioUnitario) {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.markUpPct = markUpPct;
        this.ivaPct = ivaPct;
        this.precioUnitario = precioUnitario;
    }

    public String getDescripcion() { return descripcion; }
    public BigDecimal getCantidad() { return cantidad; }
    public BigDecimal getCostoUnitario() { return costoUnitario; }
    public BigDecimal getMarkUpPct() { return markUpPct; }
    public BigDecimal getIvaPct() { return ivaPct; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }

    /** Precio sin IVA por todas las unidades (redondeado a 2 decimales). */
    public BigDecimal getTotal() {
        return precioUnitario.multiply(cantidad).setScale(2, RoundingMode.HALF_UP);
    }

    /** Costo unitario por la cantidad (sin redondear, igual que el Excel modelo). */
    public BigDecimal getCostoTotal() {
        return costoUnitario.multiply(cantidad);
    }
}
