package com.colonidefeater.game.entity;

import static com.colonidefeater.game.utils.Constants.MAP_LAYER_GROUND;
import static com.colonidefeater.game.utils.Constants.MAP_LAYER_PLAYER;
import static com.colonidefeater.game.utils.Constants.MAP_LAYER_SOLDIERS;
import static com.colonidefeater.game.utils.Constants.MAP_LAYER_WEAPONS;
import static com.colonidefeater.game.utils.Constants.MAP_PROP_HEIGHT;
import static com.colonidefeater.game.utils.Constants.MAP_PROP_TYPE;
import static com.colonidefeater.game.utils.Constants.MAP_PROP_WIDTH;
import static com.colonidefeater.game.utils.Constants.MAP_PROP_X;
import static com.colonidefeater.game.utils.Constants.MAP_PROP_Y;
import static com.colonidefeater.game.utils.Constants.PPM;
import static com.colonidefeater.game.utils.Constants.SOLDIER_PROP_SHOW;
import static com.colonidefeater.game.utils.Constants.SOLDIER_PROP_BEHAVIOR;

















import java.util.Iterator;

import com.artemis.Entity;
import com.artemis.utils.EntityBuilder;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.colonidefeater.game.component.AnimationCpt;
import com.colonidefeater.game.component.BulletCpt;
import com.colonidefeater.game.component.CollisionCpt;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.PlayerControlled;
import com.colonidefeater.game.component.TypeCpt;
import com.colonidefeater.game.component.WeaponCpt;
import com.colonidefeater.game.component.StateMachineCpt;
import com.colonidefeater.game.component.TextureCpt;
import com.colonidefeater.game.fsm.StateMachine;
import com.colonidefeater.game.fsm.player.PlayerActionState;
import com.colonidefeater.game.fsm.player.PlayerDirectionState;
import com.colonidefeater.game.fsm.player.PlayerMovementState;
import com.colonidefeater.game.fsm.soldier.FollowerSoldierState;
import com.colonidefeater.game.fsm.soldier.IdleSoldierState;
import com.colonidefeater.game.resources.AssetsManager;
import com.colonidefeater.game.utils.Constants;
import com.colonidefeater.game.utils.Direction;
import com.colonidefeater.game.utils.EntityType;
import com.colonidefeater.game.utils.MapBodyBuilder;
import com.colonidefeater.game.weapon.GameWeapons;

public class EntityFactory {

	public static final Entity createGround(com.artemis.World ecsHub,
			World physicsHub, TiledMap map) {

		final MapLayer groundMapLayer = map.getLayers().get(MAP_LAYER_GROUND);
		final MapObjects mapObjects = groundMapLayer.getObjects();

		// -- create player box2d shape
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		final Body body = physicsHub.createBody(bodyDef);

		for (Iterator<MapObject> mapObjectIt = mapObjects.iterator(); mapObjectIt
				.hasNext();) {
			PolylineMapObject ground = (PolylineMapObject) mapObjectIt.next();
			final Shape shape = MapBodyBuilder.createPolyline(ground);
			final FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			body.createFixture(fixtureDef);
			shape.dispose();
		}
		Entity e = new EntityBuilder(ecsHub).with(new TypeCpt(EntityType.GROUND), new PhysicsCpt(body)).build();
		body.setUserData(e); // link entity to body
		return e;
	}
	
