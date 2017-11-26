package com.projectx.game.Manager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * <p>This will help you control the states
 * in the game. Here is where you implement the
 * methods that will be in every game screen.</p>
 */
public abstract class State
{

    protected GSM gsm;

    /**
     *<p> constructor of teh state class.</p>
     * @param gsm game stae manager instance.
     */
    protected State(GSM gsm)
    {
        this.gsm = gsm;
    }

    /**
     * <p> update the method. </p>
     * @param delta delta time.
     */
    public abstract void update(float delta);

    /**
     * <p> update the render. </p>
     * @param batch instance of a spritebatch.
     */
    public abstract void render(SpriteBatch batch);
}
