package com.colonidefeater.game.component;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.colonidefeater.game.resources.AssetsManager;

public class TextureCpt extends Component {

	public TextureAtlas texture;
	public Array<Sprite> spriteSet;
	public String name;

	public TextureCpt(String id, String name) {
		texture = AssetsManager.manager.get(id, TextureAtlas.class);
		this.name = name;
		//createSprites("DEFAULT");
	}

	public void createSprites(String id) {
		spriteSet = texture.createSprites(id);
	}

}
