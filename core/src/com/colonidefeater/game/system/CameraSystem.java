package com.colonidefeater.game.system;

import static com.colonidefeater.game.utils.Constants.PPM;
import static com.colonidefeater.game.utils.Constants.SCALE;
import static com.colonidefeater.game.utils.Constants.V_HEIGHT;
import static com.colonidefeater.game.utils.Constants.V_WIDTH;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.PlayerControlled;
import com.colonidefeater.game.handlers.GameCamera;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.utils.Constants;

@Wire
public class CameraSystem extends EntityProcessingSystem {

	private final String tag = getClass().getName();

	/**
	 * The game camera
	 */
	public GameCamera gameCamera;
	public GameCamera box2dCamera;

	private ComponentMapper<PhysicsCpt> physicsCptMapper;

	@SuppressWarnings("unchecked")
	public CameraSystem(int xMax, int yMax) {
		super(Aspect.getAspectForOne(PlayerControlled.class));
		gameCamera = new GameCamera();
		gameCamera.setToOrtho(false, V_WIDTH * SCALE, V_HEIGHT * SCALE);
		gameCamera.setBounds(0, xMax, 0, yMax);

		//
		box2dCamera = new GameCamera();
		box2dCamera.setToOrtho(false, V_WIDTH * SCALE / PPM, V_HEIGHT * SCALE / PPM);
		box2dCamera.setBounds(0, xMax / PPM, 0, yMax / PPM);
	}

	@Override
	protected void process(Entity e) {
		final PhysicsCpt physicsCpt = physicsCptMapper.get(e);
		final Vector2 playerPosition = physicsCpt.body.getPosition();
		final Vector2 playerVelocity = physicsCpt.body.getLinearVelocity();
		
		if (isOutOfViewPort(playerPosition.x)) {
			float pushbackvel = playerVelocity.x > 0 ? 0.5f : -0.5f;
			physicsCpt.body.setLinearVelocity(-(playerVelocity.x+pushbackvel), playerVelocity.y);
			return;
		}

		gameCamera.setXPosition(playerPosition.x * PPM + gameCamera.viewportWidth / 8.0f);
		gameCamera.update();

		// box2d Camera
		if (Constants.isBox2dDebugEnabled) {
			box2dCamera.setXPosition(playerPosition.x + box2dCamera.viewportWidth / 8.0f);
			box2dCamera.update();
		}
	}
	
	public boolean isOutOfViewPort(float x) {
		float camx0 = (gameCamera.position.x - (gameCamera.viewportWidth/2))/PPM;
		float camx1 = (gameCamera.position.x + (gameCamera.viewportWidth/2))/PPM;
		return (x <= camx0 || x >= camx1);
	}

}
