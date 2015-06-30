package com.colonidefeater.game.entity.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.colonidefeater.game.MyGdxGame;
import com.colonidefeater.game.component.StateCpt;
import com.colonidefeater.game.entity.EntityFactory;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.utils.Constants;

public class WalkingFireState implements IEntityState {

	@Override
	public String name() {
		return "WalkingFire";
	}
	
	private IEntityState handleInput(Body body, StateCpt state) {
		if (GameInput.isHolded(GameInput.ENTER)) {
			final Vector2 position = body.getPosition();
			final Vector2 vel = body.getLinearVelocity();
			// fire bullet
			EntityFactory.createBullet(MyGdxGame.world, position, state.isLeftSided);
			// handle other input
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
				JumpingState.doJump(body);
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
	public IEntityState update(Body body, StateCpt state) {
		return handleInput(body, state);
	}

}
