package com.colonidefeater.game.component;

import com.artemis.Component;
import com.artemis.Entity;
import com.colonidefeater.game.fsm.StateMachine;

public class StateMachineCpt extends Component {
	
	public boolean isLeftSided = false;
	public StateMachine<Entity> stateMachine;
	
	public StateMachineCpt(StateMachine<Entity> stm) {
		stateMachine = stm;
	}

}