	public static Entity createSoldier(com.artemis.World ecsHub,
			World physicsHub, TiledMap map, RectangleMapObject enemyObject) {
		float xpos = (Float) enemyObject.getProperties().get(MAP_PROP_X);
		float ypos = (Float) enemyObject.getProperties().get(MAP_PROP_Y);
		String behavior = (String) enemyObject.getProperties().get(SOLDIER_PROP_BEHAVIOR);
		// create enemy
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.fixedRotation = true;
		bodyDef.position.x = xpos / PPM;
		bodyDef.position.y = ypos / PPM;
		final Body body = physicsHub.createBody(bodyDef);
		final PolygonShape shape = MapBodyBuilder
				.createRectangle(enemyObject);
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.filter.groupIndex = -2;
		body.createFixture(fixtureDef);
		
		// FEET
		float footSize = 5f;
		float boundBoxH = enemyObject.getRectangle().height;
		float boundBoxW = enemyObject.getRectangle().width;
		final CircleShape foot = new CircleShape();
		foot.setRadius(footSize / PPM);
		foot.setPosition(new Vector2((footSize - boundBoxW) / 2 / PPM,
				-boundBoxH / 2 / PPM));
		fixtureDef.shape = foot;
		fixtureDef.isSensor = true;
		fixtureDef.density = 10f;
		body.createFixture(fixtureDef);
		// foot 2
		foot.setPosition(new Vector2((boundBoxW - footSize) / 2 / PPM,
				-boundBoxH / 2 / PPM));
		fixtureDef.shape = foot;
		fixtureDef.isSensor = true;
		fixtureDef.density = 10f;
		body.createFixture(fixtureDef);
		shape.dispose();
		foot.dispose();
				
		// -- create entity
		Entity e = new EntityBuilder(ecsHub)
				.with(new TypeCpt(EntityType.ENEMY),
						new AnimationCpt(AssetsManager.STICK_MAN),
						new PhysicsCpt(body),
						new WeaponCpt(new GameWeapons.SimpleGun()),
						new CollisionCpt()).build();
		StateMachineCpt stateMachine = new StateMachineCpt();
		e.edit().add(stateMachine);
		if (behavior.equals("idle")) {
			stateMachine.add(IdleSoldierState.class, e, IdleSoldierState.STAND_IDLE_SIDE);
		}else if (behavior.equals("follower")) {
			stateMachine.add(FollowerSoldierState.class, e, FollowerSoldierState.WALK_IDLE_SIDE);
		}
		body.setUserData(e); //link entity to body
		return e;
	}

	public static Entity createPlayer(com.artemis.World ecsHub,
			World physicsHub, TiledMap map) {

		final MapLayer playerMapLayer = map.getLayers().get(MAP_LAYER_PLAYER);
		final RectangleMapObject playerMapObject = (RectangleMapObject) playerMapLayer
				.getObjects().get(0);

		float xpos = (Float) playerMapObject.getProperties().get(MAP_PROP_X);
		float ypos = (Float) playerMapObject.getProperties().get(MAP_PROP_Y);

		// -- create player box2d shape
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.fixedRotation = true;
		bodyDef.position.x = xpos / PPM;
		bodyDef.position.y = ypos / PPM;
		final Body body = physicsHub.createBody(bodyDef);
		final PolygonShape shape = MapBodyBuilder
				.createRectangle(playerMapObject);
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		body.createFixture(fixtureDef);

		// FEET
		float footSize = 5f;
		float boundBoxH = playerMapObject.getRectangle().height;
		float boundBoxW = playerMapObject.getRectangle().width;
		final CircleShape foot = new CircleShape();
		foot.setRadius(footSize / PPM);
		foot.setPosition(new Vector2((footSize - boundBoxW) / 2 / PPM,
				-boundBoxH / 2 / PPM));
		fixtureDef.shape = foot;
		fixtureDef.isSensor = true;
		fixtureDef.density = 10f;
		body.createFixture(fixtureDef);
		// foot 2
		foot.setPosition(new Vector2((boundBoxW - footSize) / 2 / PPM,
				-boundBoxH / 2 / PPM));
		fixtureDef.shape = foot;
		fixtureDef.isSensor = true;
		fixtureDef.density = 10f;
		body.createFixture(fixtureDef);
		shape.dispose();
		foot.dispose();
		
		// -- create entity
		Entity e = new EntityBuilder(ecsHub)
				.with(new TypeCpt(EntityType.PLAYER),
						new AnimationCpt(AssetsManager.STICK_MAN),
						new PhysicsCpt(body),
						new WeaponCpt(new GameWeapons.SimpleGun()),
						new PlayerControlled(),
						new CollisionCpt()).tag("PLAYER").build();
		StateMachineCpt stateMachine = new StateMachineCpt();
		e.edit().add(stateMachine);
		stateMachine.add(PlayerMovementState.class, e, PlayerMovementState.STAND);
		stateMachine.add(PlayerActionState.class, e, PlayerActionState.IDLE);
		stateMachine.add(PlayerDirectionState.class, e, PlayerDirectionState.SIDE);
		body.setUserData(e);
		return e;
	}

