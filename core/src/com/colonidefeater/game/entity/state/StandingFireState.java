package com.colonidefeater.game.entity.state;

import com.artemis.Entity;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.PlayerWeaponCpt;
import com.colonidefeater.game.input.GameInput;

public class StandingFireState implements IEntityState {

	@Override
	public String name() {
		return "StandingFire";
	}

	private IEntityState handleInput(Entity e) {
		PhysicsCpt physic = e.getComponent(PhysicsCpt.class);
		PlayerWeaponCpt weapon = e.getComponent(PlayerWeaponCpt.class);
		if (GameInput.isHolded(GameInput.FIRE)) {
			// fire bullet
			weapon.fire(e);
			// handle other input
			if (GameInput.isHolded(GameInput.LEFT)
					|| GameInput.isHolded(GameInput.RIGHT)) {
				return IEntityState.walkfireState;
			}
			if (GameInput.isPressed(GameInput.JUMP)) {
				JumpingState.doJump(physic.body);
				return IEntityState.jumpfirestate;
			}
		} else {
			return IEntityState.standingState;
		}
		return this;
	}

	@Override
	public IEntityState update(Entity e) {

		return handleInput(e);
	}

}
