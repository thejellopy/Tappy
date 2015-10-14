package com.jellopy.screen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.jellopy.Assets;
import com.jellopy.Tappy;
import com.jellopy.accessor.ImageButtonAccessor;
import com.jellopy.accessor.SpriteAccessor;

/**
 * The Credit Screen
 *
 * @author Jellopy
 */
public class CreditScreen extends ScreenAdapter {
	// Core variable.
	private Tappy tappy;

	// Input status.
	private boolean sharedInputEnable;

	// Camera viewport.
	private OrthographicCamera sharedOrthographicCamera;

	// Tween controller.
	private TweenManager sharedTweenManager;

	// Musics.
	private Music sharedBackgroundMusic;

	// Sounds.
	private Sound sharedButtonDownSound;

	// Sprites.
	private Sprite creditBackground;

	private Sprite creditHomeButtonUp;
	private Sprite creditHomeButtonOver;
	private Sprite creditHomeButtonDown;

	// Buttons.
	private ImageButton creditHomeButton;

	// creditScreen variable.
	private Stage creditNormalStage;

	/**
	 * Credit Screen Constructor.
	 *
	 * @param tappy
	 */
	public CreditScreen(Tappy tappy) {
		// Core Variable.
		this.tappy = tappy;

		// Disable button while animating.
		sharedInputEnable = false;

		// Set normal camera.
		sharedOrthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		sharedOrthographicCamera.position.set(sharedOrthographicCamera.viewportWidth / 2f, sharedOrthographicCamera.viewportHeight / 2f, 0);
		sharedOrthographicCamera.update();

		// Initialize TweenManager.
		sharedTweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Tween.registerAccessor(ImageButton.class, new ImageButtonAccessor());

		// Get sounds.
		sharedButtonDownSound = Assets.getSound("sharedButtonDownSound");

		// Get musics.
		sharedBackgroundMusic = Assets.getMusic("sharedBackgroundMusic");

		// Get sprites.
		creditBackground = Assets.getSprite("creditBackground");

		creditHomeButtonUp = Assets.getSprite("creditHomeButtonUp");
		creditHomeButtonOver = Assets.getSprite("creditHomeButtonOver");
		creditHomeButtonDown = Assets.getSprite("creditHomeButtonDown");

		// Styling creditHomeButton.
		ImageButtonStyle creditHomeButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		creditHomeButtonStyle.imageUp = new SpriteDrawable(creditHomeButtonUp);
		creditHomeButtonStyle.imageOver = new SpriteDrawable(creditHomeButtonOver);
		creditHomeButtonStyle.imageDown = new SpriteDrawable(creditHomeButtonDown);

		// Initialize creditHomeButton.
		creditHomeButton = new ImageButton(creditHomeButtonStyle);
		creditHomeButton.setPosition(901, 484);
		creditHomeButton.setColor(creditHomeButton.getColor().r, creditHomeButton.getColor().g, creditHomeButton.getColor().b, 0);

		// Initialize homeScreen variable.
		creditNormalStage = new Stage();

		// Register tweenEngine timeline.
		Timeline.createSequence()
			// Set default values.
			.push(Tween.set(creditBackground, SpriteAccessor.ALPHA).target(0))
			.push(Tween.set(creditHomeButton, ImageButtonAccessor.ALPHA).target(0))
			// FadeIn.
			.beginParallel()
				.push(Tween.to(creditBackground, SpriteAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(creditHomeButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
			.end()
			// Register callback.
			.setCallback(new TweenCallback() {
				@Override
				public void onEvent(int type, BaseTween<?> source) {
					sharedInputEnable = true;
				}
			})
		// Launch.
		.start(sharedTweenManager);
	}

	/**
	 * Launch creditScreen.
	 */
	@Override
	public void show() {
		// Play sharedBackgroundMusic
		sharedBackgroundMusic.setLooping(true);
		sharedBackgroundMusic.setVolume(tappy.getPreferences().getInteger("settingMusicVolume", 10) / 10f);
		sharedBackgroundMusic.play();

		// Switch to normal stage.
		tappy.setStage(creditNormalStage);

		// Add clickListener to creditHomeButton.
		creditHomeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				sharedInputEnable = false;
				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);

				// Register tweenEngine timeline.
				Timeline.createSequence()
					// FadeOut.
					.beginParallel()
						.push(Tween.to(creditBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(creditHomeButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.end()
					// Register callback.
					.setCallback(new TweenCallback() {
						@Override
						public void onEvent(int type, BaseTween<?> source) {
							sharedInputEnable = true;
							tappy.setScreen(new HomeScreen(tappy));
						}
					})
				// Launch
				.start(sharedTweenManager);
			}
		});

		// Register creditHomeButton to stage actor.
		tappy.getStage().addActor(creditHomeButton);

		// Bind inputProcessor with stage.
		Gdx.input.setInputProcessor(tappy.getStage());
	}

	/**
	 * Update everything. Called by render()
	 *
	 * @param delta
	 */
	private void update(float delta) {
		// Update normal camera.
		sharedOrthographicCamera.update();

		// Update tween engine.
		sharedTweenManager.update(delta);

		// Update stage action.
		tappy.setStage(creditNormalStage);
		tappy.getStage().act();
	}

	/**
	 * Draw everything. Called by render()
	 */
	private void draw() {
		// Clear screen.
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		// Apply normal camera.
		tappy.getSpriteBatch().setProjectionMatrix(sharedOrthographicCamera.combined);
		tappy.getSpriteBatch().begin();
			// Draw background.
			creditBackground.draw(tappy.getSpriteBatch());
		tappy.getSpriteBatch().end();

		tappy.getSpriteBatch().begin();
			// Draw button.
			tappy.getStage().draw();
		tappy.getSpriteBatch().end();
	}

	/**
	 * Self-update & Draw
	 */
	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}
}
