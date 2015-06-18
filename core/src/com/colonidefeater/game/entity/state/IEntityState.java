package com.colonidefeater.game.entity.state;

import com.badlogic.gdx.physics.box2d.Body;
import com.colonidefeater.game.system.CameraSystem;

public interface IEntityState {
	public static StandingState standingState = new StandingState();
	public static JumpingState jumpingState = new JumpingState();
	
	public String name();	
	public IEntityState handleInput(Body body);
	public void update(Body body);

}
