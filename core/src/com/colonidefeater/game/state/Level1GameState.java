package com.colonidefeater.game.state;

import static com.colonidefeater.game.utils.Constants.SCALE;
import static com.colonidefeater.game.utils.Constants.V_HEIGHT;
import static com.colonidefeater.game.utils.Constants.V_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.math.Vector2;
import com.colonidefeater.game.World;
import com.colonidefeater.game.debug.GameLogger;
import com.colonidefeater.game.entity.EntityFactory;
import com.colonidefeater.game.handlers.GameCamera;
import com.colonidefeater.game.handlers.ParallaxeBackground;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.resources.AssetsManager;
import com.colonidefeater.game.system.Box2dDebugRenderSystem;
import com.colonidefeater.game.system.CameraSystem;
import com.colonidefeater.game.system.MapRenderSystem;
import com.colonidefeater.game.system.SpriteRenderSystem;
import com.colonidefeater.game.utils.Constants;

public class Level1GameState extends GameStateAdapter {

	private World world;

	private GameCamera gameCamera;

	public Level1GameState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	@Override
	public void init() {

		// -- Preparation du level (Map)
		AssetsManager.loadMap(AssetsManager.MAP_LVL_1);
		final TiledMap tiledMap = AssetsManager.manager.get(AssetsManager.MAP_LVL_1, TiledMap.class);

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

		//
		final TiledMapImageLayer mountains = (TiledMapImageLayer) tiledMap.getLayers().get(Constants.MAP_LAYER_BG_2);
		final TiledMapImageLayer clouds = (TiledMapImageLayer) tiledMap.getLayers().get(Constants.MAP_LAYER_BG_3);

		final ParallaxeBackground[] backgrounds = new ParallaxeBackground[2];
		backgrounds[0] = new ParallaxeBackground(clouds, 0.1f);
		backgrounds[1] = new ParallaxeBackground(mountains, 0.2f);
		final Texture fixedBackground = AssetsManager.manager.get(AssetsManager.BG_SKY, Texture.class);

		// --
		world.setSystem(new MapRenderSystem(tiledMap, fixedBackground, backgrounds));
		world.setSystem(new SpriteRenderSystem());
		world.setSystem(new Box2dDebugRenderSystem());
		world.initialize();

		EntityFactory.createGround(world, tiledMap);
		EntityFactory.createPlayer(world, tiledMap);

		gameCamera = new GameCamera();
		gameCamera.setToOrtho(false, V_WIDTH * SCALE, V_HEIGHT * SCALE);
		gameCamera.setBounds(0, xMax, 0, yMax);

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
