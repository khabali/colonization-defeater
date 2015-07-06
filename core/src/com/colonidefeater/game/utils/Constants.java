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
	public static final String MAP_LAYER_WEAPONS = "weapons";
	public static final String MAP_LAYER_BG_1 = "bg_1";
	public static final String MAP_LAYER_BG_2 = "bg_2";
	public static final String MAP_LAYER_BG_3 = "bg_3";

	public static final String MAP_PROP_WIDTH = "width";
	public static final String MAP_PROP_HEIGHT = "height";
	public static final String MAP_PROP_X = "x";
	public static final String MAP_PROP_Y = "y";
	public static final String MAP_PROP_TYPE = "type";

	// ###################
	// BOX2D
	// ###################
	public static final boolean isBox2dDebugEnabled = true;
	public static final int VELOCITY_ITERATIONS = 6;
	public static final int POSITION_ITERATIONS = 2;
	public static final float PPM = 100.0f; // Nombre de pixel par metre
	public static final float playerMaxVel = 1f;

}
