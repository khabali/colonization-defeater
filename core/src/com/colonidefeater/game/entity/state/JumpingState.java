package com.colonidefeater.game.entity.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.colonidefeater.game.component.StateCpt;
import com.colonidefeater.game.system.CameraSystem;

public class JumpingState implements IEntityState {

	@Override
	public String name() {
		return "Standing";
	}

	public IEntityState handleInput(Body body, StateCpt state) {
		return this;
	}
	
	@Override
	public IEntityState update(Body body, StateCpt state) {
		IEntityState newState = handleInput(body, state);
		final Vector2 vel = body.getLinearVelocity();
		// TODO: better way to detect when I'im done jumping
		if (vel.y == 0) newState = IEntityState.standingState;
		return newState;
	}

}
