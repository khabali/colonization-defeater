package com.colonidefeater.game.tools;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class MyPacker {

	private static String inputDir = "ressource-to-pack/player";
	private static String outputDir = "ressource-packed";
	private static String packFileName = "player";

	public static void main(String[] args) throws Exception {
		Settings setting = new Settings();
		setting.stripWhitespaceX = true;
		setting.stripWhitespaceY = true;
		setting.debug = true;
		TexturePacker.process(setting, inputDir, outputDir, packFileName);
	}
}
