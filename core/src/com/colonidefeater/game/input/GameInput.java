package com.colonidefeater.game.input;

import com.badlogic.gdx.Input.Keys;

/**
 * Game Input manager <br/>
 *
 */
public class GameInput {

	private static final String tag = "GameInput";

	private static final int NUM_KEYS = 5;

	private static boolean keyState[] = new boolean[NUM_KEYS];
	private static boolean prevKeyState[] = new boolean[NUM_KEYS];

	public static final int ENTER = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int ESCAPE = 3;
	public static final int UP = 4;

	/**
	 * update the game input state
	 * 
	 * @param deltaTime
	 */
	public static void update(float deltaTime) {
		for (int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = keyState[i];
		}
	}

	public static void setKeyState(int keyCode, boolean b) {
		switch (keyCode) {
		case Keys.ENTER:
			keyState[ENTER] = b;
			break;
		case Keys.ESCAPE:
			keyState[ESCAPE] = b;
			break;
		case Keys.LEFT:
			keyState[LEFT] = b;
			break;
		case Keys.RIGHT:
			keyState[RIGHT] = b;
			break;
		case Keys.UP:
			keyState[UP] = b;
		}
	}

	public static boolean isPressed(int keyCode) {
		return keyState[keyCode] && !prevKeyState[keyCode];
	}

	public static final boolean isHolded(int keyCode) {
		return keyState[keyCode];
	}

}
