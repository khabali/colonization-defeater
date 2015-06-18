package com.colonidefeater.game.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class AssetsManager {

	private static final String TAG = "AssetsManager";
	public static final AssetManager manager = new AssetManager();

	// MAIN MENU RESSOURCES
	/** Menu background */
	public static final String MENU_BG = "main_menu.png";

	// Backgrounds
	public static final String BG_SKY = "sky.png";
	// MAPS
	public static final String MAP_LVL_1 = "maps/map_lvl1.tmx";
	// SPRITES
	public static final String SOCCER_BALL = "sprites/soccerball.txt";
	public static final String STICK_MAN = "sprites/stickman/stickman.txt";

	public static void load() {
		// Menu
		manager.load(MENU_BG, Texture.class);

	}

	public static void dispose() {
		manager.dispose();
	}

	public static void loadMap(String fileName) {
		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		manager.load(fileName, TiledMap.class);
		while (!AssetsManager.manager.update()) {
			Gdx.app.debug(TAG, "Loading assets : " + (int) (AssetsManager.manager.getProgress() * 100) + " %");
		}
	}

}
