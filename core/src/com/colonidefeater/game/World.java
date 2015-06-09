package com.colonidefeater.game;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.colonidefeater.game.utils.Constants;

public class World {
	
	public com.artemis.World ecsHub;
	public com.badlogic.gdx.physics.box2d.World physicsHub;
	
	public World(Vector2 gravity, boolean doSleep) {
		 ecsHub = new com.artemis.World();
		 physicsHub = new com.badlogic.gdx.physics.box2d.World(gravity, doSleep);	
	}
	
	public Entity createEntity() {
		return ecsHub.createEntity();
	}
	
	public void initialize() {
		ecsHub.initialize();
	}
	
	public Body createBody(BodyDef def) {
		return physicsHub.createBody(def);
	}
	
	public void process(float delta) {
		ecsHub.setDelta(delta);
		ecsHub.process();
		physicsHub.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
	}
	
	public <T extends EntitySystem> T setSystem(T system) {
		return ecsHub.setSystem(system);
	}

}
