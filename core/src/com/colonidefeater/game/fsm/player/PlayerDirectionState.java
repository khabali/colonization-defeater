package com.colonidefeater.game.fsm.player;

import com.artemis.Entity;
import com.colonidefeater.game.component.StateMachineCpt;
import com.colonidefeater.game.fsm.State;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.utils.Direction;

public enum PlayerDirectionState implements State<Entity> {
	
	SIDE() {

		private StateMachineCpt stateCpt;

		@Override
		public void enter(Entity entity) {
			stateCpt = entity.getComponent(StateMachineCpt.class);
		}

		@Override
		public void update(Entity entity) {
			handleInput(entity);
		}

		@Override
		public void exit(Entity entity) {
		}
		
		private void handleInput(Entity entity) {
			if (GameInput.isHolded(GameInput.UP)) {
				stateCpt.get(PlayerDirectionState.class).changeState(UP);
				stateCpt.looktoward = Direction.top;
			}
		}
		
	},
	
	UP() {

		private StateMachineCpt stateCpt;

		@Override
		public void enter(Entity entity) {
			stateCpt = entity.getComponent(StateMachineCpt.class);
		}

		@Override
		public void update(Entity entity) {
			handleInput(entity);	
		}

		@Override
		public void exit(Entity entity) {
		}
		
		private void handleInput(Entity entity) {
			if (!GameInput.isHolded(GameInput.UP)) {
				stateCpt.get(PlayerDirectionState.class).changeState(SIDE);
				stateCpt.looktoward = Direction.none;
			}
		}
		
	},
	
	DOWN() {

		@Override
		public void enter(Entity entity) {
			// TODO Auto-generated method stub
			
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

}
