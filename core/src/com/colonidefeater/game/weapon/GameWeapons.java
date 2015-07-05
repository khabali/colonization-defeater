package com.colonidefeater.game.weapon;

public final class GameWeapons {

	public enum Type {
		/**
		 * Heavy weapon
		 */
		H,
		/**
		 * Laser weapon
		 */
		L, ;
	}

	public static class SimpleGun extends Weapon {
		public float dammage = 1.2f;
	}

}
