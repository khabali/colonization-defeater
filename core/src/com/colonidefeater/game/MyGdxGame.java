package com.colonidefeater.game;

import static com.colonidefeater.game.utils.Constants.SCALE;
import static com.colonidefeater.game.utils.Constants.V_HEIGHT;
import static com.colonidefeater.game.utils.Constants.V_WIDTH;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.colonidefeater.game.debug.GameFPSLogger;
import com.colonidefeater.game.debug.GameLogger;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.input.InputHandler;
import com.colonidefeater.game.input.VirtualGamePad;
import com.colonidefeater.game.resources.AssetsManager;
import com.colonidefeater.game.state.GameStateManager;

public class MyGdxGame extends ApplicationAdapter {

	private final String tag = getClass().getName();

	private GameStateManager gsm;
	private InputHandler inputHandler;
	private VirtualGamePad virtualeGamePad;
	private Viewport viewPort;

	@Override
	public void create() {

		// LOG LEVEL
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		GameLogger.debug(tag, "Initialisation des logs : LEVEL DEBUG");

		AssetsManager.load();

		viewPort = new StretchViewport(V_WIDTH * SCALE, V_HEIGHT * SCALE);
		viewPort.apply();

		virtualeGamePad = new VirtualGamePad();

		//
		inputHandler = new InputHandler();
		final InputMultiplexer multiplexer = new InputMultiplexer();
		if (ApplicationType.Android.equals(Gdx.app.getType())) {
			multiplexer.addProcessor(virtualeGamePad.getInputProcessor());
		} else {
			multiplexer.addProcessor(inputHandler);
		}
		Gdx.input.setInputProcessor(multiplexer);

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

		if (Gdx.app.getType().equals(ApplicationType.Android)) {
			virtualeGamePad.aupdateAndDraw();
		} else {
			GameInput.update(Gdx.graphics.getDeltaTime());
		}

		// FPSLogger
		GameFPSLogger.log();
	}

	@Override
	public void resize(int width, int height) {
		gsm.resize(width, height);
		virtualeGamePad.resize(width, height);

		GameLogger.debug(tag, "resize calles - screen H : " + height + " W : "
				+ width);
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
		virtualeGamePad.dispose();
		GameLogger.debug(tag, "Game diposed");
	}

}
