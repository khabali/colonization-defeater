package com.colonidefeater.game.entity.state;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.StateCpt;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.utils.Constants;

public class WalkingState implements IEntityState {

	@Override
	public String name() {
		return "Walking";
	}

	private IEntityState handleInput(Entity e) {
		PhysicsCpt physic = e.getComponent(PhysicsCpt.class);
		StateCpt state = e.getComponent(StateCpt.class);

		final Vector2 position = physic.body.getPosition();
		final Vector2 vel = physic.body.getLinearVelocity();
		if (GameInput.isHolded(GameInput.RIGHT)
				&& vel.x < Constants.playerMaxVel) {
			physic.body.applyLinearImpulse(0.1f, 0, position.x, position.y,
					true);
			state.isLeftSided = false;
		}
		if (GameInput.isHolded(GameInput.LEFT)
				&& vel.x > -Constants.playerMaxVel) {
			physic.body.applyLinearImpulse(-0.1f, 0, position.x, position.y,
					true);
			state.isLeftSided = true;
		}
		if (GameInput.isPressed(GameInput.JUMP)) {
			JumpingState.doJump(physic.body);
			return IEntityState.jumpingState;
		}
		if (GameInput.isPressed(GameInput.FIRE)) {
			return IEntityState.walkfireState;
		}
		return this;
	}

	@Override
	public IEntityState update(Entity e) {
		PhysicsCpt physic = e.getComponent(PhysicsCpt.class);
		IEntityState newState = handleInput(e);
		final Vector2 vel = physic.body.getLinearVelocity();
		if (Math.abs(vel.x) <= 0.1)
			newState = IEntityState.standingState;
		return newState;
	}

}
