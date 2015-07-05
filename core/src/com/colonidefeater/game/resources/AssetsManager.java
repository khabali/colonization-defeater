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

	// -- MAIN MENU RESSOURCES
	public static final String MENU_BG = "main_menu.png";

	// -- Virtual Game Pad
	public static final String BUTTON_LEFT = "virtualpad/left_button.png";
	public static final String BUTTON_LEFT__HOVER = "virtualpad/left_button_hover.png";
	public static final String BUTTON_RIGHT = "virtualpad/right_button.png";
	public static final String BUTTON_RIGHT_HOVER = "virtualpad/right_button_hover.png";
	public static final String BUTTON_FIRE = "virtualpad/fire_button.png";
	public static final String BUTTON_JUMP = "virtualpad/jump_button.png";

	// -- Weapons powers
	public static final String WP_H = "weapon_powers/h_weapon.png";

	// -- Backgrounds
	public static final String BG_SKY = "sky.png";

	// -- MAPS
	public static final String MAP_LVL_1 = "maps/map_lvl1.tmx";

	// -- SPRITES
	public static final String SOCCER_BALL = "sprites/soccerball.txt";
	public static final String STICK_MAN = "sprites/stickman/stickman.txt";
	public static final String FIRE = "sprites/fire/fire.txt";

	public static void load() {
		// Menu
		manager.load(MENU_BG, Texture.class);

		// -- weapon powers
		manager.load(WP_H, Texture.class);

		// Virtual game pad
		manager.load(BUTTON_LEFT, Texture.class);
		manager.load(BUTTON_LEFT__HOVER, Texture.class);
		manager.load(BUTTON_RIGHT, Texture.class);
		manager.load(BUTTON_RIGHT_HOVER, Texture.class);
		manager.load(BUTTON_FIRE, Texture.class);
		manager.load(BUTTON_JUMP, Texture.class);

		while (!AssetsManager.manager.update()) {
			Gdx.app.debug(TAG, "Loading assets : "
					+ (int) (AssetsManager.manager.getProgress() * 100) + " %");
		}

	}

	public static void dispose() {
		manager.dispose();
	}

	public static void loadMap(String fileName) {
		manager.setLoader(TiledMap.class, new TmxMapLoader(
				new InternalFileHandleResolver()));
		manager.load(fileName, TiledMap.class);
		while (!AssetsManager.manager.update()) {
			Gdx.app.debug(TAG, "Loading assets : "
					+ (int) (AssetsManager.manager.getProgress() * 100) + " %");
		}
	}

}
