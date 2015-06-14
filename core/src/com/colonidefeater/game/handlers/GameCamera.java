package com.colonidefeater.game.handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * OrthographicCamera that cannot go beyond specified rectangle.
 */
public class GameCamera extends OrthographicCamera {

	private float xmin;
	private float xmax;
	private float ymin;
	private float ymax;

	public GameCamera() {
		this(0, 0, 0, 0);
	}

	/**
	 * 
	 * @param xmin
	 * @param xmax
	 *            : the world x size
	 * @param ymin
	 * @param ymax
	 *            : the world y size
	 */
	public GameCamera(float xmin, float xmax, float ymin, float ymax) {
		super();
		setBounds(xmin, xmax, ymin, ymax);
	}

	public void setBounds(float xmin, float xmax, float ymin, float ymax) {
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
	}

	public void setXPosition(float x) {
		position.x = x;
		fixXBounds();
	}

	private void fixXBounds() {
		if (position.x < xmin + viewportWidth / 2f) {
			position.x = xmin + viewportWidth / 2f;
		}
		if (position.x > xmax - viewportWidth / 2f) {
			position.x = xmax - viewportWidth / 2f;
		}
	}

}
