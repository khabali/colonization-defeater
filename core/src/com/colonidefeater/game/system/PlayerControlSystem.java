package com.colonidefeater.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.PlayerControlled;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.utils.Constants;

@Wire
public class PlayerControlSystem extends EntityProcessingSystem {
	private ComponentMapper<PhysicsCpt> physicsCptMapper;
	private CameraSystem camera;
	
	private final float playerMaxVel = 1f;

	@SuppressWarnings("unchecked")
	public PlayerControlSystem() {
		super(Aspect.getAspectForAll(PlayerControlled.class));
	}

	@Override
	protected void process(Entity e) {
		final PhysicsCpt physicsCpt = physicsCptMapper.get(e);
		final Vector2 position = physicsCpt.body.getPosition();
		final float velx = physicsCpt.body.getLinearVelocity().x;
		
		if (GameInput.isHolded(GameInput.RIGHT) 
				&& velx < playerMaxVel
				&& !camera.isOutOfViewPort(position.x + velx + 0.1f)) {
			physicsCpt.body.applyLinearImpulse(0.1f, 0, position.x, position.y, true);
		}
		if (GameInput.isHolded(GameInput.LEFT) 
				&& velx > -playerMaxVel
				&& !camera.isOutOfViewPort(position.x + velx - 0.1f)) {
			physicsCpt.body.applyLinearImpulse(-0.1f, 0, position.x, position.y, true);
		}
	}

}
