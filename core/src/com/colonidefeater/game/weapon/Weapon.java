package com.colonidefeater.game.weapon;

import com.artemis.Entity;

public abstract class Weapon {

	public int ammo = 200;
	public float dammage = 1;

		/**
	 * this methode make the weapon fire
	 */
	public abstract void fire(Entity e);

	/**
	 * this methode return the dammage of this weapon
	 * 
	 * @return weapon dammage
	 */
	public float getDammage() {
		return dammage;
	}

	/**
	 * this methode return number of ammo in this weapon
	 * 
	 * @return
	 */
	public int getAmmoAmount() {
		return ammo;
	}

	/**
	 * charge weapon ammo for finit weapon
	 * 
	 * @param ammo
	 */
	public void chargeWeapon(int ammo) {
		this.ammo += ammo;
	}

	/**
	 * this methode return false if this weapon got infinite ammo stock
	 * 
	 * @return true if this weapon need ammo
	 */
	public boolean isFiniteAmmo() {
		return false;
	}
}