	// set <to> to null to make it move screen wide
	public static Entity createBullet(Entity owner, float dammage) {
		StateMachineCpt stateCpt = owner.getComponent(StateMachineCpt.class);
		PhysicsCpt physicCpt = owner.getComponent(PhysicsCpt.class);
		Vector2 from = physicCpt.body.getPosition();
		//boolean goLeft = stateCpt.dir == Direction.left;
		//from.y += 0.05;
		//from.x += goLeft ? -0.5f : 0.5f;
		//bullet direction
		Direction dir = stateCpt.dir;
		if (stateCpt.looktoward != Direction.none) {
			dir = stateCpt.looktoward;
		}
		BulletCpt bullet = new BulletCpt(from, dir);
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.bullet = true;
		bodyDef.position.set(bullet.moveFrom);
		final Body body = physicCpt.body.getWorld().createBody(bodyDef);
		Shape shape = new CircleShape();
		shape.setRadius(10 / Constants.PPM);
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.isSensor = true;
		//filter bullet's collision so it doesn't collid with its owner
		switch (owner.getComponent(TypeCpt.class).type) {
		case ENEMY:
			fixtureDef.filter.groupIndex = -2; break;
		default: break;
		}
		body.createFixture(fixtureDef);
		shape.dispose();
		Entity e = new EntityBuilder(owner.getWorld()).with(
				new TypeCpt(EntityType.BULLET),
				new AnimationCpt(AssetsManager.FIRE), new PhysicsCpt(body),
				bullet).build();
		body.setUserData(e); //link entity to body
		return e;
	}

	/**
	 * Create weapon powers on map
	 * 
	 * @param ecsHub
	 * @param physicsHub
	 * @param map
	 * @return
	 */
	public static void createWeaponsPowers(com.artemis.World ecsHub,
			World physicsHub, TiledMap map) {
		/*
		final MapLayer wpsLayer = map.getLayers().get(MAP_LAYER_WEAPONS);
		for (MapObject power : wpsLayer.getObjects()) {
			float x = (Float) power.getProperties().get(MAP_PROP_X);
			float y = (Float) power.getProperties().get(MAP_PROP_Y);
			float width = (Float) power.getProperties().get(MAP_PROP_WIDTH);
			float height = (Float) power.getProperties().get(MAP_PROP_HEIGHT);
			String type = (String) power.getProperties().get(MAP_PROP_TYPE);

			final BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DynamicBody;
			bodyDef.position.set(x / PPM, y / PPM);
			final Body body = physicsHub.createBody(bodyDef);
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(width / 2 / PPM, height / 2 / PPM);
			final FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			fixtureDef.density = 1f;
			fixtureDef.restitution = 0.2f;
			fixtureDef.filter.groupIndex = -2;
			body.createFixture(fixtureDef);
			body.setUserData(type);
			shape.dispose();
			// TODO get type of power and load his sprite
			Entity e = new EntityBuilder(ecsHub).with(new TypeCpt(EntityType.WEAPONPOWER),
					new PhysicsCpt(body),
					new TextureCpt(AssetsManager.WP_H)).build();
			body.setUserData(e); //link entity to body
		}
		*/
	}

}
