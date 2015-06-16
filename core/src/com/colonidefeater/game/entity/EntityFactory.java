package com.colonidefeater.game.entity;

import com.artemis.Entity;
import com.artemis.utils.EntityBuilder;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.colonidefeater.game.World;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.PlayerControlled;
import com.colonidefeater.game.component.TextureCpt;
import com.colonidefeater.game.resources.AssetsManager;
import com.colonidefeater.game.utils.Constants;
import com.colonidefeater.game.utils.MapBodyBuilder;

public class EntityFactory {

	public static final Entity createGround(World world, TiledMap map) {

		final MapLayer groundMapLayer = map.getLayers().get(Constants.MAP_LAYER_GROUND);
		final PolylineMapObject groundMapObject = (PolylineMapObject) groundMapLayer.getObjects().get(0);

		// -- create player box2d shape
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		final Body body = world.createBody(bodyDef);
		final Shape shape = MapBodyBuilder.createPolyline(groundMapObject);
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.friction = 0.3f;
		body.createFixture(fixtureDef);
		shape.dispose();

		return new EntityBuilder(world.ecsHub).with(new PhysicsCpt(body)).build();
	}

	public static Entity createPlayer(World world, TiledMap map) {

		final MapLayer playerMapLayer = map.getLayers().get(Constants.MAP_LAYER_PLAYER);
		final EllipseMapObject playerMapObject = (EllipseMapObject) playerMapLayer.getObjects().get(0);

		// -- create player box2d shape
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.x = (Float) playerMapObject.getProperties().get(Constants.MAP_PROPERTIE_X) / Constants.PPM;
		bodyDef.position.y = (Float) playerMapObject.getProperties().get(Constants.MAP_PROPERTIE_Y) / Constants.PPM;
		final Body body = world.createBody(bodyDef);
		final CircleShape shape = MapBodyBuilder.createEllipse(playerMapObject);
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1.5f;
		fixtureDef.restitution = 0.4f;
		body.createFixture(fixtureDef);
		shape.dispose();

		// -- create entity
		return new EntityBuilder(world.ecsHub).with(new TextureCpt(AssetsManager.SOCCER_BALL, "soccerball"),
				new PhysicsCpt(body), new PlayerControlled()).build();
	}

}
