package com.colonidefeater.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.physics.box2d.World;
import com.colonidefeater.game.component.BulletCpt;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.debug.GameLogger;
import com.colonidefeater.game.utils.Direction;

@Wire
public class BulletSystem extends EntityProcessingSystem {

	private final String tag = getClass().getName();

	private ComponentMapper<BulletCpt> bulletCptMapper;
	private ComponentMapper<PhysicsCpt> physicsCptMapper;

	private CameraSystem camera;
	private World physicsHub;

	@SuppressWarnings("unchecked")
	public BulletSystem(World physicsHub) {
		super(Aspect.getAspectForOne(BulletCpt.class));
		this.physicsHub = physicsHub;
	}

	@Override
	protected void process(Entity e) {

		final BulletCpt bulletCpt = bulletCptMapper.get(e);
		final PhysicsCpt physicsCpt = physicsCptMapper.get(e);
		
		//set linear velocity
		switch (bulletCpt.dir) {
		case down:
		case right:
			physicsCpt.body.setLinearVelocity(20, 0);
			break;
		case left:
			physicsCpt.body.setLinearVelocity(-20, 0);
			break;
		case top:
			physicsCpt.body.setLinearVelocity(0, 20);
			break;
		}
		
		// bullet will be removed when out of screen or TODO++collides with
		// smthg++
		if (camera.isOutOfViewPort(physicsCpt.body.getPosition())) {
			GameLogger.debug(tag, "bullet out of view");
			physicsHub.destroyBody(physicsCpt.body);
			e.deleteFromWorld();
		}
		
	}

}
