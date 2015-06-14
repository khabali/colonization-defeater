package com.colonidefeater.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

@Wire
public class MapRenderSystem extends EntityProcessingSystem {

	private final OrthogonalTiledMapRenderer tmRenderer;

	private final OrthographicCamera bgCamera;
	private final SpriteBatch sbatch;
	private final Texture background;

	//
	private CameraSystem cameraSystem;

	public MapRenderSystem(TiledMap tiledMap, Texture bg) {
		super(Aspect.getEmpty());
		background = bg;
		tmRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		//
		bgCamera = new OrthographicCamera();
		bgCamera.setToOrtho(false, background.getWidth(), background.getHeight());
		sbatch = new SpriteBatch();
		sbatch.setProjectionMatrix(bgCamera.combined);
	}

	@Override
	protected void process(Entity e) {

		sbatch.begin();
		sbatch.draw(background, 0, 0);
		sbatch.end();

		tmRenderer.setView(cameraSystem.gameCamera);
		tmRenderer.render();
	}
}
