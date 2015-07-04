package com.colonidefeater.game.component;

import java.util.ArrayList;

import com.artemis.Component;
import com.artemis.Entity;
import com.colonidefeater.game.weapon.SimpleGun;
import com.colonidefeater.game.weapon.Weapon;

public class PlayerWeaponCpt extends Component {

	public static final int SIMPLE_GUN = 0;
	private ArrayList<Weapon> weapons;
	private int actifWeapon = SIMPLE_GUN;

	public PlayerWeaponCpt() {
		weapons = new ArrayList<Weapon>();
		weapons.add(new SimpleGun());
	}

	public Weapon getActifWeapon() {
		return weapons.get(actifWeapon);
	}

	public void setActifWeapon(int actifWeapon) {
		this.actifWeapon = actifWeapon;
	}

	public void fire(Entity e) {
		getActifWeapon().fire(e);
	}
}
