package com.projectx.game;

import java.util.Random;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.projectx.game.Manager.GSM;
import com.projectx.game.Manager.State;
import com.projectx.game.Manager.Timer;

/**
 * <p> Game Screen is the starting point of the game. </p>
 */
public class GameScreen extends State {

    // A SpriteBatch object allows 2D images to be drawn on the screen
    // but it must be drawn between batch.begin() and batch.end().
    SpriteBatch batch;

    //Texture objects to be used by Sprite objects
    Texture img;
    Texture pillarTex;

    // you only clicked once
    boolean mouseClicked = false;

    // speed of the professor
    float speed = 1.65f;
    float speedVert = 1;

    //Declare a vector for the Professors to be stored
    Vector<Sprite> spawnProf;

    //Declare a vector to associte each with a pillar id
    Vector<Position> posProf ;

    //Random number generator for the Professor movements
    Random rand = new Random();

    // ball creation
    Vector<Sprite> ballSpawn;

    //pillars sprites for the Professors to hide behind
    Sprite pillar;
    Sprite pillarA;
    Sprite pillarB;
    Sprite pillarC;
    Sprite pillarD;

    Sound sound;

    //background image for the level
    Sprite background;

    //fonts used to write text on the screen
    BitmapFont font;

    //timer that keeps track of the time remaining on current level
    Timer time;

    //Cross-hair Section
    /**Sprite professor;  wrong name, prof sprite is at spawnprof method **/
    //Sprites for the cross-hair and ball
    Sprite ball;
    Sprite crossHair;

    //The OrthographicCamera object is used in 2D environments, can be used
    //to move and rotate the camera around, zoom in and out, change the
    //viewpoint or simply using the camera to move around a game if necessary
    OrthographicCamera cam;

    //Vector object that holds the mouse position
    Vector3 mousePos;

    //variables that store the x and y position of the area clicked by the cross-hair
    float targetX;
    float targetY;

    //variables that store the x and y position of the ball's position
    float ballX ;
    float ballY;

    ///float profX;  //using wrong
    //float profY;  //using wrong
    //May be used as a flag to signal that we have collision
    boolean reachedTarget = false;

    //Unused variable - to be decided
    int yAxis;

    //calculates the distance between ball and the target
    float distance;

    //holds the velocities needed to get to the desired target
    float vx;
    float vy;

    //the slope needed to get to the target
    float differenceInX;
    float differenceInY;

    //total nmber of professors hit
    int professorsHit;


    //Variables to hold time, hearts and level
    int level, hearts;
    float time$;

    // -- win
    boolean win = false;

    //This is the array to be used to show how many professors got hit
    int hitGuage [];

