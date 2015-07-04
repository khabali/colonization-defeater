package com.colonidefeater.game.entity.state;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.input.GameInput;

public class JumpingState implements IEntityState {

	@Override
	public String name() {
		return "Standing";
	}

	public IEntityState handleInput(Entity e) {
		if (GameInput.isHolded(GameInput.FIRE)) {
			return IEntityState.jumpfirestate;
		}
		return this;
	}

	@Override
	public IEntityState update(Entity e) {
		
		IEntityState newState = handleInput(e);
		
		PhysicsCpt physic = e.getComponent(PhysicsCpt.class);
		final Vector2 vel = physic.body.getLinearVelocity();
		// TODO: better way to detect when I'im done jumping
		if (vel.y > -0.01 && vel.y < 0.01)
			newState = IEntityState.standingState;
		return newState;
	}

	public static void doJump(Body body) {
		float gravity = body.getWorld().getGravity().y;
		float jumpHeight = 1.5f;
		float mass = body.getMass();
		Vector2 velocity = new Vector2(0f, (float) Math.sqrt(-2 * gravity
				* jumpHeight)
				* mass);
		body.applyLinearImpulse(velocity, body.getWorldCenter(), true);
	}

}
