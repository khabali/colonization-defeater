package com.colonidefeater.game.component;

import com.artemis.Component;
import com.artemis.Entity;

public class CollisionCpt extends Component {
	
	public int numContacts = 0;
	public Entity e1;
	public Entity e2;
	
	public CollisionCpt() {
	}
	
	public boolean hasCollision() {
		return numContacts > 0;
	}

}
