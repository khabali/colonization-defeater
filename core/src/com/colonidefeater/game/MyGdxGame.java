package com.colonidefeater.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.colonidefeater.game.debug.GameFPSLogger;
import com.colonidefeater.game.debug.GameLogger;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.input.InputHandler;
import com.colonidefeater.game.state.GameStateManager;
import com.colonidefeater.game.utils.Constants;

public class MyGdxGame extends ApplicationAdapter {

	private final String tag = getClass().getName();

	private GameStateManager gsm;
	private InputHandler inputHandler;

	private Viewport viewPort;

	@Override
	public void create() {

		// LOG LEVEL
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		GameLogger.debug(tag, "Initialisation des logs : LEVEL DEBUG");

		viewPort = new StretchViewport(Constants.V_WIDTH * Constants.SCALE, Constants.V_HEIGHT * Constants.SCALE);
		viewPort.apply();

		//
		inputHandler = new InputHandler();
		final InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(inputHandler);
		multiplexer.addProcessor(new GestureDetector(inputHandler));
		Gdx.input.setInputProcessor(multiplexer);

		// Initialisation du game state manager
		gsm = new GameStateManager(viewPort);

		GameLogger.debug(tag, "Game created");
	}

	@Override
	public void render() {

		// clear screen
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gsm.update();
		gsm.draw();

		GameInput.update(Gdx.graphics.getDeltaTime());

		// FPSLogger
		GameFPSLogger.log();
	}

	@Override
	public void resize(int width, int height) {
		gsm.resize(width, height);
		GameLogger.debug(tag, "resize calles - screen H : " + height + " W : " + width);
	}

	@Override
	public void pause() {
		super.pause();
		gsm.pause();
		GameLogger.debug(tag, "Game paused");
	}

	@Override
	public void resume() {
		super.resume();
		gsm.resume();
		GameLogger.debug(tag, "Game resumed");
	}

	@Override
	public void dispose() {
		super.dispose();
		gsm.dispose();
		GameLogger.debug(tag, "Game diposed");
	}

}
