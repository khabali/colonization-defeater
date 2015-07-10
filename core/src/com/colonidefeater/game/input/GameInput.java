package com.colonidefeater.game.input;

import com.badlogic.gdx.Input.Keys;

/**
 * Game Input manager <br/>
 *
 */
public class GameInput {

	private static final String tag = "GameInput";

	private static final int NUM_KEYS = 6;

	// keys for desktop
	private static boolean keyState[] = new boolean[NUM_KEYS];
	private static boolean prevKeyState[] = new boolean[NUM_KEYS];

	// virtual keys for android
	private static boolean v_keyState[] = new boolean[NUM_KEYS];
	private static boolean v_prevKeyState[] = new boolean[NUM_KEYS];

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int JUMP = 2;
	public static final int FIRE = 3;
	public static final int ESCAPE = 4;
	public static final int UP = 5;

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
		case Keys.LEFT:
			keyState[LEFT] = b;
			break;
		case Keys.RIGHT:
			keyState[RIGHT] = b;
			break;
		case Keys.Z:
			keyState[JUMP] = b;
			break;
		case Keys.A:
			keyState[FIRE] = b;
			break;
		case Keys.ESCAPE:
			keyState[ESCAPE] = b;
			break;
		case Keys.UP:
			keyState[UP] = b;
		}
	}

	public static void setVKeyState(int keyCode, boolean b) {
		switch (keyCode) {
		case LEFT:
			v_keyState[LEFT] = b;
			break;
		case RIGHT:
			v_keyState[RIGHT] = b;
			break;
		case JUMP:
			v_keyState[JUMP] = b;
		case FIRE:
			v_keyState[FIRE] = b;
			break;
		}
	}

	public static boolean isPressed(int keyCode) {
		return (keyState[keyCode] && !prevKeyState[keyCode])
				|| (v_keyState[keyCode] && !v_prevKeyState[keyCode]);
	}

	public static final boolean isHolded(int keyCode) {
		return keyState[keyCode] || v_keyState[keyCode];
	}

}
