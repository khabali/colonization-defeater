package com.colonidefeater.game.component;

import com.artemis.Component;
import com.colonidefeater.game.weapon.Weapon;
import com.colonidefeater.game.weapon.WeaponStore;

public class PlayerWeaponCpt extends Component {

	public WeaponStore wpStore;

	public PlayerWeaponCpt(Weapon defaultWeapon) {
		wpStore = new WeaponStore(defaultWeapon);
	}

	
}
