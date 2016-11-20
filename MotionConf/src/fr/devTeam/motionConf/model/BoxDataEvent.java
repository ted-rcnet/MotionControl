/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.model;

import fr.devTeam.motionConf.msp.dao.BoxBean;
import fr.devTeam.motionConf.msp.dao.BoxIdBean;
import fr.devTeam.motionConf.msp.dao.BoxNamesBean;

/**
 *
 * @author fanny
 */
public class BoxDataEvent {
    private BoxBean box;
    private BoxIdBean boxId;
    private BoxNamesBean boxNames;

    
    public BoxBean getBox() {
        return box;
    }

    public void setBox(BoxBean box) {
        this.box = box;
    }

    public BoxIdBean getBoxId() {
        return boxId;
    }

    public void setBoxId(BoxIdBean boxId) {
        this.boxId = boxId;
    }

    public BoxNamesBean getBoxNames() {
        return boxNames;
    }

    public void setBoxNames(BoxNamesBean boxNames) {
        this.boxNames = boxNames;
    }
}