    /**
     * <p> The constructor where all the game objects are initialized. </p>
     * @param gsm instance of a game state manager.
     */
    protected GameScreen(GSM gsm, int inputLevel, int inputHearts, float inputTime)
    {
        super(gsm);

        sound = Gdx.audio.newSound(Gdx.files.internal("music/bgmusic2.wav"));

        //Input the level and the amount of hearts/lives
        level = inputLevel;
        hearts = inputHearts;



        //Constructs a BitMapFont object
        font = new BitmapFont();

        //construct texture object pillarTex
        pillarTex = new Texture("titlescreen/pillar.png");

        //Constructs a batch object
        batch = new SpriteBatch();

        //constructs a texture object img
        img = new Texture("gamescreen/professor_image.png");

        //constructs texture object pillars
        pillar = new Sprite(pillarTex);
        pillarA = new Sprite(pillarTex);
        pillarB = new Sprite(pillarTex);
        pillarC = new Sprite(pillarTex);
        pillarD = new Sprite(pillarTex);

        //initialize vectors with their default size
        spawnProf = new Vector<Sprite>(10);
        posProf = new Vector<Position>(10);

        //calls the createAProf method to spawn professors onto the screen
        createAProf();

        //constructs a time object with the default value of 30
        time$ = inputTime;
        time = new Timer(time$);

        //sets the size of the pillar Sprites
        pillar.setSize(50, 50);
        pillarA.setSize(50, 50);
        pillarB.setSize(50, 50);
        pillarC.setSize(50, 50);
        pillarD.setSize(50, 50);

        //sets the default positions of each pillar
        pillar.setPosition(50, 650);
        pillarA.setPosition(250, 450);
        pillarB.setPosition(450, 650);
        pillarC.setPosition(650, 450);
        pillarD.setPosition(850, 650);

        //constructs a Sprite background object for the game
        background = new Sprite(new Texture("gamescreen/southpark_bg.png"));

        //sets the Sprite to fit the game's screen size
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Crosshair Section
        //constructs a new OrthographicCamera object. Since this is a 2D
        //game, we only need to supply the game's screen size
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //centers the OrthographicCamera object to the middle of the screen
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        /**batch = new SpriteBatch();  //wrong, repeated
           professor = new Sprite(new Texture("titlescreen/play_btn.png"));   using it wrong **/
        //constructs a ball object
        ball = new Sprite(new Texture("gamescreen/orange_bb.png"));

        //sets the size of the ball object
        ball.setSize(35, 35);

        //Suggestion: how about we set the ball's initial position to be on the bottom left
        //sets the ball's initial position
        ballX = 100;
        ballY = 50;
        ball.setPosition(ballX, ballY);

        // only one ball will be  drawn onto the
        // screen at a time.
        ballSpawn = new Vector<Sprite>(1);
        // add ball sprite to vector
        ballSpawn.add(ball);

        /**professor.setSize(50, 50);   using it wrong **/
        //constructs a cross-hair object
        crossHair = new Sprite(new Texture("gamescreen/crosshair.png"));

        //sets the size of the cross-hair object
        crossHair.setSize(20,20);

        //constructs a mouse object
        mousePos = new Vector3();

        Gdx.input.setCursorCatched(true);


        /**profX = (Gdx.graphics.getWidth() / 2f);   using it wrong
        //profY = Gdx.graphics.getHeight() / 2f;    using it wrong
        //professor.setPosition(profX , profY); using it wrong **/

        sound.play(0.5f);

        //This is going to accurately display when a professor is hit
        hitGuage = new int [spawnProf.size()];
        for(int i = 0; i< hitGuage.length; i++)
        {
            hitGuage[i]= 0;
        }
    }

    /**
     * <p> update method updates every frame. Used for computations.</p>
     * @param delta counts every frame and returns seconds.
     */
    @Override
    public void update(float delta) {

        //logs the time
//Gdx.app.log("time", String.valueOf(delta));
        //CHANGE: Alot has been changed here for efficiency,
        //For Losing and WInning screens i push the creditScreen feel free to change ofcourse
        if(time.getTime() <= 0)
        {
            //If You win at level 1 or 2 Then you get sent back to the info screen to prepare for the next Level
            if(win && level < 3)
            {
               //Level get incremented, and the next level starts off ten seconds faster
                gsm.push(new InfoScreen(gsm, level + 1, hearts, time$ - 10));
            }

            //CHANGE: Losing is determined by the live u have, so if u run out of lives u lose
            else if(!win)
            {
                //If the level ends by time the you didnt hit all targets, meaning you failed and you lose a life
                //P.S. Round immediately ends when u hit all targets, see all the changes in render
                hearts--;

                //CHANGE: If there are no lives to take away the game ends,
                // else the game continues and you go to the info screen to prepare to redo the level.
                if (hearts <= 0)
                {
                    gsm.push(new LoserScreen(gsm));
                }
                else
                {
                    gsm.push(new InfoScreen(gsm, level, hearts, time$));
                }
            }

            //If you win push winner screen
            if(win && level == 3)
            {
                gsm.push(new WinnerScreen(gsm));
            }
        }
        time.update(delta);
    }

    /**
     * <p> renders all the images of the game. </p>
     * @param batch instance of a spritebatch.
     */
    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //beginning of batch loop - runs everything within this loop at every frame
        batch.begin();

        background.draw(batch);

        //draws the time remaining on screen
        font.draw(batch, "Time: ", 80, 760);
        font.draw(batch, String.valueOf((int)time.getTime()) , 120, 760);

        //draws the lives remaining on screen
        font.draw(batch, "Lives: "+ String.valueOf(hearts), 600, 760);

        //draws the level currently on screen
        font.draw(batch, "Level: "+ String.valueOf(level), 860, 760);

