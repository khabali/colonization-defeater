package com.colonidefeater.game.weapon;

import com.artemis.Entity;
import com.colonidefeater.game.debug.GameLogger;
import com.colonidefeater.game.entity.EntityFactory;

public abstract class Weapon {

	private String tag = getClass().getName();
	protected int ammo = 200;
	protected float dammage = 1f;
	protected float delay = 0.3f * 1000f;
	protected long lastFireTime;

	/**
	 * this methode make the weapon fire
	 */
	public void fire(Entity e) {
		// test if is a finit ammo weapon
		// if no ammo juste return without firing
		if (isFiniteAmmo() && getAmmoAmount() == 0) {
			GameLogger.debug(tag, "No ammo in this weapon");
			return;
		}

		// delay shoots
		if (System.currentTimeMillis() - lastFireTime > delay) {
			// decrease ammo stock
			if (isFiniteAmmo()) {
				ammo--;
			}

			EntityFactory.createBullet(e, dammage);
			lastFireTime = System.currentTimeMillis();
		}
	}

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
