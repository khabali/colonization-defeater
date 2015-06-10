package com.colonidefeater.game.system;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.colonidefeater.game.debug.GameLogger;

public class CameraSystem extends VoidEntitySystem {

	public final OrthographicCamera camera;

	public CameraSystem() {
		
		GameLogger.debug("", "game size w : "+Gdx.graphics.getWidth() + " hieght : "+ Gdx.graphics.getHeight());
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		this.camera.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0);
	}

	@Override
	protected void processSystem() {
	}

}
