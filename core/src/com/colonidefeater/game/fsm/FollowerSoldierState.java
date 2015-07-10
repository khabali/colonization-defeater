package com.colonidefeater.game.fsm;

import com.artemis.Entity;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.WeaponCpt;
import com.colonidefeater.game.component.StateMachineCpt;
import com.colonidefeater.game.utils.Constants;

// follower soldier state machine
public enum FollowerSoldierState implements State<Entity> {
	
	WALK() {

		private Entity target;
		private float maxVel = 0.5f;
		private float fireDistance = 1f;

		@Override
		public void enter(Entity entity) {
			target = entity.getWorld().getManager(TagManager.class).getEntity("PLAYER");
		}

		@Override
		public void update(Entity entity) {
			Body body = entity.getComponent(PhysicsCpt.class).body;
			StateMachineCpt state = entity.getComponent(StateMachineCpt.class);
			float playerx = target.getComponent(PhysicsCpt.class).body.getPosition().x;
			Vector2 position = body.getPosition();
			final Vector2 vel = body.getLinearVelocity();
			if (Math.abs(playerx-position.x) <= fireDistance) {
				state.stateMachine.changeState(FollowerSoldierState.STAND_AND_FIRE);
			}
			if (playerx > position.x && vel.x < maxVel) {
				body.applyLinearImpulse(0.1f, 0, position.x, position.y, true);
				state.isLeftSided = false;
			}
			if (playerx < position.x && vel.x > -maxVel) {
				body.applyLinearImpulse(-0.1f, 0, position.x, position.y, true);
				state.isLeftSided = true;
			}
		}

		@Override
		public void exit(Entity entity) {
		}
		
	},
	
	STAND_AND_FIRE() {
		
		private Entity target;
		private float followDistance = 1.5f;

		@Override
		public void enter(Entity entity) {
			target = entity.getWorld().getManager(TagManager.class).getEntity("PLAYER");
		}

		@Override
		public void update(Entity entity) {
			Body body = entity.getComponent(PhysicsCpt.class).body;
			StateMachineCpt state = entity.getComponent(StateMachineCpt.class);
			WeaponCpt weapon = entity.getComponent(WeaponCpt.class);
			float playerx = target.getComponent(PhysicsCpt.class).body.getPosition().x;
			Vector2 position = body.getPosition();
			if (Math.abs(playerx-position.x) > followDistance) {
				state.stateMachine.changeState(FollowerSoldierState.WALK);
			}
			if (playerx < position.x) {
				state.isLeftSided = true;
			}else {
				state.isLeftSided = false;
			}
			weapon.wpStore.getActifWeapon().fire(entity);
		}

		@Override
		public void exit(Entity entity) {
		}
		
	}

}
