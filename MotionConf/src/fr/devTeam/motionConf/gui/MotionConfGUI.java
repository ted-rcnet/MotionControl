/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.gui;

import fr.devTeam.motionConf.Erreurs.SerialException;
import fr.devTeam.motionConf.util.Constantes;
import fr.devTeam.motionConf.util.Serial;
import fr.devTeam.motionConf.util.McFileType;
import fr.devTeam.motionConf.Parameters;
import fr.devTeam.motionConf.ParametersComponents;
import fr.devTeam.motionConf.model.AttitudeDataAdapter;
import fr.devTeam.motionConf.model.DebugMsgAdapter;
import fr.devTeam.motionConf.model.MotorServoDataAdapter;
import fr.devTeam.motionConf.model.RawSerialDataAdapter;
import fr.devTeam.motionConf.msp.dao.AltitudeBean;
import fr.devTeam.motionConf.msp.dao.DebugBean;
import fr.devTeam.motionConf.msp.dao.RawGpsBean;
import fr.devTeam.motionConf.msp.dao.RawImuBean;
import fr.devTeam.motionConf.msp.dao.RcBean;
import fr.devTeam.motionConf.msp.dao.StatusBean;
import fr.devTeam.motionConf.model.RealTimeAdapter;
import fr.devTeam.motionConf.model.RealTimeData;
import fr.devTeam.motionConf.msp.TimedRealTimeRequest;
import fr.devTeam.motionConf.msp.dao.AttitudeBean;
import fr.devTeam.motionConf.msp.dao.CompGpsBean;
import fr.devTeam.motionConf.msp.dao.MotorBean;
import fr.devTeam.motionConf.msp.dao.ServoBean;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author EUGI7210
 */
public class MotionConfGUI extends JFrame {
    private Parameters params = Parameters.getInstance();
    private final TimeSeriesCollection dataset = new TimeSeriesCollection();
    private String[] seriesNames = {"Gyro X","Gyro Y","Gyro Z","Acc X", "Acc Y", "Acc Z", "Mag X", "Mag Y", "Mag Z", "Altitude","debug1","debug2","debug3","debug4"};
    private TimeSeries[] series = new TimeSeries[seriesNames.length];
    private SerialSingleton serialUtil = SerialSingleton.getInstance();
    private Serial serial = serialUtil.getSerial();
    private RealTimeData rtd = serialUtil.getRtd();
    private TimedRealTimeRequest trtr = new TimedRealTimeRequest();
    private RawSerialDataAdapter rawSerialDataAdapter = new RawSerialDataAdapter() {
        @Override
        public void newRawSerialData(byte[] rawData) {
            consoleTextArea.append(new String(rawData));
        }
    };
    
    
    /**
     * Creates new form MotionConfGUI
     */
    public MotionConfGUI() {
        this.setTitle("MotionConf");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        for (int i = 0; i< seriesNames.length; i++) {
            series[i] = new TimeSeries(seriesNames[i], Millisecond.class);
        }
        
        initComponents();
        initGraph();
        initListeners();
	trtr.start();
        worldPanel.add(new WorldWindPanel());
        
        //tabbedPane.setEnabledAt(3, false);
    }
    
