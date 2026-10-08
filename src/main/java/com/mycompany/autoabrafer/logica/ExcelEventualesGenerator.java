package com.mycompany.autoabrafer.logica;

import com.mycompany.autoabrafer.models.ProductoEventual;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Exporta los productos de "Eventuales" con el mismo formato que el Excel modelo
 * (18 columnas, de A a R, empezando en A1, con el encabezado en negrita).
 *
 * Columnas que se completan:
 *   B CANTIDAD | C DESCRIPCION | D P.DE LISTA | F PRECIO | H P.U.REAL (los tres = precio sin IVA)
 *   I TOTAL (precio sin IVA x cantidad) | J COSTO UNIT. | K COSTO TOTAL
 *   O % APLICADO (mark up) | P % IVA (10,5 o 21)
 * Quedan vacías: A, E, G, L, M, N, Q, R.
 */
public class ExcelEventualesGenerator {

    private static final String[] COLUMNAS = {
        "ARTICULO", "CANTIDAD", "DESCRIPCION", "P.DE LISTA", "% DESC", "PRECIO",
        "% DESC.GRAL.", "P.U.REAL", "TOTAL", "COSTO UNIT.", "COSTO TOTAL", "%ING.BR.",
        "$ING.BR.", "GANANCIA", "% APLICADO", "% IVA", "Nº SERIE/LOTE", "F.PROMETIDA",
        "PROVEEDOR", "CLIENTE"
    };

    // Anchos en caracteres (los del modelo; TOTAL, COSTO UNIT. y % IVA un poco más anchos
    // para que los importes grandes no se vean como ####)
    private static final double[] ANCHOS = {
        10.7, 10.4, 41.1, 11.3, 8, 8, 14.3, 9.6, 14, 13, 14.3, 9.4, 9, 10.7,
        12.6, 8, 14.4, 13.7, 20, 20
    };

    private static final String FMT_2 = "###,###,###,##0.00";
    private static final String FMT_3 = "###,###,###,##0.000";
    private static final String FMT_1 = "###,###,###,##0.0";

    // Índices de columna (0 = A)
    private static final int COL_CANTIDAD = 1, COL_DESCRIPCION = 2, COL_LISTA = 3, COL_PRECIO = 5,
        COL_PU_REAL = 7, COL_TOTAL = 8, COL_COSTO_UNIT = 9, COL_COSTO_TOTAL = 10,
        COL_MARKUP = 14, COL_IVA = 15,
        COL_PROVEEDOR = 18, COL_CLIENTE = 19;


    public void generar(List<ProductoEventual> productos,String proveedorTexto,String clienteTexto, File archivoDestino,boolean agregar) throws IOException {
        boolean existente = agregar
                && archivoDestino.exists()
                && archivoDestino.length() > 0;

        try (Workbook workbook = existente
                ? ExcelUtil.abrir(archivoDestino)
                : new XSSFWorkbook()) {

            Sheet hoja = workbook.getNumberOfSheets() > 0
                    ? workbook.getSheetAt(0)
                    : workbook.createSheet("Hoja1");

            CellStyle estiloHeader = crearEstilo(workbook, null, true);
            CellStyle estiloTexto = crearEstilo(workbook, "@", false);
            CellStyle estilo2 = crearEstilo(workbook, FMT_2, false);
            CellStyle estilo3 = crearEstilo(workbook, FMT_3, false);
            CellStyle estilo1 = crearEstilo(workbook, FMT_1, false);

            int numFila;

            if (!existente) {
                Row filaHeader = hoja.createRow(0);

                for (int i = 0; i < COLUMNAS.length; i++) {
                    Cell celda = filaHeader.createCell(i);
                    celda.setCellValue(COLUMNAS[i]);
                    celda.setCellStyle(estiloHeader);
                }

                numFila = 1;
            } else {
                int ultimaFila = ExcelUtil.ultimaFilaConDatos(hoja);
                numFila = ultimaFila + 1;
            }
            for (ProductoEventual p : productos) {
                Row fila = hoja.createRow(numFila++);

                numero(fila, COL_CANTIDAD, p.getCantidad(), estilo2);

                Cell descripcion = fila.createCell(COL_DESCRIPCION);
                descripcion.setCellValue(p.getDescripcion());
                descripcion.setCellStyle(estiloTexto);

                // P.DE LISTA, PRECIO y P.U.REAL llevan el mismo valor: precio unitario sin IVA
                numero(fila, COL_LISTA, p.getPrecioUnitario(), estilo3);
                numero(fila, COL_PRECIO, p.getPrecioUnitario(), estilo3);
                numero(fila, COL_PU_REAL, p.getPrecioUnitario(), estilo3);

                numero(fila, COL_TOTAL, p.getTotal(), estilo2);
                numero(fila, COL_COSTO_UNIT, p.getCostoUnitario(), estilo2);
                numero(fila, COL_COSTO_TOTAL, p.getCostoTotal(), estilo2);
                numero(fila, COL_MARKUP, p.getMarkUpPct(), estilo2);
                numero(fila, COL_IVA, p.getIvaPct(), estilo1);
                Cell proveedor = fila.createCell(COL_PROVEEDOR);
                proveedor.setCellValue(proveedorTexto);
                proveedor.setCellStyle(estiloTexto);

                Cell cliente = fila.createCell(COL_CLIENTE);
                cliente.setCellValue(clienteTexto);
                cliente.setCellStyle(estiloTexto);
            }

            for (int i = 0; i < ANCHOS.length; i++) {
                hoja.setColumnWidth(i, (int) Math.round(ANCHOS[i] * 256));
            }

            ExcelUtil.guardar(workbook, archivoDestino);
        }
    }

    private void numero(Row fila, int columna, BigDecimal valor, CellStyle estilo) {
        Cell celda = fila.createCell(columna);
        celda.setCellValue(valor.doubleValue());
        celda.setCellStyle(estilo);
    }

    private CellStyle crearEstilo(Workbook workbook, String formato, boolean negrita) {
        CellStyle estilo = workbook.createCellStyle();
        if (formato != null) {
            estilo.setDataFormat(workbook.createDataFormat().getFormat(formato));
        }
        if (negrita) {
            Font fuente = workbook.createFont();
            fuente.setBold(true);
            estilo.setFont(fuente);
        }
        return estilo;
    }
}
