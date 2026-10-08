package com.mycompany.autoabrafer.logica;

import com.mycompany.autoabrafer.models.ProductoEventual;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Cálculo de cotización para "Eventuales" (replica del Excel de cotización):
 *
 *   Costo unitario = Costo + (Costo * CostoExtra%)      (transporte, etc. / negativo = descuento)
 *   Precio sin IVA = Costo unitario + (Costo unitario * MarkUp%)
 *   Precio c/IVA   = Precio sin IVA * 1,105  |  * 1,21
 *
 * Decimales (iguales al Excel modelo): costo unitario 4, precios 3, totales 2.
 *
 * Los porcentajes se reciben "en porcentaje" (2 = 2%, 40 = 40%).
 * - El costo extra puede ser negativo: -15 aplica un 15% de descuento.
 * - Costo extra y mark up son opcionales en las versiones con String: vacío = 0.
 * - El costo es obligatorio.
 */
public class CotizadorEventual {

    private static final BigDecimal CIEN = new BigDecimal("100");
    private static final BigDecimal IVA_105 = new BigDecimal("1.105");
    private static final BigDecimal IVA_21 = new BigDecimal("1.21");
    private static final int DECIMALES_COSTO = 4;
    private static final int DECIMALES_PRECIO = 3;

    /** Resultado del cálculo para un producto. */
    public static class Cotizacion {
        private final BigDecimal subtotal;      // costo unitario (costo + extra/desc)
        private final BigDecimal markUpPct;
        private final BigDecimal precioSinIva;
        private final BigDecimal precioIva105;
        private final BigDecimal precioIva21;

        Cotizacion(BigDecimal subtotal, BigDecimal markUpPct, BigDecimal precioSinIva,
                   BigDecimal precioIva105, BigDecimal precioIva21) {
            this.subtotal = subtotal;
            this.markUpPct = markUpPct;
            this.precioSinIva = precioSinIva;
            this.precioIva105 = precioIva105;
            this.precioIva21 = precioIva21;
        }

        /** Costo unitario ya con el costo extra o descuento aplicado. */
        public BigDecimal getSubtotal() { return subtotal; }
        public BigDecimal getMarkUpPct() { return markUpPct; }
        public BigDecimal getPrecioSinIva() { return precioSinIva; }
        public BigDecimal getPrecioIva105() { return precioIva105; }
        public BigDecimal getPrecioIva21() { return precioIva21; }
    }

    /**
     * @param costo         costo unitario del producto en $
     * @param costoExtraPct % de costo extra (transporte, etc.); negativo = descuento (ej: -15)
     * @param markUpPct     % de mark up, ej: 40
     */
    public Cotizacion calcular(BigDecimal costo, BigDecimal costoExtraPct, BigDecimal markUpPct) {
        if (costo == null || costoExtraPct == null || markUpPct == null) {
            throw new IllegalArgumentException("Costo, costo extra y mark up son obligatorios.");
        }
        if (costo.signum() < 0 || markUpPct.signum() < 0) {
            throw new IllegalArgumentException("El costo y el mark up no pueden ser negativos.");
        }
        // El costo extra puede ser negativo (descuento), pero no -100% o menos
        if (costoExtraPct.compareTo(new BigDecimal("-100")) <= 0) {
            throw new IllegalArgumentException("El descuento no puede ser 100% o más.");
        }

        BigDecimal costoUnitario = redondear(aplicarPorcentaje(costo, costoExtraPct), DECIMALES_COSTO);
        BigDecimal sinIva = redondear(aplicarPorcentaje(costoUnitario, markUpPct), DECIMALES_PRECIO);

        return new Cotizacion(
                costoUnitario,
                markUpPct,
                sinIva,
                redondear(sinIva.multiply(IVA_105), DECIMALES_PRECIO),
                redondear(sinIva.multiply(IVA_21), DECIMALES_PRECIO));
    }

