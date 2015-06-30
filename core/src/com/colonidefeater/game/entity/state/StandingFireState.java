package com.colonidefeater.game.entity.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.colonidefeater.game.MyGdxGame;
import com.colonidefeater.game.component.StateCpt;
import com.colonidefeater.game.entity.EntityFactory;
import com.colonidefeater.game.input.GameInput;

public class StandingFireState implements IEntityState {

	@Override
	public String name() {
		return "StandingFire";
	}
	
	private IEntityState handleInput(Body body, StateCpt state) {
		final Vector2 position = body.getPosition();
		if (GameInput.isHolded(GameInput.ENTER)) {
			// fire bullet
			EntityFactory.createBullet(MyGdxGame.world, position, state.isLeftSided);
			// handle other input
			if (GameInput.isHolded(GameInput.LEFT) || GameInput.isHolded(GameInput.RIGHT)) {
				return IEntityState.walkfireState;
			}
			if (GameInput.isPressed(GameInput.UP)) {
				JumpingState.doJump(body);
				return IEntityState.jumpfirestate;
			}
		}else {
			return IEntityState.standingState;
		}
		return this;
	}

	@Override
	public IEntityState update(Body body, StateCpt state) {
		return handleInput(body, state);
	}

}
