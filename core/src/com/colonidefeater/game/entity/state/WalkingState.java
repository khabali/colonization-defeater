package com.colonidefeater.game.entity.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.colonidefeater.game.component.StateCpt;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.utils.Constants;

public class WalkingState implements IEntityState {

	@Override
	public String name() {
		return "Walking";
	}

	private IEntityState handleInput(Body body, StateCpt state) {
		final Vector2 position = body.getPosition();
		final Vector2 vel = body.getLinearVelocity();
		if (GameInput.isHolded(GameInput.RIGHT)
				&& vel.x < Constants.playerMaxVel) {
			body.applyLinearImpulse(0.1f, 0, position.x, position.y, true);
			state.isLeftSided = false;
		}
		if (GameInput.isHolded(GameInput.LEFT) 
				&& vel.x > -Constants.playerMaxVel) {
			body.applyLinearImpulse(-0.1f, 0, position.x, position.y, true);
			state.isLeftSided = true;
		}
		if (GameInput.isPressed(GameInput.UP)) {
			body.applyLinearImpulse(0, 4f, position.x, position.y, true);
			return IEntityState.jumpingState;
		}
		if (GameInput.isPressed(GameInput.ENTER)) {
			return IEntityState.walkfireState;
		}
		return this;
	}

	@Override
	public IEntityState update(Body body, StateCpt state) {
		IEntityState newState = handleInput(body, state);
		final Vector2 vel = body.getLinearVelocity();
		if (Math.abs(vel.x) <= 0.1) newState = IEntityState.standingState;
		return newState;
	}

}