    public void rotateLabelIcon(JLabel label, String imagePath, int angle)
    {
        int w = label.getIcon().getIconWidth();
        int h = label.getIcon().getIconHeight();
        int type = BufferedImage.TYPE_INT_ARGB;  // other options, see api


        BufferedImage newImage = new BufferedImage(h, w, type);
        BufferedImage image = new BufferedImage(h, w, type);
        Graphics2D g2 = newImage.createGraphics();

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException ex) {
            Logger.getLogger(MotionConfGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.rotate(Math.toRadians(angle), newImage.getWidth() / 2, newImage.getHeight() / 2);
        g2.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);


        label.setIcon(new ImageIcon(newImage));
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        buttonGroup1 = new javax.swing.ButtonGroup();
        tabbedPane = new javax.swing.JTabbedPane();
        paramsPanel = new javax.swing.JPanel();
        boxPanel1 = new fr.devTeam.motionConf.gui.BoxPanel();
        pidPanel1 = new fr.devTeam.motionConf.gui.PidPanel();
        curvesPanel1 = new fr.devTeam.motionConf.gui.curvesPanel();
        jSeparator1 = new javax.swing.JSeparator();
        sensorsPanel1 = new fr.devTeam.motionConf.gui.SensorsPanel();
        attitudePanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        thrBar = new javax.swing.JProgressBar();
        yawBar = new javax.swing.JProgressBar();
        rollBar = new javax.swing.JProgressBar();
        pitchBar = new javax.swing.JProgressBar();
        aux4Bar = new javax.swing.JProgressBar();
        aux3Bar = new javax.swing.JProgressBar();
        aux2Bar = new javax.swing.JProgressBar();
        aux1Bar = new javax.swing.JProgressBar();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        pitchImagePanel = new javax.swing.JPanel()
        {
            protected void paintComponent(Graphics g)
            {
                try {
                    Image icon = ImageIO.read(getClass().getResourceAsStream("/img/circle_bg2.png"));
                    g.drawImage(icon, 0, 0, null);
                } catch(Exception e) {System.err.println("Erreur d'ouverture de l'image");}
                super.paintComponent(g);
            }
        }
        ;
        pitchLabel = new javax.swing.JLabel();
        rollImagePanel = new javax.swing.JPanel()
        {
            protected void paintComponent(Graphics g)
            {
                try {
                    Image icon = ImageIO.read(getClass().getResourceAsStream("/img/circle_bg2.png"));
                    g.drawImage(icon, 0, 0, null);
                } catch(Exception e) {System.err.println("Erreur d'ouverture de l'image");}
                super.paintComponent(g);
            }
        }
        ;
        rollLabel = new javax.swing.JLabel();
        compassImagePanel = new javax.swing.JPanel()
        {
            protected void paintComponent(Graphics g)
            {
                try {
                    Image icon = ImageIO.read(getClass().getResourceAsStream("/img/compass_bg.png"));
                    g.drawImage(icon, 0, 0, null);
                } catch(Exception e) {System.err.println("Erreur d'ouverture de l'image");}
                super.paintComponent(g);
            }
        }
        ;
        compassLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        m1Bar = new javax.swing.JProgressBar();
        m3Bar = new javax.swing.JProgressBar();
        m4Bar = new javax.swing.JProgressBar();
        m2Bar = new javax.swing.JProgressBar();
        m8Bar = new javax.swing.JProgressBar();
        m7Bar = new javax.swing.JProgressBar();
        m6Bar = new javax.swing.JProgressBar();
        m5Bar = new javax.swing.JProgressBar();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        s1Bar = new javax.swing.JProgressBar();
        s3Bar = new javax.swing.JProgressBar();
        s4Bar = new javax.swing.JProgressBar();
        s2Bar = new javax.swing.JProgressBar();
        s8Bar = new javax.swing.JProgressBar();
        s7Bar = new javax.swing.JProgressBar();
        s6Bar = new javax.swing.JProgressBar();
        s5Bar = new javax.swing.JProgressBar();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        accCalibButton = new javax.swing.JButton();
        magCalibButton = new javax.swing.JButton();
        graphTabPanel = new javax.swing.JPanel();
        graphPanel = new javax.swing.JPanel();
        checkBoxesPanel = new javax.swing.JPanel();
        gyroXCheckBox = new javax.swing.JCheckBox();
        gyroYCheckBox = new javax.swing.JCheckBox();
        gyroZCheckBox = new javax.swing.JCheckBox();
        jSeparator3 = new javax.swing.JSeparator();
        accXCheckBox = new javax.swing.JCheckBox();
        accYCheckBox = new javax.swing.JCheckBox();
        accZCheckBox = new javax.swing.JCheckBox();
        jSeparator4 = new javax.swing.JSeparator();
        magXCheckBox = new javax.swing.JCheckBox();
        magYCheckBox = new javax.swing.JCheckBox();
        magZCheckBox = new javax.swing.JCheckBox();
        altCheckBox = new javax.swing.JCheckBox();
        jSeparator6 = new javax.swing.JSeparator();
        debug1CheckBox = new javax.swing.JCheckBox();
        debug2CheckBox = new javax.swing.JCheckBox();
        debug3CheckBox = new javax.swing.JCheckBox();
        debug4CheckBox = new javax.swing.JCheckBox();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        I2CErrorsLabel = new javax.swing.JLabel();
        cycleTimeLabel = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        numSatLabel = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        latitudeLabel = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        gpsAltitudeLabel = new javax.swing.JLabel();
        longitudeLabel = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        gpsSpeedLabel = new javax.swing.JLabel();
        groundCourseLabel = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        baroAltLabel = new javax.swing.JLabel();
        mixPanel = new javax.swing.JPanel();
        mixagesPanel1 = new fr.devTeam.motionConf.gui.MixagesPanel();
        consolePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        consoleTextArea = new javax.swing.JTextArea();
        consoleTextField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        consoleRadioButtonMC = new javax.swing.JRadioButton();
        consoleRadioButtonSerial = new javax.swing.JRadioButton();
        worldPanel = new javax.swing.JPanel();
        toolPane = new javax.swing.JPanel();
        serialPortsComboBox = new javax.swing.JComboBox();
        speedComboBox = new javax.swing.JComboBox();
        connect = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        saveEEPROMButton = new javax.swing.JButton();
        readEEPromButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        loadConfigFile = new javax.swing.JButton();
        saveConfigFile = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedPaneStateChanged(evt);
            }
        });

        pidPanel1.setToolTipText("<HTML>personnalisation des PIDs de votre machine</HTML>");

        sensorsPanel1.setToolTipText("<HTML>Etat des capteurs<br>\nVert -> le capteur est activé et présent, il peut être utilisé<br>\nRouge -> le capteur est soit absent, soit non activé dans le programme</HTML>");

        javax.swing.GroupLayout paramsPanelLayout = new javax.swing.GroupLayout(paramsPanel);
        paramsPanel.setLayout(paramsPanelLayout);
        paramsPanelLayout.setHorizontalGroup(
            paramsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paramsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(curvesPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(paramsPanelLayout.createSequentialGroup()
                .addComponent(pidPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(boxPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sensorsPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );
        paramsPanelLayout.setVerticalGroup(
            paramsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paramsPanelLayout.createSequentialGroup()
                .addGroup(paramsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paramsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(boxPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pidPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(paramsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(sensorsPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(curvesPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 64, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Paramètres", paramsPanel);

        jPanel2.setToolTipText("<HTML>Visualisation des voies radio<br>\nUtile pour calibrer les limites et le centre de votre radio</HTML>");

        thrBar.setMaximum(2000);
        thrBar.setMinimum(1000);
        thrBar.setValue(1500);
        thrBar.setString(Integer.toString(thrBar.getValue()));
        thrBar.setStringPainted(true);

        yawBar.setMaximum(2000);
        yawBar.setMinimum(1000);
        yawBar.setValue(1500);
        yawBar.setString(Integer.toString(yawBar.getValue()));
        yawBar.setStringPainted(true);

        rollBar.setMaximum(2000);
        rollBar.setMinimum(1000);
        rollBar.setToolTipText("");
        rollBar.setValue(1500);
        rollBar.setString(Integer.toString(rollBar.getValue()));
        rollBar.setStringPainted(true);

        pitchBar.setMaximum(2000);
        pitchBar.setMinimum(1000);
        pitchBar.setValue(1500);
        pitchBar.setString(Integer.toString(pitchBar.getValue()));
        pitchBar.setStringPainted(true);

        aux4Bar.setMaximum(2000);
        aux4Bar.setMinimum(1000);
        aux4Bar.setValue(1500);
        aux4Bar.setString(Integer.toString(aux4Bar.getValue()));
        aux4Bar.setStringPainted(true);

        aux3Bar.setMaximum(2000);
        aux3Bar.setMinimum(1000);
        aux3Bar.setValue(1500);
        aux3Bar.setString(Integer.toString(aux3Bar.getValue()));
        aux3Bar.setStringPainted(true);

        aux2Bar.setMaximum(2000);
        aux2Bar.setMinimum(1000);
        aux2Bar.setValue(1500);
        aux2Bar.setString(Integer.toString(aux2Bar.getValue()));
        aux2Bar.setStringPainted(true);

        aux1Bar.setMaximum(2000);
        aux1Bar.setMinimum(1000);
        aux1Bar.setValue(1500);
        aux1Bar.setString(Integer.toString(aux1Bar.getValue()));
        aux1Bar.setStringPainted(true);

        jLabel5.setText("Thr");

        jLabel6.setText("Yaw");

        jLabel7.setText("Roll");

        jLabel8.setText("Pitch");

        jLabel9.setText("Aux1");

        jLabel10.setText("Aux2");

        jLabel11.setText("Aux3");

        jLabel12.setText("Aux4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(9, 9, 9))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9)
                                .addComponent(jLabel10)
                                .addComponent(jLabel11)
                                .addComponent(jLabel12))
                            .addGap(1, 1, 1)))
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(thrBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(aux3Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(aux4Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(aux2Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(aux1Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rollBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(yawBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pitchBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(thrBar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(pitchBar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(yawBar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(rollBar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(aux1Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(aux2Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aux3Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aux4Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)))
        );

        pitchImagePanel.setMaximumSize(new java.awt.Dimension(171, 171));
        pitchImagePanel.setMinimumSize(new java.awt.Dimension(171, 171));
        pitchImagePanel.setPreferredSize(new java.awt.Dimension(171, 171));
        pitchImagePanel.setLayout(new java.awt.GridBagLayout());

        pitchLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pitch.png"))); // NOI18N
        pitchImagePanel.add(pitchLabel, new java.awt.GridBagConstraints());

        rollImagePanel.setMaximumSize(new java.awt.Dimension(171, 171));
        rollImagePanel.setMinimumSize(new java.awt.Dimension(171, 171));
        rollImagePanel.setPreferredSize(new java.awt.Dimension(171, 171));
        rollImagePanel.setLayout(new java.awt.GridBagLayout());

        rollLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/roll.png"))); // NOI18N
        rollImagePanel.add(rollLabel, new java.awt.GridBagConstraints());

        compassImagePanel.setMaximumSize(new java.awt.Dimension(171, 171));
        compassImagePanel.setMinimumSize(new java.awt.Dimension(171, 171));
        compassImagePanel.setPreferredSize(new java.awt.Dimension(171, 171));
        compassImagePanel.setLayout(new java.awt.GridBagLayout());

        compassLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/compass_arrow.png"))); // NOI18N
        compassImagePanel.add(compassLabel, new java.awt.GridBagConstraints());

        jPanel4.setToolTipText("<HTML>Visualisation des voies radio<br>\nUtile pour calibrer les limites et le centre de votre radio</HTML>");

        m1Bar.setMaximum(2000);
        m1Bar.setMinimum(1000);
        m1Bar.setString(Integer.toString(m1Bar.getValue()));
        m1Bar.setStringPainted(true);

        m3Bar.setMaximum(2000);
        m3Bar.setMinimum(1000);
        m3Bar.setString(Integer.toString(m3Bar.getValue()));
        m3Bar.setStringPainted(true);

        m4Bar.setMaximum(2000);
        m4Bar.setMinimum(1000);
        m4Bar.setString(Integer.toString(m4Bar.getValue()));
        m4Bar.setStringPainted(true);

        m2Bar.setMaximum(2000);
        m2Bar.setMinimum(1000);
        m2Bar.setString(Integer.toString(m2Bar.getValue()));
        m2Bar.setStringPainted(true);

        m8Bar.setMaximum(2000);
        m8Bar.setMinimum(1000);
        m8Bar.setString(Integer.toString(m8Bar.getValue()));
        m8Bar.setStringPainted(true);

        m7Bar.setMaximum(2000);
        m7Bar.setMinimum(1000);
        m7Bar.setString(Integer.toString(m7Bar.getValue()));
        m7Bar.setStringPainted(true);

        m6Bar.setMaximum(2000);
        m6Bar.setMinimum(1000);
        m6Bar.setString(Integer.toString(m6Bar.getValue()));
        m6Bar.setStringPainted(true);

        m5Bar.setMaximum(2000);
        m5Bar.setMinimum(1000);
        m5Bar.setString(Integer.toString(m5Bar.getValue()));
        m5Bar.setStringPainted(true);

        jLabel22.setText("M1");

        jLabel23.setText("M3");

        jLabel24.setText("M4");

        jLabel25.setText("M2");

        jLabel26.setText("M5");

        jLabel27.setText("M6");

        jLabel28.setText("M7");

        jLabel29.setText("M8");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel24)
                    .addComponent(jLabel23)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29))
                .addGap(9, 9, 9)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(m1Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(m7Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(m8Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(m6Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(m5Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(m4Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(m3Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(m2Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(m1Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(m2Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(m3Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(m4Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(m5Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(m6Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m7Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m8Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)))
        );

        jPanel5.setToolTipText("<HTML>Visualisation des voies radio<br>\nUtile pour calibrer les limites et le centre de votre radio</HTML>");

        s1Bar.setMaximum(2000);
        s1Bar.setMinimum(1000);
        s1Bar.setString(Integer.toString(m1Bar.getValue()));
        s1Bar.setStringPainted(true);

        s3Bar.setMaximum(2000);
        s3Bar.setMinimum(1000);
        s3Bar.setString(Integer.toString(m3Bar.getValue()));
        s3Bar.setStringPainted(true);

        s4Bar.setMaximum(2000);
        s4Bar.setMinimum(1000);
        s4Bar.setString(Integer.toString(m4Bar.getValue()));
        s4Bar.setStringPainted(true);

        s2Bar.setMaximum(2000);
        s2Bar.setMinimum(1000);
        s2Bar.setString(Integer.toString(m2Bar.getValue()));
        s2Bar.setStringPainted(true);

        s8Bar.setMaximum(2000);
        s8Bar.setMinimum(1000);
        s8Bar.setString(Integer.toString(m8Bar.getValue()));
        s8Bar.setStringPainted(true);

        s7Bar.setMaximum(2000);
        s7Bar.setMinimum(1000);
        s7Bar.setString(Integer.toString(m7Bar.getValue()));
        s7Bar.setStringPainted(true);

        s6Bar.setMaximum(2000);
        s6Bar.setMinimum(1000);
        s6Bar.setString(Integer.toString(m6Bar.getValue()));
        s6Bar.setStringPainted(true);

        s5Bar.setMaximum(2000);
        s5Bar.setMinimum(1000);
        s5Bar.setString(Integer.toString(m5Bar.getValue()));
        s5Bar.setStringPainted(true);

        jLabel30.setText("S1");

        jLabel31.setText("S3");

        jLabel32.setText("S4");

        jLabel33.setText("S2");

        jLabel34.setText("S5");

        jLabel35.setText("S6");

        jLabel36.setText("S7");

        jLabel37.setText("S8");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel32)
                    .addComponent(jLabel31)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37))
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(s1Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(s7Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(s8Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(s6Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(s5Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(s4Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(s3Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(s2Bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(s1Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(s2Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(s3Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(s4Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(s5Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(s6Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(s7Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(s8Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)))
        );

        accCalibButton.setText("Calib. Acc");
        accCalibButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accCalibButtonActionPerformed(evt);
            }
        });

        magCalibButton.setText("Calib.Mag");
        magCalibButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                magCalibButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout attitudePanelLayout = new javax.swing.GroupLayout(attitudePanel);
        attitudePanel.setLayout(attitudePanelLayout);
        attitudePanelLayout.setHorizontalGroup(
            attitudePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(attitudePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pitchImagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rollImagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(compassImagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(attitudePanelLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(283, Short.MAX_VALUE))
            .addGroup(attitudePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(accCalibButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(magCalibButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        attitudePanelLayout.setVerticalGroup(
            attitudePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(attitudePanelLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(attitudePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accCalibButton)
                    .addComponent(magCalibButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(attitudePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pitchImagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(compassImagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rollImagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(attitudePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Attitude", attitudePanel);

        graphPanel.setLayout(new java.awt.BorderLayout());

        checkBoxesPanel.setToolTipText("<HTML>Choix des capteurs (Et axes de chacun)<br>\nque vous souhaitez voir sur le graphique<br>\n</HTML>");

        gyroXCheckBox.setSelected(true);
        gyroXCheckBox.setText("Gyro X");
        gyroXCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gyroXCheckBoxActionPerformed(evt);
            }
        });

        gyroYCheckBox.setSelected(true);
        gyroYCheckBox.setText("Gyro Y");
        gyroYCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gyroYCheckBoxActionPerformed(evt);
            }
        });

        gyroZCheckBox.setSelected(true);
        gyroZCheckBox.setText("Gyro Z");
        gyroZCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gyroZCheckBoxActionPerformed(evt);
            }
        });

        accXCheckBox.setSelected(true);
        accXCheckBox.setText("Acc X");
        accXCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accXCheckBoxActionPerformed(evt);
            }
        });

        accYCheckBox.setSelected(true);
        accYCheckBox.setText("Acc Y");
        accYCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accYCheckBoxActionPerformed(evt);
            }
        });

        accZCheckBox.setSelected(true);
        accZCheckBox.setText("Acc Z");
        accZCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accZCheckBoxActionPerformed(evt);
            }
        });

        magXCheckBox.setText("Mag X");
        magXCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                magXCheckBoxActionPerformed(evt);
            }
        });

        magYCheckBox.setText("Mag Y");
        magYCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                magYCheckBoxActionPerformed(evt);
            }
        });

        magZCheckBox.setText("Mag Z");
        magZCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                magZCheckBoxActionPerformed(evt);
            }
        });

        altCheckBox.setText("Altitude");
        altCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altCheckBoxActionPerformed(evt);
            }
        });

        debug1CheckBox.setText("debug1");
        debug1CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                debug1CheckBoxActionPerformed(evt);
            }
        });

        debug2CheckBox.setText("debug2");
        debug2CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                debug2CheckBoxActionPerformed(evt);
            }
        });

        debug3CheckBox.setText("debug3");
        debug3CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                debug3CheckBoxActionPerformed(evt);
            }
        });

        debug4CheckBox.setText("debug4");
        debug4CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                debug4CheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout checkBoxesPanelLayout = new javax.swing.GroupLayout(checkBoxesPanel);
        checkBoxesPanel.setLayout(checkBoxesPanelLayout);
        checkBoxesPanelLayout.setHorizontalGroup(
            checkBoxesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addComponent(jSeparator4)
            .addGroup(checkBoxesPanelLayout.createSequentialGroup()
                .addGroup(checkBoxesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gyroXCheckBox)
                    .addComponent(gyroYCheckBox)
                    .addComponent(gyroZCheckBox)
                    .addComponent(accXCheckBox)
                    .addComponent(accYCheckBox)
                    .addComponent(accZCheckBox)
                    .addComponent(magXCheckBox)
                    .addComponent(magYCheckBox)
                    .addComponent(magZCheckBox)
                    .addComponent(debug2CheckBox)
                    .addComponent(debug3CheckBox)
                    .addComponent(debug4CheckBox)
                    .addComponent(altCheckBox)
                    .addComponent(debug1CheckBox))
                .addGap(0, 17, Short.MAX_VALUE))
            .addComponent(jSeparator6)
            .addComponent(jSeparator5)
        );
        checkBoxesPanelLayout.setVerticalGroup(
            checkBoxesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(checkBoxesPanelLayout.createSequentialGroup()
                .addComponent(gyroXCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gyroYCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gyroZCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(accXCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(accYCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(accZCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(magXCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(magYCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(magZCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(altCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(debug1CheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(debug2CheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(debug3CheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(debug4CheckBox)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        jLabel13.setText("Erreurs I2C :");
        jLabel13.setToolTipText("<HTML>\nAffiche les erreurs I2C, elles peuvent être duent a l'absence d'un capteur déclaré.<br>\nPar exemple si vous avez activé le module GPS mais qu'il n'est pas connecté.\n</HTML>");

        jLabel14.setText("Temps cycle :");

        I2CErrorsLabel.setText("0");

        cycleTimeLabel.setText("0");

        jLabel15.setText("Satellites :");
        jLabel15.setToolTipText("<html>\nNombre de sattelites en vue<br>\nLe fond clignote en rouge si aucun fix n'est acquis<br>\nSi un fix est acquis, le fond clignote en vert\n</html>");

        numSatLabel.setText("0");

        jLabel16.setText("Latitude :");
        jLabel16.setToolTipText("<HTML>Latitude lue par MotionControl</HTML>");

        latitudeLabel.setText("0");

        jLabel17.setText("Altitude :");
        jLabel17.setToolTipText("<html>Altitude <b>GPS</b> lue par MotionControl</html>");

        jLabel18.setText("longitude :");
        jLabel18.setToolTipText("<HTML>longitude lue par MotionControl</HTML>");

        gpsAltitudeLabel.setText("0");

        longitudeLabel.setText("0");

        jLabel19.setText("Vitesse :");
        jLabel19.setToolTipText("<HTML>Vitesse instantanée vue par le GPS</HTML>");

        jLabel20.setText("Route :");
        jLabel20.setToolTipText("<html>\nAngle entre le point de départ et la position courante\nCette donnée en degrées <b>n'est pas identique</b> au cap\n</html>");

        gpsSpeedLabel.setText("0");

        groundCourseLabel.setText("0");

        jLabel38.setText("Altitude baro :");

        baroAltLabel.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(I2CErrorsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cycleTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(numSatLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(latitudeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(gpsAltitudeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(longitudeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(gpsSpeedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(groundCourseLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(baroAltLabel)
                .addContainerGap(365, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(I2CErrorsLabel)
                    .addComponent(jLabel15)
                    .addComponent(numSatLabel)
                    .addComponent(jLabel17)
                    .addComponent(gpsAltitudeLabel)
                    .addComponent(jLabel19)
                    .addComponent(gpsSpeedLabel)
                    .addComponent(jLabel38)
                    .addComponent(baroAltLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(cycleTimeLabel)
                    .addComponent(jLabel16)
                    .addComponent(latitudeLabel)
                    .addComponent(jLabel18)
                    .addComponent(longitudeLabel)
                    .addComponent(jLabel20)
                    .addComponent(groundCourseLabel))
                .addGap(55, 55, 55))
        );

        javax.swing.GroupLayout graphTabPanelLayout = new javax.swing.GroupLayout(graphTabPanel);
        graphTabPanel.setLayout(graphTabPanelLayout);
        graphTabPanelLayout.setHorizontalGroup(
            graphTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(graphTabPanelLayout.createSequentialGroup()
                .addGroup(graphTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(graphTabPanelLayout.createSequentialGroup()
                        .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkBoxesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        graphTabPanelLayout.setVerticalGroup(
            graphTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(graphTabPanelLayout.createSequentialGroup()
                .addGroup(graphTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(graphPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkBoxesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabbedPane.addTab("Temps réel", graphTabPanel);

        javax.swing.GroupLayout mixPanelLayout = new javax.swing.GroupLayout(mixPanel);
        mixPanel.setLayout(mixPanelLayout);
        mixPanelLayout.setHorizontalGroup(
            mixPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mixPanelLayout.createSequentialGroup()
                .addComponent(mixagesPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 508, Short.MAX_VALUE))
        );
        mixPanelLayout.setVerticalGroup(
            mixPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mixPanelLayout.createSequentialGroup()
                .addComponent(mixagesPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 185, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Mixages", mixPanel);

        consoleTextArea.setEditable(false);
        consoleTextArea.setColumns(20);
        consoleTextArea.setRows(5);
        jScrollPane1.setViewportView(consoleTextArea);

        consoleTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consoleTextFieldActionPerformed(evt);
            }
        });

        sendButton.setText("Envoyer");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel21.setText("Mode :");
        jPanel1.add(jLabel21, new java.awt.GridBagConstraints());

        buttonGroup1.add(consoleRadioButtonMC);
        consoleRadioButtonMC.setSelected(true);
        consoleRadioButtonMC.setText("MotionControl");
        consoleRadioButtonMC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consoleRadioButtonMCActionPerformed(evt);
            }
        });
        jPanel1.add(consoleRadioButtonMC, new java.awt.GridBagConstraints());

        buttonGroup1.add(consoleRadioButtonSerial);
        consoleRadioButtonSerial.setText("Serial");
        consoleRadioButtonSerial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consoleRadioButtonSerialActionPerformed(evt);
            }
        });
        jPanel1.add(consoleRadioButtonSerial, new java.awt.GridBagConstraints());

        javax.swing.GroupLayout consolePanelLayout = new javax.swing.GroupLayout(consolePanel);
        consolePanel.setLayout(consolePanelLayout);
        consolePanelLayout.setHorizontalGroup(
            consolePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(consolePanelLayout.createSequentialGroup()
                .addComponent(consoleTextField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sendButton))
            .addGroup(consolePanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 898, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 908, Short.MAX_VALUE)
        );
        consolePanelLayout.setVerticalGroup(
            consolePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(consolePanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(consolePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(consoleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton)))
        );

        tabbedPane.addTab("Console", consolePanel);

        worldPanel.setLayout(new java.awt.BorderLayout());
        tabbedPane.addTab("World", worldPanel);

        serialPortsComboBox.setModel(new DefaultComboBoxModel(fr.devTeam.motionConf.util.Network.getPortList().toArray()));
        serialPortsComboBox.setToolTipText("<HTML>Selectionnez le port correspondant a votre module FTDI</HTML>");
        serialPortsComboBox.setMaximumSize(new java.awt.Dimension(90, 20));
        serialPortsComboBox.setMinimumSize(new java.awt.Dimension(90, 20));
        serialPortsComboBox.setPreferredSize(new java.awt.Dimension(90, 20));

        speedComboBox.setModel(new javax.swing.DefaultComboBoxModel(Constantes.SERIAL_SPEED));
        speedComboBox.setToolTipText("<HTML>Sélectionnez la vitesse de transmission des données<br>\n115200 si vous avez laissé ce paramètre par défaut</HTML>");
        speedComboBox.setMaximumSize(new java.awt.Dimension(90, 20));
        speedComboBox.setMinimumSize(new java.awt.Dimension(90, 20));
        speedComboBox.setPreferredSize(new java.awt.Dimension(90, 20));

        connect.setText("Connecter");
        connect.setToolTipText("<HTML>Ouverture de la connexion a la FC<br>\nassurez vous qu'elle est correctement connectée et donc allumée</HTML>");
        connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectActionPerformed(evt);
            }
        });

        resetButton.setText("Réinitialiser");
        resetButton.setToolTipText("<HTML>Réinitialise tous les paramètres contenus dans l'EEPROM et relance l'interface</HTML>");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        saveEEPROMButton.setText("Sauver");
        saveEEPROMButton.setToolTipText("<HTML>Sauver les paramètres courants dans l'EEPROM<br>\n<b>Attention</b> les réglages actuels de la FC sont éffacés <br>\net remplacés par ceux-ci </HTML>");
        saveEEPROMButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveEEPROMButtonActionPerformed(evt);
            }
        });

        readEEPromButton.setText("Lire");
        readEEPromButton.setToolTipText("<HTML>Lecture des paramètres contenu dand l'EEPROM de la FC</HTML>");
        readEEPromButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readEEPromButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("EEPROM");

        jLabel1.setText("Port :");

        jLabel2.setText("Vitesse :");

        jButton1.setText("Rafraichir");
        jButton1.setToolTipText("<HTML>Permets de rafraichir la liste des ports</HTML>");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        loadConfigFile.setText("Charger");
        loadConfigFile.setToolTipText("<HTML>Charger des paramètres depuis un fichier sur l'ordinateur</HTML>");
        loadConfigFile.setMaximumSize(new java.awt.Dimension(95, 23));
        loadConfigFile.setMinimumSize(new java.awt.Dimension(95, 23));
        loadConfigFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadConfigFileActionPerformed(evt);
            }
        });

        saveConfigFile.setText("Sauvegarder");
        saveConfigFile.setToolTipText("<HTML>Sauvegarder les paramètres actuels de l'interface dans un fichier sur l'ordinateur</HTML>");
        saveConfigFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveConfigFileActionPerformed(evt);
            }
        });

        jLabel4.setText("Paramètres");

        javax.swing.GroupLayout toolPaneLayout = new javax.swing.GroupLayout(toolPane);
        toolPane.setLayout(toolPaneLayout);
        toolPaneLayout.setHorizontalGroup(
            toolPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolPaneLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(toolPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(toolPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(toolPaneLayout.createSequentialGroup()
                        .addComponent(speedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(connect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(toolPaneLayout.createSequentialGroup()
                        .addComponent(serialPortsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(toolPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(toolPaneLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel3))
                    .addComponent(resetButton))
                .addGap(18, 18, 18)
                .addGroup(toolPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(saveEEPROMButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(readEEPromButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(10, 10, 10)
                .addGroup(toolPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(saveConfigFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loadConfigFile, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        toolPaneLayout.setVerticalGroup(
            toolPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolPaneLayout.createSequentialGroup()
                .addGroup(toolPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(toolPaneLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel2))
                    .addGroup(toolPaneLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(toolPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(toolPaneLayout.createSequentialGroup()
                                .addComponent(loadConfigFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(saveConfigFile))
                            .addGroup(toolPaneLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel4))))
                    .addGroup(toolPaneLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(toolPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(serialPortsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGap(8, 8, 8)
                        .addGroup(toolPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(speedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(connect)))
                    .addGroup(toolPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(toolPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, toolPaneLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel3)
                                .addGap(11, 11, 11)
                                .addGroup(toolPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(resetButton)
                                    .addComponent(readEEPromButton)))
                            .addComponent(saveEEPROMButton, javax.swing.GroupLayout.Alignment.LEADING))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(toolPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectActionPerformed
	if (!"<none>".equals((String)serialPortsComboBox.getSelectedItem())) {
	    if (!serial.isConnectionOpened()) {
		try {
		    serial.connect((String)serialPortsComboBox.getSelectedItem(), Integer.parseInt((String)speedComboBox.getSelectedItem()));
		    connect.setText("Déconnecter");
		    
                    trtr.setInactive();
                    serialPane();
		} catch (SerialException ex) {
		    Logger.getLogger(MotionConfGUI.class.getName()).log(Level.SEVERE, null, ex);
		    JOptionPane.showMessageDialog(this, ex.getMessage(), "Connection error",  JOptionPane.ERROR_MESSAGE);

		    if (ex.getCode() == SerialException.E_ALREADY_CONNECTED) {
			connect.setText("Connecter");
		    }
		}
	    } else {
		try {
		    trtr.setInactive();
                    Thread.sleep(300);
		    serial.disconnect();
		    
		    connect.setText("Connecter");
		} catch (Exception ex) {
		    JOptionPane.showMessageDialog(this, ex.getMessage(), "disconnection error",  JOptionPane.ERROR_MESSAGE);
		    Logger.getLogger(MotionConfGUI.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	}
    }//GEN-LAST:event_connectActionPerformed

    private void loadConfigFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadConfigFileActionPerformed
        if (!Constantes.FILE_DESCRIPTION.equals(fileChooser.getFileFilter().getDescription())) {
            fileChooser.setFileFilter(McFileType.getExtensionFilter());
        }
        
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                FileInputStream fis = new FileInputStream(file.getAbsolutePath());
                ObjectInputStream ois = new ObjectInputStream(fis);
                Parameters.setInstance((Parameters) ois.readObject());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "load error",  JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_loadConfigFileActionPerformed

    private void saveConfigFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveConfigFileActionPerformed
        if (!Constantes.FILE_DESCRIPTION.equals(fileChooser.getFileFilter().getDescription())) {
            fileChooser.setFileFilter(McFileType.getExtensionFilter());
        }
        
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(params);
            } catch (java.io.IOException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "save error",  JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_saveConfigFileActionPerformed

    private void tabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedPaneStateChanged
        serialPane();
    }//GEN-LAST:event_tabbedPaneStateChanged

    private void debug4CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_debug4CheckBoxActionPerformed
        refreshSeries(debug4CheckBox);
    }//GEN-LAST:event_debug4CheckBoxActionPerformed

    private void debug3CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_debug3CheckBoxActionPerformed
        refreshSeries(debug3CheckBox);
    }//GEN-LAST:event_debug3CheckBoxActionPerformed

    private void debug2CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_debug2CheckBoxActionPerformed
        refreshSeries(debug2CheckBox);
    }//GEN-LAST:event_debug2CheckBoxActionPerformed

    private void debug1CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_debug1CheckBoxActionPerformed
        refreshSeries(debug1CheckBox);
    }//GEN-LAST:event_debug1CheckBoxActionPerformed

    private void altCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altCheckBoxActionPerformed
        refreshSeries(altCheckBox);
    }//GEN-LAST:event_altCheckBoxActionPerformed

    private void magZCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_magZCheckBoxActionPerformed
        refreshSeries(magZCheckBox);
    }//GEN-LAST:event_magZCheckBoxActionPerformed

    private void magYCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_magYCheckBoxActionPerformed
        refreshSeries(magYCheckBox);
    }//GEN-LAST:event_magYCheckBoxActionPerformed

    private void magXCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_magXCheckBoxActionPerformed
        refreshSeries(magXCheckBox);
    }//GEN-LAST:event_magXCheckBoxActionPerformed

    private void accZCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accZCheckBoxActionPerformed
        refreshSeries(accZCheckBox);
    }//GEN-LAST:event_accZCheckBoxActionPerformed

    private void accYCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accYCheckBoxActionPerformed
        refreshSeries(accYCheckBox);
    }//GEN-LAST:event_accYCheckBoxActionPerformed

    private void accXCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accXCheckBoxActionPerformed
        refreshSeries(accXCheckBox);
    }//GEN-LAST:event_accXCheckBoxActionPerformed

    private void gyroZCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gyroZCheckBoxActionPerformed
        refreshSeries(gyroZCheckBox);
    }//GEN-LAST:event_gyroZCheckBoxActionPerformed

    private void gyroYCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gyroYCheckBoxActionPerformed
        refreshSeries(gyroYCheckBox);
    }//GEN-LAST:event_gyroYCheckBoxActionPerformed

    private void gyroXCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gyroXCheckBoxActionPerformed
        refreshSeries(gyroXCheckBox);
    }//GEN-LAST:event_gyroXCheckBoxActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
           serial.writeRawString(consoleTextField.getText());
           consoleTextArea.append(consoleTextField.getText()+"\n");
           consoleTextField.setText("");
    }//GEN-LAST:event_sendButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        serialPortsComboBox.setModel(new DefaultComboBoxModel(fr.devTeam.motionConf.util.Network.getPortList().toArray()));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void readEEPromButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_readEEPromButtonActionPerformed
        sendConfRequests();
    }//GEN-LAST:event_readEEPromButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        if (serial.isConnectionOpened()) {
            serial.writeMSP(Constantes.MSP_RESET_CONF);
            sendConfRequests();
        }
    }//GEN-LAST:event_resetButtonActionPerformed

    private void saveEEPROMButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveEEPROMButtonActionPerformed
        serial.writeMSP(Constantes.MSP_SET_PID, ParametersComponents.getInstance().getPids());
        serial.writeMSP(Constantes.MSP_SET_RC_TUNING, ParametersComponents.getInstance().getRcTuning());
        serial.writeMSP(Constantes.MSP_SET_BOX, ParametersComponents.getInstance().getBoxes());
        serial.writeMSP(Constantes.MSP_EEPROM_WRITE);
        sendConfRequests();
    }//GEN-LAST:event_saveEEPROMButtonActionPerformed

    private void accCalibButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accCalibButtonActionPerformed
        if (SerialSingleton.getInstance().getSerial().isConnectionOpened())
            serial.writeMSP(Constantes.MSP_ACC_CALIBRATION);
    }//GEN-LAST:event_accCalibButtonActionPerformed

    private void magCalibButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_magCalibButtonActionPerformed
        if (SerialSingleton.getInstance().getSerial().isConnectionOpened())
            serial.writeMSP(Constantes.MSP_MAG_CALIBRATION);
    }//GEN-LAST:event_magCalibButtonActionPerformed

    private void consoleRadioButtonMCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consoleRadioButtonMCActionPerformed
        if (SerialSingleton.getInstance().getSerial().isConnectionOpened()) {
            trtr.setRealTimeTasks(new int[]{Constantes.MSP_DEBUGMSG});
            trtr.setSleepTime(200);
            trtr.setActive();
            SerialSingleton.getInstance().getSerial().removeRawSerialDataListener(rawSerialDataAdapter);
        }
    }//GEN-LAST:event_consoleRadioButtonMCActionPerformed

    private void consoleRadioButtonSerialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consoleRadioButtonSerialActionPerformed
        if (SerialSingleton.getInstance().getSerial().isConnectionOpened()) {
            trtr.setSleepTime(200);
            trtr.setInactive();
            SerialSingleton.getInstance().getSerial().addRawSerialDataListener(rawSerialDataAdapter);
        }
    }//GEN-LAST:event_consoleRadioButtonSerialActionPerformed

    private void consoleTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consoleTextFieldActionPerformed
        sendButtonActionPerformed(evt);
    }//GEN-LAST:event_consoleTextFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
/*            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());*/
            UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(MotionConfGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MotionConfGUI().setVisible(true);
            }
        });
    }
    
    private void serialPane() {
        if (serial.isConnectionOpened()) {
            switch (tabbedPane.getSelectedIndex()) {
                case 1 :
                    trtr.setRealTimeTasks(new int[]{Constantes.MSP_ATTITUDE, Constantes.MSP_RC,
                        Constantes.MSP_MOTOR, Constantes.MSP_SERVO});
                    trtr.setSleepTime(50);
                    trtr.setActive();
                    break;
                case 2 :
                    trtr.setRealTimeTasks(new int[]{Constantes.MSP_RAW_IMU, Constantes.MSP_RAW_GPS, 
                            Constantes.MSP_DEBUG, Constantes.MSP_ALTITUDE,
                            Constantes.MSP_STATUS, Constantes.MSP_COMP_GPS});
                    trtr.setSleepTime(100);
                    trtr.setActive();
                    break;
                case 4 :
                    if (consoleRadioButtonMC.isSelected()) {
                        trtr.setRealTimeTasks(new int[]{Constantes.MSP_DEBUGMSG});
                        trtr.setSleepTime(200);
                        trtr.setActive();
                    } else {
                        trtr.setSleepTime(200);
                        trtr.setInactive();
                        SerialSingleton.getInstance().getSerial().addRawSerialDataListener(rawSerialDataAdapter);
                    }
                    break;
                default:
                    SerialSingleton.getInstance().getSerial().removeRawSerialDataListener(rawSerialDataAdapter);
                    trtr.setSleepTime(200);
                    trtr.setInactive();
            }
        } else {
            trtr.setSleepTime(200);
            trtr.setInactive();
        }
    }
    
    private void sendConfRequests () {
        if (serial.isConnectionOpened()) {
            serial.writeMSP(Constantes.MSP_BOXIDS);
            serial.writeMSP(Constantes.MSP_BOXNAMES);
            serial.writeMSP(Constantes.MSP_BOX);
            serial.writeMSP(Constantes.MSP_PID);
            serial.writeMSP(Constantes.MSP_RC_TUNING);
            serial.writeMSP(Constantes.MSP_STATUS);
        }
    }
    
    private void initGraph () {
        final JFreeChart chart = createChart(dataset);

        final ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        graphPanel.add(chartPanel);
        
        
        refreshSeries(gyroXCheckBox);
        refreshSeries(gyroYCheckBox);
        refreshSeries(gyroZCheckBox);
        refreshSeries(accXCheckBox);
        refreshSeries(accYCheckBox);
        refreshSeries(accZCheckBox);
        
    }
    
    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
            null, 
            null, 
            null,
            dataset, 
            true, 
            true, 
            false
        );
        final XYPlot plot = result.getXYPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(60000.0);  // 60 seconds
        axis = plot.getRangeAxis();
        axis.setAutoRange(true);
        return result;
    }
    
    private void refreshSeries (JCheckBox box) {
        for (int i = 0; i < series.length; i++) {
            if (box.getText().equals(seriesNames[i])) {
                if (box.isSelected()) {
                    dataset.addSeries(series[i]);
                } else {
                    dataset.removeSeries(series[i]);
                }
            }
        }
    }
    
    private void initListeners() {
        SerialSingleton.getInstance().getSerial().getMotorServo().addMotorServoDataListener(new MotorServoDataAdapter() {
           @Override public void newMotorData(final MotorBean motor) {
                int[] motors = motor.getMotor();
                m1Bar.setValue(motors[0]);
                m2Bar.setValue(motors[1]);
                m3Bar.setValue(motors[2]);
                m4Bar.setValue(motors[3]);
                m5Bar.setValue(motors[4]);
                m6Bar.setValue(motors[5]);
                m7Bar.setValue(motors[6]);
                m8Bar.setValue(motors[7]);
                m1Bar.setString(Integer.toString(motors[0]));
                m2Bar.setString(Integer.toString(motors[1]));
                m3Bar.setString(Integer.toString(motors[2]));
                m4Bar.setString(Integer.toString(motors[3]));
                m5Bar.setString(Integer.toString(motors[4]));
                m6Bar.setString(Integer.toString(motors[5]));
                m7Bar.setString(Integer.toString(motors[6]));
                m8Bar.setString(Integer.toString(motors[7]));
           }

           @Override public void newServoData(final ServoBean servo) {
                int[] servos = servo.getServo();
                s1Bar.setValue(servos[0]);
                s2Bar.setValue(servos[1]);
                s3Bar.setValue(servos[2]);
                s4Bar.setValue(servos[3]);
                s5Bar.setValue(servos[4]);
                s6Bar.setValue(servos[5]);
                s7Bar.setValue(servos[6]);
                s8Bar.setValue(servos[7]);
                s1Bar.setString(Integer.toString(servos[0]));
                s2Bar.setString(Integer.toString(servos[1]));
                s3Bar.setString(Integer.toString(servos[2]));
                s4Bar.setString(Integer.toString(servos[3]));
                s5Bar.setString(Integer.toString(servos[4]));
                s6Bar.setString(Integer.toString(servos[5]));
                s7Bar.setString(Integer.toString(servos[6]));
                s8Bar.setString(Integer.toString(servos[7]));
            }
        });
        
        SerialSingleton.getInstance().getSerial().getAttitudeData().addAttitudeDataListener(new AttitudeDataAdapter() {
            @Override public void newAttitudeData(final AttitudeBean attitude) {
                rotateLabelIcon(pitchLabel, "/img/pitch.png", attitude.getAngle()[1]/10);
                rotateLabelIcon(rollLabel, "/img/roll.png", attitude.getAngle()[0]/10);
                rotateLabelIcon(compassLabel, "/img/compass_arrow.png", attitude.getHeading());
            }
        });
        
        rtd.addRealTimeDataListener(new RealTimeAdapter() {
            @Override public void newRcData (final RcBean rcData) {
		SwingUtilities.invokeLater(new Runnable() {
		    @Override public void run() {
			int[] rcValues = rcData.getRcData();
			yawBar.setValue(rcValues[Constantes.YAW]);
			yawBar.setString(Integer.toString(rcValues[Constantes.YAW]));
			thrBar.setValue(rcValues[Constantes.THROTTLE]);
			thrBar.setString(Integer.toString(rcValues[Constantes.THROTTLE]));
			rollBar.setValue(rcValues[Constantes.ROLL]);
			rollBar.setString(Integer.toString(rcValues[Constantes.ROLL]));
			pitchBar.setValue(rcValues[Constantes.PITCH]);
			pitchBar.setString(Integer.toString(rcValues[Constantes.PITCH]));
			aux1Bar.setValue(rcValues[Constantes.AUX1]);
			aux1Bar.setString(Integer.toString(rcValues[Constantes.AUX1]));
			aux2Bar.setValue(rcValues[Constantes.AUX2]);
			aux2Bar.setString(Integer.toString(rcValues[Constantes.AUX2]));
			aux3Bar.setValue(rcValues[Constantes.AUX3]);
			aux3Bar.setString(Integer.toString(rcValues[Constantes.AUX3]));
			aux4Bar.setValue(rcValues[Constantes.AUX4]);
			aux4Bar.setString(Integer.toString(rcValues[Constantes.AUX4]));
		    }
		});
            }

            @Override public void newRawIMUData(RawImuBean rawImu) {
                int[] gyro = rawImu.getRawGyro();
                int[] acc = rawImu.getRawAcc();
                int[] mag = rawImu.getRawMag();
                Millisecond ms = new Millisecond();
                
                series[0].addOrUpdate(ms, gyro[0]);
                series[1].addOrUpdate(ms, gyro[1]);
                series[2].addOrUpdate(ms, gyro[2]);
                
                series[3].addOrUpdate(ms, acc[0]);
                series[4].addOrUpdate(ms, acc[1]);
                series[5].addOrUpdate(ms, acc[2]);
                
                series[6].addOrUpdate(ms, mag[0]);
                series[7].addOrUpdate(ms, mag[1]);
                series[8].addOrUpdate(ms, mag[2]);
            }
            
            @Override public void newDebugData(DebugBean debug) {
                int[] debugData = debug.getDebug();
                Millisecond ms = new Millisecond();
                
                series[10].addOrUpdate(ms, debugData[0]);
                series[11].addOrUpdate(ms, debugData[1]);
                series[12].addOrUpdate(ms, debugData[2]);
                series[13].addOrUpdate(ms, debugData[3]);
            }
            
            @Override public void newAltitudeData(AltitudeBean altitude) {
                series[9].addOrUpdate(new Millisecond(), altitude.getAltitude());
                baroAltLabel.setText(Float.toString((float)altitude.getAltitude()/100));
            }
            
            @Override public void newRawGPSData (final RawGpsBean rawGps) {
		SwingUtilities.invokeLater(new Runnable() {
		    @Override public void run() {
			numSatLabel.setText(Integer.toString(rawGps.getNumSat()));
			longitudeLabel.setText(Long.toString(rawGps.getLongitude()));
			latitudeLabel.setText(Long.toString(rawGps.getLatitude()));
			gpsAltitudeLabel.setText(Integer.toString(rawGps.getAltitude()));
			gpsSpeedLabel.setText(Integer.toString(rawGps.getSpeed()));
			groundCourseLabel.setText(Integer.toString(rawGps.getGroundCourse()));
		    }
		});
            }
            
            @Override public void newStatusData (final StatusBean status) {
		SwingUtilities.invokeLater(new Runnable() {
		    @Override public void run() {
			I2CErrorsLabel.setText(Integer.toString(status.getI2cErrors()));
			cycleTimeLabel.setText(Integer.toString(status.getCycleTime()));
		    }
		});
            }
            
            @Override public void newCompGPSData (final CompGpsBean compGps) {
		SwingUtilities.invokeLater(new Runnable() {
		    @Override public void run() {
			if (compGps.getUpdate() == 0) {
			    numSatLabel.setForeground(Color.BLACK);
			} else {
			    if (rtd.getRawGps().getFix() == 0) {
				numSatLabel.setForeground(Color.RED);
			    } else {
				numSatLabel.setForeground(Color.GREEN);
			    }
			}
		    }
		});
            }
        });
        
        SerialSingleton.getInstance().getSerial().getDebugMsg().addDebugMsgDataListener(new DebugMsgAdapter() {
            @Override public void newDebugMsgData(final String msg) {
                consoleTextArea.append(msg.replace('\0', '\n'));
            }
}       );
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel I2CErrorsLabel;
    private javax.swing.JButton accCalibButton;
    private javax.swing.JCheckBox accXCheckBox;
    private javax.swing.JCheckBox accYCheckBox;
    private javax.swing.JCheckBox accZCheckBox;
    private javax.swing.JCheckBox altCheckBox;
    private javax.swing.JPanel attitudePanel;
    private javax.swing.JProgressBar aux1Bar;
    private javax.swing.JProgressBar aux2Bar;
    private javax.swing.JProgressBar aux3Bar;
    private javax.swing.JProgressBar aux4Bar;
    private javax.swing.JLabel baroAltLabel;
    private fr.devTeam.motionConf.gui.BoxPanel boxPanel1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel checkBoxesPanel;
    private javax.swing.JPanel compassImagePanel;
    private javax.swing.JLabel compassLabel;
    private javax.swing.JButton connect;
    private javax.swing.JPanel consolePanel;
    private javax.swing.JRadioButton consoleRadioButtonMC;
    private javax.swing.JRadioButton consoleRadioButtonSerial;
    private javax.swing.JTextArea consoleTextArea;
    private javax.swing.JTextField consoleTextField;
    private fr.devTeam.motionConf.gui.curvesPanel curvesPanel1;
    private javax.swing.JLabel cycleTimeLabel;
    private javax.swing.JCheckBox debug1CheckBox;
    private javax.swing.JCheckBox debug2CheckBox;
    private javax.swing.JCheckBox debug3CheckBox;
    private javax.swing.JCheckBox debug4CheckBox;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JLabel gpsAltitudeLabel;
    private javax.swing.JLabel gpsSpeedLabel;
    private javax.swing.JPanel graphPanel;
    private javax.swing.JPanel graphTabPanel;
    private javax.swing.JLabel groundCourseLabel;
    private javax.swing.JCheckBox gyroXCheckBox;
    private javax.swing.JCheckBox gyroYCheckBox;
    private javax.swing.JCheckBox gyroZCheckBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel latitudeLabel;
    private javax.swing.JButton loadConfigFile;
    private javax.swing.JLabel longitudeLabel;
    private javax.swing.JProgressBar m1Bar;
    private javax.swing.JProgressBar m2Bar;
    private javax.swing.JProgressBar m3Bar;
    private javax.swing.JProgressBar m4Bar;
    private javax.swing.JProgressBar m5Bar;
    private javax.swing.JProgressBar m6Bar;
    private javax.swing.JProgressBar m7Bar;
    private javax.swing.JProgressBar m8Bar;
    private javax.swing.JButton magCalibButton;
    private javax.swing.JCheckBox magXCheckBox;
    private javax.swing.JCheckBox magYCheckBox;
    private javax.swing.JCheckBox magZCheckBox;
    private javax.swing.JPanel mixPanel;
    private fr.devTeam.motionConf.gui.MixagesPanel mixagesPanel1;
    private javax.swing.JLabel numSatLabel;
    private javax.swing.JPanel paramsPanel;
    private fr.devTeam.motionConf.gui.PidPanel pidPanel1;
    private javax.swing.JProgressBar pitchBar;
    private javax.swing.JPanel pitchImagePanel;
    private javax.swing.JLabel pitchLabel;
    private javax.swing.JButton readEEPromButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JProgressBar rollBar;
    private javax.swing.JPanel rollImagePanel;
    private javax.swing.JLabel rollLabel;
    private javax.swing.JProgressBar s1Bar;
    private javax.swing.JProgressBar s2Bar;
    private javax.swing.JProgressBar s3Bar;
    private javax.swing.JProgressBar s4Bar;
    private javax.swing.JProgressBar s5Bar;
    private javax.swing.JProgressBar s6Bar;
    private javax.swing.JProgressBar s7Bar;
    private javax.swing.JProgressBar s8Bar;
    private javax.swing.JButton saveConfigFile;
    private javax.swing.JButton saveEEPROMButton;
    private javax.swing.JButton sendButton;
    private fr.devTeam.motionConf.gui.SensorsPanel sensorsPanel1;
    private javax.swing.JComboBox serialPortsComboBox;
    private javax.swing.JComboBox speedComboBox;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JProgressBar thrBar;
    private javax.swing.JPanel toolPane;
    private javax.swing.JPanel worldPanel;
    private javax.swing.JProgressBar yawBar;
    // End of variables declaration//GEN-END:variables
}
