package fr.devTeam.motionConf.wwUtils;


import gov.nasa.worldwind.Movable;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.PointPlacemark;

public class MovablePointPlacemark extends PointPlacemark implements Movable {
    private int pointNumber = 0;

    public MovablePointPlacemark(Position position) {
        super(position);
    }

    public int getPointNumber() {
        return pointNumber;
    }

    public void setPointNumber(int pointNumber) {
        this.pointNumber = pointNumber;
    }


    @Override
    public Position getReferencePosition() {
        return getPosition();
    }

    @Override
    public void move(Position delta) {
        setPosition(getReferencePosition().add(delta));
    }

    @Override
    public void moveTo(Position position) {
        setPosition(position);
    }

}