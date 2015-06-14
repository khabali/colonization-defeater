package com.colonidefeater.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.colonidefeater.game.World;
import com.colonidefeater.game.debug.GameLogger;
import com.colonidefeater.game.entity.EntityFactory;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.resources.AssetsManager;
import com.colonidefeater.game.system.Box2dDebugRenderSystem;
import com.colonidefeater.game.system.CameraSystem;
import com.colonidefeater.game.system.MapRenderSystem;
import com.colonidefeater.game.system.SpriteRenderSystem;

public class Level1GameState extends GameStateAdapter {

	private World world;

	public Level1GameState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	@Override
	public void init() {

		// -- Preparation du level (Map)
		AssetsManager.loadMap(AssetsManager.MAP_LVL_1);
		final TiledMap tiledMap = AssetsManager.manager.get(AssetsManager.MAP_LVL_1, TiledMap.class);

		// -- level Backgrounds
		final Texture background = AssetsManager.manager.get(AssetsManager.BG_SKY, Texture.class);

		// World game init
		world = new World(new Vector2(0, -7f), true);

		// -- Camera system
		final int width = (Integer) tiledMap.getProperties().get("width");
		final int height = (Integer) tiledMap.getProperties().get("height");
		GameLogger.debug(tag, "map w : " + width + " h : " + height);

		final int tileWidth = (Integer) tiledMap.getProperties().get("tilewidth");
		final int tileHeight = (Integer) tiledMap.getProperties().get("tileheight");
		GameLogger.debug(tag, "map tile w : " + tileWidth + " h : " + tileHeight);

		final int xMax = width * tileWidth;
		final int yMax = height * tileHeight;
		world.setSystem(new CameraSystem(xMax, yMax));

		// --
		world.setSystem(new MapRenderSystem(tiledMap, background));
		world.setSystem(new SpriteRenderSystem());
		world.setSystem(new Box2dDebugRenderSystem());
		world.initialize();

		EntityFactory.createGround(world, tiledMap);
		EntityFactory.createPlayer(world, tiledMap);

		// EntityFactory.createTestEntity(world);

	}

	@Override
	public void update() {
		handleInput();

	}

	@Override
	public void draw() {
		world.process(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void handleInput() {
		// Return to menu if escape is pressed
		if (GameInput.getInstance().getEscapeButton().isPressed()) {
			gsm.setState(GameStateManager.STATE_MENU);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
	}

}
