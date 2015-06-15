package com.colonidefeater.game.state;

import com.colonidefeater.game.debug.GameLogger;

public abstract class GameStateAdapter extends GameState {

	protected String tag = getClass().getName();

	public GameStateAdapter(GameStateManager gsm) {
		super(gsm);
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
