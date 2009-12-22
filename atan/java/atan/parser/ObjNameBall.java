package atan.parser;

//~--- non-JDK imports --------------------------------------------------------

import atan.model.Controller;

/**
 * Class description
 * @author Atan
 */
class ObjNameBall implements ObjName {

    /**
     * Method description
     * @param c
     * @param dist
     * @param dir
     */
    @Override
    public void infoSeeFromEast(Controller c, double dist, double dir) {
        c.infoSeeBall(dist, dir);
    }

    /**
     * Method description
     * @param c
     * @param dist
     * @param dir
     */
    @Override
    public void infoSeeFromWest(Controller c, double dist, double dir) {
        c.infoSeeBall(dist, dir);
    }
}
