package com.mycompany.autoabrafer.logica;

import com.mycompany.autoabrafer.models.Factura;
import com.mycompany.autoabrafer.models.ItemFactura;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LectorFacturaPDF {

    // Cod. 001 = Factura A, Cod. 006 = Factura B (estándar AFIP)
    private static final Pattern PATRON_COD =
            Pattern.compile("Cod\\.\\s*0*(\\d{1,3})");
    private static final Map<String, String> MAPA_TIPO = Map.of(
            "1", "A", "6", "B", "11", "C"
    );
    
    private static final String NUM = "\\d[\\d.]*,\\d{2}";
    
    private static final Pattern PATRON_PEDIDO =
        Pattern.compile("PEDIDO\\s*(\\d+)\\s*/\\s*(\\S+)", Pattern.CASE_INSENSITIVE);
    
    private static final Pattern PATRON_CUENTA =
        Pattern.compile("Sres\\.:\\s*(\\d+)\\s*-");

    private static final Pattern PATRON_NUMERO =
            Pattern.compile("Comprobante\\s*N[°º]?\\s*:?\\s*(\\d{4,5}-\\d{6,8})",
                    Pattern.CASE_INSENSITIVE);

    private static final Pattern PATRON_FECHA =
            Pattern.compile("Fecha\\s*:?\\s*(\\d{2}/\\d{2}/\\d{4})",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    // \bTOTAL\b + $ inmediato evita matchear "Subtotal" y el header de columna "...TOTAL"
    private static final Pattern PATRON_MONTO =
            Pattern.compile("\\bTOTAL\\b\\s*\\$\\s*([\\d.,]+)");

    // Factura A: todo el ítem en una sola línea
    // CODIGO  CANT  UM  DESCRIPCION  P.LISTA  P.UNIT  TOTAL
    private static final Pattern PATRON_ITEM_A = Pattern.compile(
        "^\\s*(?:\\d+\\s+)?(\\S+)\\s+(\\d[\\d.]*,\\d+)\\s+(\\S+)\\s+(?<desc>.+?)\\s+"
        + NUM + "(?:\\s+" + NUM + ")?\\s+" + NUM + "\\s+(?<total>" + NUM + ")\\s*$");

    // Factura B: código+descripción en una línea, cantidad/precio en la siguiente
    private static final Pattern PATRON_ITEM_B_CANT =
            Pattern.compile("^\\s*([\\d.,]+)\\s+(\\S+)\\s+([\\d.,]+)\\s+([\\d.,]+)\\s*$");
    private static final Pattern PATRON_ITEM_B_DESC =
            Pattern.compile("^(\\S+)\\s+(.+)$");

    private static final SimpleDateFormat FORMATO_FECHA =
            new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "AR"));

    public String extraerTexto(File archivoPdf) throws IOException {
        try (PDDocument documento = Loader.loadPDF(archivoPdf)) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            return stripper.getText(documento);
        }
    }

    public Factura leerFactura(File archivoPdf) throws IOException {
        String texto = extraerTexto(archivoPdf);

        Factura factura = new Factura();

        String tipo = detectarTipo(texto);
        factura.setTipo(tipo);
        factura.setNumero(buscar(PATRON_NUMERO, texto));

        String fechaStr = buscar(PATRON_FECHA, texto);
        if (fechaStr != null) {
            try {
                factura.setFecha(FORMATO_FECHA.parse(fechaStr));
            } catch (ParseException e) {
                factura.setFecha(null);
            }
        }

        String montoStr = buscar(PATRON_MONTO, texto);
        if (montoStr != null) {
            factura.setMonto(Double.parseDouble(normalizarNumero(montoStr)));
        }
        String cuentaStr = buscar(PATRON_CUENTA, texto);
        factura.setCuenta(cuentaStr != null ? Integer.parseInt(cuentaStr) : 13146);

        Matcher mPedido = PATRON_PEDIDO.matcher(texto);
        if (mPedido.find()) {
            factura.setPedido(mPedido.group(1));
            factura.setEntrega(mPedido.group(2));
            factura.setCanal("WEB");
        } else {
            factura.setPedido("WPP");
            factura.setEntrega("RETIRO EN SUCURSAL");
            factura.setCanal("WPP");
        }

        List<ItemFactura> items = extraerItems(texto, tipo);
        factura.setItems(items);
        factura.setProductos(unirDescripciones(items));

        factura.setMetodo(null);

        return factura;
    }

    private String detectarTipo(String texto) {
        Matcher m = PATRON_COD.matcher(texto);
        if (m.find()) {
            return MAPA_TIPO.get(m.group(1));
        }
        return null;
    }

    private List<ItemFactura> extraerItems(String texto, String tipo) {
        if ("B".equals(tipo)) {
            return extraerItemsB(texto);
        }
        return extraerItemsA(texto); // por defecto, formato A
    }

    /** Mismo texto de siempre para la columna "Referencia": descripciones separadas por "; ". */
    private String unirDescripciones(List<ItemFactura> items) {
        List<String> descripciones = new ArrayList<>();
        for (ItemFactura it : items) {
            descripciones.add(it.getDescripcion());
        }
        return String.join("; ", descripciones);
    }

    private List<ItemFactura> extraerItemsA(String texto) {
        List<ItemFactura> items = new ArrayList<>();
        boolean enTabla = false;
        for (String linea : texto.split("\\r?\\n")) {
            if (linea.contains("CODIGO") && linea.contains("DESCRIPCION")) {
                enTabla = true;                 // arranca la tabla (también en cada página nueva)
                continue;
            }
            if (enTabla && linea.trim().startsWith("PESOS")) {
                enTabla = false;                // termina la tabla de esta página
                continue;
            }
            if (!enTabla) continue;

            Matcher m = PATRON_ITEM_A.matcher(linea);
            if (m.matches()) {
                // colapsa espacios repetidos: "TUERCA     3/8 W X U." -> "TUERCA 3/8 W X U."
                items.add(new ItemFactura(
                        m.group(1),
                        m.group("desc").trim().replaceAll("\\s+", " "),
                        parsearNumero(m.group(2)),
                        parsearNumero(m.group("total"))));
            }
        }
        return items;
    }

    private List<ItemFactura> extraerItemsB(String texto) {
        List<ItemFactura> items = new ArrayList<>();
        String[] lineas = texto.split("\\r?\\n");
        for (int i = 1; i < lineas.length; i++) {
            Matcher mCant = PATRON_ITEM_B_CANT.matcher(lineas[i]);
            if (mCant.matches()) {
                Matcher mDesc = PATRON_ITEM_B_DESC.matcher(lineas[i - 1].trim());
                if (mDesc.matches()) {
                    // cantidad / UM / precio unit. / total del renglón
                    items.add(new ItemFactura(
                            mDesc.group(1),
                            mDesc.group(2).trim(),
                            parsearNumero(mCant.group(1)),
                            parsearNumero(mCant.group(4))));
                }
            }
        }
        return items;
    }

    /** "1.234,56" -> 1234.56 ; "70" -> 70.0 ; null si no se puede leer. */
    private Double parsearNumero(String texto) {
        if (texto == null) return null;
        String limpio = texto.trim();
        if (limpio.contains(",")) {
            limpio = limpio.replace(".", "").replace(",", ".");
        }
        try {
            return Double.parseDouble(limpio);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String buscar(Pattern patron, String texto) {
        Matcher m = patron.matcher(texto);
        return m.find() ? m.group(1).trim() : null;
    }

    private String normalizarNumero(String numero) {
        return numero.replace(".", "").replace(",", ".");
    }

    // --- Prueba rápida: pasa la ruta del PDF como argumento ---
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java LectorFacturaPDF <ruta-al-pdf>");
            return;
        }
        try {
            LectorFacturaPDF lector = new LectorFacturaPDF();
            File pdf = new File(args[0]);
            System.out.println("--- TEXTO CRUDO ---");
            System.out.println(lector.extraerTexto(pdf));

            Factura f = lector.leerFactura(pdf);
            System.out.println("--- FACTURA PARSEADA ---");
            System.out.println("Tipo: " + f.getTipo());
            System.out.println("Número: " + f.getNumero());
            System.out.println("Fecha: " + f.getFecha());
            System.out.println("Monto: " + f.getMonto());
            System.out.println("Productos: " + f.getProductos());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}