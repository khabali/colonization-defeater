package com.colonidefeater.game.input;

public class GameInput {

	private static final String tag = "GameInput";

	private static GameInput _instance;

	public static GameInput getInstance() {
		if (_instance == null) {
			_instance = new GameInput();
		}
		return _instance;
	}

	private final InputButton enterButton;
	private final InputButton escapeButton;
	private final InputButton aButton;
	private final InputButton zButton;
	private final TouchButton touchButton;

	private GameInput() {
		enterButton = new InputButton();
		escapeButton = new InputButton();
		aButton = new InputButton();
		zButton = new InputButton();
		touchButton = new TouchButton();
	}

	public void update(float deltaTime) {

		if (escapeButton.isPressed() && escapeButton.getPressedDuration(System.currentTimeMillis()) <= deltaTime * 2.0f) {
			escapeButton.release();
		}

		if (enterButton.isPressed() && enterButton.getPressedDuration(System.currentTimeMillis()) <= deltaTime * 2.0f) {
			enterButton.release();
		}

		if (aButton.isPressed() && aButton.getPressedDuration(System.currentTimeMillis()) <= deltaTime * 2.0f) {
			aButton.release();
		}

		if (zButton.isPressed() && zButton.getPressedDuration(System.currentTimeMillis()) <= deltaTime * 2.0f) {
			zButton.release();
		}

		if (touchButton.isTouched() || touchButton.isPaned() || touchButton.isDraged()
				&& touchButton.getTouchDuration(System.currentTimeMillis()) >= deltaTime * 2.0f) {
			// Gdx.app.debug(tag, "Touch released ...");
			touchButton.release();
		}
	}

	public TouchButton getTouchButton() {
		return touchButton;
	}

	public InputButton getEnterButton() {
		return enterButton;
	}

	public InputButton getEscapeButton() {
		return escapeButton;
	}

	public InputButton getAButton() {
		return aButton;
	}

	public InputButton getZButton() {
		return zButton;
	}
}
