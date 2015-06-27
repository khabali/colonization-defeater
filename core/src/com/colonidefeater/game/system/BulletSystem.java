package com.colonidefeater.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.colonidefeater.game.MyGdxGame;
import com.colonidefeater.game.component.BulletCpt;
import com.colonidefeater.game.component.PhysicsCpt;

@Wire
public class BulletSystem extends EntityProcessingSystem {
	private ComponentMapper<BulletCpt> bulletCptMapper;
	private ComponentMapper<PhysicsCpt> physicsCptMapper;
	
	private CameraSystem camera;

	@SuppressWarnings("unchecked")
	public BulletSystem() {
		super(Aspect.getAspectForOne(BulletCpt.class));
	}

	@Override
	protected void process(Entity e) {
		final BulletCpt bulletCpt = bulletCptMapper.get(e);
		final PhysicsCpt physicsCpt = physicsCptMapper.get(e);

		if (bulletCpt.isLeftSided) {
			physicsCpt.body.setLinearVelocity(-20, 0);
		}else {
			physicsCpt.body.setLinearVelocity(20, 0);
		}
		
		// bullet will be removed when out of screen or TODO++collides with smthg++
		if (camera.isOutOfViewPort(physicsCpt.body.getPosition().x)) {
			MyGdxGame.world.physicsHub.destroyBody(physicsCpt.body);
			e.deleteFromWorld();
		}
	}

}
