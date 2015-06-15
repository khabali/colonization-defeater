package com.colonidefeater.game.debug;

import com.badlogic.gdx.graphics.FPSLogger;

public final class GameFPSLogger {

	private static final boolean loggin = true;
	private static FPSLogger logger = new FPSLogger();

	public static void log() {
		if (loggin) {
			logger.log();
		}
	}

	private GameFPSLogger() {
	}
}
