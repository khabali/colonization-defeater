package com.colonidefeater.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.colonidefeater.game.debug.GameLogger;
import com.colonidefeater.game.utils.Constants;

public abstract class GameStateAdapter extends GameState {

	protected String tag = getClass().getName();

	protected com.artemis.World ecsHub;
	protected World physicsHub;

	public GameStateAdapter(GameStateManager gsm) {
		super(gsm);
		ecsHub = new com.artemis.World();
		physicsHub = new World(new Vector2(0, -9f), true);
		init();
	}

	@Override
	public void update() {
		handleInput();
	}

	@Override
	public void draw() {
		ecsHub.process();
		physicsHub.step(Gdx.graphics.getDeltaTime(),
				Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
	}

	@Override
	public void resize(int width, int height) {
		gsm.getViewPort().update(width, height);
	}

	@Override
	public void pause() {
		GameLogger.debug(tag, "state paused");
	}

	@Override
	public void resume() {
		GameLogger.debug(tag, "state resumed");
	}

	@Override
	public void dispose() {
		GameLogger.debug(tag, "state disposed");
	}

}
