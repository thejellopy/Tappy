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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.jellopy.Assets;
import com.jellopy.Tappy;
import com.jellopy.accessor.BitmapFontAccessor;
import com.jellopy.accessor.ImageButtonAccessor;
import com.jellopy.accessor.SpriteAccessor;

/**
 * The Setting Screen
 *
 * @author Jellopy
 */
public class SettingScreen extends ScreenAdapter {
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
	private Sprite settingBackground;

	private Sprite settingHomeButtonUp;
	private Sprite settingHomeButtonOver;
	private Sprite settingHomeButtonDown;

	private Sprite settingCreditButtonUp;
	private Sprite settingCreditButtonOver;
	private Sprite settingCreditButtonDown;

	private Sprite settingMinusButtonUp;
	private Sprite settingMinusButtonOver;
	private Sprite settingMinusButtonDown;

	private Sprite settingPlusButtonUp;
	private Sprite settingPlusButtonOver;
	private Sprite settingPlusButtonDown;

	// Buttons.
	private ImageButton settingHomeButton;
	private ImageButton settingCreditButton;

	private ImageButton settingMusicMinusButton;
	private ImageButton settingMusicPlusButton;

	private ImageButton settingButtonMinusButton;
	private ImageButton settingButtonPlusButton;

	private ImageButton settingSpeedMinusButton;
	private ImageButton settingSpeedPlusButton;

	// Fonts.
	private BitmapFont settingSpeedFont;

	// HomeScreen variable.
	private Stage settingNormalStage;

