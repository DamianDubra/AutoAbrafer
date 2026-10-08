package com.mycompany.autoabrafer.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Factura {
    private String tipo;
    private String numero;
    private Date fecha;
    private Double monto;
    private String metodo;
    private Integer cuenta;
    private String productos;
    private String pedido;
    private String entrega;
    private String canal;                                   // "WEB" o "WPP"
    private List<ItemFactura> items = new ArrayList<>(); // renglones, para el reporte semanal

    public Factura() {
    }

    public Factura(String tipo, String numero, Date fecha, Double monto, String metodo, int cuenta, String productos) {
        this.tipo = tipo;
        this.numero = numero;
        this.fecha = fecha;
        this.monto = monto;
        this.metodo = metodo;
        this.cuenta = cuenta;
        this.productos = productos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public List<ItemFactura> getItems() {
        return items;
    }

    public void setItems(List<ItemFactura> items) {
        this.items = items != null ? items : new ArrayList<>();
    }

   
    
}
