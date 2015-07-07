package com.colonidefeater.game.fsm;

import com.artemis.Entity;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;

public enum IdleSoldierState implements State<Entity> {
	
	STAND() {

		@Override
		public void enter(Entity entity) {
			
		}

		@Override
		public void update(Entity entity) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void exit(Entity entity) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private Entity target;
	
	public void setTarget(Entity e) {
		
	}
	

}
