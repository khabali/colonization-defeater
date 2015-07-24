package com.colonidefeater.game.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;
import com.colonidefeater.game.utils.Direction;

public class BulletCpt extends Component {
	public Vector2 moveFrom;
	public Direction dir;
	public boolean doCollide = true;
	public float dammage = 0;

	// set <to> to null to make it move all along the screen
	public BulletCpt(Vector2 from) {
		moveFrom = from;
	}

	public BulletCpt(Vector2 from, Direction d) {
		moveFrom = from;
		setDir(d);
	}

	public BulletCpt(Vector2 from, float dammage, Direction d) {
		moveFrom = from;
		setDir(d);
		this.dammage = dammage;
	}
	
	public void setDir(Direction d) {
		dir = d;
		switch (dir) {
		case left:
			moveFrom.x += -0.5;
			break;
		case right:
			moveFrom.x += 0.5;
			break;
		case top:
			moveFrom.y += 0.5;
			break;
		default:
			break;
		}
	}

}