	/**
	 * Setting Screen Constructor.
	 *
	 * @param tappy
	 */
	public SettingScreen(Tappy tappy) {
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
		Tween.registerAccessor(BitmapFont.class, new BitmapFontAccessor());

		// Get sounds.
		sharedButtonDownSound = Assets.getSound("sharedButtonDownSound");

		// Get musics.
		sharedBackgroundMusic = Assets.getMusic("sharedBackgroundMusic");

		// Get sprites.
		settingBackground = Assets.getSprite("settingBackground");

		settingHomeButtonUp = Assets.getSprite("settingHomeButtonUp");
		settingHomeButtonOver = Assets.getSprite("settingHomeButtonOver");
		settingHomeButtonDown = Assets.getSprite("settingHomeButtonDown");

		settingCreditButtonUp = Assets.getSprite("settingCreditButtonUp");
		settingCreditButtonOver = Assets.getSprite("settingCreditButtonOver");
		settingCreditButtonDown = Assets.getSprite("settingCreditButtonDown");

		settingMinusButtonUp = Assets.getSprite("settingMinusButtonUp");
		settingMinusButtonOver = Assets.getSprite("settingMinusButtonOver");
		settingMinusButtonDown = Assets.getSprite("settingMinusButtonDown");

		settingPlusButtonUp = Assets.getSprite("settingPlusButtonUp");
		settingPlusButtonOver = Assets.getSprite("settingPlusButtonOver");
		settingPlusButtonDown = Assets.getSprite("settingPlusButtonDown");

		// Styling settingHomeButton.
		ImageButtonStyle settingHomeButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		settingHomeButtonStyle.imageUp = new SpriteDrawable(settingHomeButtonUp);
		settingHomeButtonStyle.imageOver = new SpriteDrawable(settingHomeButtonOver);
		settingHomeButtonStyle.imageDown = new SpriteDrawable(settingHomeButtonDown);

		// Initialize settingHomeButton.
		settingHomeButton = new ImageButton(settingHomeButtonStyle);
		settingHomeButton.setPosition(901, 484);
		settingHomeButton.setColor(settingHomeButton.getColor().r, settingHomeButton.getColor().g, settingHomeButton.getColor().b, 0);

		// Styling settingCreditButton.
		ImageButtonStyle settingCreditButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		settingCreditButtonStyle.imageUp = new SpriteDrawable(settingCreditButtonUp);
		settingCreditButtonStyle.imageOver = new SpriteDrawable(settingCreditButtonOver);
		settingCreditButtonStyle.imageDown = new SpriteDrawable(settingCreditButtonDown);

		// Initialize settingCreditButton.
		settingCreditButton = new ImageButton(settingCreditButtonStyle);
		settingCreditButton.setPosition(11, 14);
		settingCreditButton.setColor(settingCreditButton.getColor().r, settingCreditButton.getColor().g, settingCreditButton.getColor().b, 0);

		// Styling settingMinusButton.
		ImageButtonStyle settingMinusButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		settingMinusButtonStyle.imageUp = new SpriteDrawable(settingMinusButtonUp);
		settingMinusButtonStyle.imageOver = new SpriteDrawable(settingMinusButtonOver);
		settingMinusButtonStyle.imageDown = new SpriteDrawable(settingMinusButtonDown);

		// Initialize settingMusicMinusButton.
		settingMusicMinusButton = new ImageButton(settingMinusButtonStyle);
		settingMusicMinusButton.setPosition(241, 322);
		settingMusicMinusButton.setColor(settingMusicMinusButton.getColor().r, settingMusicMinusButton.getColor().g, settingMusicMinusButton.getColor().b, 0);

		// Initialize settingButtonMinusButton.
		settingButtonMinusButton = new ImageButton(settingMinusButtonStyle);
		settingButtonMinusButton.setPosition(535, 322);
		settingButtonMinusButton.setColor(settingButtonMinusButton.getColor().r, settingButtonMinusButton.getColor().g, settingButtonMinusButton.getColor().b, 0);

		// Initialize settingSpeedMinusButton.
		settingSpeedMinusButton = new ImageButton(settingMinusButtonStyle);
		settingSpeedMinusButton.setPosition(388, 134);
		settingSpeedMinusButton.setColor(settingSpeedMinusButton.getColor().r, settingSpeedMinusButton.getColor().g, settingSpeedMinusButton.getColor().b, 0);

		// Styling settingPlusButton.
		ImageButtonStyle settingPlusButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		settingPlusButtonStyle.imageUp = new SpriteDrawable(settingPlusButtonUp);
		settingPlusButtonStyle.imageOver = new SpriteDrawable(settingPlusButtonOver);
		settingPlusButtonStyle.imageDown = new SpriteDrawable(settingPlusButtonDown);

		// Initialize settingMusicPlusButton.
		settingMusicPlusButton = new ImageButton(settingPlusButtonStyle);
		settingMusicPlusButton.setPosition(380, 322);
		settingMusicPlusButton.setColor(settingMusicPlusButton.getColor().r, settingMusicPlusButton.getColor().g, settingMusicPlusButton.getColor().b, 0);

		// Initialize settingButtonPlusButton.
		settingButtonPlusButton = new ImageButton(settingPlusButtonStyle);
		settingButtonPlusButton.setPosition(674, 322);
		settingButtonPlusButton.setColor(settingButtonPlusButton.getColor().r, settingButtonPlusButton.getColor().g, settingButtonPlusButton.getColor().b, 0);

		// Initialize settingSpeedPlusButton.
		settingSpeedPlusButton = new ImageButton(settingPlusButtonStyle);
		settingSpeedPlusButton.setPosition(527, 134);
		settingSpeedPlusButton.setColor(settingSpeedPlusButton.getColor().r, settingSpeedPlusButton.getColor().g, settingSpeedPlusButton.getColor().b, 0);

		// Get fonts.
		FreeTypeFontParameter settingSpeedFontParameter = new FreeTypeFontParameter();
		settingSpeedFontParameter.size = 60;
		settingSpeedFont = Assets.getFont("codeBoldFont").generateFont(settingSpeedFontParameter);
		settingSpeedFont.setColor(1f, 1f, 1f, 0f);

		// Initialize homeScreen variable.
		settingNormalStage = new Stage();

		// Register tweenEngine timeline.
		Timeline.createSequence()
			// Set default values.
			.push(Tween.set(settingBackground, SpriteAccessor.ALPHA).target(0))
			.push(Tween.set(settingHomeButton, ImageButtonAccessor.ALPHA).target(0))
			.push(Tween.set(settingCreditButton, ImageButtonAccessor.ALPHA).target(0))
			.push(Tween.set(settingMusicMinusButton, ImageButtonAccessor.ALPHA).target(0))
			.push(Tween.set(settingMusicPlusButton, ImageButtonAccessor.ALPHA).target(0))
			.push(Tween.set(settingButtonMinusButton, ImageButtonAccessor.ALPHA).target(0))
			.push(Tween.set(settingButtonPlusButton, ImageButtonAccessor.ALPHA).target(0))
			.push(Tween.set(settingSpeedMinusButton, ImageButtonAccessor.ALPHA).target(0))
			.push(Tween.set(settingSpeedPlusButton, ImageButtonAccessor.ALPHA).target(0))
			.push(Tween.set(settingSpeedFont, BitmapFontAccessor.ALPHA).target(0))
			// FadeIn.
			.beginParallel()
				.push(Tween.to(settingBackground, SpriteAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(settingHomeButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(settingCreditButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(settingMusicMinusButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(settingMusicPlusButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(settingButtonMinusButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(settingButtonPlusButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(settingSpeedMinusButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(settingSpeedPlusButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(settingSpeedFont, BitmapFontAccessor.ALPHA, 1.2f).target(1))
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
	 * Launch settingScreen.
	 */
	@Override
	public void show() {
		// Play sharedBackgroundMusic
		sharedBackgroundMusic.setLooping(true);
		sharedBackgroundMusic.setVolume(tappy.getPreferences().getInteger("settingMusicVolume", 10) / 10f);
		sharedBackgroundMusic.play();

		// Switch to normal stage.
		tappy.setStage(settingNormalStage);

		// Add clickListener to settingHomeButton.
		settingHomeButton.addListener(new ClickListener() {
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
						.push(Tween.to(settingBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(settingHomeButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(settingCreditButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(settingMusicMinusButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(settingMusicPlusButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(settingButtonMinusButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(settingButtonPlusButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(settingSpeedMinusButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(settingSpeedPlusButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(settingSpeedFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
					.end()
					// Register callback.
					.setCallback(new TweenCallback() {
						@Override
						public void onEvent(int type, BaseTween<?> source) {
							sharedInputEnable = true;
							tappy.setScreen(new HomeScreen(tappy));
						}
					})
				// Launch.
				.start(sharedTweenManager);
			}
		});

		// Register settingHomeButton to stage actor.
		tappy.getStage().addActor(settingHomeButton);

		// Add clickListener to settingCreditButton.
		settingCreditButton.addListener(new ClickListener() {
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
						.push(Tween.to(settingBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(settingHomeButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(settingCreditButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(settingMusicMinusButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(settingMusicPlusButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(settingButtonMinusButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(settingButtonPlusButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(settingSpeedMinusButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(settingSpeedPlusButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(settingSpeedFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
					.end()
					// Register callback.
					.setCallback(new TweenCallback() {
						@Override
						public void onEvent(int type, BaseTween<?> source) {
							sharedInputEnable = true;
							tappy.setScreen(new CreditScreen(tappy));
						}
					})
				// Launch.
				.start(sharedTweenManager);
			}
		});

		// Register settingCreditButton to stage actor.
		tappy.getStage().addActor(settingCreditButton);

		// Add clickListener to settingMusicMinusButton.
		settingMusicMinusButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (tappy.getPreferences().getInteger("settingMusicVolume", 10) <= 0) {
					return;
				}

				tappy.getPreferences().putInteger("settingMusicVolume", tappy.getPreferences().getInteger("settingMusicVolume", 10) - 1);
				sharedBackgroundMusic.setVolume(tappy.getPreferences().getInteger("settingMusicVolume", 10) / 10f);
				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);
			}
		});

		// Register settingMusicMinusButton to stage actor.
		tappy.getStage().addActor(settingMusicMinusButton);

		// Add clickListener to settingMusicPlusButton.
		settingMusicPlusButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (tappy.getPreferences().getInteger("settingMusicVolume", 10) >= 10) {
					return;
				}

				tappy.getPreferences().putInteger("settingMusicVolume", tappy.getPreferences().getInteger("settingMusicVolume", 10) + 1);
				sharedBackgroundMusic.setVolume(tappy.getPreferences().getInteger("settingMusicVolume", 10) / 10f);
				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);
			}
		});

		// Register settingMusicPlusButton to stage actor.
		tappy.getStage().addActor(settingMusicPlusButton);

		// Add clickListener to settingButtonMinusButton.
		settingButtonMinusButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (tappy.getPreferences().getInteger("settingButtonVolume", 10) <= 0) {
					return;
				}

				tappy.getPreferences().putInteger("settingButtonVolume", tappy.getPreferences().getInteger("settingButtonVolume", 10) - 1);
				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);
			}
		});

		// Register settingButtonMinusButton to stage actor.
		tappy.getStage().addActor(settingButtonMinusButton);

		// Add clickListener to settingMusicPlusButton.
		settingButtonPlusButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (tappy.getPreferences().getInteger("settingButtonVolume", 10) >= 10) {
					return;
				}

				tappy.getPreferences().putInteger("settingButtonVolume", tappy.getPreferences().getInteger("settingButtonVolume", 10) + 1);
				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);
			}
		});

		// Register settingMusicPlusButton to stage actor.
		tappy.getStage().addActor(settingButtonPlusButton);

		// Add clickListener to settingSpeedMinusButton.
		settingSpeedMinusButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (tappy.getPreferences().getInteger("settingPlaySpeed", 1) <= 1) {
					return;
				}

				tappy.getPreferences().putInteger("settingPlaySpeed", tappy.getPreferences().getInteger("settingPlaySpeed", 1) - 1);
				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);
			}
		});

		// Register settingButtonMinusButton to stage actor.
		tappy.getStage().addActor(settingSpeedMinusButton);

		// Add clickListener to settingSpeedPlusButton.
		settingSpeedPlusButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (tappy.getPreferences().getInteger("settingPlaySpeed", 1) >= 9) {
					return;
				}

				tappy.getPreferences().putInteger("settingPlaySpeed", tappy.getPreferences().getInteger("settingPlaySpeed", 1) + 1);
				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);
			}
		});

		// Register settingMusicPlusButton to stage actor.
		tappy.getStage().addActor(settingSpeedPlusButton);

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
		tappy.setStage(settingNormalStage);
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
			settingBackground.draw(tappy.getSpriteBatch());
			settingSpeedFont.draw(tappy.getSpriteBatch(), "" + (tappy.getPreferences().getInteger("settingMusicVolume", 10)), 334 - (settingSpeedFont.getBounds("" + (tappy.getPreferences().getInteger("settingMusicVolume", 10))).width / 2), 376);
			settingSpeedFont.draw(tappy.getSpriteBatch(), "" + (tappy.getPreferences().getInteger("settingButtonVolume", 10)), 628 - (settingSpeedFont.getBounds("" + (tappy.getPreferences().getInteger("settingButtonVolume", 10))).width / 2), 376);
			settingSpeedFont.draw(tappy.getSpriteBatch(), "" + tappy.getPreferences().getInteger("settingPlaySpeed", 1), 480 - (settingSpeedFont.getBounds("" + tappy.getPreferences().getInteger("settingPlaySpeed", 1)).width / 2), 186);
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
