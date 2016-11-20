/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.util;

import fr.devTeam.motionConf.wwUtils.MovablePointPlacemark;
import gov.nasa.worldwindx.examples.kml.KMZDocumentBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author EUGI7210
 */
public class mwcToKml {
    public static String convert(File inFile) throws FileNotFoundException {
        StringBuilder out = new StringBuilder();
        BufferedReader br = null;

        out.append(writeHeader());
        
        try {
            String sCurrentLine;

            br = new BufferedReader(new FileReader(inFile));

            while ((sCurrentLine = br.readLine()) != null) {
                int indComma = 0, indCommaNext = 0;
                indCommaNext = sCurrentLine.indexOf(',');

                try {
                    String latitude = sCurrentLine.substring(indComma, indCommaNext);
                    String longitude = sCurrentLine.substring(indComma = indCommaNext + 1, indCommaNext = sCurrentLine.indexOf(',', indCommaNext+1));
                    String altitude = sCurrentLine.substring(indComma = indCommaNext + 1);

                    Double dLat = Double.parseDouble(latitude) * Math.pow(10, -7);
                    Double dLon = Double.parseDouble(longitude)* Math.pow(10, -7);
                    Double dAlt = Double.parseDouble(altitude) / 100;
                    
                    if (dAlt > 10000) {dAlt = (double)20;}
                    
                    out.append(dLon + "," + dLat + "," + dAlt +" ");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        out.append(writeFooter());
        return out.toString();
    }

    public static void exportMission (File file, ArrayList<MovablePointPlacemark> mpps) {
        
        for (MovablePointPlacemark mpp : mpps) {
            
        }
    }
    
    private static String writeHeader() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n"
                + "<Document>\n"
                + "	<name></name>\n"
                + "	<Style id=\"sh_ylw-pushpin\">\n"
                + "		<IconStyle>\n"
                + "			<scale>1.3</scale>\n"
                + "			<Icon>\n"
                + "				<href>http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png</href>\n"
                + "			</Icon>\n"
                + "			<hotSpot x=\"20\" y=\"2\" xunits=\"pixels\" yunits=\"pixels\"/>\n"
                + "		</IconStyle>\n"
                + "		<LineStyle>\n"
                + "			<color>ffff0000</color>\n"
                + "			<width>3.1</width>\n"
                + "		</LineStyle>\n"
                + "	</Style>\n"
                + "	<StyleMap id=\"msn_ylw-pushpin\">\n"
                + "		<Pair>\n"
                + "			<key>normal</key>\n"
                + "			<styleUrl>#sn_ylw-pushpin</styleUrl>\n"
                + "		</Pair>\n"
                + "		<Pair>\n"
                + "			<key>highlight</key>\n"
                + "			<styleUrl>#sh_ylw-pushpin</styleUrl>\n"
                + "		</Pair>\n"
                + "	</StyleMap>\n"
                + "	<Style id=\"sn_ylw-pushpin\">\n"
                + "		<IconStyle>\n"
                + "			<scale>1.1</scale>\n"
                + "			<Icon>\n"
                + "				<href>http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png</href>\n"
                + "			</Icon>\n"
                + "			<hotSpot x=\"20\" y=\"2\" xunits=\"pixels\" yunits=\"pixels\"/>\n"
                + "		</IconStyle>\n"
                + "		<LineStyle>\n"
                + "			<color>ffff0000</color>\n"
                + "			<width>3.1</width>\n"
                + "		</LineStyle>\n"
                + "	</Style>\n"
                + "	<Placemark>\n"
                + "		<name>trajet_mwc</name>\n"
                + "		<styleUrl>#msn_ylw-pushpin</styleUrl>\n"
                + "		<LineString>\n"
                + "			<extrude>1</extrude>\n"
                + "			<altitudeMode>relativeToGround</altitudeMode>\n"
                + "			<coordinates>";
    }

    private static String writeFooter() {
        return "</coordinates>\n"
                + "		</LineString>\n"
                + "	</Placemark>\n"
                + "</Document>\n"
                + "</kml>";
    }
}
