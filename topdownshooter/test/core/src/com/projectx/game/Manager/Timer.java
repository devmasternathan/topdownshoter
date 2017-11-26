package com.projectx.game.Manager;

/**
 * <h1> Timer </h1>
 * <p> keeps the time. </p>
 */
public class Timer
{
    private float time;

    /**
     * <p> default constructor.</p>
     */
    public Timer()
    {
        time = 30;
    }

    /**
     * <p> constructor that takes in a timer. </p>
     * @param time a float instance
     */
    public Timer(float time)
    {
        this.time = time;
    }

    /**
     * <p> updates the time. </p>
     */
    public void update()
    {
        if (this.time != 0)
        {
            this.time--;
        }

    }


    /**
     * <p> updates the time based on a delta variable.. </p>
     */
    public void update(float x)
    {
        if (this.time != 0)
        {
            this.time = this.time - x;
        }

    }

    /**
     * <p> gets the time. </p>
     * @return
     */
    public float getTime()
    {
        return this.time;
    }
}