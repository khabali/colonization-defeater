package com.colonidefeater.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.colonidefeater.game.component.PhysicsCpt;

@Wire
public class Box2dDebugRenderSystem extends EntityProcessingSystem {

	private static final String tag = "Box2dDebugRenderSystem";

	//
	
	private ComponentMapper<PhysicsCpt> physicComponentMapper;
	//
	private Box2DDebugRenderer b2dDebugRender;

	//
	private CameraSystem cameraSystem;

	@SuppressWarnings("unchecked")
	public Box2dDebugRenderSystem() {
		super(Aspect.getAspectForOne(PhysicsCpt.class));
		b2dDebugRender = new Box2DDebugRenderer();
	}

	@Override
	protected void process(Entity e) {

		//Gdx.app.debug(tag, "processing box2d debug object...");

		PhysicsCpt physicCpt = physicComponentMapper.get(e);
		World box2dWorld = physicCpt.body.getWorld();
		b2dDebugRender.render(box2dWorld, cameraSystem.camera.combined);

	}

}
