package com.colonidefeater.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.debug.GameLogger;
import com.colonidefeater.game.utils.Constants;

@Wire
public class Box2dDebugRenderSystem extends EntityProcessingSystem {

	private final String tag = getClass().getName();
	private final Box2DDebugRenderer b2dDebugRender;
	

	//
	private ComponentMapper<PhysicsCpt> physicComponentMapper;
	private CameraSystem CameraSystem;

	@SuppressWarnings("unchecked")
	public Box2dDebugRenderSystem() {
		super(Aspect.getAspectForOne(PhysicsCpt.class));
		b2dDebugRender = new Box2DDebugRenderer();

		if (Constants.isBox2dDebugEnabled) {
			GameLogger.debug(tag, "Box2d debug render is enabled");
		}
	}

	@Override
	protected void process(Entity e) {
		if (Constants.isBox2dDebugEnabled) {
			final PhysicsCpt physicCpt = physicComponentMapper.get(e);
			final World box2dWorld = physicCpt.body.getWorld();
			b2dDebugRender
					.render(box2dWorld, CameraSystem.box2dCamera.combined);
		}
	}

}
