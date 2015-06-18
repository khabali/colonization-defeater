package com.colonidefeater.game.entity.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.colonidefeater.game.system.CameraSystem;

public class JumpingState implements IEntityState {

	@Override
	public String name() {
		return "DEFAULT";
	}

	@Override
	public IEntityState handleInput(Body body) {
		final Vector2 vel = body.getLinearVelocity();
		// TODO: better way to detect when I'im done jumping
		if (vel.y == 0) return IEntityState.standingState;
		return this;
	}
	
	@Override
	public void update(Body body) {		
	}

}
