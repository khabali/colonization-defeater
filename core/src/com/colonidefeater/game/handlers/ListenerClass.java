package com.colonidefeater.game.handlers;

import com.artemis.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.colonidefeater.game.component.CollisionCpt;

public class ListenerClass implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Entity e1 = (Entity)contact.getFixtureA().getBody().getUserData();
		Entity e2 = (Entity)contact.getFixtureB().getBody().getUserData();
		CollisionCpt colCpt1 = e1.getComponent(CollisionCpt.class);
		CollisionCpt colCpt2 = e2.getComponent(CollisionCpt.class);
		if (colCpt1 != null) {
			colCpt1.numContacts++;
		}
		if (colCpt2 != null) {
			colCpt2.numContacts++;
		}
	}

	@Override
	public void endContact(Contact contact) {
		Entity e1 = (Entity)contact.getFixtureA().getBody().getUserData();
		Entity e2 = (Entity)contact.getFixtureB().getBody().getUserData();
		CollisionCpt colCpt1 = e1.getComponent(CollisionCpt.class);
		CollisionCpt colCpt2 = e2.getComponent(CollisionCpt.class);
		if (colCpt1 != null) {
			colCpt1.numContacts--;
		}
		if (colCpt2 != null) {
			colCpt2.numContacts--;
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