        //loop that spawns the professor at current index of the loop
        for (int i = 0; i < spawnProf.size(); i++)
        {

            spawnProf.get(i).draw(batch);
            spawnProf.get(i).setPosition(posProf.get(i).x, posProf.get(i).y);

            // if the ball touches a professor then the ball disappears
            if(!ballSpawn.isEmpty())
            {
                if (spawnProf.get(i).getBoundingRectangle().contains(ballSpawn.get(0).getX(), ballSpawn.get(0).getY()))
                {

                    ballSpawn.remove(0);
                    vx = 0;
                    vy = 0;

                    //CHANGE: If the Prof that is hit has a value in the array that has been changed already it doesnt go through this loop
                    if(!spawnProf.isEmpty() && spawnProf.get(i) != null && hitGuage[i] != 1)
                    {
                        Gdx.app.log("bi : ", String.valueOf(i));
                        //spawnProf.remove(spawnProf.elementAt(i));
                        spawnProf.get(i).setColor(Color.BLUE);
                        //spawnProf.setSize(spawnProf.size() - 1);
                        Gdx.app.log("ai : ", String.valueOf(i));

                        //CHANGE: Changes value in the Array to 1 so the Prof with this index number doesn't go th
                        hitGuage[i] = 1;


                        professorsHit++;


                    }

                    //CHANGE: It was move out here to keep the ball shooting
                    mouseClicked = false;
                    //if(!spawnProf.isEmpty())
                    //spawnProf.remove(i);

                }
            }

            // if the number is equal to the total size of professors then
            // you win.


            if(professorsHit == spawnProf.size())
            {
                // go to he next level

                win = true;

            }


            // if the ball is destroyed then
            // create another one at the default spot.
            if(ballSpawn.isEmpty() )
            {
                //sets the size of the ball object
                ball.setSize(35, 35);

                //Suggestion: how about we set the ball's initial position to be on the bottom left
                //sets the ball's initial position
                ballX = 100;
                ballY = 50;
                ball.setPosition(ballX, ballY);

                ballSpawn.add(ball);
            }

            //CHANGE: This was put out here to end the game immediately after you hit all targets
            if( professorsHit ==  spawnProf.size())
            {
                if(win && level < 3)
                {
//lives--;
                    gsm.push(new InfoScreen(gsm, level + 1, hearts, time$ - 10));
                }

                if(win && level == 3)
                {
                    gsm.push(new CreditScreen(gsm));
                }
            }

            // if touch pillar repick a new random number
            // so the professor knows what to hide behind.
            /**if(pillar.getBoundingRectangle().contains(posProf.get(i).x, posProf.get(i).y) ||
             pillarA.getBoundingRectangle().contains(posProf.get(i).x, posProf.get(i).y) ||
             pillarB.getBoundingRectangle().contains(posProf.get(i).x, posProf.get(i).y) ||
             pillarC.getBoundingRectangle().contains(posProf.get(i).x, posProf.get(i).y) ||
             pillarD.getBoundingRectangle().contains(posProf.get(i).x, posProf.get(i).y) )
             {
             int randNum = rand.nextInt(5);
             posProf.get(i).setRandom(randNum);
             } **/
        }

