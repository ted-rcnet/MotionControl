/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.gui;

import fr.devTeam.motionConf.ParametersComponents;
import fr.devTeam.motionConf.model.PidConfData;
import fr.devTeam.motionConf.model.PidConfDataAdapter;
import fr.devTeam.motionConf.model.PidConfDataEvent;
import java.awt.Color;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author rotule
 */
public class curvesPanel extends javax.swing.JPanel {
    private final XYSeriesCollection seriesThr = new XYSeriesCollection();
    private final XYSeriesCollection seriesRc = new XYSeriesCollection();
    private PidConfData pidConfData = SerialSingleton.getInstance().getSerial().getPidConfData();
    
    /**
     * Creates new form curvesPanel
     */
    public curvesPanel() {
        initComponents();
        createDatasetThr();
        createDatasetRc();
        initGraphs();
        initListeners();
        initParametersComponents();
    }
    
    private void initParametersComponents() {
        ParametersComponents params = ParametersComponents.getInstance();
        params.setRcExpoLabel(rcExpoLabel);
        params.setRcRateLabel(rcRateLabel);
        params.setThrExpLabel(thrExpLabel);
        params.setThrGraph(thrGraph);
        params.setThrMidLabel(thrMidLabel);
    }
    
    private void initListeners() {
        pidConfData.addBoxDataListener(new PidConfDataAdapter() {
            @Override public void newPidConfData(final PidConfDataEvent ev) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override public void run() {
                        thrExpSlider.setValue(ev.getRcTuning().getThrExpo());
                        thrMidSlider.setValue(ev.getRcTuning().getThrMid());
                        rcRateSlider.setValue(ev.getRcTuning().getRcRate());
                        rcExpoSlider.setValue(ev.getRcTuning().getRcExpo());
                        
                        thrExpLabel.setText(Integer.toString(thrExpSlider.getValue()));
                        thrMidLabel.setText(Integer.toString(thrMidSlider.getValue()));
                        rcRateLabel.setText(Integer.toString(rcRateSlider.getValue()));
                        rcExpoLabel.setText(Integer.toString(rcExpoSlider.getValue()));
                        
                        seriesRc.removeAllSeries();
                        createDatasetRc();
                        seriesThr.removeAllSeries();
                        createDatasetThr();
                    }
                });
            }
        });
    }
    
    private void initGraphs () {
        final JFreeChart chartThr = createChart(seriesThr);
        final ChartPanel chartPanelThr = new ChartPanel(chartThr);
        
        final JFreeChart chartRc = createChart(seriesRc);
        final XYPlot plot = chartRc.getXYPlot();
        ValueAxis range = plot.getRangeAxis();
        range.setRange(0, 500);
        final ChartPanel chartPanelRc = new ChartPanel(chartRc);
        
        chartPanelThr.setPreferredSize(new java.awt.Dimension(40, 50));
        thrGraph.add(chartPanelThr);
        chartPanelRc.setPreferredSize(new java.awt.Dimension(40, 50));
        rcGraph.add(chartPanelRc);
    }
    
    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createXYLineChart(
            null, 
            null, 
            null,
            dataset, 
            PlotOrientation.VERTICAL,
            false,
            false, 
            false
        );
        final XYPlot plot = result.getXYPlot();
        plot.setBackgroundPaint(new Color(248, 248, 248));
        ValueAxis range = plot.getRangeAxis();
        range.setVisible(false);
        ValueAxis abscisses = plot.getDomainAxis();
        abscisses.setVisible(false);
        return result;
    }
    
    public final  void createDatasetThr() {
        XYSeries serie = new XYSeries("");
        
        for(int i=0;i<11;i++) {
           int tmp = 10*i-thrMidSlider.getValue();
           int  y = 1;
           if (tmp>0) y = 100-thrMidSlider.getValue();
           if (tmp<0) y = thrMidSlider.getValue();
           int lookupThrottleRC = 10*thrMidSlider.getValue() + tmp*( 100-thrExpSlider.getValue()+thrExpSlider.getValue()*(tmp*tmp)/(y*y) )/10; // [0;1000]
           serie.add(i, 200 * lookupThrottleRC/1000);            // [0;1000] -> [conf.minthrottle;MAXTHROTTLE]
        }
        
        seriesThr.addSeries(serie);
    }
    
    public final  void createDatasetRc() {
        XYSeries serie = new XYSeries("");
        
        for(int i=0;i<6;i++) {
            serie.add(i, (2500+rcExpoSlider.getValue()*(i*i-25))*i*rcRateSlider.getValue()/2500);
        }
        
        seriesRc.addSeries(serie);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        thrMidSlider = new javax.swing.JSlider();
        thrExpSlider = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        rcRateSlider = new javax.swing.JSlider();
        rcExpoSlider = new javax.swing.JSlider();
        thrGraph = new javax.swing.JPanel();
        rcGraph = new javax.swing.JPanel();
        thrMidLabel = new javax.swing.JLabel();
        thrExpLabel = new javax.swing.JLabel();
        rcRateLabel = new javax.swing.JLabel();
        rcExpoLabel = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Thr MID");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 62, -1));

        jLabel2.setText("Thr EXP");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 62, -1));

        thrMidSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                thrMidSliderStateChanged(evt);
            }
        });
        add(thrMidSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, -1));

        thrExpSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                thrExpSliderStateChanged(evt);
            }
        });
        add(thrExpSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, -1, -1));

        jLabel3.setText("RC rate");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 62, -1));

        jLabel4.setText("RC expo");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 62, -1));

        rcRateSlider.setMaximum(150);
        rcRateSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rcRateSliderStateChanged(evt);
            }
        });
        add(rcRateSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, -1, -1));

        rcExpoSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rcExpoSliderStateChanged(evt);
            }
        });
        add(rcExpoSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, -1, -1));

        thrGraph.setLayout(new java.awt.BorderLayout());
        add(thrGraph, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 0, 131, 85));

        rcGraph.setLayout(new java.awt.BorderLayout());
        add(rcGraph, new org.netbeans.lib.awtextra.AbsoluteConstraints(757, 1, 131, 84));

        thrMidLabel.setText("50");
        add(thrMidLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 26, -1));

        thrExpLabel.setText("50");
        add(thrExpLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 26, -1));

        rcRateLabel.setText("50");
        add(rcRateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 26, -1));

        rcExpoLabel.setText("50");
        add(rcExpoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 26, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void thrExpSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_thrExpSliderStateChanged
        thrExpLabel.setText(Integer.toString(thrExpSlider.getValue()));
        seriesThr.removeAllSeries();
        createDatasetThr();
    }//GEN-LAST:event_thrExpSliderStateChanged

    private void thrMidSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_thrMidSliderStateChanged
        thrMidLabel.setText(Integer.toString(thrMidSlider.getValue()));
        seriesThr.removeAllSeries();
        createDatasetThr();
    }//GEN-LAST:event_thrMidSliderStateChanged

    private void rcRateSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rcRateSliderStateChanged
        rcRateLabel.setText(Integer.toString(rcRateSlider.getValue()));
        seriesRc.removeAllSeries();
        createDatasetRc();
    }//GEN-LAST:event_rcRateSliderStateChanged

    private void rcExpoSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rcExpoSliderStateChanged
        rcExpoLabel.setText(Integer.toString(rcExpoSlider.getValue()));
        seriesRc.removeAllSeries();
        createDatasetRc();
    }//GEN-LAST:event_rcExpoSliderStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel rcExpoLabel;
    private javax.swing.JSlider rcExpoSlider;
    private javax.swing.JPanel rcGraph;
    private javax.swing.JLabel rcRateLabel;
    private javax.swing.JSlider rcRateSlider;
    private javax.swing.JLabel thrExpLabel;
    private javax.swing.JSlider thrExpSlider;
    private javax.swing.JPanel thrGraph;
    private javax.swing.JLabel thrMidLabel;
    private javax.swing.JSlider thrMidSlider;
    // End of variables declaration//GEN-END:variables
}
