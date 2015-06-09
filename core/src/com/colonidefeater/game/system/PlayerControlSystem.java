package com.colonidefeater.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.colonidefeater.game.component.PlayerControlled;

public class PlayerControlSystem extends EntityProcessingSystem {

	public PlayerControlSystem() {
		super(Aspect.getAspectForAll(PlayerControlled.class));
	}

	@Override
	protected void process(Entity e) {
		//Player input processing here
	}

}
