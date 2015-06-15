package com.colonidefeater.game.state;

import java.util.Stack;

import com.badlogic.gdx.utils.viewport.Viewport;

public class GameStateManager {

	// La liste des états gerés dans le jeux
	public static final int STATE_COUNT = 2;
	public static final int STATE_MENU = 0;
	public static final int STATE_LEVEL1 = 1;

	private int currentGameState;
	private final Stack<GameState> states;

	private final Viewport viewPort;

	public GameStateManager(Viewport viewPort) {

		this.viewPort = viewPort;

		states = new Stack<GameState>();
		currentGameState = STATE_MENU;
		loadState(currentGameState);
	}

	public void setState(int stateCode) {
		if (stateCode != currentGameState) {
			states.pop();
			currentGameState = stateCode;
			loadState(currentGameState);
		}
	}

	private void loadState(int stateCode) {

		if (STATE_MENU == stateCode) {
			states.push(new MainMenuGameState(this));
		}

		if (STATE_LEVEL1 == stateCode) {
			states.push(new Level1GameState(this));
		}
	}

	/**
	 * Cette methode permet de mettre à jour l'état courant du jeux
	 */
	public void update() {
		if (!states.isEmpty()) {
			final GameState state = states.peek();
			if (state != null) {
				state.update();
			}
		}
	}

	/**
	 * Cette methode permet de dessiner l'etat courant du jeux
	 */
	public void draw() {
		if (!states.isEmpty()) {
			final GameState state = states.peek();
			if (state != null) {
				state.draw();
			}
		}
	}

	public void resize(int width, int height) {
		if (!states.isEmpty()) {
			final GameState state = states.peek();
			if (state != null) {
				state.resize(width, height);
			}
		}
	}

	/**
	 * cette methode permet de mettre l'etat courant en pause
	 */
	public void pause() {
		if (!states.isEmpty()) {
			final GameState state = states.peek();
			if (state != null) {
				state.pause();
			}
		}
	}

	/**
	 * cette methode permet de relancer l'etat courant du jeux
	 */
	public void resume() {
		if (!states.isEmpty()) {
			final GameState state = states.peek();
			if (state != null) {
				state.resume();
			}
		}
	}

	/**
	 * Cette methode permet de liberer les ressources des etats courants du jeux
	 */
	public void dispose() {
		for (int i = 0; i < states.size(); i++) {
			final GameState state = states.pop();
			if (state != null) {
				state.dispose();
			}
		}
	}

	public Viewport getViewPort() {
		return viewPort;
	}

}
