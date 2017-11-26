package com.projectx.game;

import java.util.Random;

/**
 * <h1> Position </h1>
 * <p> hold the position of a class. </p>
 */
public class Position
{
    /**
     * <p> getx the x coordinates. </p>
     * @return float
     */
    public float getX()
    {
        return x;
    }

    /**
     * <p> getx the y coordinates. </p>
     * @return float
     */
    public float getY()
    {
        return y;
    }

    /**
     * <p> sets  the x coordinates. </p>
     */
    public void setX(float x)
    {
        this.x = x;
    }

    /**
     * <p> sets  the y coordinates. </p>
     */
    public void setY(float y)
    {
        this.y = y;
    }

    /**
     * <p> gets a random number. </p>
     */
    public int getRandom() {return random;}

    /**
     * <p> sets a random number. </p>
     */
    public void setRandom(int random){this.random = random;}

    // variables
    float x;
    float y;
    int random;
}