        //loop that chooses a professor to go to a pillar
        for (int i = 0; i < spawnProf.size(); i++)
        {
            if(pillar.getX() + 5 > spawnProf.get(i).getX() &&
                    pillar.getX() < spawnProf.get(i).getX())
            {
                int randNum = rand.nextInt(5);
                posProf.get(i).setRandom(randNum);
            }

            if(pillarA.getX() + 5 > spawnProf.get(i).getX() &&
                    pillarA.getX() < spawnProf.get(i).getX())
            {
                int randNum = rand.nextInt(5);
                posProf.get(i).setRandom(randNum);
            }

            if(pillarB.getX() + 5 > spawnProf.get(i).getX() &&
                    pillarB.getX() < spawnProf.get(i).getX())
            {
                int randNum = rand.nextInt(5);
                posProf.get(i).setRandom(randNum);
            }

            if(pillarC.getX() + 5 > spawnProf.get(i).getX() &&
                    pillarC.getX() < spawnProf.get(i).getX())
            {
                int randNum = rand.nextInt(5);
                posProf.get(i).setRandom(randNum);
            }

            if(pillarD.getX() + 5 > spawnProf.get(i).getX() &&
                    pillarD.getX() < spawnProf.get(i).getX())
            {
                int randNum = rand.nextInt(5);
                posProf.get(i).setRandom(randNum);
            }

            //switch statement that chooses a random pillar id for
            // the current indexed professor to move towards
            switch (posProf.get(i).getRandom()) {
                case 0:
                    // go to pillar
                    if (pillar.getX() < spawnProf.get(i).getX())
                        moveLeft(posProf, i);

                    if (pillar.getX() > spawnProf.get(i).getX())
                        moveRight(posProf, i);

                    if (pillar.getY() < spawnProf.get(i).getY())
                        moveDown(posProf, i);

                    if (pillar.getY() > spawnProf.get(i).getY()){
                        moveUp(posProf, i);
                    }
                    break;
                case 1:
                    // go to pillar A
                    if (pillarA.getX() < spawnProf.get(i).getX())
                        moveLeft(posProf, i);

                    if (pillarA.getX() > spawnProf.get(i).getX())
                        moveRight(posProf, i);

                    if (pillarA.getY() < spawnProf.get(i).getY())
                        moveDown(posProf, i);

                    if (pillarA.getY() > spawnProf.get(i).getY()){
                        moveUp(posProf, i);
                    }
                    break;

                case 2:
                    // go to pillar B
                    if (pillarB.getX() < spawnProf.get(i).getX())
                        moveLeft(posProf, i);

                    if (pillarB.getX() > spawnProf.get(i).getX())
                        moveRight(posProf, i);

                    if (pillarB.getY() < spawnProf.get(i).getY())
                        moveDown(posProf, i);

                    if (pillarB.getY() > spawnProf.get(i).getY()){
                        moveUp(posProf, i);
                    }

                    break;

                case 3:
                    // go to pillar C
                    if (pillarC.getX() < spawnProf.get(i).getX())
                        moveLeft(posProf, i);

                    if (pillarC.getX() > spawnProf.get(i).getX())
                        moveRight(posProf, i);

                    if (pillarC.getY() < spawnProf.get(i).getY())
                        moveDown(posProf, i);

                    if (pillarC.getY() > spawnProf.get(i).getY()){
                        moveUp(posProf, i);
                    }
                    break;

                case 4:
                    // go to pillar D
                    if (pillarD.getX() < spawnProf.get(i).getX())
                        moveLeft(posProf, i);

                    if (pillarD.getX() > spawnProf.get(i).getX())
                        moveRight(posProf, i);

                    if (pillarD.getY() < spawnProf.get(i).getY())
                        moveDown(posProf, i);

                    if (pillarD.getY() > spawnProf.get(i).getY()){
                        moveUp(posProf, i);
                    }
                    break;

                default:
                    System.out.println("picked wrong number");
                    break;
            }
        }

        // allows the mouse to have to same coordinates
        // as the screen. If you don't put this the mouse
        // may not work correctly.
        mousePos.x = Gdx.input.getX();
        mousePos.y = Gdx.input.getY();

        //OrthographicCamera object calls unproject method
        //which essentially fetches the screen's x and y
        //coordinates and translates it into the game world
        cam.unproject(mousePos);

        // sets the  position of the cross-hair.
        crossHair.setPosition(mousePos.x - (crossHair.getWidth() / 2), mousePos.y - (crossHair.getHeight() / 2));

        // when you press A the targetX and targetY values will hold the mouse position that you lasted clicked.
        if(Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.justTouched() && !mouseClicked)
        {

            mouseClicked = true;

            //store the X and Y of the cross-hair
            targetX = crossHair.getX();
            targetY = crossHair.getY();

            //finds the difference in x to get to target
            differenceInX = crossHair.getX() - ball.getX();

            //finds the difference in y to get to target
            differenceInY = crossHair.getY() - ball.getY();

            //the distance needed to get to the target
            distance = (float)Math.sqrt(differenceInX*differenceInX + differenceInY*differenceInY);

            //the velocities that the ball will travel
            vx = differenceInX * 15/distance;
            vy = differenceInY * 15/distance;

            //reachedTarget = true;
        }

