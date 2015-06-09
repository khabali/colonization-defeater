package com.colonidefeater.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.colonidefeater.game.World;
import com.colonidefeater.game.entity.EntityFactory;
import com.colonidefeater.game.input.GameInput;
import com.colonidefeater.game.resources.AssetsManager;
import com.colonidefeater.game.system.CameraSystem;
import com.colonidefeater.game.system.SpriteRenderSystem;

public class Level1GameState extends GameStateAdapter {

	private World world;


	public Level1GameState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	@Override
	public void init() {
		world = new World(new Vector2(0, -10), true);	
		world.setSystem(new CameraSystem());
		world.setSystem(new SpriteRenderSystem());
		world.initialize();
		
		EntityFactory.createTestEntity(world);
	}

	@Override
	public void update() {
		handleInput();
	}

	@Override
	public void draw() {
		world.process(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void handleInput() {
		// Return to menu if escape is pressed
		if (GameInput.getInstance().getEscapeButton().isPressed()) {
			gsm.setState(GameStateManager.STATE_MENU);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
	}

}
