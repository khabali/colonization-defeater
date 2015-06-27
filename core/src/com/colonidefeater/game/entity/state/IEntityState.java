package com.colonidefeater.game.entity.state;

import com.badlogic.gdx.physics.box2d.Body;
import com.colonidefeater.game.component.StateCpt;
import com.colonidefeater.game.system.CameraSystem;

public interface IEntityState {
	public static StandingState standingState = new StandingState();
	public static JumpingState jumpingState = new JumpingState();
	public static WalkingState walkingState = new WalkingState();
	public static StandingFireState standfireState = new StandingFireState();
	public static WalkingFireState walkfireState = new WalkingFireState();
	public static JumpingFireState jumpfirestate = new JumpingFireState();
	
	public String name();	
	public IEntityState update(Body body, StateCpt state);

}
