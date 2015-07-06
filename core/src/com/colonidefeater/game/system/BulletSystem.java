package com.colonidefeater.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.physics.box2d.World;
import com.colonidefeater.game.component.BulletCpt;
import com.colonidefeater.game.component.PhysicsCpt;

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

		physicsCpt.body.setLinearVelocity(20, 0);
		if (bulletCpt.isLeftSided) {
			physicsCpt.body.setLinearVelocity(-20, 0);
		}

		// bullet will be removed when out of screen or TODO++collides with
		// smthg++
		if (camera.isOutOfViewPort(physicsCpt.body.getPosition().x)) {
			physicsHub.destroyBody(physicsCpt.body);
			e.deleteFromWorld();
		}
	}

}
