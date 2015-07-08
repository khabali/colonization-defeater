package com.colonidefeater.game.state;

import static com.colonidefeater.game.utils.Constants.MAP_LAYER_BG_2;
import static com.colonidefeater.game.utils.Constants.MAP_LAYER_BG_3;
import static com.colonidefeater.game.utils.Constants.MAP_PROP_HEIGHT;
import static com.colonidefeater.game.utils.Constants.MAP_PROP_WIDTH;

import com.artemis.managers.TagManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.colonidefeater.game.entity.EntityFactory;
import com.colonidefeater.game.handlers.ParallaxeBackground;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.resources.AssetsManager;
import com.colonidefeater.game.system.Box2dDebugRenderSystem;
import com.colonidefeater.game.system.BulletSystem;
import com.colonidefeater.game.system.CameraSystem;
import com.colonidefeater.game.system.MapRenderSystem;
import com.colonidefeater.game.system.SoldierSpawnSystem;
import com.colonidefeater.game.system.SpriteRenderSystem;
import com.colonidefeater.game.system.StateMachineSystem;
import com.colonidefeater.game.system.TextureRenderSystem;

public class Level1GameState extends GameStateAdapter {

	public Level1GameState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {

		loadRessources();

		final TiledMap tiledMap = AssetsManager.manager
				.get(AssetsManager.MAP_LVL_1);

		// -- Camera system
		final int width = (Integer) tiledMap.getProperties()
				.get(MAP_PROP_WIDTH);
		final int height = (Integer) tiledMap.getProperties().get(
				MAP_PROP_HEIGHT);

		final int tileWidth = (Integer) tiledMap.getProperties().get(
				"tilewidth");
		final int tileHeight = (Integer) tiledMap.getProperties().get(
				"tileheight");

		final int xMax = width * tileWidth;
		final int yMax = height * tileHeight;
		ecsHub.setSystem(new CameraSystem(xMax, yMax));

		//
		final TiledMapImageLayer mountains = (TiledMapImageLayer) tiledMap
				.getLayers().get(MAP_LAYER_BG_2);
		final TiledMapImageLayer clouds = (TiledMapImageLayer) tiledMap
				.getLayers().get(MAP_LAYER_BG_3);

		final ParallaxeBackground[] backgrounds = new ParallaxeBackground[2];
		backgrounds[0] = new ParallaxeBackground(clouds, 0.1f);
		backgrounds[1] = new ParallaxeBackground(mountains, 0.2f);
		final Texture fixedBackground = AssetsManager.manager
				.get(AssetsManager.BG_SKY);

		// --
		ecsHub.setManager(new TagManager());
		ecsHub.setSystem(new MapRenderSystem(tiledMap, fixedBackground,
				backgrounds));
		ecsHub.setSystem(new BulletSystem(physicsHub));
		ecsHub.setSystem(new SpriteRenderSystem());
		ecsHub.setSystem(new TextureRenderSystem());
		ecsHub.setSystem(new SoldierSpawnSystem(physicsHub, tiledMap));
		ecsHub.setSystem(new StateMachineSystem());
		ecsHub.setSystem(new Box2dDebugRenderSystem(physicsHub));
		ecsHub.initialize();

		EntityFactory.createGround(ecsHub, physicsHub, tiledMap);
		EntityFactory.createPlayer(ecsHub, physicsHub, tiledMap);
		//EntityFactory.createSoldiers(ecsHub, physicsHub, tiledMap, null);
		EntityFactory.createWeaponsPowers(ecsHub, physicsHub, tiledMap);
	}

	/**
	 * Load level ressources
	 */
	private void loadRessources() {
		// TODO call this in charge game screen
		AssetsManager.load();

		AssetsManager.loadMap(AssetsManager.MAP_LVL_1);
		AssetsManager.manager.load(AssetsManager.BG_SKY, Texture.class);
		AssetsManager.manager.load(AssetsManager.STICK_MAN, TextureAtlas.class);
		AssetsManager.manager.load(AssetsManager.FIRE, TextureAtlas.class);

		// Wait for ressource to load
		while (!AssetsManager.manager.update()) {
		}
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
		AssetsManager.manager.unload(AssetsManager.FIRE);
	}

}
