package com.colonidefeater.game.entity.state;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.PlayerWeaponCpt;
import com.colonidefeater.game.input.GameInput;

public class JumpingFireState implements IEntityState {

	@Override
	public String name() {
		return "StandingFire";
	}

	private IEntityState handleInput(Entity e) {

		if (!GameInput.isHolded(GameInput.FIRE)) {
			return IEntityState.jumpingState;
		}
		return this;
	}

	@Override
	public IEntityState update(Entity e) {

		IEntityState newState = handleInput(e);
		PlayerWeaponCpt weapon = e.getComponent(PlayerWeaponCpt.class);
		weapon.fire(e);
		PhysicsCpt physic = e.getComponent(PhysicsCpt.class);
		final Vector2 vel = physic.body.getLinearVelocity();
		// TODO: better way to detect when I'im done jumping
		if (vel.y > -0.01 && vel.y < 0.01) {
			newState = IEntityState.standfireState;
		}
		return newState;
	}

}
