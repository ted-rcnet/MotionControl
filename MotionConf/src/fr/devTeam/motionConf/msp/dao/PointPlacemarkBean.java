/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.devTeam.motionConf.msp.dao;

import fr.devTeam.motionConf.wwUtils.MovablePointPlacemark;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Offset;
import gov.nasa.worldwind.render.PointPlacemarkAttributes;
import java.io.Serializable;

/**
 *
 * @author fanny
 */
public class PointPlacemarkBean implements Serializable {
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private Double heading;
    private int number;

    public PointPlacemarkBean() {
    }

    public PointPlacemarkBean(Double latitude, Double longitude, Double altitude, Double heading, int number) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.heading = heading;
        this.number = number;
    }
    
    public PointPlacemarkBean(MovablePointPlacemark mpp) {
        this.latitude = mpp.getPosition().getLatitude().getDegrees();
        this.longitude = mpp.getPosition().getLongitude().getDegrees();
        this.altitude = mpp.getPosition().getAltitude();
        this.heading = mpp.getAttributes().getHeading();
        this.number = mpp.getPointNumber();
    }
    
    public MovablePointPlacemark toMovablePointPlacemark() {
        Position p = new Position(Angle.fromDegrees(latitude), Angle.fromDegrees(longitude), altitude);
        MovablePointPlacemark pm = new MovablePointPlacemark(p);
        PointPlacemarkAttributes attrs = new PointPlacemarkAttributes();
        attrs.setImageAddress("img/imageicon-64.png");
        attrs.setImageOffset(new Offset(0.5, 0.5, AVKey.FRACTION, AVKey.FRACTION));
        attrs.setHeading(heading);
        pm.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        pm.setLabelText("0");
        pm.setLineEnabled(true);
        pm.setAttributes(attrs);
        
        return pm;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Double getHeading() {
        return heading;
    }

    public void setHeading(Double heading) {
        this.heading = heading;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    
}
