package com.projectx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.projectx.game.Manager.GSM;
import com.projectx.game.Manager.State;

/**
 * Loser Screen
 */
public class LoserScreen extends State {

    BitmapFont font;

    // A SpriteBatch object allows 2D images to be drawn on the screen
    // but it must be drawn between batch.begin() and batch.end().
    SpriteBatch batch;

    // used to switch screens
    GSM _gsm;

    // A Sprite object can be manipulated to move on the screen when needed.
    //Sprite background;
    Sprite playagainButton;
    Sprite exitButton;
    Sprite creditButton;
    Sprite youlose;

    //The OrthographicCamera object is used in 2D environments, can be used
    //to move and rotate the camera around, zoom in and out, change the
    //viewpoint or simply using the camera to move around a game if necessary
    OrthographicCamera cam;

    //Vector object that holds the mouse position
    Vector3 mousePos;

    //boolean variables to indicate whether back button was pressed
    boolean playagainButtonTouched;
    boolean exitButtonTouched;
    boolean creditButtonTouched;

    public LoserScreen(GSM gsm) {

        super(gsm);
        _gsm = gsm;
        font = new BitmapFont();

        //constructs a batch object
        batch = new SpriteBatch();

        //constructs a new OrthographicCamera object. Since this is a 2D
        //game, we only need to supply the game's screen size
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //constructs Sprite objects
        //background = new Sprite(new Texture("infoscreen/new_infoscreen.png"));
        playagainButton = new Sprite(new Texture("titlescreen/play_btn.png"));
        exitButton = new Sprite(new Texture("titlescreen/exit_btn.png"));
        creditButton = new Sprite(new Texture("titlescreen/credit_btn.png"));
        youlose = new Sprite(new Texture("titlescreen/you_lose.png"));


        //centers the OrthographicCamera object to the middle of the screen
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //position and scale the playagainButton Sprite on the loser screen
        playagainButton.setPosition((Gdx.graphics.getWidth()/2) - playagainButton.getWidth() / 2 , 375);

        //position and scale the exitButton Sprite on the loser screen
        exitButton.setPosition((Gdx.graphics.getWidth()/2) - exitButton.getWidth() / 2 , 250);

        //position and scale the creditButton Sprite on the loser screen
        creditButton.setPosition((Gdx.graphics.getWidth()/2) - creditButton.getWidth() / 2 , 100);

        //position you_lose
        youlose.setPosition((Gdx.graphics.getWidth()/2) - youlose.getWidth() / 2 , 500);

        Gdx.input.setCursorCatched(false);

        //construct a Vector object for the mouse coordinates
        mousePos = new Vector3();
    }

    @Override
    public void update(float delta) {
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

        // checks if the the playagain button is pressed
        if(playagainButtonTouched = playagainButton.getBoundingRectangle().contains(mousePos.x, mousePos.y)
                && Gdx.input.isTouched()) {
            gsm.push(new TitleScreen(gsm));
        }

        // checks if the mouse the exit button is pressed
        if(exitButtonTouched = exitButton.getBoundingRectangle().contains(mousePos.x, mousePos.y)
                && Gdx.input.isTouched())
            Gdx.app.exit();

        // checks if the the credit button is pressed
        if(creditButtonTouched = creditButton.getBoundingRectangle().contains(mousePos.x, mousePos.y)
                && Gdx.input.isTouched()) {
            gsm.push(new CreditScreen(gsm));
        }
    }

    @Override
    public void render(SpriteBatch batch) {

        batch.begin();

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);

        //background.draw(batch);
        youlose.draw(batch);
        playagainButton.draw(batch);
        exitButton.draw(batch);
        creditButton.draw(batch);

        batch.end();
    }
}
