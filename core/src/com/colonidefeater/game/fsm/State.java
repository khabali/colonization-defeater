package com.colonidefeater.game.fsm;

public interface State<E> {
	public void enter(E entity);
	public void update(E entity);
	public void exit(E entity);
}
