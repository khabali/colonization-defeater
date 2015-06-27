package com.colonidefeater.game.component;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.colonidefeater.game.resources.AssetsManager;

public class AnimationCpt extends Component {

	public TextureAtlas texture;
	public Array<Sprite> spriteSet;
	public String name;
	public Animation animation;
	
	public String curId = ""; //current spriteset id

	public AnimationCpt(String id, String name) {
		texture = AssetsManager.manager.get(id, TextureAtlas.class);
		this.name = name;
	}

	public void updateState(String id) {
		if (!id.equals(curId)) {
			curId = id;
			spriteSet = texture.createSprites(id);
			if (spriteSet.size == 0) {
				throw new RuntimeException(id + " state is not supported in this texture!");
			}
			animation = new Animation(0.090f, spriteSet);
		}
	}

}
