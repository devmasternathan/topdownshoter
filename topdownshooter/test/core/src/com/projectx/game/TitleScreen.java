package com.projectx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.projectx.game.Manager.GSM;
import com.projectx.game.Manager.State;

/**
 * <p> Title Screen is the starting title of the game. </p>
 */
public class TitleScreen extends State
{

    // A SpriteBatch object allows 2D images to be drawn on the screen
    // but it must be drawn between batch.begin() and batch.end().
    SpriteBatch batch;
    // used to switch screens
    GSM _gsm;
    // A Sprite object can be manipulated to move on the screen when needed.
    Sprite exitButton;
    Sprite playButton;
    Sprite background;
    Sprite creditButton;
    Sprite cartman;
    Sprite kyle;
    Sprite title;

    //The OrthographicCamera object is used in 2D environments, can be used
    //to move and rotate the camera around, zoom in and out, change the
    //viewpoint or simply using the camera to move around a game if necessary
    OrthographicCamera cam;

    //Vector object that holds the mouse position
    Vector3 mousePos;

    //boolean variables to indicate whether play or exit button was pressed
    boolean exitButtonTouched;
    boolean playButtonTouched;
    boolean creditButtonTouched;

    /**
     * <p> The constructor where all the game objects are initialized. </p>
     * @param gsm instance of a game state manager.
     */
    public TitleScreen(GSM gsm)
    {
        super(gsm);

        _gsm = gsm;

        //constructs a batch object
        batch = new SpriteBatch();

        //constructs a new OrthographicCamera object. Since this is a 2D
        //game, we only need to supply the game's screen size
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //constructs Sprite objects
        exitButton = new Sprite(new Texture("titlescreen/exit_btn.png"));
        playButton = new Sprite(new Texture("titlescreen/play_btn.png"));
        creditButton = new Sprite(new Texture("titlescreen/credit_btn.png"));
        title = new Sprite(new Texture("titlescreen/game_title1.png"));
        background = new Sprite(new Texture("titlescreen/bg.png"));
        cartman = new Sprite(new Texture("titlescreen/cartman.png"));
        kyle = new Sprite(new Texture("titlescreen/kyle.png"));

        //centers the OrthographicCamera object to the middle of the screen
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        //sets the background Sprite to fit the game screen
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //position and scale the playButton Sprite on the game screen
        playButton.setPosition((Gdx.graphics.getWidth()/2) - playButton.getWidth() / 2 , Gdx.graphics.getHeight() / 3.5f);
        playButton.setScale(.8f, .8f);

        //position and scale the exitButton Sprite on the game screen
        exitButton.setPosition((Gdx.graphics.getWidth()/2) - playButton.getWidth() / 2 , Gdx.graphics.getHeight() / 6f);
        exitButton.setScale(.8f, .8f);

        //position and scale the title Sprite on the game screen
        title.setPosition((Gdx.graphics.getWidth()/2) - title.getWidth()/2 , Gdx.graphics.getHeight()/2);
        title.setScale(.8f, .8f);

        //position the character Sprites on the game screen
        kyle.setPosition(0, 0);
        cartman.setPosition((Gdx.graphics.getWidth() - cartman.getWidth()), 0);

        //construct a Vector object for the mouse coordinates
        mousePos = new Vector3();

        Gdx.input.setCursorCatched(false);
    }

    /**
     * <p> update method updates every frame. Used for computations.</p>
     * @param delta counts every frame and returns seconds.
     */
    @Override
    public void update(float delta)
    {
        // allows the mouse to have to same coordinates
        // as the screen. If you don't put this the mouse
        // may not work correctly because the mouse may
        //use its own preset coordinates
        mousePos.x = Gdx.input.getX();
        mousePos.y = Gdx.input.getY();

        //OrthographicCamera object calls unproject method
        //which essentially fetches the screen's x and y
        //coordinates and translates it into the game world
        cam.unproject(mousePos);

        // checks if the mouse the exit button is pressed
        if(exitButtonTouched = exitButton.getBoundingRectangle().contains(mousePos.x, mousePos.y)
                && Gdx.input.isTouched()){

            dispose();
            //System.out.println("mouse touched exitbutton : "  + exitButtonTouched);
        }


        // checks if the the play button is pressed
        if(playButtonTouched = playButton.getBoundingRectangle().contains(mousePos.x, mousePos.y)
                && Gdx.input.isTouched()){
            gsm.push(new InfoScreen(gsm, 1, 3, 30));
            //System.out.println("mouse touched playbutton : "  + playButtonTouched);
        }
    }

    /**
     * <p> renders all the images of the game. </p>
     * @param batch instance of a spritebatch.
     */
    @Override
    public void render(SpriteBatch batch)
	{
        //glClearColor method sets the color used when clearing
        //the color buffer. glClear method is used to clear the
        //screen. We do this because we don't want data from the
        //previous frame to be preserved.
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //batch.setProjectionMatrix(cam.projection);
        batch.begin();

        // draws the Sprite images onto the screen at every frame
        background.draw(batch);
        playButton.draw(batch);
        exitButton.draw(batch);
        title.draw(batch);
        kyle.draw(batch);
        cartman.draw(batch);

        batch.end();
	}

    public void dispose()
    {
        Gdx.app.exit();

    }

    }

