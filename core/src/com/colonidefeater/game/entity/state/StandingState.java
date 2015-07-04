package com.colonidefeater.game.entity.state;

import com.artemis.Entity;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.input.GameInput;

public class StandingState implements IEntityState {

	@Override
	public String name() {
		return "Standing";
	}

	private IEntityState handleInput(Entity e) {
		PhysicsCpt physic = e.getComponent(PhysicsCpt.class);
		if (GameInput.isHolded(GameInput.RIGHT)
				|| GameInput.isHolded(GameInput.LEFT)) {
			return IEntityState.walkingState;
		}
		if (GameInput.isPressed(GameInput.JUMP)) {
			JumpingState.doJump(physic.body);
			return IEntityState.jumpingState;
		}
		if (GameInput.isHolded(GameInput.FIRE)) {
			return IEntityState.standfireState;
		}
		return this;
	}

	@Override
	public IEntityState update(Entity e) {
		return handleInput(e);
	}

}
