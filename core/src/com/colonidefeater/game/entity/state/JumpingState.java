package com.colonidefeater.game.entity.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.colonidefeater.game.MyGdxGame;
import com.colonidefeater.game.component.StateCpt;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.system.CameraSystem;

public class JumpingState implements IEntityState {

	@Override
	public String name() {
		return "Standing";
	}

	public IEntityState handleInput(Body body, StateCpt state) {
		if (GameInput.isHolded(GameInput.ENTER)) {
			return IEntityState.jumpfirestate;
		}
		return this;
	}
	
	@Override
	public IEntityState update(Body body, StateCpt state) {
		IEntityState newState = handleInput(body, state);
		final Vector2 vel = body.getLinearVelocity();
		// TODO: better way to detect when I'im done jumping
		if (vel.y > -0.01 && vel.y < 0.01) newState = IEntityState.standingState;
		return newState;
	}
	
	public static void doJump(Body body) {
		float gravity = MyGdxGame.world.physicsHub.getGravity().y;
		float jumpHeight = 1.5f;
		float mass = body.getMass();
		Vector2 velocity = new Vector2(0f, (float)Math.sqrt(-2 * gravity * jumpHeight) * mass);
		body.applyLinearImpulse(velocity, body.getWorldCenter(), true);
	}

}
