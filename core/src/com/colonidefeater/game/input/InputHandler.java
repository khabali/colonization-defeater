package com.colonidefeater.game.input;

import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {

	@Override
	public boolean keyDown(int keycode) {
		GameInput.setKeyState(keycode, true);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		GameInput.setKeyState(keycode, false);
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
