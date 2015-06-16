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

		// FIXME -- Debug to be deleted
		if (GameInput.isHolded(GameInput.RIGHT)) {
			playerPosition.x += 1.5f / PPM;
			physicsCpt.body.applyForceToCenter(0, 2f, true);
			physicsCpt.body.setTransform(playerPosition, 0);
		}
		if (GameInput.isPressed(GameInput.LEFT)) {
			playerPosition.x -= 30.5f / PPM;
			physicsCpt.body.applyForceToCenter(0, 50f, true);
			physicsCpt.body.setTransform(playerPosition, 0);
		}

		gameCamera.setXPosition(playerPosition.x * PPM + gameCamera.viewportWidth / 8.0f);
		gameCamera.update();

		// box2d Camera
		if (Constants.isBox2dDebugEnabled) {
			box2dCamera.setXPosition(playerPosition.x + box2dCamera.viewportWidth / 8.0f);
			box2dCamera.update();
		}
	}

}
