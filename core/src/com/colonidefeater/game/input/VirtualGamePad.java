package com.colonidefeater.game.input;

import static com.colonidefeater.game.resources.AssetsManager.BUTTON_FIRE;
import static com.colonidefeater.game.resources.AssetsManager.BUTTON_JUMP;
import static com.colonidefeater.game.resources.AssetsManager.BUTTON_LEFT;
import static com.colonidefeater.game.resources.AssetsManager.BUTTON_LEFT__HOVER;
import static com.colonidefeater.game.resources.AssetsManager.BUTTON_RIGHT;
import static com.colonidefeater.game.resources.AssetsManager.BUTTON_RIGHT_HOVER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.colonidefeater.game.resources.AssetsManager;

public class VirtualGamePad {

	private static final float MAX_FPS = 1 / 30f;
	private Stage stage;

	// button
	private ImageButton leftButton;
	private ImageButton rightButton;
	private ImageButton fireButton;
	private ImageButton jumpButton;

	//
	private static final int BUTTON_SIZE = 64 * 2;
	private static final int BUTTON_PADING = 25;

	// Skins name
	private static final String SKIN_LEFT = "skinLeft";
	private static final String SKIN_RIGHT = "skinRight";
	private static final String SKIN_FIRE = "skinFire";
	private static final String SKIN_JUMP = "skinJump";

	public VirtualGamePad() {

		stage = new Stage();

		// Get texture image for game pad buttons
		Texture leftImage = AssetsManager.manager.get(BUTTON_LEFT);
		Texture leftImageHover = AssetsManager.manager.get(BUTTON_LEFT__HOVER);
		Texture rightImage = AssetsManager.manager.get(BUTTON_RIGHT);
		Texture rightImageHover = AssetsManager.manager.get(BUTTON_RIGHT_HOVER);
		Texture fireImage = AssetsManager.manager.get(BUTTON_FIRE);
		Texture jumpImage = AssetsManager.manager.get(BUTTON_JUMP);

		// -- Skins
		Skin skin_left = createSkin(SKIN_LEFT, leftImage, leftImageHover);
		Skin skin_right = createSkin(SKIN_RIGHT, rightImage, rightImageHover);
		Skin skin_fire = createSkin(SKIN_FIRE, fireImage, fireImage);
		Skin skin_jump = createSkin(SKIN_JUMP, jumpImage, jumpImage);

		// Create left table for direction buttons
		Table leftTable = new Table();
		leftTable.setFillParent(true);
		leftTable.bottom();
		leftTable.left();
		leftTable.pad(30);
		stage.addActor(leftTable);

		// create right table for actions buttons
		Table rightTable = new Table();
		rightTable.setFillParent(true);
		rightTable.bottom();
		rightTable.right();
		rightTable.pad(30);
		stage.addActor(rightTable);

		// Create a button with the "default" TextButtonStyle. A 3rd parameter
		// can be used to specify a name other than "default".
		leftButton = new ImageButton(skin_left);
		rightButton = new ImageButton(skin_right);
		fireButton = new ImageButton(skin_fire);
		jumpButton = new ImageButton(skin_jump);

		// Touchpad touchpad = new Touchpad(5, skin);
		leftTable.add(leftButton).size(BUTTON_SIZE).pad(BUTTON_PADING);
		leftTable.add(rightButton).size(BUTTON_SIZE).pad(BUTTON_PADING);
		rightTable.add(fireButton).size(BUTTON_SIZE).pad(BUTTON_PADING);
		rightTable.add(jumpButton).size(BUTTON_SIZE).pad(BUTTON_PADING);

		// Add click listener on buttons
		leftButton.addListener(new ClickListener());
	}

	private Skin createSkin(String name, Texture image, Texture imageHover) {
		// skin left
		String nameHover = name + "Hover";
		Skin skin = new Skin();
		skin.add(name, image);
		skin.add(nameHover, imageHover);
		ImageButtonStyle buttonStyle = new ImageButtonStyle();
		buttonStyle.up = skin.newDrawable(name);
		buttonStyle.down = skin.newDrawable(nameHover);
		buttonStyle.over = skin.newDrawable(nameHover);
		skin.add("default", buttonStyle);

		return skin;
	}

	public void aupdateAndDraw() {
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), MAX_FPS));
		stage.draw();

		GameInput.setVKeyState(GameInput.LEFT, leftButton.isPressed());
		GameInput.setVKeyState(GameInput.RIGHT, rightButton.isPressed());
		GameInput.setVKeyState(GameInput.JUMP, jumpButton.isPressed());
		GameInput.setVKeyState(GameInput.FIRE, fireButton.isPressed());
	}

	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	public void dispose() {
		stage.dispose();
	}

	public Stage getInputProcessor() {
		return stage;
	}

}
