package com.colonidefeater.game.component;

import com.artemis.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class PhysicsCpt extends Component {

	public Body body;

	public PhysicsCpt(Body b) {
		body = b;
	}

	public void getPosition() {
		body.getPosition();
	}

}
