package com.colonidefeater.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.colonidefeater.game.component.StateMachineCpt;
import com.colonidefeater.game.fsm.StateMachine;

@Wire
public class StateMachineSystem extends EntityProcessingSystem {
	private ComponentMapper<StateMachineCpt> stateMachineCptMapper;

	@SuppressWarnings("unchecked")
	public StateMachineSystem() {
		super(Aspect.getAspectForOne(StateMachineCpt.class));
	}

	@Override
	protected void process(Entity e) {
		final StateMachineCpt stateCpt = stateMachineCptMapper.get(e);
		for (StateMachine<Entity> sm : stateCpt.stateMachines.values()) {
			sm.update();
		}
	}

}
