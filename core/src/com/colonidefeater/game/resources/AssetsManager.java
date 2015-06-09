package com.colonidefeater.game.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetsManager {

	public static final AssetManager manager = new AssetManager();

	// MAIN MENU RESSOURCES
	/** Menu background */
	public static final String MENU_BG = "main_menu.png";
	public static final String SOCCER_BALL = "sprites/soccerball.txt";

	public static void load() {
		// Menu
		manager.load(MENU_BG, Texture.class);

		// sprites
		manager.load(SOCCER_BALL, TextureAtlas.class);
		//manager.load(SPRITE_SHEET_WOMEN, Texture.class);
		//manager.load(SPRITE_SHEET_VILLAGER, TextureAtlas.class);
		//manager.load(SPRITE_SHEET_ARCHER, TextureAtlas.class);
	}

	public static void dispose() {
		manager.dispose();
	}

}
