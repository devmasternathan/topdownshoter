package com.projectx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.projectx.game.Manager.GSM;
import com.projectx.game.Manager.State;

/**
 *Credits: Giving credit to everyone in TEAM 1 who participated to make this game happen.
 */
public class CreditScreen extends State {
	
	SpriteBatch batch;
	BitmapFont font;
	
    protected CreditScreen(GSM gsm) {
        super(gsm);
        font = new BitmapFont();

		Gdx.input.setCursorCatched(false);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
    	
    	batch.begin();

		Gdx.gl.glClearColor(135/255f, 206/255f, 250/255f, 1);

    	font.draw(batch, "Project X Credits Page", 435, 768);
    	font.draw(batch, "Backbone", 100, 640);
    	font.draw(batch, "Nathaniel Blanchard", 100, 610);
    	font.draw(batch, "Raymond Ye", 100, 590);
    	font.draw(batch, "Chad Mercene", 100, 570);
    	font.draw(batch, "Stephanie Thomas", 100, 550);
    	font.draw(batch, "Hendy Valcin", 100, 530);
    	
    	font.draw(batch, "Quality Control", 350, 640);
    	font.draw(batch, "Stephen Lin", 350, 610);
    	font.draw(batch, "Deshi Wu", 350, 590);
    	font.draw(batch, "Yujie Huang", 350, 570);
    	font.draw(batch, "Samson Chen", 350, 550);
    	
    	font.draw(batch, "Graphics", 600, 640);
    	font.draw(batch, "Anthony Fang", 600, 610);
    	font.draw(batch, "Nika Tsankashvili", 600, 590);
    	font.draw(batch, "Diana Rudaya", 600, 570);
    	font.draw(batch, "Jack Shomer", 600, 550);
    	
    	font.draw(batch, "Specifications", 825, 640);
    	font.draw(batch, "Hanyao Lu", 825, 610);
    	font.draw(batch, "Kenneth Liu", 825, 590);
    	font.draw(batch, "Farahim Ibrahimli", 825, 570);
    	
    	font.draw(batch, "Pointy Headed Boss", 100, 710);
    	font.draw(batch, "Professor Gross", 100, 680);
    	
    	batch.end();
    }
}
