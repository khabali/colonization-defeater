package com.colonidefeater.game.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.colonidefeater.game.component.PhysicsCpt;
import com.colonidefeater.game.component.TextureCpt;
import com.colonidefeater.game.utils.Constants;

@Wire
public class TextureRenderSystem extends EntityProcessingSystem {

	private final String tag = getClass().getName();
	private ComponentMapper<TextureCpt> textureCptMapper;
	private ComponentMapper<PhysicsCpt> physicsMapper;

	// auto wired
	private CameraSystem cameraSystem;

	private SpriteBatch batch;

	@SuppressWarnings("unchecked")
	public TextureRenderSystem() {
		super(Aspect.getAspectForAll(TextureCpt.class, PhysicsCpt.class));
	}

	@Override
	protected void initialize() {
		batch = new SpriteBatch();
	}

	@Override
	protected void begin() {
		batch.setProjectionMatrix(cameraSystem.gameCamera.combined);
		batch.begin();
	}

	@Override
	protected void process(Entity e) {
		final TextureCpt textureCpt = textureCptMapper.get(e);
		final PhysicsCpt physics = physicsMapper.get(e);
		final Vector2 pos = physics.body.getPosition();

		final Texture sprite = textureCpt.texture;
		float xpos = (pos.x * Constants.PPM) - sprite.getWidth() / 2;
		float ypos = (pos.y * Constants.PPM) - sprite.getHeight() / 2;

		batch.draw(new TextureRegion(sprite), xpos, ypos,
				sprite.getWidth() / 2, sprite.getHeight() / 2,
				sprite.getWidth(), sprite.getHeight(), 1f, 1f,
				physics.body.getAngle() * MathUtils.radiansToDegrees);

	}

	@Override
	protected void end() {
		batch.end();
	}

}
