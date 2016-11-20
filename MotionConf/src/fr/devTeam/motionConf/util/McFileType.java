/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.util;

import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author EUGI7210
 */
public class McFileType {
    
    public static FileNameExtensionFilter getExtensionFilter () {
        return new FileNameExtensionFilter(Constantes.FILE_DESCRIPTION, Constantes.FILE_EXTENSION);
    }
}
