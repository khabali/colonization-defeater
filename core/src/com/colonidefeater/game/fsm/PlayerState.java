package com.colonidefeater.game.fsm;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.PlayerWeaponCpt;
import com.colonidefeater.game.component.StateMachineCpt;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.utils.Constants;

public enum PlayerState implements State<Entity> {

	STAND() {

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
			PhysicsCpt physic = entity.getComponent(PhysicsCpt.class);
			if (GameInput.isHolded(GameInput.RIGHT)
					|| GameInput.isHolded(GameInput.LEFT)) {
				stateCpt.stateMachine.changeState(WALK);
			}
			if (GameInput.isPressed(GameInput.JUMP)) {
				doJump(physic.body);
				stateCpt.stateMachine.changeState(JUMP);
			}
			if (GameInput.isHolded(GameInput.FIRE)) {
				stateCpt.stateMachine.changeState(STAND_AND_FIRE);
			}
		}
	},

	WALK() {

		private StateMachineCpt stateCpt;

		@Override
		public void enter(Entity entity) {
			stateCpt = entity.getComponent(StateMachineCpt.class);
		}

		@Override
		public void update(Entity entity) {
			handleInput(entity);
			PhysicsCpt physic = entity.getComponent(PhysicsCpt.class);
			final Vector2 vel = physic.body.getLinearVelocity();
			if (Math.abs(vel.x) <= 0.1)
				stateCpt.stateMachine.changeState(STAND);
		}

		@Override
		public void exit(Entity entity) {
		}

		private void handleInput(Entity entity) {
			PhysicsCpt physic = entity.getComponent(PhysicsCpt.class);
			StateMachineCpt state = entity.getComponent(StateMachineCpt.class);

			final Vector2 position = physic.body.getPosition();
			final Vector2 vel = physic.body.getLinearVelocity();
			if (GameInput.isHolded(GameInput.RIGHT)
					&& vel.x < Constants.playerMaxVel) {
				physic.body.applyLinearImpulse(0.1f, 0, position.x, position.y,
						true);
				state.isLeftSided = false;
			}
			if (GameInput.isHolded(GameInput.LEFT)
					&& vel.x > -Constants.playerMaxVel) {
				physic.body.applyLinearImpulse(-0.1f, 0, position.x,
						position.y, true);
				state.isLeftSided = true;
			}
			if (GameInput.isPressed(GameInput.JUMP)) {
				doJump(physic.body);
				stateCpt.stateMachine.changeState(JUMP);
			}
			if (GameInput.isPressed(GameInput.FIRE)) {
				stateCpt.stateMachine.changeState(WALK_AND_FIRE);
			}
		}
	},

	JUMP() {

		private StateMachineCpt stateCpt;

		@Override
		public void enter(Entity entity) {
			stateCpt = entity.getComponent(StateMachineCpt.class);
		}

		@Override
		public void update(Entity entity) {
			handleInput(entity);
			PhysicsCpt physic = entity.getComponent(PhysicsCpt.class);
			final Vector2 vel = physic.body.getLinearVelocity();
			// TODO: better way to detect when I'im done jumping
			if (vel.y > -0.01 && vel.y < 0.01)
				stateCpt.stateMachine.changeState(STAND);
		}

		@Override
		public void exit(Entity entity) {
		}

		private void handleInput(Entity entity) {
			if (GameInput.isHolded(GameInput.FIRE)) {
				stateCpt.stateMachine.changeState(JUMP_AND_FIRE);
			}
		}
	},

	STAND_AND_FIRE() {

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
			PhysicsCpt physic = entity.getComponent(PhysicsCpt.class);
			PlayerWeaponCpt weapon = entity.getComponent(PlayerWeaponCpt.class);
			if (GameInput.isHolded(GameInput.FIRE)) {
				// fire bullet
				weapon.wpStore.getActifWeapon().fire(entity);
				// handle other input
				if (GameInput.isHolded(GameInput.LEFT)
						|| GameInput.isHolded(GameInput.RIGHT)) {
					stateCpt.stateMachine.changeState(WALK_AND_FIRE);
				}
				if (GameInput.isPressed(GameInput.JUMP)) {
					PlayerState.JUMP.doJump(physic.body);
					stateCpt.stateMachine.changeState(JUMP_AND_FIRE);
				}
			} else {
				stateCpt.stateMachine.changeState(STAND);
			}
		}
	},

	WALK_AND_FIRE() {

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

		private void handleInput(Entity e) {
			PhysicsCpt physic = e.getComponent(PhysicsCpt.class);
			StateMachineCpt state = e.getComponent(StateMachineCpt.class);
			PlayerWeaponCpt weapon = e.getComponent(PlayerWeaponCpt.class);

			if (GameInput.isHolded(GameInput.FIRE)) {
				final Vector2 position = physic.body.getPosition();
				final Vector2 vel = physic.body.getLinearVelocity();
				// fire bullet
				weapon.wpStore.getActifWeapon().fire(e);
				// handle other input
				if (GameInput.isHolded(GameInput.RIGHT)
						&& vel.x < Constants.playerMaxVel) {
					physic.body.applyLinearImpulse(0.1f, 0, position.x,
							position.y, true);
					state.isLeftSided = false;
				}
				if (GameInput.isHolded(GameInput.LEFT)
						&& vel.x > -Constants.playerMaxVel) {
					physic.body.applyLinearImpulse(-0.1f, 0, position.x,
							position.y, true);
					state.isLeftSided = true;
				}
				if (GameInput.isPressed(GameInput.JUMP)) {
					doJump(physic.body);
					stateCpt.stateMachine.changeState(JUMP_AND_FIRE);
				}
				if (!GameInput.isHolded(GameInput.RIGHT)
						&& !GameInput.isHolded(GameInput.LEFT)) {
					stateCpt.stateMachine.changeState(STAND_AND_FIRE);
				}
			} else {
				stateCpt.stateMachine.changeState(WALK);
			}
		}
	},

	JUMP_AND_FIRE() {

		private StateMachineCpt stateCpt;

		@Override
		public void enter(Entity entity) {
			stateCpt = entity.getComponent(StateMachineCpt.class);
		}

		@Override
		public void update(Entity entity) {
			handleInput(entity);
			PlayerWeaponCpt weapon = entity.getComponent(PlayerWeaponCpt.class);
			weapon.wpStore.getActifWeapon().fire(entity);
			PhysicsCpt physic = entity.getComponent(PhysicsCpt.class);
			final Vector2 vel = physic.body.getLinearVelocity();
			// TODO: better way to detect when I'im done jumping
			if (vel.y > -0.01 && vel.y < 0.01) {
				stateCpt.stateMachine.changeState(STAND_AND_FIRE);
			}
		}

		@Override
		public void exit(Entity entity) {
		}

		private void handleInput(Entity e) {
			if (!GameInput.isHolded(GameInput.FIRE)) {
				stateCpt.stateMachine.changeState(JUMP);
			}
		}
	};

	// helper method
	protected void doJump(Body body) {
		float gravity = body.getWorld().getGravity().y;
		float jumpHeight = 1.5f;
		float mass = body.getMass();
		Vector2 velocity = new Vector2(0f, (float) Math.sqrt(-2 * gravity
				* jumpHeight)
				* mass);
		body.applyLinearImpulse(velocity, body.getWorldCenter(), true);
	}

}
