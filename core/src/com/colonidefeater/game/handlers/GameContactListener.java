package com.colonidefeater.game.handlers;

import com.artemis.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.colonidefeater.game.component.CollisionCpt;

public class GameContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Entity e1 = (Entity)contact.getFixtureA().getBody().getUserData();
		Entity e2 = (Entity)contact.getFixtureB().getBody().getUserData();
		CollisionCpt collisionCpt = e1.getComponent(CollisionCpt.class);
		if (collisionCpt != null) {
			collisionCpt.numContacts++;
			collisionCpt.e1 = e1;
			collisionCpt.e2 = e2;
		}
	}

	@Override
	public void endContact(Contact contact) {
		Entity e1 = (Entity)contact.getFixtureA().getBody().getUserData();
		Entity e2 = (Entity)contact.getFixtureB().getBody().getUserData();
		CollisionCpt collisionCpt = e1.getComponent(CollisionCpt.class);
		if (collisionCpt != null) {
			e1.getComponent(CollisionCpt.class).numContacts--;
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
