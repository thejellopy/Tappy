package com.jellopy.screen;

import java.util.HashMap;

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
 * The Result Screen
 *
 * @author Jellopy
 */
public class ResultScreen extends ScreenAdapter {
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
	private Sprite resultBackground;

	private Sprite resultReplayButtonUp;
	private Sprite resultReplayButtonOver;
	private Sprite resultReplayButtonDown;

	private Sprite resultSongsButtonUp;
	private Sprite resultSongsButtonOver;
	private Sprite resultSongsButtonDown;

	private Sprite resultHomeButtonUp;
	private Sprite resultHomeButtonOver;
	private Sprite resultHomeButtonDown;

	// Buttons.
	private ImageButton resultReplayButton;
	private ImageButton resultSongsButton;
	private ImageButton resultHomeButton;

	// Fonts.
	private BitmapFont resultMusicNameFont;
	private BitmapFont resultScoreSumFont;
	private BitmapFont resultComboFont;

	// ResultScreen variable.
	private Stage resultNormalStage;
	private String resultMusicRawName;
	private String resultMusicName;
	private HashMap<String, Integer> resultScore;
	private int resultScoreSum;

	/**
	 * Result Screen Constructor.
	 *
	 * @param tappy
	 */
	public ResultScreen(Tappy tappy, String rawName, String name, HashMap<String, Integer> score, int scoreSum) {
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
		resultBackground = Assets.getSprite("resultBackground");

		resultReplayButtonUp = Assets.getSprite("resultReplayButtonUp");
		resultReplayButtonOver = Assets.getSprite("resultReplayButtonOver");
		resultReplayButtonDown = Assets.getSprite("resultReplayButtonDown");

		resultSongsButtonUp = Assets.getSprite("resultSongsButtonUp");
		resultSongsButtonOver = Assets.getSprite("resultSongsButtonOver");
		resultSongsButtonDown = Assets.getSprite("resultSongsButtonDown");

		resultHomeButtonUp = Assets.getSprite("resultHomeButtonUp");
		resultHomeButtonOver = Assets.getSprite("resultHomeButtonOver");
		resultHomeButtonDown = Assets.getSprite("resultHomeButtonDown");

		// Styling resultReplayButton.
		ImageButtonStyle resultReplayButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		resultReplayButtonStyle.imageUp = new SpriteDrawable(resultReplayButtonUp);
		resultReplayButtonStyle.imageOver = new SpriteDrawable(resultReplayButtonOver);
		resultReplayButtonStyle.imageDown = new SpriteDrawable(resultReplayButtonDown);

		// Initialize resultReplayButton.
		resultReplayButton = new ImageButton(resultReplayButtonStyle);
		resultReplayButton.setPosition(331, 34);
		resultReplayButton.setColor(resultReplayButton.getColor().r, resultReplayButton.getColor().g, resultReplayButton.getColor().b, 0);

		// Styling resultSongsButton.
		ImageButtonStyle resultSongsButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		resultSongsButtonStyle.imageUp = new SpriteDrawable(resultSongsButtonUp);
		resultSongsButtonStyle.imageOver = new SpriteDrawable(resultSongsButtonOver);
		resultSongsButtonStyle.imageDown = new SpriteDrawable(resultSongsButtonDown);

		// Initialize resultSongsButton.
		resultSongsButton = new ImageButton(resultSongsButtonStyle);
		resultSongsButton.setPosition(449, 34);
		resultSongsButton.setColor(resultSongsButton.getColor().r, resultSongsButton.getColor().g, resultSongsButton.getColor().b, 0);

		// Styling resultHomeButton.
		ImageButtonStyle resultHomeButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		resultHomeButtonStyle.imageUp = new SpriteDrawable(resultHomeButtonUp);
		resultHomeButtonStyle.imageOver = new SpriteDrawable(resultHomeButtonOver);
		resultHomeButtonStyle.imageDown = new SpriteDrawable(resultHomeButtonDown);

		// Initialize resultHomeButton.
		resultHomeButton = new ImageButton(resultHomeButtonStyle);
		resultHomeButton.setPosition(563, 34);
		resultHomeButton.setColor(resultHomeButton.getColor().r, resultHomeButton.getColor().g, resultHomeButton.getColor().b, 0);

		// Get fonts.
		FreeTypeFontParameter resultMusicNameFontParameter = new FreeTypeFontParameter();
		resultMusicNameFontParameter.size = 48;
		resultMusicNameFont = Assets.getFont("codeBoldFont").generateFont(resultMusicNameFontParameter);
		resultMusicNameFont.setColor(1f, 1f, 1f, 0f);

		FreeTypeFontParameter resultScoreSumFontParameter = new FreeTypeFontParameter();
		resultScoreSumFontParameter.size = 100;
		resultScoreSumFont = Assets.getFont("codeBoldFont").generateFont(resultScoreSumFontParameter);
		resultScoreSumFont.setColor(1f, 0.96f, 0f, 0f);

		FreeTypeFontParameter resultComboFontParameter = new FreeTypeFontParameter();
		resultComboFontParameter.size = 48;
		resultComboFont = Assets.getFont("codeBoldFont").generateFont(resultComboFontParameter);
		resultComboFont.setColor(1f, 1f, 1f, 0f);

		// Initialize resultScreen variable.
		resultNormalStage = new Stage();
		resultMusicRawName = rawName;
		resultMusicName = name;
		resultScore = score;
		resultScoreSum = scoreSum;

		// Register tweenEngine timeline.
		Timeline.createSequence()
			// Set default values.
			.push(Tween.set(resultBackground, SpriteAccessor.ALPHA).target(0))
			.push(Tween.set(resultReplayButton, ImageButtonAccessor.ALPHA).target(0))
			.push(Tween.set(resultSongsButton, ImageButtonAccessor.ALPHA).target(0))
			.push(Tween.set(resultHomeButton, ImageButtonAccessor.ALPHA).target(0))
			.push(Tween.set(resultMusicNameFont, BitmapFontAccessor.ALPHA).target(0))
			.push(Tween.set(resultScoreSumFont, BitmapFontAccessor.ALPHA).target(0))
			.push(Tween.set(resultComboFont, BitmapFontAccessor.ALPHA).target(0))
			// FadeIn.
			.beginParallel()
				.push(Tween.to(resultBackground, SpriteAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(resultReplayButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(resultSongsButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(resultHomeButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(resultMusicNameFont, BitmapFontAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(resultScoreSumFont, BitmapFontAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(resultComboFont, BitmapFontAccessor.ALPHA, 1.2f).target(1))
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
	 * Launch resultScreen.
	 */
	@Override
	public void show() {
		// Play sharedBackgroundMusic
		sharedBackgroundMusic.setLooping(true);
		sharedBackgroundMusic.setVolume(tappy.getPreferences().getInteger("settingMusicVolume", 10) / 10f);
		sharedBackgroundMusic.play();

		// Switch to normal stage.
		tappy.setStage(resultNormalStage);

		// Add clickListener to resultReplayButton.
		resultReplayButton.addListener(new ClickListener() {
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
						.push(Tween.to(resultBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(resultReplayButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(resultSongsButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(resultHomeButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(resultMusicNameFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(resultScoreSumFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(resultComboFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
					.end()
					// Register callback.
					.setCallback(new TweenCallback() {
						@Override
						public void onEvent(int type, BaseTween<?> source) {
							sharedInputEnable = true;
							sharedBackgroundMusic.stop();
							tappy.setScreen(new PlayScreen(tappy, resultMusicRawName));
						}
					})
				// Launch.
				.start(sharedTweenManager);
			}
		});

		// Register resultReplayButton to stage actor.
		tappy.getStage().addActor(resultReplayButton);

		// Add clickListener to resultSongsButton.
		resultSongsButton.addListener(new ClickListener() {
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
						.push(Tween.to(resultBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(resultReplayButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(resultSongsButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(resultHomeButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(resultMusicNameFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(resultScoreSumFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(resultComboFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
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

		// Register resultSongsButton to stage actor.
		tappy.getStage().addActor(resultSongsButton);

		// Add clickListener to resultHomeButton.
		resultHomeButton.addListener(new ClickListener() {
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
						.push(Tween.to(resultBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(resultReplayButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(resultSongsButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(resultHomeButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(resultMusicNameFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(resultScoreSumFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(resultComboFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
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

		// Register resultHomeButton to stage actor.
		tappy.getStage().addActor(resultHomeButton);

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
		tappy.setStage(resultNormalStage);
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
			resultBackground.draw(tappy.getSpriteBatch());
			resultMusicNameFont.draw(tappy.getSpriteBatch(), resultMusicName, (Gdx.graphics.getWidth() / 2f) - (resultMusicNameFont.getBounds(resultMusicName).width / 2), 455);
			resultScoreSumFont.draw(tappy.getSpriteBatch(), "" + resultScoreSum, (Gdx.graphics.getWidth() / 2f) - (resultScoreSumFont.getBounds("" + resultScoreSum).width / 2), 395);
			resultComboFont.draw(tappy.getSpriteBatch(), "" + resultScore.get("perfect"), 195 - (resultComboFont.getBounds("" + resultScore.get("perfect")).width / 2), 245);
			resultComboFont.draw(tappy.getSpriteBatch(), "" + resultScore.get("great"), 385 - (resultComboFont.getBounds("" + resultScore.get("great")).width / 2), 245);
			resultComboFont.draw(tappy.getSpriteBatch(), "" + resultScore.get("cool"), 575 - (resultComboFont.getBounds("" + resultScore.get("cool")).width / 2), 245);
			resultComboFont.draw(tappy.getSpriteBatch(), "" + resultScore.get("miss"), 765 - (resultComboFont.getBounds("" + resultScore.get("miss")).width / 2), 245);
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
