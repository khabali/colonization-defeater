package com.colonidefeater.game.state;

import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.math.Vector2;
import com.colonidefeater.game.World;
import com.colonidefeater.game.debug.GameLogger;
import com.colonidefeater.game.entity.EntityFactory;
import com.colonidefeater.game.handlers.ParallaxeBackground;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.resources.AssetsManager;
import com.colonidefeater.game.system.Box2dDebugRenderSystem;
import com.colonidefeater.game.system.CameraSystem;
import com.colonidefeater.game.system.MapRenderSystem;
import com.colonidefeater.game.system.PlayerControlSystem;
import com.colonidefeater.game.system.SpriteRenderSystem;
import com.colonidefeater.game.utils.Constants;

public class Level1GameState extends GameStateAdapter {

	private World world;

	public Level1GameState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	@Override
	public void init() {

		loadRessources();

		// -- World game init
		world = new World(new Vector2(0, -7f), true);

		final TiledMap tiledMap = AssetsManager.manager.get(AssetsManager.MAP_LVL_1, TiledMap.class);

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

		//
		final TiledMapImageLayer mountains = (TiledMapImageLayer) tiledMap.getLayers().get(Constants.MAP_LAYER_BG_2);
		final TiledMapImageLayer clouds = (TiledMapImageLayer) tiledMap.getLayers().get(Constants.MAP_LAYER_BG_3);

		final ParallaxeBackground[] backgrounds = new ParallaxeBackground[2];
		backgrounds[0] = new ParallaxeBackground(clouds, 0.1f);
		backgrounds[1] = new ParallaxeBackground(mountains, 0.2f);
		final Texture fixedBackground = AssetsManager.manager.get(AssetsManager.BG_SKY, Texture.class);

		// --
		world.setManager(new TagManager());
		world.setSystem(new MapRenderSystem(tiledMap, fixedBackground, backgrounds));
		world.setSystem(new PlayerControlSystem());
		world.setSystem(new SpriteRenderSystem());
		world.setSystem(new Box2dDebugRenderSystem());
		world.initialize();

		EntityFactory.createGround(world, tiledMap);
		EntityFactory.createPlayer(world, tiledMap);	
	}

	/**
	 * Load level ressources
	 */
	private void loadRessources() {
		AssetsManager.loadMap(AssetsManager.MAP_LVL_1);
		AssetsManager.manager.load(AssetsManager.BG_SKY, Texture.class);
		AssetsManager.manager.load(AssetsManager.STICK_MAN, TextureAtlas.class);

		// Wait for ressource to load
		while (!AssetsManager.manager.update()) {
		}

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
		if (GameInput.isPressed(GameInput.ESCAPE)) {
			gsm.setState(GameStateManager.STATE_MENU);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetsManager.manager.unload(AssetsManager.MAP_LVL_1);
		AssetsManager.manager.unload(AssetsManager.STICK_MAN);
		AssetsManager.manager.unload(AssetsManager.BG_SKY);
	}

}
