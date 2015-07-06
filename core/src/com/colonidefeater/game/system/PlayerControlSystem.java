package com.colonidefeater.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.colonidefeater.game.component.PlayerControlled;
import com.colonidefeater.game.component.StateMachineCpt;

@Wire
public class PlayerControlSystem extends EntityProcessingSystem {
	private final String tag = getClass().getName();
	private ComponentMapper<StateMachineCpt> stateMachineCptMapper;

	@SuppressWarnings("unchecked")
	public PlayerControlSystem() {
		super(Aspect.getAspectForOne(PlayerControlled.class));
	}

	@Override
	protected void process(Entity e) {
		final StateMachineCpt stateCpt = stateMachineCptMapper.get(e);
		stateCpt.stateMachine.update();
	}

}
