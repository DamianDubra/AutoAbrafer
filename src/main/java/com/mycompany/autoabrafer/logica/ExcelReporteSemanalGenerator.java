package com.mycompany.autoabrafer.logica;

import com.mycompany.autoabrafer.models.Factura;
import com.mycompany.autoabrafer.models.ItemFactura;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Genera/actualiza el reporte semanal con el mismo formato que Reporte_semanal_web.xlsx:
 * fila 1 vacía, encabezado en la fila 2 (fondo amarillo) y un renglón por producto vendido:
 *
 *   A Sku | B Productos + vendidos | C Monto | D Unidades | E Canal (WEB / WPP)
 *
 * No arma hojas por semana: todo va en UNA hoja ("Reporte semanal") y cada exportación
 * agrega los renglones al final. Si se elige un Excel existente (por ejemplo el de las
 * semanas), no se toca ninguna de sus hojas: se usa/crea la hoja "Reporte semanal".
 */
public class ExcelReporteSemanalGenerator {

    private static final String NOMBRE_HOJA = "Reporte semanal";
    private static final String[] COLUMNAS = {
        "Sku", "Productos + vendidos", "Monto", "Unidades", "Canal"
    };
    private static final double[] ANCHOS = {16, 51.25, 16, 12, 10};

    private static final int FILA_HEADER = 1; // fila 2 (0 = fila 1)

    /** @return cantidad de renglones (productos) agregados */
    public int generar(List<Factura> facturas, File destino, boolean agregar) throws IOException {
        boolean existente = agregar && destino.exists() && destino.length() > 0;
        int agregados = 0;

        try (Workbook libro = existente ? ExcelUtil.abrir(destino) : new XSSFWorkbook()) {
            Sheet hoja = libro.getSheet(NOMBRE_HOJA);
            if (hoja == null) {
                hoja = libro.createSheet(NOMBRE_HOJA);
            }

            CellStyle estiloHeader = estiloColor(libro, 0xFF, 0xE5, 0x99, null);
            CellStyle estiloSku = estiloColor(libro, 0xFF, 0xFF, 0xFF, "@");
            CellStyle estiloMonto = estiloColor(libro, 0xFF, 0xFF, 0xFF, "\"$\"#,##0.00");
            CellStyle estiloUnidades = estiloColor(libro, 0xF0, 0xF0, 0xF0, "#,##0.00");

            int ultima = ExcelUtil.ultimaFilaConDatos(hoja);
            int numFila;
            if (ultima < FILA_HEADER) {
                Row header = hoja.createRow(FILA_HEADER);
                for (int i = 0; i < COLUMNAS.length; i++) {
                    Cell c = header.createCell(i);
                    c.setCellValue(COLUMNAS[i]);
                    c.setCellStyle(estiloHeader);
                }
                numFila = FILA_HEADER + 1;
            } else {
                numFila = ultima + 1;
            }

            for (Factura f : facturas) {
                String canal = f.getCanal() != null ? f.getCanal() : "WPP";
                for (ItemFactura it : f.getItems()) {
                    Row fila = hoja.createRow(numFila++);

                    Cell sku = fila.createCell(0);
                    sku.setCellValue(it.getCodigo() != null ? it.getCodigo() : "");
                    sku.setCellStyle(estiloSku);

                    fila.createCell(1).setCellValue(it.getDescripcion() != null ? it.getDescripcion() : "");

                    Cell monto = fila.createCell(2);
                    if (it.getTotal() != null) {
                        monto.setCellValue(it.getTotal());
                    }
                    monto.setCellStyle(estiloMonto);

                    Cell unidades = fila.createCell(3);
                    if (it.getCantidad() != null) {
                        unidades.setCellValue(it.getCantidad());
                    }
                    unidades.setCellStyle(estiloUnidades);

                    fila.createCell(4).setCellValue(canal);
                    agregados++;
                }
            }

            if (!existente || hoja.getColumnWidth(1) < 20 * 256) {
                for (int i = 0; i < ANCHOS.length; i++) {
                    hoja.setColumnWidth(i, (int) Math.round(ANCHOS[i] * 256));
                }
            }

            ExcelUtil.guardar(libro, destino);
        }
        return agregados;
    }

    private CellStyle estiloColor(Workbook libro, int r, int g, int b, String formato) {
        XSSFCellStyle estilo = (XSSFCellStyle) libro.createCellStyle();
        estilo.setFillForegroundColor(new XSSFColor(new byte[]{(byte) r, (byte) g, (byte) b}, null));
        estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        if (formato != null) {
            estilo.setDataFormat(libro.createDataFormat().getFormat(formato));
        }
        return estilo;
    }
}
