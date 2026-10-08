
package com.mycompany.autoabrafer.IGU;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;



public class CargaFacturas extends javax.swing.JFrame {

    private final java.util.List<com.mycompany.autoabrafer.models.Factura> facturasCargadas =
        new java.util.ArrayList<>();
    
    // arriba, junto a facturasCargadas
    private final javax.swing.table.DefaultTableModel modeloTabla =
            new javax.swing.table.DefaultTableModel(
                    new Object[]{"Tipo", "Número", "Fecha", "Monto", "Productos"}, 0);
    
    public CargaFacturas() {
        initComponents();
        jTable1.setModel(modeloTabla);
        habilitarDragAndDrop();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnCancelar = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        CargarExcel = new javax.swing.JButton();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("AutoAbrafer");

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnCancelar.setBackground(new java.awt.Color(107, 107, 107));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnExportar.setBackground(new java.awt.Color(107, 107, 107));
        btnExportar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnExportar.setForeground(new java.awt.Color(255, 255, 255));
        btnExportar.setText("Exportar");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 396, Short.MAX_VALUE)
                        .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExportar, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );

        jButton1.setBackground(new java.awt.Color(102, 102, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Cargar Factura");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        CargarExcel.setBackground(new java.awt.Color(102, 102, 102));
        CargarExcel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        CargarExcel.setForeground(new java.awt.Color(255, 255, 255));
        CargarExcel.setText("Cargar Excel");
        CargarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CargarExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(308, 308, 308)
                                .addComponent(jLabel1)))
                        .addContainerGap(38, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CargarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CargarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    
    private void procesarFacturas(File[] archivos) {
        com.mycompany.autoabrafer.logica.LectorFacturaPDF lector =
                new com.mycompany.autoabrafer.logica.LectorFacturaPDF();

        java.text.SimpleDateFormat formatoFecha = new java.text.SimpleDateFormat("dd/MM/yyyy");
        java.text.NumberFormat formatoMonto =
                java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("es", "AR"));

        for (File archivo : archivos) {
            if (!archivo.getName().toLowerCase().endsWith(".pdf")) {
                continue; // por si arrastran algo que no es PDF junto con los PDFs
            }
            try {
                com.mycompany.autoabrafer.models.Factura factura = lector.leerFactura(archivo);
                facturasCargadas.add(factura);

                String fechaStr = factura.getFecha() != null
                        ? formatoFecha.format(factura.getFecha()) : "-";
                String montoStr = factura.getMonto() != null
                        ? formatoMonto.format(factura.getMonto()) : "-";

                modeloTabla.addRow(new Object[]{
                        factura.getTipo() != null ? factura.getTipo() : "?",
                        factura.getNumero() != null ? factura.getNumero() : "-",
                        fechaStr,
                        montoStr,
                        factura.getProductos()
                });

            } catch (java.io.IOException e) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "No se pudo leer " + archivo.getName() + ": " + e.getMessage(),
                        "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser selector = new JFileChooser();
        selector.setMultiSelectionEnabled(true);
        selector.setFileFilter(new FileNameExtensionFilter("Archivos PDF", "pdf"));

        int resultado = selector.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            procesarFacturas(selector.getSelectedFiles());
        }
        

    }//GEN-LAST:event_jButton1ActionPerformed

    private void habilitarDragAndDrop() {
        javax.swing.TransferHandler handler = new javax.swing.TransferHandler() {

            @Override
            public boolean canImport(TransferSupport support) {
                return support.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.javaFileListFlavor);
            }

            @Override
            public boolean importData(TransferSupport support) {
                if (!canImport(support)) {
                    return false;
                }
                try {
                    @SuppressWarnings("unchecked")
                    java.util.List<File> archivos = (java.util.List<File>)
                            support.getTransferable()
                                   .getTransferData(java.awt.datatransfer.DataFlavor.javaFileListFlavor);

                    java.util.List<File> pdfs = new java.util.ArrayList<>();
                    for (File f : archivos) {
                        if (f.getName().toLowerCase().endsWith(".pdf")) {
                            pdfs.add(f);
                        }
                    }

                    if (pdfs.isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(CargaFacturas.this,
                                "Ninguno de los archivos soltados es un PDF.",
                                "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
                        return false;
                    }

                    procesarFacturas(pdfs.toArray(new File[0]));
                    return true;

                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(CargaFacturas.this,
                            "No se pudieron leer los archivos arrastrados: " + e.getMessage(),
                            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        };

        // Se lo seteamos al content pane, la tabla y el scrollpane
        // para que el drop funcione sin importar dónde sueltes el archivo dentro de la ventana
        ((javax.swing.JComponent) getContentPane()).setTransferHandler(handler);
        jTable1.setTransferHandler(handler);
        jScrollPane1.setTransferHandler(handler);
    }
    
    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        if (facturasCargadas.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "No hay facturas cargadas para exportar.",
                    "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser guardar = new JFileChooser();
        guardar.setDialogTitle("Guardar Excel");
        guardar.setSelectedFile(new File("facturas.xlsx"));
        guardar.setFileFilter(new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx"));

        int resultado = guardar.showSaveDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File destino = guardar.getSelectedFile();
            if (!destino.getName().toLowerCase().endsWith(".xlsx")) {
                destino = new File(destino.getParentFile(), destino.getName() + ".xlsx");
            }

            try {
                new com.mycompany.autoabrafer.logica.ExcelGenerator().generar(facturasCargadas, destino, true);
            } catch (java.io.IOException e) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Error al generar el Excel: " + e.getMessage(),
                        "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            String mensaje = "Excel generado correctamente en:\n" + destino.getAbsolutePath();

            // Segundo Excel: reporte semanal (si cancelás este diálogo, solo se genera el primero)
            File semanal = elegirExcelSemanal(destino.getParentFile());
            if (semanal != null) {
                try {
                    int filas = new com.mycompany.autoabrafer.logica.ExcelReporteSemanalGenerator()
                            .generar(facturasCargadas, semanal, true);
                    mensaje += "\n\nReporte semanal actualizado (" + filas + " productos) en:\n"
                            + semanal.getAbsolutePath();

                    long sinItems = facturasCargadas.stream().filter(f -> f.getItems().isEmpty()).count();
                    if (sinItems > 0) {
                        mensaje += "\n\nAviso: " + sinItems + " factura(s) no tenían renglones legibles"
                                + " y no se agregaron al reporte semanal.";
                    }
                } catch (java.io.IOException e) {
                    mensaje += "\n\nNo se pudo generar el reporte semanal: " + e.getMessage();
                }
            }

            javax.swing.JOptionPane.showMessageDialog(this, mensaje,
                    "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExportarActionPerformed

    /** Pide dónde guardar/actualizar el reporte semanal. Devuelve null si se cancela. */
    private File elegirExcelSemanal(File carpetaInicial) {
        JFileChooser guardar = new JFileChooser(carpetaInicial);
        guardar.setDialogTitle("Guardar / actualizar reporte semanal");
        guardar.setSelectedFile(new File(carpetaInicial, "Reporte_semanal_web.xlsx"));
        guardar.setFileFilter(new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx"));

        if (guardar.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        File destino = guardar.getSelectedFile();
        if (!destino.getName().toLowerCase().endsWith(".xlsx")) {
            destino = new File(destino.getParentFile(), destino.getName() + ".xlsx");
        }
        return destino;
    }

    private void CargarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CargarExcelActionPerformed
        JFileChooser selector = new JFileChooser();

        selector.setDialogTitle("Seleccionar Excel");
        selector.setFileFilter(new FileNameExtensionFilter(
                "Archivos Excel (*.xlsx)", "xlsx"
        ));

        int resultado = selector.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();

            // Acá guardás el archivo seleccionado
            System.out.println("Excel seleccionado: " + archivo.getAbsolutePath());
        }
    }//GEN-LAST:event_CargarExcelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CargarExcel;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
