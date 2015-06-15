package com.colonidefeater.game.handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.colonidefeater.game.utils.Constants;

public class ParallaxeBackground {

	private final TextureRegion image;
	private final float scale;

	private float x;
	private float y;
	private final int startDrawingY;
	private final int repeatDrawX;

	private float dx;
	private float dy;

	public ParallaxeBackground(TiledMapImageLayer imageLayer, float scale) {
		image = imageLayer.getTextureRegion();
		this.scale = scale;
		startDrawingY = imageLayer.getY();
		repeatDrawX = Constants.V_WIDTH * Constants.SCALE / image.getRegionWidth() + 1;
	}

	public void render(SpriteBatch sb, OrthographicCamera cam) {

		final float x = ((this.x + cam.viewportWidth / 2 - cam.position.x) * scale) % image.getRegionWidth();
		final float y = ((this.y + cam.viewportHeight / 2 - cam.position.y) * scale) % image.getRegionHeight();

		final int colOffset = x > 0 ? -1 : 0;
		final int rowOffset = y > 0 ? -1 : 0;
		for (int col = 0; col < repeatDrawX; col++) {
			sb.draw(image, x + (col + colOffset) * image.getRegionWidth(),
					startDrawingY + y + rowOffset * image.getRegionHeight());
		}
	}

}
