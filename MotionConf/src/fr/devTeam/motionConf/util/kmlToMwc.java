/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.util;

import fr.devTeam.motionConf.wwUtils.MovablePointPlacemark;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author EUGI7210
 */
public class kmlToMwc {

    public static void convert(File outFile, ArrayList<MovablePointPlacemark> mpps) throws FileNotFoundException, IOException {
        PrintWriter pwOutFile = null;

        FileWriter writer = new FileWriter(outFile, true);
        pwOutFile = new PrintWriter(writer);

        Collections.sort(mpps, new Comparator<MovablePointPlacemark> () {
            @Override
            public int compare(MovablePointPlacemark o1, MovablePointPlacemark o2) {
                return Integer.compare(o1.getPointNumber(), o2.getPointNumber());
            }
        });

        for (MovablePointPlacemark mpp : mpps) {
            String line;
            line = getFormatedValues(mpp);

            pwOutFile.println(line);
        }
        
        pwOutFile.close();
    }

    private static String getFormatedValues(MovablePointPlacemark mpp) {
        int latitude = (int) Math.round(mpp.getPosition().getLatitude().getDegrees() * Math.pow(10, 7));
        int longitude = (int) Math.round(mpp.getPosition().getLongitude().getDegrees() * Math.pow(10, 7));
        int heading = (int) Math.round(mpp.getAttributes().getHeading());

        int altitude = (int) Math.round(mpp.getPosition().getAltitude());

        return "" + latitude + "," + longitude + "," + altitude + "," + heading;
    }
}
