package com.colonidefeater.game.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.artemis.Component;
import com.artemis.Entity;
import com.colonidefeater.game.fsm.State;
import com.colonidefeater.game.fsm.StateMachine;
import com.colonidefeater.game.fsm.player.PlayerActionState;
import com.colonidefeater.game.utils.Direction;

public class StateMachineCpt extends Component {
	
	public Direction dir;
	public Direction looktoward = Direction.none;
	//public StateMachine<Entity> stateMachine;
	
	//public ArrayList<StateMachine<Entity>> stateMachines = new ArrayList<StateMachine<Entity>>();
	public LinkedHashMap<Class<?>, StateMachine<Entity>> stateMachines = new LinkedHashMap<Class<?>, StateMachine<Entity>>();
	
	public StateMachineCpt() {
	}
	
	public <T> void add(Class<?> s, Entity owner, State<Entity> initial) {
		StateMachine<Entity> sm = new StateMachine<Entity>(owner);
		sm.setInitialState(initial);
		stateMachines.put(s, sm);
	}
	
	public StateMachine<Entity> get(Class<?> s) {
		return stateMachines.get(s);
	}
	
	public String getId() {
		String id = "";
		for (StateMachine<Entity> sm : stateMachines.values()) {
			id += "_" + sm.getCurrentState().toString();
		}
		return id.replaceFirst("_", "");
	}

}
