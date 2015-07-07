package com.colonidefeater.game.system;

import static com.colonidefeater.game.utils.Constants.PPM;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.colonidefeater.game.component.AnimationCpt;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.StateMachineCpt;

@Wire
public class SpriteRenderSystem extends EntityProcessingSystem {

	private final String tag = getClass().getName();
	private ComponentMapper<AnimationCpt> animationMapper;
	private ComponentMapper<PhysicsCpt> physicsMapper;
	private ComponentMapper<StateMachineCpt> stateMapper;

	// auto wired
	private CameraSystem cameraSystem;

	private SpriteBatch batch;
	private float frameTime = 0;
	private int doFlip = 1;

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

	/**
	 * // process only entities that have a body and an animation
	 */
	@Override
	protected void process(Entity e) {
		final AnimationCpt animationCpt = animationMapper.get(e);
		final PhysicsCpt physics = physicsMapper.get(e);
		final Vector2 pos = physics.body.getPosition();

		String statename = "Default";
		if (stateMapper.has(e)) {
			final StateMachineCpt state = stateMapper.get(e);
			statename = state.stateMachine.getCurrentState().toString();
			doFlip = (state.isLeftSided) ? -1 : 1;
		}

		final TextureRegion sprite = animationCpt.getKeyFrame(statename,
				frameTime);
		float xpos = (pos.x * PPM) - doFlip * sprite.getRegionWidth() / 2;
		float ypos = (pos.y * PPM) - sprite.getRegionHeight() / 2;

		batch.draw(sprite, xpos, ypos, doFlip * sprite.getRegionWidth(),
				sprite.getRegionHeight());
	}

	@Override
	protected void end() {
		batch.end();
	}

}
