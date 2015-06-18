package com.colonidefeater.game.entity.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.system.CameraSystem;
import com.colonidefeater.game.utils.Constants;

public class StandingState implements IEntityState {

	@Override
	public String name() {
		return "DEFAULT";
	}

	@Override
	public IEntityState handleInput(Body body) {
		final Vector2 position = body.getPosition();
		final Vector2 vel = body.getLinearVelocity();
		if (GameInput.isHolded(GameInput.RIGHT)
				&& vel.x < Constants.playerMaxVel) {
			body.applyLinearImpulse(0.1f, 0, position.x, position.y, true);
		}
		if (GameInput.isHolded(GameInput.LEFT) 
				&& vel.x > -Constants.playerMaxVel) {
			body.applyLinearImpulse(-0.1f, 0, position.x, position.y, true);
		}
		if (GameInput.isPressed(GameInput.UP)) {
			body.applyLinearImpulse(0, 4f, position.x, position.y, true);
			return IEntityState.jumpingState;
		}
		return this;
	}

	@Override
	public void update(Body body) {
		// TODO Auto-generated method stub
		
	}

}
