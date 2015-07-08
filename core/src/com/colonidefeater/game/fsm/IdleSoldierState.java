package com.colonidefeater.game.fsm;

import com.artemis.Entity;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.StateMachineCpt;

// Idle soldier state machine
public enum IdleSoldierState implements State<Entity> {
	
	STAND() {
		
		private Entity target;

		@Override
		public void enter(Entity entity) {
			target = entity.getWorld().getManager(TagManager.class).getEntity("PLAYER");
		}

		@Override
		public void update(Entity entity) {
			float playerx = target.getComponent(PhysicsCpt.class).body.getPosition().x;
			float x = entity.getComponent(PhysicsCpt.class).body.getPosition().x;
			if (playerx < x) {
				entity.getComponent(StateMachineCpt.class).isLeftSided = true;
			}else {
				entity.getComponent(StateMachineCpt.class).isLeftSided = false;
			}
		}

		@Override
		public void exit(Entity entity) {
		}
		
	};

}
