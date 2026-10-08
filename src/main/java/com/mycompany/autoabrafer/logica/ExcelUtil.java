package com.mycompany.autoabrafer.logica;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Utilidades para trabajar con un Excel que ya existe (agregar filas al final
 * en vez de pisar el archivo). Las usan ExcelGenerator y ExcelEventualesGenerator.
 */
public final class ExcelUtil {

    private ExcelUtil() {
    }

    /** Abre un .xlsx existente para poder agregarle filas. */
    public static Workbook abrir(File archivo) throws IOException {
        try (FileInputStream fis = new FileInputStream(archivo)) {
            return new XSSFWorkbook(fis);
        } catch (IllegalArgumentException e) {
            // POI lanza esto cuando el archivo no es un .xlsx válido (ej: .xls viejo o dañado)
            throw new IOException("El archivo no es un Excel .xlsx válido.", e);
        }
    }

    /**
     * Índice (0 = fila 1) de la última fila que tiene algún dato, o -1 si la hoja está vacía.
     * Ignora filas que solo tienen formato o celdas vacías, así no se agrega
     * el contenido nuevo después de un montón de filas en blanco.
     */
    public static int ultimaFilaConDatos(Sheet hoja) {
        for (int i = hoja.getLastRowNum(); i >= 0; i--) {
            Row fila = hoja.getRow(i);
            if (fila == null) {
                continue;
            }
            for (Cell celda : fila) {
                if (tieneDato(celda)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static boolean tieneDato(Cell celda) {
        CellType tipo = celda.getCellType();
        if (tipo == CellType.BLANK) {
            return false;
        }
        if (tipo == CellType.STRING) {
            return !celda.getStringCellValue().trim().isEmpty();
        }
        return true;
    }

    /**
     * Guarda el libro de forma segura: escribe primero en un archivo temporal y recién
     * al terminar lo mueve sobre el destino. Si algo falla a mitad de camino (o el archivo
     * está abierto en Excel) el original no queda a medio escribir ni corrupto.
     */
    public static void guardar(Workbook libro, File destino) throws IOException {
        File carpeta = destino.getAbsoluteFile().getParentFile();
        File temporal = File.createTempFile("autoabrafer_", ".xlsx", carpeta);
        try {
            try (FileOutputStream fos = new FileOutputStream(temporal)) {
                libro.write(fos);
            }
            Files.move(temporal.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } finally {
            temporal.delete(); // si el move salió bien ya no existe; no pasa nada
        }
    }
}
