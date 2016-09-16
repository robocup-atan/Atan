package com.github.robocup_atan.atan.parser.objects;

//~--- non-JDK imports --------------------------------------------------------

import com.github.robocup_atan.atan.model.ControllerCoach;
import com.github.robocup_atan.atan.model.ControllerPlayer;
import com.github.robocup_atan.atan.model.ControllerTrainer;
import com.github.robocup_atan.atan.model.enums.Flag;

/**
 * The parser object for flag left.
 *
 * @author Atan
 */
public class ObjNameFlagLeft implements ObjName {
    int  number;
    char qualifier;

    /**
     * A constructor for left flags.
     *
     * @param qualifier Either 't' or 'b'.
     * @param number Either 10, 20 or 30.
     */
    public ObjNameFlagLeft(char qualifier, int number) {
        this.qualifier = qualifier;
        this.number    = number;
    }

    /** {@inheritDoc} */
    @Override
    public void infoSeeFromEast(ControllerPlayer c, double distance, double direction, double distChange,
                                double dirChange, double bodyFacingDirection, double headFacingDirection) {
        switch (qualifier) {
            case 't' :
                switch (number) {
                    case 30 :
                        c.infoSeeFlagOther(Flag.RIGHT_30, distance, direction, distChange, dirChange,
                                           bodyFacingDirection, headFacingDirection);
                        break;
                    case 20 :
                        c.infoSeeFlagOther(Flag.RIGHT_20, distance, direction, distChange, dirChange,
                                           bodyFacingDirection, headFacingDirection);
                        break;
                    case 10 :
                        c.infoSeeFlagOther(Flag.RIGHT_10, distance, direction, distChange, dirChange,
                                           bodyFacingDirection, headFacingDirection);
                        break;
                }
                ;
                break;
            case 'b' :
                switch (number) {
                    case 30 :
                        c.infoSeeFlagOther(Flag.LEFT_30, distance, direction, distChange, dirChange,
                                           bodyFacingDirection, headFacingDirection);
                        break;
                    case 20 :
                        c.infoSeeFlagOther(Flag.LEFT_20, distance, direction, distChange, dirChange,
                                           bodyFacingDirection, headFacingDirection);
                        break;
                    case 10 :
                        c.infoSeeFlagOther(Flag.LEFT_10, distance, direction, distChange, dirChange,
                                           bodyFacingDirection, headFacingDirection);
                        break;
                }
                ;
                break;
            case '0' :
                c.infoSeeFlagOther(Flag.CENTER, distance, direction, distChange, dirChange, bodyFacingDirection,
                                   headFacingDirection);
                break;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void infoSeeFromWest(ControllerPlayer c, double distance, double direction, double distChange,
                                double dirChange, double bodyFacingDirection, double headFacingDirection) {
        switch (qualifier) {
            case 't' :
                switch (number) {
                    case 30 :
                        c.infoSeeFlagOwn(Flag.LEFT_30, distance, direction, distChange, dirChange, bodyFacingDirection,
                                         headFacingDirection);
                        break;
                    case 20 :
                        c.infoSeeFlagOwn(Flag.LEFT_20, distance, direction, distChange, dirChange, bodyFacingDirection,
                                         headFacingDirection);
                        break;
                    case 10 :
                        c.infoSeeFlagOwn(Flag.LEFT_10, distance, direction, distChange, dirChange, bodyFacingDirection,
                                         headFacingDirection);
                        break;
                }
                ;
                break;
            case 'b' :
                switch (number) {
                    case 30 :
                        c.infoSeeFlagOwn(Flag.RIGHT_30, distance, direction, distChange, dirChange,
                                         bodyFacingDirection, headFacingDirection);
                        break;
                    case 20 :
                        c.infoSeeFlagOwn(Flag.RIGHT_20, distance, direction, distChange, dirChange,
                                         bodyFacingDirection, headFacingDirection);
                        break;
                    case 10 :
                        c.infoSeeFlagOwn(Flag.RIGHT_10, distance, direction, distChange, dirChange,
                                         bodyFacingDirection, headFacingDirection);
                        break;
                }
                ;
                break;
            case '0' :
                c.infoSeeFlagOwn(Flag.CENTER, distance, direction, distChange, dirChange, bodyFacingDirection,
                                 headFacingDirection);
                break;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void infoSeeFromEast(ControllerCoach c, double x, double y, double deltaX, double deltaY, double bodyAngle,
                                double neckAngle) {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    @Override
    public void infoSeeFromWest(ControllerCoach c, double x, double y, double deltaX, double deltaY, double bodyAngle,
                                double neckAngle) {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    @Override
    public void infoSee(ControllerTrainer c) {
        throw new UnsupportedOperationException();
    }
}
