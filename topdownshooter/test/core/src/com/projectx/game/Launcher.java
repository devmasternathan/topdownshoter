package com.projectx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.projectx.game.Manager.GSM;

public class Launcher extends ApplicationAdapter
{
	public static GSM gsm;
	private SpriteBatch batch;
	FPSLogger logger;

	@Override
	public void create ()
	{
		gsm = new GSM();
		gsm.push(new TitleScreen(gsm));
		batch = new SpriteBatch();
		logger = new FPSLogger();
	}

	@Override
	public void render ()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1, 1, 1, 1);

		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
		logger.log();
	}
}
