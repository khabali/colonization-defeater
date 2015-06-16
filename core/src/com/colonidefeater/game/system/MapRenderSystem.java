package com.colonidefeater.game.system;

import com.artemis.annotations.Wire;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.colonidefeater.game.handlers.ParallaxeBackground;
import com.colonidefeater.game.utils.Constants;

@Wire
public class MapRenderSystem extends VoidEntitySystem {

	private final OrthogonalTiledMapRenderer tmRenderer;
	private final SpriteBatch sbatch;
	private final Texture fixedBackground;
	private final ParallaxeBackground[] backgrounds;

	private final OrthographicCamera fixedBackgroundCam;
	private final OrthographicCamera parallaxeBackgroundCam;
	//
	private CameraSystem cameraSystem;

	public MapRenderSystem(TiledMap tiledMap, Texture fixedBackground, ParallaxeBackground[] backgrounds) {
		super();
		tmRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		this.fixedBackground = fixedBackground;
		this.backgrounds = backgrounds;

		//
		parallaxeBackgroundCam = new OrthographicCamera();
		parallaxeBackgroundCam.setToOrtho(false, Constants.V_WIDTH * Constants.SCALE, Constants.V_HEIGHT
				* Constants.SCALE);
		//
		fixedBackgroundCam = new OrthographicCamera();
		fixedBackgroundCam.setToOrtho(false, fixedBackground.getWidth(), fixedBackground.getHeight());
		sbatch = new SpriteBatch();
	}

	@Override
	protected void initialize() {
		super.initialize();
	}

	@Override
	protected void processSystem() {

		sbatch.begin();

		// fixe background layer
		sbatch.setProjectionMatrix(fixedBackgroundCam.combined);
		sbatch.draw(fixedBackground, 0, 0);

		// parallaxe background layers
		sbatch.setProjectionMatrix(parallaxeBackgroundCam.combined);
		for (int i = 0; i < backgrounds.length; i++) {
			backgrounds[i].render(sbatch, cameraSystem.gameCamera);
		}
		sbatch.end();

		tmRenderer.setView(cameraSystem.gameCamera);
		tmRenderer.render();
	}

}
