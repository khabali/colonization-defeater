package com.colonidefeater.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.StateCpt;
import com.colonidefeater.game.component.TextureCpt;

@Wire
public class SpriteRenderSystem extends EntityProcessingSystem {
	ComponentMapper<TextureCpt> spriteMapper;
	ComponentMapper<PhysicsCpt> physicsMapper;
	ComponentMapper<StateCpt> stateMapper;
	
	//auto wired
	private CameraSystem cameraSystem;
	
	private SpriteBatch batch;
	private  Animation animation;
	private float frameTime = 0;

	public SpriteRenderSystem() {
		super(Aspect.getAspectForAll(TextureCpt.class));
	}

	@Override
	protected void initialize() {
		batch = new SpriteBatch();
	}
	

	@Override
	protected void begin() {
		batch.begin();
	}

	@Override
	// process only entities that have a sprite and a position
	protected void process(Entity e) {
		// set camera
		batch.setProjectionMatrix(cameraSystem.camera.combined);
		TextureCpt texture = spriteMapper.get(e);
		PhysicsCpt physics = physicsMapper.get(e);
		if (stateMapper.has(e)) {
			StateCpt state = stateMapper.get(e);
			texture.createSprites(state.currentState.name());
		}
		frameTime += Gdx.graphics.getDeltaTime();
		Vector2 pos = physics.body.getPosition();
		batch.draw(new Animation(0.090f, texture.spriteSet).getKeyFrame(frameTime, true), pos.x,  pos.y);
	}

	@Override
	protected void end() {
		batch.end();
	}

}
