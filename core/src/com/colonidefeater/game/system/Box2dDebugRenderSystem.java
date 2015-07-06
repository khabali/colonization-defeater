package com.colonidefeater.game.system;

import com.artemis.annotations.Wire;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.colonidefeater.game.debug.GameLogger;
import com.colonidefeater.game.utils.Constants;

@Wire
public class Box2dDebugRenderSystem extends VoidEntitySystem {

	private final String tag = getClass().getName();
	private final Box2DDebugRenderer b2dDebugRender;
	private World physicHub;

	//
	private CameraSystem CameraSystem;

	public Box2dDebugRenderSystem(World physicHub) {
		this.b2dDebugRender = new Box2DDebugRenderer();
		this.physicHub = physicHub;

		if (Constants.isBox2dDebugEnabled) {
			GameLogger.debug(tag, "Box2d debug render is enabled");
		}
	}

	@Override
	protected void processSystem() {
		if (Constants.isBox2dDebugEnabled) {
			b2dDebugRender.render(physicHub, CameraSystem.box2dCamera.combined);
		}
	}

}
