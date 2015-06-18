package com.colonidefeater.game.system;

import javax.management.RuntimeErrorException;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.StateCpt;
import com.colonidefeater.game.component.TextureCpt;
import com.colonidefeater.game.utils.Constants;

@Wire
public class SpriteRenderSystem extends EntityProcessingSystem {

	private final String tag = getClass().getName();
	private ComponentMapper<TextureCpt> spriteMapper;
	private ComponentMapper<PhysicsCpt> physicsMapper;
	private ComponentMapper<StateCpt> stateMapper;

	// auto wired
	private CameraSystem cameraSystem;

	private SpriteBatch batch;
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
		batch.setProjectionMatrix(cameraSystem.gameCamera.combined);
		final TextureCpt texture = spriteMapper.get(e);
		final PhysicsCpt physics = physicsMapper.get(e);
		boolean doFlip = false;
		if (stateMapper.has(e)) {
			final StateCpt state = stateMapper.get(e);
			texture.createSprites(state.currentState.name());
			if (texture.spriteSet.size == 0) {
				throw new RuntimeException(state.currentState.name() + " state is not supported!");
			}
			doFlip = state.isLeftSided;
		}

		frameTime += Gdx.graphics.getDeltaTime();
		final Vector2 pos = physics.body.getPosition();
		final TextureRegion sprite = new Animation(0.090f, texture.spriteSet).getKeyFrame(frameTime, true);
		sprite.flip(doFlip, false);
		batch.draw(sprite, (pos.x * Constants.PPM) - sprite.getRegionWidth() / 2,
				(pos.y * Constants.PPM) - sprite.getRegionHeight() / 2);
	}

	@Override
	protected void end() {
		batch.end();
	}

}
