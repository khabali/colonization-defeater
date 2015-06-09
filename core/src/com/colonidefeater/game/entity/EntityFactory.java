package com.colonidefeater.game.entity;

import com.artemis.Entity;
import com.artemis.utils.EntityBuilder;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.colonidefeater.game.World;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.TextureCpt;
import com.colonidefeater.game.resources.AssetsManager;

public class EntityFactory {
	
	public static Entity createTestEntity(World world) {
		// entity physics, we want to create a body for the entity
		BodyDef bodyDef = new BodyDef(); // First we create a body definition
		bodyDef.type = BodyType.DynamicBody; // We set our body to dynamic
		bodyDef.position.set(100, 300); // Set our body's starting position in the world
		Body body = world.createBody(bodyDef); // Create our body in the world using our body definition
		// Create a circle shape and set its radius to 6
		CircleShape circle = new CircleShape();
		circle.setRadius(6f);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f; // Make it bounce a little bit
		body.createFixture(fixtureDef);
		// create entity
		return new EntityBuilder(world.ecsHub)
		.with(new TextureCpt(AssetsManager.SOCCER_BALL, "soccerball"), new PhysicsCpt(body))
		.build();
	}

}
