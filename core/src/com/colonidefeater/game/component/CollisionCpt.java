package com.colonidefeater.game.component;

import com.artemis.Component;

public class CollisionCpt extends Component {
	
	public int numContacts = 0;
	
	public boolean hasCollision() {
		return numContacts > 0;
	}

}
