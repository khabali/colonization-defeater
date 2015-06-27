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
import com.colonidefeater.game.component.AnimationCpt;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.PlayerControlled;
import com.colonidefeater.game.component.StateCpt;
import com.colonidefeater.game.utils.Constants;

@Wire
public class SpriteRenderSystem extends EntityProcessingSystem {

	private final String tag = getClass().getName();
	private ComponentMapper<AnimationCpt> animationMapper;
	private ComponentMapper<PhysicsCpt> physicsMapper;
	private ComponentMapper<StateCpt> stateMapper;

	// auto wired
	private CameraSystem cameraSystem;

	private SpriteBatch batch;
	private float frameTime = 0;

	@SuppressWarnings("unchecked")
	public SpriteRenderSystem() {
		super(Aspect.getAspectForAll(AnimationCpt.class, PhysicsCpt.class));
	}

	@Override
	protected void initialize() {
		batch = new SpriteBatch();
	}

	@Override
	protected void begin() {
		frameTime += Gdx.graphics.getDeltaTime();	
		batch.setProjectionMatrix(cameraSystem.gameCamera.combined);
		batch.begin();
	}

	@Override
	// process only entities that have a sprite and a position
	protected void process(Entity e) {
		final AnimationCpt animationCpt = animationMapper.get(e);
		final PhysicsCpt physics = physicsMapper.get(e);
		final Vector2 pos = physics.body.getPosition();
		boolean doFlip = false;
		
		String statename = "Default";
		if (stateMapper.has(e)) {
			final StateCpt state = stateMapper.get(e);
			statename = state.currentState.name();
			doFlip = state.isLeftSided;
		}
		
		animationCpt.updateState(statename);
			
		final TextureRegion sprite = animationCpt.animation.getKeyFrame(frameTime, true);	
		float xpos = (pos.x * Constants.PPM) - sprite.getRegionWidth() / 2;
		if (doFlip) xpos += sprite.getRegionWidth();
		
		batch.draw(sprite, xpos, (pos.y * Constants.PPM) - sprite.getRegionHeight() / 2,
				doFlip ? -sprite.getRegionWidth() : sprite.getRegionWidth(), sprite.getRegionHeight());
	}

	@Override
	protected void end() {
		batch.end();
	}

}
