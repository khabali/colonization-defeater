package com.colonidefeater.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.colonidefeater.game.MyGdxGame;
import com.colonidefeater.game.utils.Constants;

public class DesktopLauncher {
	public static void main(String[] arg) {
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		final LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = Constants.TITLE;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
