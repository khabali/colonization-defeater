package com.colonidefeater.game.fsm.player;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.colonidefeater.game.component.AnimationCpt;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.StateMachineCpt;
import com.colonidefeater.game.fsm.State;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.utils.Constants;
import com.colonidefeater.game.utils.Direction;

public enum PlayerMovementState implements State<Entity> {
	
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
				stateCpt.get(PlayerMovementState.class).changeState(WALK);
			}
			if (GameInput.isPressed(GameInput.JUMP)) {
				doJump(physic.body);
				stateCpt.get(PlayerMovementState.class).changeState(JUMP);
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
				stateCpt.get(PlayerMovementState.class).changeState(STAND);
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
				state.dir = Direction.right;
			}
			if (GameInput.isHolded(GameInput.LEFT)
					&& vel.x > -Constants.playerMaxVel) {
				physic.body.applyLinearImpulse(-0.1f, 0, position.x,
						position.y, true);
				state.dir = Direction.left;
			}
			if (GameInput.isPressed(GameInput.JUMP)) {
				doJump(physic.body);
				stateCpt.get(PlayerMovementState.class).changeState(JUMP);
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
				stateCpt.get(PlayerMovementState.class).changeState(STAND);
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
				state.dir = Direction.right;
			}
			if (GameInput.isHolded(GameInput.LEFT)
					&& vel.x > -Constants.playerMaxVel) {
				physic.body.applyLinearImpulse(-0.1f, 0, position.x,
						position.y, true);
				state.dir = Direction.left;
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