        // depending on where that target is, navigate to the target.
        //Suggestion: How about we only use increments since we wont be shooting down. Just have the ball reset to initial position
        //Fix: This only moves the ball if mouse was pressed, and only if the mouse/rosshair's position was less than the ball's position
        //-- In addition, the movement is moving in an S movement
        /**   if( reachedTarget && ballX > targetX)
         {
         ballX-=1;
         ball.setPosition(ballX, ballY);
         } **/

        /**if( reachedTarget && ballX < targetX) **/


        //Don't do this ballX < crossHair.getX() && ballY < crossHair.getY()
        //Otherwise the ball will keep following cursor, store the X and Y
        if (ballX < targetX && ballY < targetY) {
            ballX += vx;
            ballY += vy;

            if(ballX == targetX && ballY == targetY) {

            }
            //ballX+=1;
            //ballY+=1;
            ballSpawn.get(0).setPosition(ballX, ballY);
            //ball.setPosition(ballX, ballY);
        }

        else if (ballX > targetX && ballY < targetY) {
            ballX += vx;
            ballY += vy;
            //ball.setPosition(ballX, ballY);
            if(!ballSpawn.isEmpty())
                ballSpawn.get(0).setPosition(ballX, ballY);

        }

        else
        {
            // removes the ball if miss
            if(mouseClicked)
            {
                ballSpawn.remove(0);
                vx = 0;
                vy = 0;
                mouseClicked = false;
            }
        }



        /** if( reachedTarget && ballY > targetY)
         {
         ballY-=1;
         ball.setPosition(ballX, ballY);
         }

        if( reachedTarget && ballY < targetY)
        {
        	ballY+=1;
        	ball.setPosition(ballX, ballY);
        } **/


        font.draw(batch, "Test", 100, 100);
        pillarA.draw(batch);
        pillarB.draw(batch);
        pillarC.draw(batch);
        pillarD.draw(batch);
        pillar.draw(batch);

        //draws ball on the screen at every frame
        //ball.draw(batch);
       /** professor.draw(batch);    using it wrong **/
        //draws the cross-hair on the screen at every frame
        crossHair.draw(batch);

        // draws the ball if something is in the
        // vector.
        if(!ballSpawn.isEmpty())
            ballSpawn.get(0).draw(batch);

        //end of the batch loop
        batch.end();
    }

    /**
     * <p> Create a professor and places them in a  Vector.
     * Instance of a sprite. </p>
     */
    public void createAProf()
    {
        // create professor.
        for (int i = 0; i < 5; i++)
        {
            //Fixed: Changed Sprite sprite to Sprite Prof
            Sprite Prof = new Sprite(img);
            Position pos = new Position();
            // place in a specific position
            Prof.setSize(80,80);
            pos.setX((float)(Math.random() * 984));
            //choosing a random starting below 
            pos.setY((float)(Math.random() * 300 + 700));
            posProf.add(pos);

            Prof.setPosition(posProf.get(i).x, (posProf.get(i).y));
            posProf.get(i).setRandom(rand.nextInt(4));
            spawnProf.add(Prof);
        }
    }

    /**
     * <p> moves the professor left. </p>
     * @param pos an instance of a vector that holds the position.
     * @param i get the specific index of a vector
     */
    public void moveLeft(Vector<Position> pos, int i)
    {
        pos.get(i).x-=speed;
    }

    /**
     * <p> moves the professor right. </p>
     * @param pos an instance of a vector that holds the position.
     * @param i get the specific index of a vector
     */
    public void moveRight(Vector<Position> pos, int i)
    {
        pos.get(i).x+=speed;
    }

    /**
     * <p> moves the professor up. </p>
     * @param pos an instance of a vector that holds the position.
     * @param i get the specific index of a vector
     */
    public void moveUp(Vector<Position> pos, int i)
    {
        /**  Need Fix: I found the reason there are jumping up and down
         *  They are using the Y up and down methods at every frame
         *  So they are jumping like that **/
        pos.get(i).y+=speedVert;

    }

    /**
     * <p> moves the professor down. </p>
     * @param pos an instance of a vector that holds the position.
     * @param i get the specific index of a vector
     */
    public void moveDown(Vector<Position> pos, int i)
    {
        pos.get(i).y-=speedVert;
    }

}
