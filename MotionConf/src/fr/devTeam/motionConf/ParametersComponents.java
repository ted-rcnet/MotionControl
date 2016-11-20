/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author fanny
 */
public class ParametersComponents {
    private static ParametersComponents Instance = new ParametersComponents();
    
    private JTable boxTable;
    private javax.swing.JTextField pAltTextField;
    private javax.swing.JTextField pLevelTextField;
    private javax.swing.JTextField pMagTextField;
    private javax.swing.JTextField pNavrTextField;
    private javax.swing.JTextField pPitchTextField;
    private javax.swing.JTextField pPosTextField;
    private javax.swing.JTextField pPosrTextField;
    private javax.swing.JTextField pRollTextField;
    private javax.swing.JTextField pYawTextField;
    private javax.swing.JTextField ratePRTextField;
    private javax.swing.JTextField tpaTextField;
    private javax.swing.JTextField yawRateTextField;
    private javax.swing.JTextField dAltTextField;
    private javax.swing.JTextField dLevelTextField;
    private javax.swing.JTextField dNavrTextField;
    private javax.swing.JTextField dPitchTextField;
    private javax.swing.JTextField dPosrTextField;
    private javax.swing.JTextField dRollTextField;
    private javax.swing.JTextField dYawTextField;
    private javax.swing.JTextField iAltTextField;
    private javax.swing.JTextField iLevelTextField;
    private javax.swing.JTextField iNavrTextField;
    private javax.swing.JTextField iPitchTextField;
    private javax.swing.JTextField iPosTextField;
    private javax.swing.JTextField iPosrTextField;
    private javax.swing.JTextField iRollTextField;
    private javax.swing.JTextField iYawTextField;
    private javax.swing.JLabel rcExpoLabel;
    private javax.swing.JLabel rcRateLabel;
    private javax.swing.JLabel thrExpLabel;
    private javax.swing.JPanel thrGraph;
    private javax.swing.JLabel thrMidLabel;
    
    private ParametersComponents () {}

    public static ParametersComponents getInstance() {
        return Instance;
    }

    public int[] getBoxes () {
        DefaultTableModel model = (DefaultTableModel)boxTable.getModel();
        int[] boxes = new int[model.getRowCount()];
        int[] message = new int[model.getRowCount() * 2];
        
        for(int i = 0; i < model.getRowCount(); i++) {
            boxes[i] = 0;
           for(int j = 1; j < model.getColumnCount(); j++) {
               Boolean active = (Boolean)model.getValueAt(i, j);
               if (active) boxes[i] |= 1 << (j-1);
           }
        }
        
        for (int i = 0; i<boxes.length; i++) {
            message[i*2] = boxes[i] & 0xFF;
            message[i*2+1] = (boxes[i] & 0xFF00) >> 8;
        }
        
        return message;
    }
    
    public int[] getRcTuning () {
        int[] rcTuning = new int[7];
        
        rcTuning[0] = Integer.parseInt(rcRateLabel.getText());
        rcTuning[1] = Integer.parseInt(rcExpoLabel.getText());
        rcTuning[2] = Integer.parseInt(ratePRTextField.getText());
        rcTuning[3] = Integer.parseInt(yawRateTextField.getText());
        rcTuning[4] = Integer.parseInt(tpaTextField.getText());
        rcTuning[5] = Integer.parseInt(thrMidLabel.getText());
        rcTuning[6] = Integer.parseInt(thrExpLabel.getText());
        
        return rcTuning;
    }
    
