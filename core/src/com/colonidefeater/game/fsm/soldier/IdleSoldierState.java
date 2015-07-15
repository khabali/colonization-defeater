package com.colonidefeater.game.fsm.soldier;

import com.artemis.Entity;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.StateMachineCpt;
import com.colonidefeater.game.fsm.State;
import com.colonidefeater.game.utils.Direction;

// Idle soldier state machine
public enum IdleSoldierState implements State<Entity> {
	
	STAND_IDLE_SIDE() {
		
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
				entity.getComponent(StateMachineCpt.class).dir = Direction.right;
			}else {
				entity.getComponent(StateMachineCpt.class).dir = Direction.left;
			}
		}

		@Override
		public void exit(Entity entity) {
		}
		
	};

}
