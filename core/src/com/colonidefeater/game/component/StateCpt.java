package com.colonidefeater.game.component;

import com.artemis.Component;
import com.colonidefeater.game.entity.state.IEntityState;

public class StateCpt extends Component {
	
	public IEntityState currentState;
	public boolean isLeftSided = false;
	
	public StateCpt(IEntityState cur) {
		currentState = cur;
	}

}
