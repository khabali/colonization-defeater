package com.colonidefeater.game.entity;

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
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.colonidefeater.game.World;
import com.colonidefeater.game.component.AnimationCpt;
import com.colonidefeater.game.component.BulletCpt;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.PlayerControlled;
import com.colonidefeater.game.component.StateCpt;
import com.colonidefeater.game.entity.state.IEntityState;
import com.colonidefeater.game.entity.state.StandingState;
import com.colonidefeater.game.resources.AssetsManager;
import com.colonidefeater.game.utils.Constants;
import com.colonidefeater.game.utils.MapBodyBuilder;

public class EntityFactory {

	public static final Entity createGround(World world, TiledMap map) {

		final MapLayer groundMapLayer = map.getLayers().get(
				Constants.MAP_LAYER_GROUND);
		final MapObjects mapObjects = groundMapLayer.getObjects();

		// -- create player box2d shape
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		final Body body = world.createBody(bodyDef);
		
		for (Iterator<MapObject> mapObjectIt = mapObjects.iterator(); mapObjectIt
				.hasNext();) {
			PolylineMapObject ground = (PolylineMapObject) mapObjectIt.next();
				final Shape shape = MapBodyBuilder.createPolyline(ground);
			final FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			fixtureDef.friction = 0.3f;
			body.createFixture(fixtureDef);
			shape.dispose();
		}

		return new EntityBuilder(world.ecsHub).with(new PhysicsCpt(body))
				.build();
	}

	public static Entity createPlayer(World world, TiledMap map) {

		final MapLayer playerMapLayer = map.getLayers().get(
				Constants.MAP_LAYER_PLAYER);
		final EllipseMapObject playerMapObject = (EllipseMapObject) playerMapLayer
				.getObjects().get(0);

		// -- create player box2d shape
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.x = (Float) playerMapObject.getProperties().get(
				Constants.MAP_PROPERTIE_X)
				/ Constants.PPM;
		bodyDef.position.y = (Float) playerMapObject.getProperties().get(
				Constants.MAP_PROPERTIE_Y)
				/ Constants.PPM;
		final Body body = world.createBody(bodyDef);
		final CircleShape shape = MapBodyBuilder.createEllipse(playerMapObject);
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		//fixtureDef.density = 1000f;
		// fixtureDef.restitution = 0.4f;
		body.createFixture(fixtureDef);
		shape.dispose();

		// -- create entity
		return new EntityBuilder(world.ecsHub)
				.with(new AnimationCpt(AssetsManager.STICK_MAN, "stickman"),
						new PhysicsCpt(body),
						new StateCpt(IEntityState.standingState),
						new PlayerControlled()).tag("PLAYER").build();
	}
	
	// set <to> to null to make it move screen wide
	public static Entity createBullet(World world, Vector2 from, boolean goLeft) {
		//adjust position
		from.y += 0.1;
		from.x += goLeft ? -0.5f : 0.5f;
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.bullet = true;
		bodyDef.position.set(from);
		final Body body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(25/Constants.PPM, 24/Constants.PPM);
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		//fixtureDef.density = 1000f;
		//fixtureDef.restitution = 1.0f;
		//fixtureDef.friction = 0.0f;
		fixtureDef.isSensor = true;
		body.createFixture(fixtureDef);
		shape.dispose();

		return new EntityBuilder(world.ecsHub)
				.with(new AnimationCpt(AssetsManager.FIRE, "fire"),
						new PhysicsCpt(body),
						new BulletCpt(from, goLeft))
				.build();
	}

}
