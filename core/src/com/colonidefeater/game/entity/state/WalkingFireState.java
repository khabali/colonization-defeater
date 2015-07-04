package com.colonidefeater.game.entity.state;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.PlayerWeaponCpt;
import com.colonidefeater.game.component.StateCpt;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.utils.Constants;

public class WalkingFireState implements IEntityState {

	@Override
	public String name() {
		return "WalkingFire";
	}
	
	private IEntityState handleInput(Entity e) {
		PhysicsCpt physic = e.getComponent(PhysicsCpt.class);
		StateCpt state = e.getComponent(StateCpt.class);
		PlayerWeaponCpt weapon = e.getComponent(PlayerWeaponCpt.class);
	
		if (GameInput.isHolded(GameInput.FIRE)) {
			final Vector2 position = physic.body.getPosition();
			final Vector2 vel = physic.body.getLinearVelocity();
			// fire bullet
			weapon.fire(e);
			// handle other input
			if (GameInput.isHolded(GameInput.RIGHT)
					&& vel.x < Constants.playerMaxVel) {
				physic.body.applyLinearImpulse(0.1f, 0, position.x, position.y, true);
				state.isLeftSided = false;
			}
			if (GameInput.isHolded(GameInput.LEFT) 
					&& vel.x > -Constants.playerMaxVel) {
				physic.body.applyLinearImpulse(-0.1f, 0, position.x, position.y, true);
				state.isLeftSided = true;
			}
			if (GameInput.isPressed(GameInput.JUMP)) {
				JumpingState.doJump(physic.body);
				return IEntityState.jumpfirestate;
			}
			if (!GameInput.isHolded(GameInput.RIGHT) && !GameInput.isHolded(GameInput.LEFT)) {
				return IEntityState.standfireState;
			}
		}else {
			return IEntityState.walkingState;
		}
		return this;
	}

	@Override
	public IEntityState update(Entity e) {
		return handleInput(e);
	}

}
