package com.colonidefeater.game.component;

import com.artemis.Component;
import com.colonidefeater.game.utils.EntityType;

public class TypeCpt extends Component {
	
	public EntityType type;
	
	public TypeCpt(EntityType t) {
		type = t;
	}

}
