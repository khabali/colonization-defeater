package com.colonidefeater.game.utils;

public class Constants {

	// ###################
	// GAME
	// ###################
	public static final String TITLE = "Colonization defeater";
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 3;

	// ###################
	// MAP
	// ###################
	public static final String MAP_LAYER_PLAYER = "player";
	public static final String MAP_LAYER_GROUND = "ground";
	public static final String MAP_LAYER_BG_1 = "bg_1";
	public static final String MAP_LAYER_BG_2 = "bg_2";
	public static final String MAP_LAYER_BG_3 = "bg_3";

	public static final String MAP_PROPERTIE_X = "x";
	public static final String MAP_PROPERTIE_Y = "y";

	// ###################
	// BOX2D
	// ###################
	public static final boolean isBox2dDebugEnabled = true;
	public static final float TIME_STEP = 1 / 60f;
	public static final int VELOCITY_ITERATIONS = 1;
	public static final int POSITION_ITERATIONS = 1;
	public static final float PPM = 100.0f; // Nombre de pixel par metre

}
