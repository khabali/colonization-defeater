package com.colonidefeater.game.system;

import static com.colonidefeater.game.utils.Constants.MAP_LAYER_SOLDIERS;
import static com.colonidefeater.game.utils.Constants.MAP_PROP_X;
import static com.colonidefeater.game.utils.Constants.SOLDIER_PROP_SHOW;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.PlayerControlled;
import com.colonidefeater.game.entity.EntityFactory;
import com.colonidefeater.game.utils.Constants;

public class SoldierSpawnSystem extends VoidEntitySystem {
	
	private TiledMap map;
	private World physicsHub;
	private List<RectangleMapObject> hiddenSoldiers = new ArrayList<RectangleMapObject>();

	public SoldierSpawnSystem(World phy, TiledMap m) {
		this.map = m;
		this.physicsHub = phy;
	}
	
	@Override
	protected void initialize() {
		//get enemies and copy them in a list
		final MapObjects soldiersObjects = map.getLayers().get(MAP_LAYER_SOLDIERS).getObjects();
		Iterator<MapObject> iter = soldiersObjects.iterator();
		while (iter.hasNext())
			hiddenSoldiers.add((RectangleMapObject) iter.next());
		// TODO sort the list
		//hiddenSoldiers.sort(arg0);
	}

	@Override
	protected void processSystem() {
		Entity player = world.getManager(TagManager.class).getEntity("PLAYER");
		Body playerBody = player.getComponent(PhysicsCpt.class).body;
		float playerxpos = playerBody.getPosition().x * Constants.PPM;
		for (int i=0; i<hiddenSoldiers.size(); i++) {
			RectangleMapObject soldierObject = hiddenSoldiers.get(i);
			float showDistance = Float.valueOf((String)soldierObject.getProperties().get(SOLDIER_PROP_SHOW));
			float xpos = (Float) soldierObject.getProperties().get(MAP_PROP_X);
			if (xpos - playerxpos <= showDistance) {
				EntityFactory.createSoldier(world, physicsHub, map, soldierObject);
				hiddenSoldiers.remove(soldierObject);
			}
		}
	}

}
