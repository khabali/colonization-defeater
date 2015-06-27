package com.colonidefeater.game.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class BulletCpt extends Component {
	public Vector2 moveFrom;
	public boolean isLeftSided = false;
	public boolean doCollide = true;
	
	// set <to> to null to make it move all along the screen
	public BulletCpt(Vector2 from) {
		moveFrom = from;
	}
	
	public BulletCpt(Vector2 from, boolean goLeft) {
		moveFrom = from;
		isLeftSided = goLeft;
	}

}
