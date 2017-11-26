package com.projectx.game.Manager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;


/**
 * <h1> Game State Manager</h1>
 * <p> This is where you control how the game states
 * are handled.</p>
 */
public class GSM extends Game
{

    private Stack<State> states;

    /**
     * <p> constructor that initializes the stack of states.</p>
     */
    public GSM()
    {
        states = new Stack<State>();
    }

    /**
     * <p> pushes the states into the game state. </p>
     * @param s a state instance
     */
    public void push(State s)
    {
        states.push(s);
    }

    /**
     * <p> pops the state on top. </p>
     */
    public void pop()
    {
        states.pop();
    }

    /**
     * <p> sets the states. </p>
     * @param set instance of a state.
     */
    public void set(State set)
    {
        states.push(set);
        states.pop();
    }

    /**
     * <p> takes the states update function </p>
     * @param delta delta time.
     */
    public void update(float delta){ states.peek().update(delta);}

    /**
     * <p> takes the states render function </p>
     * @param batch instance of a spritebatch.
     */
    public void render(SpriteBatch batch){states.peek().render(batch);}

    @Override
    public void create() {

    }
}
