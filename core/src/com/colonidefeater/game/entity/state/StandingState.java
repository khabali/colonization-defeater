package com.colonidefeater.game.entity.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.colonidefeater.game.MyGdxGame;
import com.colonidefeater.game.component.StateCpt;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.system.CameraSystem;
import com.colonidefeater.game.utils.Constants;

public class StandingState implements IEntityState {

	@Override
	public String name() {
		return "Standing";
	}

	private IEntityState handleInput(Body body, StateCpt state) {
		final Vector2 position = body.getPosition();
		final Vector2 vel = body.getLinearVelocity();
		if (GameInput.isHolded(GameInput.RIGHT) || GameInput.isHolded(GameInput.LEFT)) {
			return IEntityState.walkingState;
		}
		if (GameInput.isPressed(GameInput.UP)) {
			JumpingState.doJump(body);
			return IEntityState.jumpingState;
		}
		if (GameInput.isHolded(GameInput.ENTER)) {
			return IEntityState.standfireState;
		}
 		return this;
	}

	@Override
	public IEntityState update(Body body, StateCpt state) {
		return handleInput(body, state);	
	}

}
