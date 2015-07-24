package com.colonidefeater.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.physics.box2d.World;
import com.colonidefeater.game.component.CollisionCpt;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.TypeCpt;
import com.colonidefeater.game.utils.EntityType;

@Wire
public class CollisionSystem extends EntityProcessingSystem {
	private ComponentMapper<CollisionCpt> collisionCptMapper;
	
	private World physicsHub;

	@SuppressWarnings("unchecked")
	public CollisionSystem(World physicsHub) {
		super(Aspect.getAspectForOne(CollisionCpt.class));
		this.physicsHub = physicsHub;
	}

	@Override
	protected void process(Entity e) {
		final CollisionCpt collisionCpt = collisionCptMapper.get(e);
		if (collisionCpt.hasCollision()) {
			TypeCpt t1 = collisionCpt.e1.getComponent(TypeCpt.class);
			TypeCpt t2 = collisionCpt.e2.getComponent(TypeCpt.class);
			if (t1 != null && t2 != null) {
				if (t1.type == EntityType.ENEMY && t2.type == EntityType.BULLET) {
					enemyBulletHandle(collisionCpt.e1, collisionCpt.e2);
				}
				if (t1.type == EntityType.PLAYER && t2.type == EntityType.BULLET) {
					playerBulletHandle(collisionCpt.e1, collisionCpt.e2);
				}
			}
			
		}
	}
	
	private void enemyBulletHandle(Entity eEnemy, Entity eBullet) {
		physicsHub.destroyBody(eBullet.getComponent(PhysicsCpt.class).body);
		eBullet.deleteFromWorld();
		physicsHub.destroyBody(eEnemy.getComponent(PhysicsCpt.class).body);
		eEnemy.deleteFromWorld();
	}
	
	private void playerBulletHandle(Entity ePlayer, Entity eBullet) {
		physicsHub.destroyBody(eBullet.getComponent(PhysicsCpt.class).body);
		eBullet.deleteFromWorld();
		physicsHub.destroyBody(ePlayer.getComponent(PhysicsCpt.class).body);
		ePlayer.deleteFromWorld();
	}

}
