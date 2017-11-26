package com.projectx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.projectx.game.Manager.GSM;
import com.projectx.game.Manager.State;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.projectx.game.Manager.Timer;



/**
 * <h1> InfoScreen Screen</h1>
 * <p> displays the information needed to play the game. </p>
 */
public class InfoScreen extends State
{

    // A SpriteBatch object allows 2D images to be drawn on the screen
    // but it must be drawn between batch.begin() and batch.end().
    SpriteBatch batch;
    // used to switch screens
    GSM _gsm;
    // A Sprite object can be manipulated to move on the screen when needed.
    Sprite background;
    Sprite lifeCount1;
    Sprite lifeCount2;
    Sprite lifeCount3;
    Sprite backButton;
    Sprite startButton;

    //The OrthographicCamera object is used in 2D environments, can be used
    //to move and rotate the camera around, zoom in and out, change the
    //viewpoint or simply using the camera to move around a game if necessary
    OrthographicCamera cam;

    //Vector object that holds the mouse position
    Vector3 mousePos;

    //boolean variables to indicate whether back button was pressed
    boolean backButtonTouched;
    boolean startButtonTouched;

    //To write stuff
    BitmapFont font;
    BitmapFont rulefont;
    FileHandle file;
    String text;
    int level, hearts;
    float time$;
    Timer time;

    /**
     * <p> The constructor where all the game objects are initialized. </p>
     * @param gsm instance of a game state manager.
     */
    public InfoScreen(GSM gsm, int inputLevel, int inputHearts, float inputTime)
    {
        super(gsm);

        _gsm = gsm;

        font = new BitmapFont();
        rulefont = new BitmapFont();

        file = Gdx.files.internal("rules.txt");
        text = file.readString();

        rulefont.getData().setScale(1, 1);
        font.getData().setScale(5);

        time$ = inputTime;
        time = new Timer(time$);

        level = inputLevel;

        hearts = inputHearts;

        //constructs a batch object
        batch = new SpriteBatch();

        //constructs a new OrthographicCamera object. Since this is a 2D
        //game, we only need to supply the game's screen size
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //constructs Sprite objects
        background = new Sprite(new Texture("infoscreen/new_infoscreen.png"));
        lifeCount1 = new Sprite(new Texture("infoscreen/life.png"));
        lifeCount2 = new Sprite(new Texture("infoscreen/life.png"));
        lifeCount3 = new Sprite(new Texture("infoscreen/life.png"));
        backButton = new Sprite(new Texture("infoscreen/back_button.png"));
        startButton = new Sprite(new Texture("infoscreen/start_button.png"));


        //centers the OrthographicCamera object to the middle of the screen
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //position and scale the backButton Sprite on the info screen
        backButton.setPosition(30, 630);

        //position and scale the backButton Sprite on the info screen
        startButton.setPosition((Gdx.graphics.getWidth()/2) - startButton.getWidth() / 2 , 345);

        //sets the background Sprite to fit the game screen
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.input.setCursorCatched(false);

        lifeCount1.setSize(50, 50);
        lifeCount2.setSize(50, 50);
        lifeCount3.setSize(50, 50);

        //construct a Vector object for the mouse coordinates
        mousePos = new Vector3();


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

        // checks if the the back button is pressed
        if(backButtonTouched = backButton.getBoundingRectangle().contains(mousePos.x, mousePos.y)
                && Gdx.input.isTouched()) {
            gsm.push(new TitleScreen(gsm));
        }

        // checks if the the start button is pressed
        if(startButtonTouched = startButton.getBoundingRectangle().contains(mousePos.x, mousePos.y)
                && Gdx.input.isTouched()) {
            gsm.push(new GameScreen(gsm, level, hearts, time$));
        }

        /** Told the polling out **/
        //System.out.println(mousePos);
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
        backButton.draw(batch);
        startButton.draw(batch);
        font.setColor(Color.BLACK);
        rulefont.setColor(Color.BLACK);
        font.draw(batch, String.valueOf(level) , 590, 525);
        font.draw(batch, "0:"+String.valueOf((int)time$) , 490, 340);
        font.draw(batch, "Eliminate 5 Targets", 200, Gdx.graphics.getHeight() - 10);
        rulefont.draw(batch, text, 250, Gdx.graphics.getHeight()/4);

        //Creat the hearts
        switch(hearts) {
            case 1:
                lifeCount1.setPosition(490, 225);
                lifeCount1.draw(batch);
                break;
            case 2:
                lifeCount1.setPosition(490, 225);
                lifeCount2.setPosition(550, 225);
                lifeCount1.draw(batch);
                lifeCount2.draw(batch);
                break;
            case 3:
                lifeCount1.setPosition(490, 225);
                lifeCount2.setPosition(550, 225);
                lifeCount3.setPosition(610, 225);
                lifeCount1.draw(batch);
                lifeCount2.draw(batch);
                lifeCount3.draw(batch);
            case 0:
// you lose.
                break;
        }

        batch.end();
    }
}