    public int[] getPids () {
        int[] pids = new int[27];
    
        
        pids[0] = Integer.parseInt(pRollTextField.getText());
        pids[1] = Integer.parseInt(iRollTextField.getText());
        pids[2] = Integer.parseInt(dRollTextField.getText());
        pids[3] = Integer.parseInt(pPitchTextField.getText());
        pids[4] = Integer.parseInt(iPitchTextField.getText());
        pids[5] = Integer.parseInt(dPitchTextField.getText());
        pids[6] = Integer.parseInt(pYawTextField.getText());
        pids[7] = Integer.parseInt(iYawTextField.getText());
        pids[8] = Integer.parseInt(dYawTextField.getText());
        pids[9] = Integer.parseInt(pAltTextField.getText());
        pids[10] = Integer.parseInt(iAltTextField.getText());
        pids[11] = Integer.parseInt(dAltTextField.getText());
        pids[12] = Integer.parseInt(pPosTextField.getText());
        pids[13] = Integer.parseInt(iPosTextField.getText());
        pids[14] = 0;
        pids[15] = Integer.parseInt(pPosrTextField.getText());
        pids[16] = Integer.parseInt(iPosrTextField.getText());
        pids[17] = Integer.parseInt(dPosrTextField.getText());
        pids[18] = Integer.parseInt(pNavrTextField.getText());
        pids[19] = Integer.parseInt(iNavrTextField.getText());
        pids[20] = Integer.parseInt(dNavrTextField.getText());
        pids[21] = Integer.parseInt(pLevelTextField.getText());
        pids[22] = Integer.parseInt(iLevelTextField.getText());
        pids[23] = Integer.parseInt(dLevelTextField.getText());
        pids[24] = Integer.parseInt(pMagTextField.getText());
        pids[25] = 0;
        pids[26] = 0;
                
        return pids;
    }
    
    public JTable getBoxTable() {
        return boxTable;
    }

    public void setBoxTable(JTable boxTable) {
        this.boxTable = boxTable;
    }

    public JTextField getpAltTextField() {
        return pAltTextField;
    }

    public void setpAltTextField(JTextField pAltTextField) {
        this.pAltTextField = pAltTextField;
    }

    public JTextField getpLevelTextField() {
        return pLevelTextField;
    }

    public void setpLevelTextField(JTextField pLevelTextField) {
        this.pLevelTextField = pLevelTextField;
    }

    public JTextField getpMagTextField() {
        return pMagTextField;
    }

    public void setpMagTextField(JTextField pMagTextField) {
        this.pMagTextField = pMagTextField;
    }

    public JTextField getpNavrTextField() {
        return pNavrTextField;
    }

    public void setpNavrTextField(JTextField pNavrTextField) {
        this.pNavrTextField = pNavrTextField;
    }

    public JTextField getpPitchTextField() {
        return pPitchTextField;
    }

    public void setpPitchTextField(JTextField pPitchTextField) {
        this.pPitchTextField = pPitchTextField;
    }

    public JTextField getpPosTextField() {
        return pPosTextField;
    }

    public void setpPosTextField(JTextField pPosTextField) {
        this.pPosTextField = pPosTextField;
    }

    public JTextField getpPosrTextField() {
        return pPosrTextField;
    }

    public void setpPosrTextField(JTextField pPosrTextField) {
        this.pPosrTextField = pPosrTextField;
    }

    public JTextField getpRollTextField() {
        return pRollTextField;
    }

    public void setpRollTextField(JTextField pRollTextField) {
        this.pRollTextField = pRollTextField;
    }

    public JTextField getpYawTextField() {
        return pYawTextField;
    }

    public void setpYawTextField(JTextField pYawTextField) {
        this.pYawTextField = pYawTextField;
    }

    public JTextField getRatePRTextField() {
        return ratePRTextField;
    }

    public void setRatePRTextField(JTextField ratePRTextField) {
        this.ratePRTextField = ratePRTextField;
    }

    public JTextField getTpaTextField() {
        return tpaTextField;
    }

    public void setTpaTextField(JTextField tpaTextField) {
        this.tpaTextField = tpaTextField;
    }

    public JTextField getYawRateTextField() {
        return yawRateTextField;
    }

    public void setYawRateTextField(JTextField yawRateTextField) {
        this.yawRateTextField = yawRateTextField;
    }

