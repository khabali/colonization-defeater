package com.colonidefeater.game.component;

import java.util.HashMap;
import java.util.Map;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.colonidefeater.game.resources.AssetsManager;

public class AnimationCpt extends Component {

	public TextureAtlas texture;
	public Array<Sprite> spriteSet;
	public Map<String, Animation> animations;

	public String curId = ""; // current spriteset id

	public AnimationCpt(String id) {
		texture = AssetsManager.manager.get(id, TextureAtlas.class);

		animations = new HashMap<String, Animation>();
	}

	public TextureRegion getKeyFrame(String id, float frameTime) {
		if (!animations.containsKey(id)) {
			curId = id;
			spriteSet = texture.createSprites(id);
			if (spriteSet.size == 0) {
				throw new RuntimeException(id
						+ " state is not supported in this texture!");
			}
			animations.put(id, new Animation(0.090f, spriteSet));
		}

		return animations.get(id).getKeyFrame(frameTime, true);
	}

}
