package com.colonidefeater.game.entity.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.colonidefeater.game.MyGdxGame;
import com.colonidefeater.game.component.StateCpt;
import com.colonidefeater.game.entity.EntityFactory;
import com.colonidefeater.game.input.GameInput;

public class JumpingFireState implements IEntityState {

	@Override
	public String name() {
		return "StandingFire";
	}
	
	private IEntityState handleInput(Body body, StateCpt state) {
		Vector2 position = body.getPosition();
		EntityFactory.createBullet(MyGdxGame.world, position, state.isLeftSided); // fire bullet
		if (!GameInput.isHolded(GameInput.ENTER)) {
			return IEntityState.jumpingState;
		}
		return this;
	}

	@Override
	public IEntityState update(Body body, StateCpt state) {
		IEntityState newState = handleInput(body, state);
		final Vector2 vel = body.getLinearVelocity();
		// TODO: better way to detect when I'im done jumping
		if (vel.y > -0.01 && vel.y < 0.01) {
			newState = IEntityState.standfireState;
		}
		return newState;
	}

}
