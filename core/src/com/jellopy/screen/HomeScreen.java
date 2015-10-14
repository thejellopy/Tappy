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
 * The Home Screen
 *
 * @author Jellopy
 */
public class HomeScreen extends ScreenAdapter {
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
	private Sprite homeBackground;

	private Sprite homeStartButtonUp;
	private Sprite homeStartButtonOver;
	private Sprite homeStartButtonDown;

	private Sprite homeSettingButtonUp;
	private Sprite homeSettingButtonOver;
	private Sprite homeSettingButtonDown;

	// Buttons.
	private ImageButton homeStartButton;
	private ImageButton homeSettingButton;

	// HomeScreen variable.
	private Stage homeNormalStage;

	/**
	 * Home Screen Constructor.
	 *
	 * @param tappy
	 */
	public HomeScreen(Tappy tappy) {
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
		homeBackground = Assets.getSprite("homeBackground");

		homeStartButtonUp = Assets.getSprite("homeStartButtonUp");
		homeStartButtonOver = Assets.getSprite("homeStartButtonOver");
		homeStartButtonDown = Assets.getSprite("homeStartButtonDown");

		homeSettingButtonUp = Assets.getSprite("homeSettingButtonUp");
		homeSettingButtonOver = Assets.getSprite("homeSettingButtonOver");
		homeSettingButtonDown = Assets.getSprite("homeSettingButtonDown");

		// Styling homeStartButton.
		ImageButtonStyle homeStartButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		homeStartButtonStyle.imageUp = new SpriteDrawable(homeStartButtonUp);
		homeStartButtonStyle.imageOver = new SpriteDrawable(homeStartButtonOver);
		homeStartButtonStyle.imageDown = new SpriteDrawable(homeStartButtonDown);

		// Initialize homeStartButton.
		homeStartButton = new ImageButton(homeStartButtonStyle);
		homeStartButton.setPosition(388, 88);
		homeStartButton.setColor(homeStartButton.getColor().r, homeStartButton.getColor().g, homeStartButton.getColor().b, 0);

		// Styling homeSettingButton.
		ImageButtonStyle homeSettingButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		homeSettingButtonStyle.imageUp = new SpriteDrawable(homeSettingButtonUp);
		homeSettingButtonStyle.imageOver = new SpriteDrawable(homeSettingButtonOver);
		homeSettingButtonStyle.imageDown = new SpriteDrawable(homeSettingButtonDown);

		// Initialize homeSettingButton.
		homeSettingButton = new ImageButton(homeSettingButtonStyle);
		homeSettingButton.setPosition(901, 476);
		homeSettingButton.setColor(homeSettingButton.getColor().r, homeSettingButton.getColor().g, homeSettingButton.getColor().b, 0);

		// Initialize homeScreen variable.
		homeNormalStage = new Stage();

		// Register tweenEngine timeline.
		Timeline.createSequence()
			// Set default values.
			.push(Tween.set(homeBackground, SpriteAccessor.ALPHA).target(0))
			.push(Tween.set(homeStartButton, ImageButtonAccessor.ALPHA).target(0))
			.push(Tween.set(homeSettingButton, ImageButtonAccessor.ALPHA).target(0))
			// FadeIn.
			.beginParallel()
				.push(Tween.to(homeBackground, SpriteAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(homeStartButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(homeSettingButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
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
	 * Launch homeScreen.
	 */
	@Override
	public void show() {
		// Play sharedBackgroundMusic
		sharedBackgroundMusic.setLooping(true);
		sharedBackgroundMusic.setVolume(tappy.getPreferences().getInteger("settingMusicVolume", 10) / 10f);
		sharedBackgroundMusic.play();

		// Switch to normal stage.
		tappy.setStage(homeNormalStage);

		// Add clickListener to homeStartButton.
		homeStartButton.addListener(new ClickListener() {
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
						.push(Tween.to(homeBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(homeStartButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(homeSettingButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.end()
					// Register callback.
					.setCallback(new TweenCallback() {
						@Override
						public void onEvent(int type, BaseTween<?> source) {
							sharedInputEnable = true;
							tappy.setScreen(new SelectScreen(tappy));
						}
					})
				// Launch.
				.start(sharedTweenManager);
			}
		});

		// Register homeStartButton to stage actor.
		tappy.getStage().addActor(homeStartButton);

		// Add clickListener to homeSettingButton.
		homeSettingButton.addListener(new ClickListener() {
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
						.push(Tween.to(homeBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(homeStartButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(homeSettingButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.end()
					// Register callback.
					.setCallback(new TweenCallback() {
						@Override
						public void onEvent(int type, BaseTween<?> source) {
							sharedInputEnable = true;
							tappy.setScreen(new SettingScreen(tappy));
						}
					})
				// Launch
				.start(sharedTweenManager);
			}
		});

		// Register homeSettingButton to stage actor.
		tappy.getStage().addActor(homeSettingButton);

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
		tappy.setStage(homeNormalStage);
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
			homeBackground.draw(tappy.getSpriteBatch());
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