    public JTextField getdAltTextField() {
        return dAltTextField;
    }

    public void setdAltTextField(JTextField dAltTextField) {
        this.dAltTextField = dAltTextField;
    }

    public JTextField getdLevelTextField() {
        return dLevelTextField;
    }

    public void setdLevelTextField(JTextField dLevelTextField) {
        this.dLevelTextField = dLevelTextField;
    }

    public JTextField getdNavrTextField() {
        return dNavrTextField;
    }

    public void setdNavrTextField(JTextField dNavrTextField) {
        this.dNavrTextField = dNavrTextField;
    }

    public JTextField getdPitchTextField() {
        return dPitchTextField;
    }

    public void setdPitchTextField(JTextField dPitchTextField) {
        this.dPitchTextField = dPitchTextField;
    }

    public JTextField getdPosrTextField() {
        return dPosrTextField;
    }

    public void setdPosrTextField(JTextField dPosrTextField) {
        this.dPosrTextField = dPosrTextField;
    }

    public JTextField getdRollTextField() {
        return dRollTextField;
    }

    public void setdRollTextField(JTextField dRollTextField) {
        this.dRollTextField = dRollTextField;
    }

    public JTextField getdYawTextField() {
        return dYawTextField;
    }

    public void setdYawTextField(JTextField dYawTextField) {
        this.dYawTextField = dYawTextField;
    }

    public JTextField getiAltTextField() {
        return iAltTextField;
    }

    public void setiAltTextField(JTextField iAltTextField) {
        this.iAltTextField = iAltTextField;
    }

    public JTextField getiLevelTextField() {
        return iLevelTextField;
    }

    public void setiLevelTextField(JTextField iLevelTextField) {
        this.iLevelTextField = iLevelTextField;
    }

    public JTextField getiNavrTextField() {
        return iNavrTextField;
    }

    public void setiNavrTextField(JTextField iNavrTextField) {
        this.iNavrTextField = iNavrTextField;
    }

    public JTextField getiPitchTextField() {
        return iPitchTextField;
    }

    public void setiPitchTextField(JTextField iPitchTextField) {
        this.iPitchTextField = iPitchTextField;
    }

    public JTextField getiPosTextField() {
        return iPosTextField;
    }

    public void setiPosTextField(JTextField iPosTextField) {
        this.iPosTextField = iPosTextField;
    }

    public JTextField getiPosrTextField() {
        return iPosrTextField;
    }

    public void setiPosrTextField(JTextField iPosrTextField) {
        this.iPosrTextField = iPosrTextField;
    }

    public JTextField getiRollTextField() {
        return iRollTextField;
    }

    public void setiRollTextField(JTextField iRollTextField) {
        this.iRollTextField = iRollTextField;
    }

    public JTextField getiYawTextField() {
        return iYawTextField;
    }

    public void setiYawTextField(JTextField iYawTextField) {
        this.iYawTextField = iYawTextField;
    }

    public JLabel getRcExpoLabel() {
        return rcExpoLabel;
    }

    public void setRcExpoLabel(JLabel rcExpoLabel) {
        this.rcExpoLabel = rcExpoLabel;
    }

    public JLabel getRcRateLabel() {
        return rcRateLabel;
    }

    public void setRcRateLabel(JLabel rcRateLabel) {
        this.rcRateLabel = rcRateLabel;
    }

    public JLabel getThrExpLabel() {
        return thrExpLabel;
    }

    public void setThrExpLabel(JLabel thrExpLabel) {
        this.thrExpLabel = thrExpLabel;
    }

    public JPanel getThrGraph() {
        return thrGraph;
    }

    public void setThrGraph(JPanel thrGraph) {
        this.thrGraph = thrGraph;
    }

    public JLabel getThrMidLabel() {
        return thrMidLabel;
    }

    public void setThrMidLabel(JLabel thrMidLabel) {
        this.thrMidLabel = thrMidLabel;
    }
}
