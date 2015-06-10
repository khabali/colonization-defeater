package com.colonidefeater.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.colonidefeater.game.MyGdxGame;
import com.colonidefeater.game.utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = Constants.TITLE;
		cfg.width = Constants.V_WIDTH * Constants.SCALE;
		cfg.height = Constants.V_HEIGHT * Constants.SCALE;
		
		new LwjglApplication(new MyGdxGame(), config);
	}
}
