package com.colonidefeater.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.colonidefeater.game.component.CollisionCpt;
import com.colonidefeater.game.component.TypeCpt;
import com.colonidefeater.game.utils.EntityType;

@Wire
public class CollisionSystem extends EntityProcessingSystem {
	private ComponentMapper<CollisionCpt> collisionCptMapper;

	@SuppressWarnings("unchecked")
	public CollisionSystem() {
		super(Aspect.getAspectForOne(CollisionCpt.class));
	}

	@Override
	protected void process(Entity e) {
		final CollisionCpt collisionCpt = collisionCptMapper.get(e);
		if (collisionCpt.hasCollision()) {
			EntityType t1 = collisionCpt.e1.getComponent(TypeCpt.class).type;
			EntityType t2 = collisionCpt.e2.getComponent(TypeCpt.class).type;
			if (t1 == EntityType.ENEMY && t2 == EntityType.BULLET) {
				System.out.println("ENEMY TOUCH");
			}
		}
	}

}
