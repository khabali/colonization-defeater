package com.colonidefeater.game.fsm.player;

import com.artemis.Entity;
import com.colonidefeater.game.component.StateMachineCpt;
import com.colonidefeater.game.component.WeaponCpt;
import com.colonidefeater.game.fsm.State;
import com.colonidefeater.game.input.GameInput;

public enum PlayerActionState implements State<Entity> {
	
	IDLE() {
		
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
			if (GameInput.isHolded(GameInput.FIRE)) {
				stateCpt.get(PlayerActionState.class).changeState(ATTACK);
			}
		}
		
	},
	
	ATTACK() {

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
			WeaponCpt weapon = entity.getComponent(WeaponCpt.class);
			if (GameInput.isHolded(GameInput.FIRE)) {
				// fire bullet
				weapon.wpStore.getActifWeapon().fire(entity);
			} else {
				stateCpt.get(PlayerActionState.class).changeState(IDLE);
			}
		}
	},

}
