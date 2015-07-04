package com.colonidefeater.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.colonidefeater.game.component.PlayerControlled;
import com.colonidefeater.game.component.StateCpt;

@Wire
public class PlayerControlSystem extends EntityProcessingSystem {

	private ComponentMapper<StateCpt> stateCptMapper;

	@SuppressWarnings("unchecked")
	public PlayerControlSystem() {
		super(Aspect.getAspectForOne(PlayerControlled.class));
	}

	@Override
	protected void process(Entity e) {
		final StateCpt stateCpt = stateCptMapper.get(e);
		stateCpt.currentState = stateCpt.currentState.update(e);
	}

}
