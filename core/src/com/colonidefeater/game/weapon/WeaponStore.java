package com.colonidefeater.game.weapon;

public class WeaponStore {

	private Weapon defaultWeapon;
	private Weapon actifWeapon;

	public WeaponStore(Weapon defaultWp) {
		defaultWeapon = defaultWp;
		actifWeapon = defaultWp;
	}

	public Weapon getActifWeapon() {
		return actifWeapon;
	}

	public void changeWeapon(Weapon wp) {
		actifWeapon = wp;
	}

	public void dropWeapon() {
		actifWeapon = defaultWeapon;
	}

}
