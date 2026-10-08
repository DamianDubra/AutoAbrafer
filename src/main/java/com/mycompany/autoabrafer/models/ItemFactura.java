package com.mycompany.autoabrafer.models;

/**
 * Un renglón de una factura: lo que necesita el reporte semanal
 * (SKU, descripción, cantidad y total del renglón).
 */
public class ItemFactura {

    private final String codigo;
    private final String descripcion;
    private final Double cantidad;
    private final Double total;

    public ItemFactura(String codigo, String descripcion, Double cantidad, Double total) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.total = total;
    }

    public String getCodigo() { return codigo; }
    public String getDescripcion() { return descripcion; }
    public Double getCantidad() { return cantidad; }
    public Double getTotal() { return total; }
}
