package com.colonidefeater.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.PlayerControlled;
import com.colonidefeater.game.component.StateCpt;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.utils.Constants;

@Wire
public class PlayerControlSystem extends EntityProcessingSystem {
	private ComponentMapper<PhysicsCpt> physicsCptMapper;
	private ComponentMapper<StateCpt> stateCptMapper;

	@SuppressWarnings("unchecked")
	public PlayerControlSystem() {
		super(Aspect.getAspectForOne(PlayerControlled.class));
	}

	@Override
	protected void process(Entity e) {
		final PhysicsCpt physicsCpt = physicsCptMapper.get(e);
		final StateCpt stateCpt = stateCptMapper.get(e);
		
		stateCpt.currentState = stateCpt.currentState.handleInput(physicsCpt.body);
		stateCpt.currentState.update(physicsCpt.body);
		
		/*
		if (GameInput.isPressed(GameInput.UP) && vel.y >= 0) {
			physicsCpt.body.applyLinearImpulse(0, 4f, position.x, position.y, true);
		}
		*/
	}

}
