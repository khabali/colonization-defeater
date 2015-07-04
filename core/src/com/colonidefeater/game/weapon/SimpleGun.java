package com.colonidefeater.game.weapon;

import com.artemis.Entity;
import com.colonidefeater.game.entity.EntityFactory;

public class SimpleGun extends Weapon {

	public float dammage = 1.2f;

	

	@Override
	public void fire(Entity e) {
		// test if is a finit ammo weapon
		// if no ammo juste return without firing
		if (isFiniteAmmo() && getAmmoAmount() == 0) {
			return;
		}
		// decrease ammo stock
		if (isFiniteAmmo()) {
			ammo--;
		}

		EntityFactory.createBullet(e, dammage);
	}

}