    /** Versión cómoda para leer directo de los JTextField de la IGU. */
    public Cotizacion calcular(String costo, String costoExtraPct, String markUpPct) {
        return calcular(parsear(costo), parsearOpcional(costoExtraPct), parsearOpcional(markUpPct));
    }

    /**
     * Igual que calcular(), pero el costo viene en USD: primero se convierte a pesos
     * multiplicando por la cotización del dólar y después sigue el mismo cálculo.
     */
    public Cotizacion calcularDesdeDolares(String costoUsd, String cotizacionDolar,
                                           String costoExtraPct, String markUpPct) {
        BigDecimal dolar = parsear(cotizacionDolar);
        if (dolar.signum() <= 0) {
            throw new IllegalArgumentException("La cotización del dólar debe ser mayor a 0.");
        }
        BigDecimal costoPesos = parsear(costoUsd).multiply(dolar);
        return calcular(costoPesos, parsearOpcional(costoExtraPct), parsearOpcional(markUpPct));
    }

    /**
     * Arma un producto para la lista de eventuales a partir de una cotización ya calculada.
     *
     * @param ivaPct condición de IVA elegida: 10.5 o 21
     */
    public ProductoEventual crearProducto(String descripcion, String cantidad,
                                          Cotizacion cotizacion, BigDecimal ivaPct) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("Ingresá el título del producto.");
        }
        if (cantidad == null || cantidad.trim().isEmpty()) {
            throw new IllegalArgumentException("Ingresá la cantidad.");
        }
        BigDecimal cant = parsear(cantidad);
        if (cant.signum() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }
        if (cotizacion == null) {
            throw new IllegalArgumentException("Falta calcular la cotización.");
        }
        return new ProductoEventual(
                descripcion.trim().replaceAll("\\s+", " "),
                cant,
                cotizacion.getSubtotal(),
                cotizacion.getMarkUpPct(),
                ivaPct,
                cotizacion.getPrecioSinIva());
    }

    // base + (base * pct / 100), sin redondear en los pasos intermedios
    private BigDecimal aplicarPorcentaje(BigDecimal base, BigDecimal pct) {
        return base.add(base.multiply(pct).divide(CIEN));
    }

    private BigDecimal redondear(BigDecimal valor, int decimales) {
        return valor.setScale(decimales, RoundingMode.HALF_UP);
    }

    /** Igual que parsear(), pero un campo vacío vale 0 (para costo extra y mark up). */
    private BigDecimal parsearOpcional(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return parsear(texto);
    }

    /** Acepta "10000", "10000.50" y también formato AR: "10.000,50" o "10000,50". */
    private BigDecimal parsear(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("Hay un campo vacío.");
        }
        String limpio = texto.trim().replace("$", "").replace("%", "").replace(" ", "");
        if (limpio.contains(",")) {
            limpio = limpio.replace(".", "").replace(",", ".");
        }
        try {
            return new BigDecimal(limpio);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Número inválido: " + texto);
        }
    }

    // --- Prueba rápida ---
    // Ejemplo 1: 10000, 2, 40   -> 10200 / 14280 / 15779,4 / 17278,8
    // Ejemplo 2: 10000, -15, 40 -> 8500  / 11900 / 13149,5 / 14399
    public static void main(String[] args) {
        CotizadorEventual cot = new CotizadorEventual();
        for (String[] caso : new String[][]{{"10000", "2", "40"}, {"10000", "-15", "40"}}) {
            Cotizacion c = cot.calcular(caso[0], caso[1], caso[2]);
            System.out.println("Costo " + caso[0] + " | extra " + caso[1] + "% | mark up " + caso[2] + "%");
            System.out.println("  Costo unit.:   " + c.getSubtotal());
            System.out.println("  Sin IVA:       " + c.getPrecioSinIva());
            System.out.println("  Con IVA 10,5%: " + c.getPrecioIva105());
            System.out.println("  Con IVA 21%:   " + c.getPrecioIva21());
        }
    }
}
