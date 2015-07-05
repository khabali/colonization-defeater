package com.colonidefeater.game.fsm;

public class StateMachine<E> {
	private E owner;
	private State<E> currentState;
	private State<E> previousState;
	
	public StateMachine(E owner) {
		this.owner = owner;
	}
	
	public void setInitialState(State<E> state) {
		this.previousState = null;
		this.currentState = state;
		currentState.enter(owner);
	}
	
	public State<E> getCurrentState() {
		return currentState;
	}
	
	public State<E> getPreviousState() {
		return previousState;
	}
	
	public void update() {
		currentState.update(owner);
	}
	
	public void changeState(State<E> newState) {
		previousState = currentState;
		if (currentState != null) currentState.exit(owner);
		currentState = newState;
		if (currentState != null) currentState.enter(owner);
	}
	
	public boolean isInState(State<E> state) {
		return currentState == state;
	}

}
