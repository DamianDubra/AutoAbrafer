package com.mycompany.autoabrafer.logica;

import com.mycompany.autoabrafer.models.Factura;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExcelGenerator {

    private static final String[] COLUMNAS = {
        "Fecha de Compra", "N° Pedido", "Factura", "Importe",
        "Medio de Pago", "N° Cuenta", "Referencia", "Entrega",
        "Status", "Observaciones"
    };
    private static final int COL_INICIO = 1; // columna B (0=A, 1=B)

    /** Genera un Excel nuevo (si el archivo existe, lo reemplaza). */
    public void generar(List<Factura> facturas, File archivoDestino) throws IOException {
        generar(facturas, archivoDestino, false);
    }

    /**
     * @param agregar true = si el archivo ya existe, le agrega las facturas al final
     *                sin tocar lo que ya tiene; false = crea un Excel nuevo (reemplaza).
     */
    public void generar(List<Factura> facturas, File archivoDestino, boolean agregar) throws IOException {
        boolean existente = agregar && archivoDestino.exists() && archivoDestino.length() > 0;

        try (Workbook workbook = existente ? ExcelUtil.abrir(archivoDestino) : new XSSFWorkbook()) {
            Sheet hoja = workbook.getNumberOfSheets() > 0
                    ? workbook.getSheetAt(0)
                    : workbook.createSheet("Facturas");

            // Los estilos se crean en ESTE libro (no se pueden reusar entre libros distintos)
            CellStyle estiloHeader = crearEstiloHeader(workbook);
            CellStyle estiloMoneda = crearEstiloMoneda(workbook);
            CellStyle estiloFecha = crearEstiloFecha(workbook);

            int ultimaFila = ExcelUtil.ultimaFilaConDatos(hoja);
            int numFila;
            if (ultimaFila < 0) {
                // Hoja vacía (archivo nuevo): se escribe el encabezado
                Row filaHeader = hoja.createRow(0);
                for (int i = 0; i < COLUMNAS.length; i++) {
                    Cell celda = filaHeader.createCell(COL_INICIO + i);
                    celda.setCellValue(COLUMNAS[i]);
                    celda.setCellStyle(estiloHeader);
                }
                numFila = 1;
            } else {
                // Excel con datos: se sigue a continuación de la última fila
                numFila = ultimaFila + 1;
            }

            for (Factura f : facturas) {
                Row fila = hoja.createRow(numFila++);

                Cell celdaFecha = fila.createCell(COL_INICIO); // Fecha de Compra
                if (f.getFecha() != null) {
                    celdaFecha.setCellValue(f.getFecha());
                    celdaFecha.setCellStyle(estiloFecha);
                }

                fila.createCell(COL_INICIO + 1)
                        .setCellValue(f.getPedido() != null ? f.getPedido() : "");       // N° Pedido
                fila.createCell(COL_INICIO + 2)
                        .setCellValue(f.getNumero() != null ? f.getNumero() : "");        // Factura

                Cell celdaMonto = fila.createCell(COL_INICIO + 3); // Importe
                if (f.getMonto() != null) {
                    celdaMonto.setCellValue(f.getMonto());
                    celdaMonto.setCellStyle(estiloMoneda);
                }

                fila.createCell(COL_INICIO + 4)
                        .setCellValue(f.getMetodo() != null ? f.getMetodo() : "");        // Medio de Pago (manual)

                if (f.getCuenta() != null) {
                    fila.createCell(COL_INICIO + 5).setCellValue(f.getCuenta());          // N° Cuenta
                }

                fila.createCell(COL_INICIO + 6)
                        .setCellValue(f.getProductos() != null ? f.getProductos() : "");  // Referencia
                fila.createCell(COL_INICIO + 7)
                        .setCellValue(f.getEntrega() != null ? f.getEntrega() : "");       // Entrega
                fila.createCell(COL_INICIO + 8).setCellValue("");                          // Status (manual)
                fila.createCell(COL_INICIO + 9).setCellValue("");                          // Observaciones (manual)
            }

            // Solo se ajustan los anchos en archivos nuevos: en uno existente se respeta lo que armó el usuario
            if (!existente) {
                for (int i = 0; i < COLUMNAS.length; i++) {
                    hoja.autoSizeColumn(COL_INICIO + i);
                }
            }

            ExcelUtil.guardar(workbook, archivoDestino);
        }
    }

    private CellStyle crearEstiloHeader(Workbook workbook) {
        Font fuente = workbook.createFont();
        fuente.setBold(true);
        fuente.setColor(IndexedColors.WHITE.getIndex());

        CellStyle estilo = workbook.createCellStyle();
        estilo.setFont(fuente);
        estilo.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return estilo;
    }

    private CellStyle crearEstiloMoneda(Workbook workbook) {
        CellStyle estilo = workbook.createCellStyle();
        DataFormat formato = workbook.createDataFormat();
        estilo.setDataFormat(formato.getFormat("$ #,##0.00"));
        return estilo;
    }

    private CellStyle crearEstiloFecha(Workbook workbook) {
        CellStyle estilo = workbook.createCellStyle();
        CreationHelper helper = workbook.getCreationHelper();
        estilo.setDataFormat(helper.createDataFormat().getFormat("dd/mm/yyyy"));
        return estilo;
    }
}