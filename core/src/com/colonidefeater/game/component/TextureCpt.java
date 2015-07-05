package com.colonidefeater.game.component;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Texture;
import com.colonidefeater.game.resources.AssetsManager;

public class TextureCpt extends Component{

	public Texture texture;

	public TextureCpt(String resourceId) {
		texture = AssetsManager.manager.get(resourceId);
	}
}
