/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.gui;

import fr.devTeam.motionConf.model.RealTimeAdapter;
import fr.devTeam.motionConf.model.RealTimeData;
import fr.devTeam.motionConf.msp.dao.StatusBean;
import java.awt.Color;
import javax.swing.SwingUtilities;

/**
 *
 * @author EUGI7210
 */
public class SensorsPanel extends javax.swing.JPanel {
    private RealTimeData rtd = SerialSingleton.getInstance().getRtd();
    
    /**
     * Creates new form SensorsPanel
     */
    public SensorsPanel() {
        initComponents();
        initListeners();
    }
    
    private void initListeners () {
        rtd.addRealTimeDataListener(new RealTimeAdapter() {
            @Override public void newStatusData (final StatusBean status) {
		SwingUtilities.invokeLater(new Runnable() {
		    @Override public void run() {
                        int sensors = status.getPresentSensors();
                        if ((sensors & 1) == 0) accelTextField.setBackground(Color.red);
                            else accelTextField.setBackground(new Color(188,255,188));
                        if ((sensors & 2) == 0) baroTextField.setBackground(Color.red);
                            else baroTextField.setBackground(new Color(188,255,188));
                        if ((sensors & 4) == 0) magTextField.setBackground(Color.red);
                            else magTextField.setBackground(new Color(188,255,188));
                        if ((sensors & 8) == 0) gpsTextField.setBackground(Color.red);
                            else gpsTextField.setBackground(new Color(188,255,188));
		    }
		});
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        accelTextField = new javax.swing.JTextField();
        baroTextField = new javax.swing.JTextField();
        magTextField = new javax.swing.JTextField();
        gpsTextField = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();

        accelTextField.setEditable(false);
        accelTextField.setBackground(new java.awt.Color(204, 204, 204));
        accelTextField.setText("Accéléromètre");

        baroTextField.setEditable(false);
        baroTextField.setBackground(new java.awt.Color(204, 204, 204));
        baroTextField.setText("Baromètre");

        magTextField.setEditable(false);
        magTextField.setBackground(new java.awt.Color(204, 204, 204));
        magTextField.setText("Magnétomètre");

        gpsTextField.setEditable(false);
        gpsTextField.setBackground(new java.awt.Color(204, 204, 204));
        gpsTextField.setText("GPS");

        jTextField5.setEditable(false);
        jTextField5.setBackground(new java.awt.Color(204, 204, 204));
        jTextField5.setText("Sonar");

        jTextField6.setEditable(false);
        jTextField6.setBackground(new java.awt.Color(204, 204, 204));
        jTextField6.setText("Optique");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(magTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(baroTextField)
                    .addComponent(accelTextField)
                    .addComponent(gpsTextField)
                    .addComponent(jTextField5)
                    .addComponent(jTextField6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(accelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(baroTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(magTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(gpsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField accelTextField;
    private javax.swing.JTextField baroTextField;
    private javax.swing.JTextField gpsTextField;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField magTextField;
    // End of variables declaration//GEN-END:variables
}