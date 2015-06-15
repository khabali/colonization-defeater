package com.colonidefeater.game.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class AssetsManager {

	private static final String TAG = "AssetsManager";
	public static final AssetManager manager = new AssetManager();

	// MAIN MENU RESSOURCES
	/** Menu background */
	public static final String MENU_BG = "main_menu.png";
	public static final String SOCCER_BALL = "sprites/soccerball.txt";

	// Backgroundss
	public static final String BG_SKY = "sky.png";
	public static final String BG_MOUNTAINS = "maps/tiles/mountains.png";
	public static final String BG_NUAGE = "maps/tiles/nuage.png";

	// MAPS
	public static final String MAP_LVL_1 = "maps/map_lvl1.tmx";

	public static void load() {
		// Menu
		manager.load(MENU_BG, Texture.class);

		// Game Backgrounds
		manager.load(BG_SKY, Texture.class);
		manager.load(BG_MOUNTAINS, Texture.class);
		manager.load(BG_NUAGE, Texture.class);

		// sprites
		manager.load(SOCCER_BALL, TextureAtlas.class);
		// manager.load(SPRITE_SHEET_WOMEN, Texture.class);
		// manager.load(SPRITE_SHEET_VILLAGER, TextureAtlas.class);
		// manager.load(SPRITE_SHEET_ARCHER, TextureAtlas.class);
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
