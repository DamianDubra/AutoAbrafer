package com.mycompany.autoabrafer.IGU;

import com.mycompany.autoabrafer.logica.CotizadorEventual;
import com.mycompany.autoabrafer.logica.ExcelEventualesGenerator;
import com.mycompany.autoabrafer.models.ProductoEventual;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class Eventuales extends javax.swing.JFrame {

    private final CotizadorEventual cotizador = new CotizadorEventual();

    // Lista de productos cargados (es lo que se exporta) y su tabla en pantalla
    private final List<ProductoEventual> productos = new ArrayList<>();
    private File excelCargado;
    private final DefaultTableModel modeloTabla = new DefaultTableModel(
            new Object[]{"Descripción", "Cantidad", "Costo unit.", "Markup %",
                         "Precio unit. s/IVA", "Total s/IVA", "IVA %"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false; // la tabla es solo para mostrar
        }
    };

    private final NumberFormat monedaPrecio = moneda(3);  // precios: hasta 3 decimales
    private final NumberFormat monedaCosto = moneda(4);   // costo unitario: hasta 4 decimales
    private final NumberFormat numero = NumberFormat.getNumberInstance(new Locale("es", "AR"));

    // Última cotización válida calculada con los datos de pantalla (null si faltan datos)
    private CotizadorEventual.Cotizacion ultimaCotizacion = null;

    // Cotización del dólar (se pide al tildar "Dolar")
    private String cotizacionDolar = "";

    // Componentes que se crean por código en construirPanelProductos()
    // (el título y la cantidad están en el diseñador: descripcionTxt y cantidadTxt)
    private JComboBox<String> ivaCmb;
    private JButton btnAgregar;
    private JButton btnQuitar;
    private JButton btnExportar;
    private JTable tablaProductos;

    public Eventuales() {
        initComponents();
        // Al cerrar esta ventana NO se cierra toda la aplicación
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        construirPanelProductos();
        configurarCalculo();
        pack();
    }

    private static NumberFormat moneda(int maxDecimales) {
        NumberFormat f = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
        f.setMinimumFractionDigits(2);
        f.setMaximumFractionDigits(maxDecimales);
        return f;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        costoTxt = new CampoConHint("Ingrese costo del producto");
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        costoExtTxt = new CampoConHint("% de costo extra o descuento");
        markUpTxt = new CampoConHint("Margen de ganancia %");
        dlarTxt = new javax.swing.JLabel();
        isdolarChb = new javax.swing.JCheckBox();
        btnCancelar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cantidadTxt = new CampoConHint("Cantidad");
        jLabel7 = new javax.swing.JLabel();
        descripcionTxt = new CampoConHint("Título del producto");
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        clienteTxt = new CampoConHint("Ingrese el nombre del cliente");
        proveedorTxt = new CampoConHint("Ingrese el nombre del proveedor");
        jPanel3 = new javax.swing.JPanel();
        iva10 = new javax.swing.JLabel();
        iva21 = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        CargarExcel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setText("AutoAbrafer");

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        jLabel2.setText("Costo sin iva");

        jLabel3.setText("Costo adicional %");

        jLabel4.setText("Subtotal");

        jLabel5.setText("Mark up %");

        costoExtTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costoExtTxtActionPerformed(evt);
            }
        });

        dlarTxt.setText("Dolar");

        isdolarChb.setBackground(new java.awt.Color(153, 153, 153));
        isdolarChb.setText("dolarTxt");

        btnCancelar.setBackground(new java.awt.Color(102, 102, 102));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel6.setText("Titulo del producto");

        jLabel7.setText("Cantidad");

        descripcionTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descripcionTxtActionPerformed(evt);
            }
        });

        jLabel8.setText("Proveedor");

        jLabel9.setText("Cliente");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                                .addComponent(costoExtTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(costoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(dlarTxt))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(markUpTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(isdolarChb)
                                    .addComponent(cantidadTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(descripcionTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(clienteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(proveedorTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(24, 24, 24))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(costoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(costoExtTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(markUpTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dlarTxt)
                    .addComponent(isdolarChb))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(descripcionTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cantidadTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(proveedorTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(clienteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));

        iva10.setText("Iva 10,5%");

        iva21.setText("Iva 21%");

        total.setText("Total sin iva");

        CargarExcel.setBackground(new java.awt.Color(102, 102, 102));
        CargarExcel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        CargarExcel.setForeground(new java.awt.Color(255, 255, 255));
        CargarExcel.setText("Cargar Excel");
        CargarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CargarExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(total)
                    .addComponent(iva21)
                    .addComponent(iva10))
                .addContainerGap(338, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(CargarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(iva10)
                .addGap(18, 18, 18)
                .addComponent(iva21)
                .addGap(18, 18, 18)
                .addComponent(total)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(CargarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(311, 311, 311))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void costoExtTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costoExtTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_costoExtTxtActionPerformed

    private void descripcionTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descripcionTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_descripcionTxtActionPerformed

    private void CargarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CargarExcelActionPerformed
        JFileChooser selector = new JFileChooser();

            selector.setDialogTitle("Seleccionar Excel");
            selector.setFileFilter(new FileNameExtensionFilter(
                "Archivos Excel (*.xlsx)", "xlsx"
            ));

            int resultado = selector.showOpenDialog(this);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                excelCargado = selector.getSelectedFile();

                JOptionPane.showMessageDialog(this,
                    "Excel cargado correctamente:\n" + excelCargado.getAbsolutePath(),
                    "Excel cargado",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
    }//GEN-LAST:event_CargarExcelActionPerformed

    // ------------------------------------------------------------------
    // Todo lo de abajo está fuera de la zona generada por NetBeans
    // ------------------------------------------------------------------

    /**
     * Crea por código el combo de IVA, los botones y la tabla,
     * y los pone debajo de la pantalla diseñada en NetBeans (jPanel1).
     */
    

    private void construirPanelProductos() {

        ivaCmb = new JComboBox<>(
                new DefaultComboBoxModel<>(
                        new String[]{"IVA 21%", "IVA 10,5%"}
                )
        );

        btnAgregar = new JButton("Agregar a la lista");
        btnQuitar = new JButton("Quitar seleccionado");
        btnExportar = new JButton("Exportar Excel");

        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setFillsViewportHeight(true);

        // COLORES DE LA INTERFAZ SUPERIOR
        java.awt.Color gris = new java.awt.Color(153, 153, 153);
        java.awt.Color grisOscuro = new java.awt.Color(102, 102, 102);

        // =========================
        // ESTILO BOTONES
        // =========================

        btnAgregar.setBackground(grisOscuro);
        btnAgregar.setForeground(java.awt.Color.WHITE);
        btnAgregar.setFont(new java.awt.Font("Segoe UI", 0, 12));
        btnAgregar.setFocusPainted(false);
        btnAgregar.setBorderPainted(false);

        btnQuitar.setBackground(grisOscuro);
        btnQuitar.setForeground(java.awt.Color.WHITE);
        btnQuitar.setFont(new java.awt.Font("Segoe UI", 0, 12));
        btnQuitar.setFocusPainted(false);
        btnQuitar.setBorderPainted(false);

        btnExportar.setBackground(grisOscuro);
        btnExportar.setForeground(java.awt.Color.WHITE);
        btnExportar.setFont(new java.awt.Font("Segoe UI", 0, 12));
        btnExportar.setFocusPainted(false);
        btnExportar.setBorderPainted(false);

        // =========================
        // ESTILO COMBO IVA
        // =========================

        ivaCmb.setFont(new java.awt.Font("Segoe UI", 0, 12));
        ivaCmb.setBackground(java.awt.Color.WHITE);

        // =========================
        // ESTILO TABLA
        // =========================

        tablaProductos.setFont(new java.awt.Font("Segoe UI", 0, 12));
        tablaProductos.setRowHeight(24);
        tablaProductos.setBackground(java.awt.Color.WHITE);

        tablaProductos.getTableHeader().setFont(
                new java.awt.Font("Segoe UI", 0, 12)
        );

        tablaProductos.getTableHeader().setBackground(gris);
        tablaProductos.getTableHeader().setForeground(java.awt.Color.BLACK);

        // =========================
        // FILA SUPERIOR
        // =========================

        JPanel filaCarga = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 8, 4)
        );

        filaCarga.setBackground(gris);

        // SE MANTIENE EXACTAMENTE COMO ESTABA
        filaCarga.add(new javax.swing.JLabel("Condición de IVA:"));
        filaCarga.add(ivaCmb);
        filaCarga.add(btnAgregar);

        // =========================
        // TABLA
        // =========================

        JScrollPane scroll = new JScrollPane(tablaProductos);

        scroll.setPreferredSize(new Dimension(800, 180));
        scroll.setBackground(gris);
        scroll.getViewport().setBackground(java.awt.Color.WHITE);

        // =========================
        // BOTONES INFERIORES
        // =========================

        JPanel filaBotones = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );

        filaBotones.setBackground(gris);

        filaBotones.add(btnQuitar);
        filaBotones.add(btnExportar);

        // =========================
        // PANEL GENERAL
        // =========================

        JPanel panel = new JPanel(
                new BorderLayout(0, 6)
        );

        panel.setBackground(gris);

        panel.setBorder(
                BorderFactory.createEmptyBorder(6, 12, 12, 12)
        );

        panel.add(filaCarga, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(filaBotones, BorderLayout.SOUTH);

        // =========================
        // SE AGREGA ABAJO
        // =========================

        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());

        getContentPane().add(jPanel1, BorderLayout.CENTER);
        getContentPane().add(panel, BorderLayout.SOUTH);
    }


    private void configurarCalculo() {
        DocumentListener recalcular = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { calcular(); }
            @Override public void removeUpdate(DocumentEvent e) { calcular(); }
            @Override public void changedUpdate(DocumentEvent e) { calcular(); }
        };

        for (JTextField campo : new JTextField[]{costoTxt, costoExtTxt, markUpTxt}) {
            campo.getDocument().addDocumentListener(recalcular);
        }

        isdolarChb.setText("Costo en USD");
        isdolarChb.addActionListener(e -> alternarDolar());

        btnAgregar.addActionListener(e -> agregarProducto());
        btnQuitar.addActionListener(e -> quitarProducto());
        btnExportar.addActionListener(e -> exportarExcel());
    }

    /** Al tildar "Dolar" pide la cotización; al destildar vuelve a trabajar en pesos. */
    private void alternarDolar() {
        if (isdolarChb.isSelected()) {
            String ingresado = JOptionPane.showInputDialog(this,
                    "Cotización del dólar:", cotizacionDolar.isEmpty() ? "1500" : cotizacionDolar);

            if (ingresado == null || !cotizacionValida(ingresado)) {
                if (ingresado != null) {
                    JOptionPane.showMessageDialog(this, "Cotización inválida: " + ingresado,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                isdolarChb.setSelected(false); // canceló o puso algo inválido
                isdolarChb.setText("Costo en USD");
                calcular();
                return;
            }
            cotizacionDolar = ingresado.trim();
            isdolarChb.setText("Costo en USD (cotiz. " + cotizacionDolar + ")");
        } else {
            isdolarChb.setText("Costo en USD");
        }
        calcular();
    }

    private boolean cotizacionValida(String texto) {
        try {
            cotizador.calcularDesdeDolares("1", texto, "0", "0");
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /** Recalcula los resultados en pantalla (se ejecuta al escribir en los campos). */
    private void calcular() {
        try {
            CotizadorEventual.Cotizacion c = isdolarChb.isSelected()
                    ? cotizador.calcularDesdeDolares(costoTxt.getText(), cotizacionDolar,
                                                     costoExtTxt.getText(), markUpTxt.getText())
                    : cotizador.calcular(costoTxt.getText(), costoExtTxt.getText(), markUpTxt.getText());

            ultimaCotizacion = c;
            jLabel4.setText("Costo unit.: " + monedaCosto.format(c.getSubtotal()));
            iva10.setText("Iva 10,5%: " + monedaPrecio.format(c.getPrecioIva105()));
            iva21.setText("Iva 21%: " + monedaPrecio.format(c.getPrecioIva21()));
            total.setText("Total sin iva: " + monedaPrecio.format(c.getPrecioSinIva()));
        } catch (IllegalArgumentException e) {
            ultimaCotizacion = null;
            limpiarResultados(); // faltan datos o hay texto inválido: no mostramos nada
        }
    }

    private void limpiarResultados() {
        jLabel4.setText("Subtotal");
        iva10.setText("Iva 10,5%");
        iva21.setText("Iva 21%");
        total.setText("Total sin iva");
    }

    /** Condición de IVA elegida en el combo: 21 (primera opción) o 10,5. */
    private BigDecimal ivaSeleccionado() {
        return ivaCmb.getSelectedIndex() == 1 ? new BigDecimal("10.5") : new BigDecimal("21");
    }

    /** Suma el producto que está en pantalla a la lista y a la tabla. */
    private void agregarProducto() {
        if (ultimaCotizacion == null) {
            JOptionPane.showMessageDialog(this,
                    "Completá el costo (y la cotización del dólar si corresponde) antes de agregar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            ProductoEventual p = cotizador.crearProducto(
                    descripcionTxt.getText(),
                    cantidadTxt.getText(),
                    ultimaCotizacion,
                    ivaSeleccionado());

            productos.add(p);
            modeloTabla.addRow(new Object[]{
                p.getDescripcion(),
                numero.format(p.getCantidad()),
                monedaCosto.format(p.getCostoUnitario()),
                numero.format(p.getMarkUpPct()) + "%",
                monedaPrecio.format(p.getPrecioUnitario()),
                monedaPrecio.format(p.getTotal()),
                numero.format(p.getIvaPct()) + "%"
            });

            // Se limpian solo los datos propios del producto; el costo extra y el
            // mark up quedan cargados porque normalmente se repiten entre productos.
            cantidadTxt.setText("");
            
            costoTxt.setText("");
            cantidadTxt.requestFocusInWindow();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void quitarProducto() {
        int fila = tablaProductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccioná un producto de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        productos.remove(fila);
        modeloTabla.removeRow(fila);
    }

    private void exportarExcel() {
        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos cargados para exportar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser guardar = new JFileChooser();
        guardar.setDialogTitle("Guardar Excel");
        guardar.setSelectedFile(new File("eventuales.xlsx"));
        guardar.setFileFilter(new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx"));

        if (guardar.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File destino = guardar.getSelectedFile();
            if (!destino.getName().toLowerCase().endsWith(".xlsx")) {
                destino = new File(destino.getParentFile(), destino.getName() + ".xlsx");
            }
            try {
                String proveedor = proveedorTxt.getText().trim();
                String cliente = clienteTxt.getText().trim();

                new ExcelEventualesGenerator().generar(
                        productos,
                        
                        proveedor,
                        cliente,
                        destino,true
                );

                JOptionPane.showMessageDialog(this,
                        "Excel generado correctamente en:\n" + destino.getAbsolutePath(),
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Error al generar el Excel: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CargarExcel;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JTextField cantidadTxt;
    private javax.swing.JTextField clienteTxt;
    private javax.swing.JTextField costoExtTxt;
    private javax.swing.JTextField costoTxt;
    private javax.swing.JTextField descripcionTxt;
    private javax.swing.JLabel dlarTxt;
    private javax.swing.JCheckBox isdolarChb;
    private javax.swing.JLabel iva10;
    private javax.swing.JLabel iva21;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField markUpTxt;
    private javax.swing.JTextField proveedorTxt;
    private javax.swing.JLabel total;
    // End of variables declaration//GEN-END:variables
}
