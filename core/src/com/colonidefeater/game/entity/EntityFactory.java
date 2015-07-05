package com.colonidefeater.game.entity;

import static com.colonidefeater.game.utils.Constants.MAP_LAYER_GROUND;
import static com.colonidefeater.game.utils.Constants.MAP_LAYER_PLAYER;
import static com.colonidefeater.game.utils.Constants.MAP_PROP_X;
import static com.colonidefeater.game.utils.Constants.MAP_PROP_Y;
import static com.colonidefeater.game.utils.Constants.PPM;

import java.util.Iterator;

import com.artemis.Entity;
import com.artemis.utils.EntityBuilder;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.colonidefeater.game.component.AnimationCpt;
import com.colonidefeater.game.component.BulletCpt;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.PlayerControlled;
import com.colonidefeater.game.component.PlayerWeaponCpt;
import com.colonidefeater.game.component.StateMachineCpt;
import com.colonidefeater.game.entity.state.PlayerState;
import com.colonidefeater.game.fsm.StateMachine;
import com.colonidefeater.game.resources.AssetsManager;
import com.colonidefeater.game.utils.Constants;
import com.colonidefeater.game.utils.MapBodyBuilder;

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

		return new EntityBuilder(ecsHub).with(new PhysicsCpt(body)).build();
	}

	public static Entity createPlayer(com.artemis.World ecsHub,
			World physicsHub, TiledMap map) {

		final MapLayer playerMapLayer = map.getLayers().get(MAP_LAYER_PLAYER);
		final EllipseMapObject playerMapObject = (EllipseMapObject) playerMapLayer
				.getObjects().get(0);

		// -- create player box2d shape
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.x = (Float) playerMapObject.getProperties().get(
				MAP_PROP_X)
				/ PPM;
		bodyDef.position.y = (Float) playerMapObject.getProperties().get(
				MAP_PROP_Y)
				/ PPM;
		final Body body = physicsHub.createBody(bodyDef);
		final CircleShape shape = MapBodyBuilder.createEllipse(playerMapObject);
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		// fixtureDef.density = 1.5f;
		// fixtureDef.restitution = 0.4f;
		body.createFixture(fixtureDef);
		shape.dispose();

		// -- create entity
		Entity e = new EntityBuilder(ecsHub)
				.with(new AnimationCpt(AssetsManager.STICK_MAN, "stickman"),
						new PhysicsCpt(body),
						new PlayerWeaponCpt(), new PlayerControlled())
				.tag("PLAYER").build();
		StateMachineCpt stateMachine = new StateMachineCpt(new StateMachine<Entity>(e));
		e.edit().add(stateMachine);
		stateMachine.stateMachine.setInitialState(PlayerState.STAND);
		return e;
	}

	// set <to> to null to make it move screen wide
	public static Entity createBullet(Entity owner, float dammage) {

		StateMachineCpt stateCpt = owner.getComponent(StateMachineCpt.class);
		PhysicsCpt physicCpt = owner.getComponent(PhysicsCpt.class);
		Vector2 from = physicCpt.body.getPosition();
		boolean goLeft = stateCpt.isLeftSided;
		from.y += 0.05;
		from.x += goLeft ? -0.5f : 0.5f;
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.bullet = true;
		bodyDef.position.set(from);
		final Body body = physicCpt.body.getWorld().createBody(bodyDef);
		Shape shape = new CircleShape();
		shape.setRadius(10 / Constants.PPM);
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.isSensor = true;
		body.createFixture(fixtureDef);
		shape.dispose();

		return new EntityBuilder(owner.getWorld()).with(
				new AnimationCpt(AssetsManager.FIRE, "fire"),
				new PhysicsCpt(body), new BulletCpt(from, goLeft))
				.build();
	}

}
