package com.jellopy.screen;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Expo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
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
import com.jellopy.Helper;
import com.jellopy.Tappy;
import com.jellopy.accessor.BitmapFontAccessor;
import com.jellopy.accessor.ImageButtonAccessor;
import com.jellopy.accessor.SpriteAccessor;

/**
 * The Select Screen
 *
 * @author Jellopy
 */
public class SelectScreen extends ScreenAdapter {
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
	private Sprite selectBackground;
	private Sprite selectForeground;
	private Sprite selectConfirmBackground;

	private Sprite selectHomeButtonUp;
	private Sprite selectHomeButtonOver;
	private Sprite selectHomeButtonDown;

	private Sprite selectSettingButtonUp;
	private Sprite selectSettingButtonOver;
	private Sprite selectSettingButtonDown;

	private Sprite selectLeftButtonUp;
	private Sprite selectLeftButtonOver;
	private Sprite selectLeftButtonDown;

	private Sprite selectRightButtonUp;
	private Sprite selectRightButtonOver;
	private Sprite selectRightButtonDown;

	private Sprite selectPlayButtonUp;
	private Sprite selectPlayButtonOver;
	private Sprite selectPlayButtonDown;

	private Sprite selectConfirmPlayButtonUp;
	private Sprite selectConfirmPlayButtonOver;
	private Sprite selectConfirmPlayButtonDown;

	private Sprite selectConfirmMinusButtonUp;
	private Sprite selectConfirmMinusButtonOver;
	private Sprite selectConfirmMinusButtonDown;

	private Sprite selectConfirmPlusButtonUp;
	private Sprite selectConfirmPlusButtonOver;
	private Sprite selectConfirmPlusButtonDown;

	// Buttons.
	private ImageButton selectHomeButton;
	private ImageButton selectSettingButton;
	private ImageButton selectLeftButton;
	private ImageButton selectRightButton;
	private ImageButton selectPlayButton;
	private ImageButton selectConfirmPlayButton;
	private ImageButton selectConfirmMinusButton;
	private ImageButton selectConfirmPlusButton;

	// Fonts.
	private BitmapFont selectMusicNameFont;
	private BitmapFont selectMusicHighscoreFont;
	private BitmapFont selectConfirmSpeedFont;

	// SelectScreen variable.
	private boolean selectConfirming;
	private Stage selectNormalStage;
	private Stage selectConfirmStage;

	private int selectPlaySpeed;

	private Timeline selectPlaylistTimeline;

	private FileHandle selectPlaylistFile;

	private int selectFocusIndex;
	private ArrayList<Sprite> selectPlaylistAlbumSprites;
	private ArrayList<String> selectPlaylistName;
	private ArrayList<String> selectPlaylistRawName;
	private ArrayList<Integer> selectPlaylistHighscore;

	/**
	 * Select Screen Constructor.
	 *
	 * @param tappy
	 */
	public SelectScreen(Tappy tappy) {
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

		// Get musics.
		sharedBackgroundMusic = Assets.getMusic("sharedBackgroundMusic");

		// Get sounds.
		sharedButtonDownSound = Assets.getSound("sharedButtonDownSound");

		// Get sprites.
		selectBackground = Assets.getSprite("selectBackground");
		selectForeground = Assets.getSprite("selectForeground");
		selectConfirmBackground = Assets.getSprite("selectConfirmBackground");

		selectHomeButtonUp = Assets.getSprite("selectHomeButtonUp");
		selectHomeButtonOver = Assets.getSprite("selectHomeButtonOver");
		selectHomeButtonDown = Assets.getSprite("selectHomeButtonDown");

		selectSettingButtonUp = Assets.getSprite("selectSettingButtonUp");
		selectSettingButtonOver = Assets.getSprite("selectSettingButtonOver");
		selectSettingButtonDown = Assets.getSprite("selectSettingButtonDown");

		selectLeftButtonUp = Assets.getSprite("selectLeftButtonUp");
		selectLeftButtonOver = Assets.getSprite("selectLeftButtonOver");
		selectLeftButtonDown = Assets.getSprite("selectLeftButtonDown");

		selectRightButtonUp = Assets.getSprite("selectRightButtonUp");
		selectRightButtonOver = Assets.getSprite("selectRightButtonOver");
		selectRightButtonDown = Assets.getSprite("selectRightButtonDown");

		selectPlayButtonUp = Assets.getSprite("selectPlayButtonUp");
		selectPlayButtonOver = Assets.getSprite("selectPlayButtonOver");
		selectPlayButtonDown = Assets.getSprite("selectPlayButtonDown");

		selectConfirmPlayButtonUp = Assets.getSprite("selectConfirmPlayButtonUp");
		selectConfirmPlayButtonOver = Assets.getSprite("selectConfirmPlayButtonOver");
		selectConfirmPlayButtonDown = Assets.getSprite("selectConfirmPlayButtonDown");

		selectConfirmMinusButtonUp = Assets.getSprite("selectConfirmMinusButtonUp");
		selectConfirmMinusButtonOver = Assets.getSprite("selectConfirmMinusButtonOver");
		selectConfirmMinusButtonDown = Assets.getSprite("selectConfirmMinusButtonDown");

		selectConfirmPlusButtonUp = Assets.getSprite("selectConfirmPlusButtonUp");
		selectConfirmPlusButtonOver = Assets.getSprite("selectConfirmPlusButtonOver");
		selectConfirmPlusButtonDown = Assets.getSprite("selectConfirmPlusButtonDown");

		// Styling selectHomeButton.
		ImageButtonStyle selectHomeButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		selectHomeButtonStyle.imageUp = new SpriteDrawable(selectHomeButtonUp);
		selectHomeButtonStyle.imageOver = new SpriteDrawable(selectHomeButtonOver);
		selectHomeButtonStyle.imageDown = new SpriteDrawable(selectHomeButtonDown);

		// Initialize selectHomeButton.
		selectHomeButton = new ImageButton(selectHomeButtonStyle);
		selectHomeButton.setPosition(56, 39);
		selectHomeButton.setColor(selectHomeButton.getColor().r, selectHomeButton.getColor().g, selectHomeButton.getColor().b, 0);

		// Styling selectSettingButton.
		ImageButtonStyle selectSettingButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		selectSettingButtonStyle.imageUp = new SpriteDrawable(selectSettingButtonUp);
		selectSettingButtonStyle.imageOver = new SpriteDrawable(selectSettingButtonOver);
		selectSettingButtonStyle.imageDown = new SpriteDrawable(selectSettingButtonDown);

		// Initialize selectSettingButton.
		selectSettingButton = new ImageButton(selectSettingButtonStyle);
		selectSettingButton.setPosition(152, 35);
		selectSettingButton.setColor(selectSettingButton.getColor().r, selectSettingButton.getColor().g, selectSettingButton.getColor().b, 0);

		// Styling selectLeftButton.
		ImageButtonStyle selectLeftButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		selectLeftButtonStyle.imageUp = new SpriteDrawable(selectLeftButtonUp);
		selectLeftButtonStyle.imageOver = new SpriteDrawable(selectLeftButtonOver);
		selectLeftButtonStyle.imageDown = new SpriteDrawable(selectLeftButtonDown);

		// Initialize selectLeftButton.
		selectLeftButton = new ImageButton(selectLeftButtonStyle);
		selectLeftButton.setPosition(263, 37);
		selectLeftButton.setColor(selectLeftButton.getColor().r, selectLeftButton.getColor().g, selectLeftButton.getColor().b, 0);

		// Styling selectRightButton.
		ImageButtonStyle selectRightButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		selectRightButtonStyle.imageUp = new SpriteDrawable(selectRightButtonUp);
		selectRightButtonStyle.imageOver = new SpriteDrawable(selectRightButtonOver);
		selectRightButtonStyle.imageDown = new SpriteDrawable(selectRightButtonDown);

		// Initialize selectRightButton.
		selectRightButton = new ImageButton(selectRightButtonStyle);
		selectRightButton.setPosition(638, 37);
		selectRightButton.setColor(selectRightButton.getColor().r, selectRightButton.getColor().g, selectRightButton.getColor().b, 0);

		// Styling selectPlayButton.
		ImageButtonStyle selectPlayButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		selectPlayButtonStyle.imageUp = new SpriteDrawable(selectPlayButtonUp);
		selectPlayButtonStyle.imageOver = new SpriteDrawable(selectPlayButtonOver);
		selectPlayButtonStyle.imageDown = new SpriteDrawable(selectPlayButtonDown);

		// Initialize selectPlayButton.
		selectPlayButton = new ImageButton(selectPlayButtonStyle);
		selectPlayButton.setPosition(825, 4);
		selectPlayButton.setColor(selectPlayButton.getColor().r, selectPlayButton.getColor().g, selectPlayButton.getColor().b, 0);

		// Styling selectConfirmPlayButton.
		ImageButtonStyle selectConfirmPlayButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		selectConfirmPlayButtonStyle.imageUp = new SpriteDrawable(selectConfirmPlayButtonUp);
		selectConfirmPlayButtonStyle.imageOver = new SpriteDrawable(selectConfirmPlayButtonOver);
		selectConfirmPlayButtonStyle.imageDown = new SpriteDrawable(selectConfirmPlayButtonDown);

		// Initialize selectConfirmPlayButton.
		selectConfirmPlayButton = new ImageButton(selectConfirmPlayButtonStyle);
		selectConfirmPlayButton.setPosition(395, 185);
		selectConfirmPlayButton.setColor(selectConfirmPlayButton.getColor().r, selectConfirmPlayButton.getColor().g, selectConfirmPlayButton.getColor().b, 0);

		// Styling selectConfirmMinusButton.
		ImageButtonStyle selectConfirmMinusButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		selectConfirmMinusButtonStyle.imageUp = new SpriteDrawable(selectConfirmMinusButtonUp);
		selectConfirmMinusButtonStyle.imageOver = new SpriteDrawable(selectConfirmMinusButtonOver);
		selectConfirmMinusButtonStyle.imageDown = new SpriteDrawable(selectConfirmMinusButtonDown);

		// Initialize selectConfirmMinusButton.
		selectConfirmMinusButton = new ImageButton(selectConfirmMinusButtonStyle);
		selectConfirmMinusButton.setPosition(387, 405);
		selectConfirmMinusButton.setColor(selectConfirmMinusButton.getColor().r, selectConfirmMinusButton.getColor().g, selectConfirmMinusButton.getColor().b, 0);

		// Styling selectConfirmPlusButton.
		ImageButtonStyle selectConfirmPlusButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		selectConfirmPlusButtonStyle.imageUp = new SpriteDrawable(selectConfirmPlusButtonUp);
		selectConfirmPlusButtonStyle.imageOver = new SpriteDrawable(selectConfirmPlusButtonOver);
		selectConfirmPlusButtonStyle.imageDown = new SpriteDrawable(selectConfirmPlusButtonDown);

		// Initialize selectConfirmPlusButton.
		selectConfirmPlusButton = new ImageButton(selectConfirmPlusButtonStyle);
		selectConfirmPlusButton.setPosition(526, 405);
		selectConfirmPlusButton.setColor(selectConfirmPlusButton.getColor().r, selectConfirmPlusButton.getColor().g, selectConfirmPlusButton.getColor().b, 0);

		// Get fonts.
		FreeTypeFontParameter selectMusicNameFontParameter = new FreeTypeFontParameter();
		selectMusicNameFontParameter.size = 26;
		selectMusicNameFont = Assets.getFont("codeBoldFont").generateFont(selectMusicNameFontParameter);
		selectMusicNameFont.setColor(1f, 1f, 1f, 0f);

		FreeTypeFontParameter selectMusicHighscoreFontParameter = new FreeTypeFontParameter();
		selectMusicHighscoreFontParameter.size = 24;
		selectMusicHighscoreFont = Assets.getFont("codeBoldFont").generateFont(selectMusicHighscoreFontParameter);
		selectMusicHighscoreFont.setColor(1f, 1f, 1f, 0f);
		selectMusicHighscoreFont.setFixedWidthGlyphs("0123456789");

		FreeTypeFontParameter selectConfirmSpeedFontParameter = new FreeTypeFontParameter();
		selectConfirmSpeedFontParameter.size = 60;
		selectConfirmSpeedFont = Assets.getFont("codeBoldFont").generateFont(selectConfirmSpeedFontParameter);
		selectConfirmSpeedFont.setColor(1f, 1f, 1f, 0f);
		selectConfirmSpeedFont.setFixedWidthGlyphs("0123456789");

		// Initialize selectScreen variable.
		selectConfirming = false;
		selectNormalStage = new Stage();
		selectConfirmStage = new Stage();

		selectPlaySpeed = tappy.getPreferences().getInteger("settingPlaySpeed", 1);

		selectPlaylistTimeline = null;

		selectPlaylistFile = Gdx.files.internal("play/playlist.txt");

		selectFocusIndex = 0;
		selectPlaylistAlbumSprites = new ArrayList<Sprite>();
		selectPlaylistName = new ArrayList<String>();
		selectPlaylistRawName = new ArrayList<String>();
		selectPlaylistHighscore = new ArrayList<Integer>();

		// Load album sprites.
		String [] musics = selectPlaylistFile.readString().split("\n");
		for (int i = 0; i < musics.length; i++) {
			// Load sprite.
			String textureName = "selectPlaylist" + Helper.ucfirst(musics[i]) + "Sprite";
			Assets.loadTexture(textureName, "play/" + musics[i] + "/album.jpg");
			Sprite album = Assets.getSprite(textureName);
			album.setPosition(Gdx.graphics.getWidth() * i, 0);
			selectPlaylistAlbumSprites.add(album);

			// Load meta.
			String [] metas = Gdx.files.internal("play/" + musics[i] + "/meta.txt").readString().split(",");
			selectPlaylistName.add(metas[0]);
			selectPlaylistRawName.add(musics[i]);
			selectPlaylistHighscore.add(tappy.getPreferences().getInteger(musics[i] + "Highscore", 0));
		}

		// Register tweenEngine timeline.
		Timeline.createSequence()
			// Set default values.
			.push(Tween.set(selectBackground, SpriteAccessor.ALPHA).target(0))
			.push(Tween.set(selectPlaylistAlbumSprites.get(0), SpriteAccessor.ALPHA).target(0))
			.push(Tween.set(selectForeground, SpriteAccessor.ALPHA).target(0))
			.push(Tween.set(selectHomeButton, ImageButtonAccessor.ALPHA).target(0))
			.push(Tween.set(selectSettingButton, ImageButtonAccessor.ALPHA).target(0))
			.push(Tween.set(selectLeftButton, ImageButtonAccessor.ALPHA).target(0))
			.push(Tween.set(selectRightButton, ImageButtonAccessor.ALPHA).target(0))
			.push(Tween.set(selectPlayButton, ImageButtonAccessor.ALPHA).target(0))
			.push(Tween.set(selectMusicNameFont, BitmapFontAccessor.ALPHA).target(0))
			.push(Tween.set(selectMusicHighscoreFont, BitmapFontAccessor.ALPHA).target(0))
			// FadeIn.
			.beginParallel()
				.push(Tween.to(selectBackground, SpriteAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(selectPlaylistAlbumSprites.get(0), SpriteAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(selectForeground, SpriteAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(selectHomeButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(selectSettingButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(selectLeftButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(selectRightButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(selectPlayButton, ImageButtonAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(selectMusicNameFont, BitmapFontAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(selectMusicHighscoreFont, BitmapFontAccessor.ALPHA, 1.2f).target(1))
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
	 * Launch selectScreen.
	 */
	@Override
	public void show() {
		// Play sharedBackgroundMusic
		sharedBackgroundMusic.setLooping(true);
		sharedBackgroundMusic.setVolume(tappy.getPreferences().getInteger("settingMusicVolume", 10) / 10f);
		sharedBackgroundMusic.play();

		// Switch to normal stage.
		tappy.setStage(selectNormalStage);

		// Add clickListener to selectHomeButton.
		selectHomeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (selectConfirming) {
					return;
				}

				sharedInputEnable = false;
				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);

				// Register tweenEngine timeline.
				selectPlaylistTimeline = Timeline.createSequence().beginParallel();
				for (int i = 0; i < selectPlaylistAlbumSprites.size(); i++) {
					selectPlaylistTimeline.push(Tween.to(selectPlaylistAlbumSprites.get(i), SpriteAccessor.ALPHA, 0.6f).target(0));
				}
				selectPlaylistTimeline.push(Tween.to(selectBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectPlaylistAlbumSprites.get(selectFocusIndex), SpriteAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectForeground, SpriteAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectHomeButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectSettingButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectLeftButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectRightButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectPlayButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectMusicNameFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectMusicHighscoreFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectConfirmBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectConfirmSpeedFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectConfirmPlayButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectConfirmMinusButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectConfirmPlusButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
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

		// Register selectHomeButton to stage actor.
		tappy.getStage().addActor(selectHomeButton);

		// Add clickListener to selectSettingButton.
		selectSettingButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (selectConfirming) {
					return;
				}

				sharedInputEnable = false;
				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);

				// Register tweenEngine timeline.
				selectPlaylistTimeline = Timeline.createSequence().beginParallel();
				for (int i = 0; i < selectPlaylistAlbumSprites.size(); i++) {
					selectPlaylistTimeline.push(Tween.to(selectPlaylistAlbumSprites.get(i), SpriteAccessor.ALPHA, 0.6f).target(0));
				}
				selectPlaylistTimeline.push(Tween.to(selectBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectPlaylistAlbumSprites.get(selectFocusIndex), SpriteAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectForeground, SpriteAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectHomeButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectSettingButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectLeftButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectRightButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectPlayButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectMusicNameFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectMusicHighscoreFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectConfirmBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectConfirmSpeedFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectConfirmPlayButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectConfirmMinusButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectConfirmPlusButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
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

		// Register selectSettingButton to stage actor.
		tappy.getStage().addActor(selectSettingButton);

		// Add clickListener to selectLeftButton.
		selectLeftButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (selectConfirming) {
					return;
				}

				sharedInputEnable = false;
				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);

				// Register tweenEngine timeline.
				selectPlaylistTimeline = Timeline.createSequence().beginParallel();
				for (int i = 0; i < selectPlaylistAlbumSprites.size(); i++) {
					selectPlaylistTimeline.push(Tween.to(selectPlaylistAlbumSprites.get(i), SpriteAccessor.POSITION, 0.4f).target(selectPlaylistAlbumSprites.get(i).getX() + (selectFocusIndex == 0 ? (-(selectPlaylistAlbumSprites.size() - 1) * Gdx.graphics.getWidth()) : Gdx.graphics.getWidth()), 0).ease(Expo.INOUT));
				}
				selectPlaylistTimeline.end()
				// Register callback.
				.setCallback(new TweenCallback() {
					@Override
					public void onEvent(int type, BaseTween<?> source) {
						selectFocusIndex = selectFocusIndex == 0 ? selectPlaylistAlbumSprites.size() - 1 : selectFocusIndex - 1;
						sharedInputEnable = true;
					}
				})
				// Launch.
				.start(sharedTweenManager);
			}
		});

		// Register selectLeftButton to stage actor.
		tappy.getStage().addActor(selectLeftButton);

		// Add clickListener to selectRightButton.
		selectRightButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (selectConfirming) {
					return;
				}

				sharedInputEnable = false;
				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);

				// Register tweenEngine timeline.
				selectPlaylistTimeline = Timeline.createSequence().beginParallel();
				for (int i = 0; i < selectPlaylistAlbumSprites.size(); i++) {
					selectPlaylistTimeline.push(Tween.to(selectPlaylistAlbumSprites.get(i), SpriteAccessor.POSITION, 0.4f).target(selectPlaylistAlbumSprites.get(i).getX() - (selectFocusIndex == (selectPlaylistAlbumSprites.size() - 1) ? (-(selectPlaylistAlbumSprites.size() - 1) * Gdx.graphics.getWidth()) : Gdx.graphics.getWidth()), 0).ease(Expo.INOUT));
				}
				selectPlaylistTimeline.end()
				// Register callback.
				.setCallback(new TweenCallback() {
					@Override
					public void onEvent(int type, BaseTween<?> source) {
						selectFocusIndex = selectFocusIndex == (selectPlaylistAlbumSprites.size() - 1) ? 0 : selectFocusIndex + 1;
						sharedInputEnable = true;
					}
				})
				// Launch.
				.start(sharedTweenManager);
			}
		});

		// Register selectRightButton to stage actor.
		tappy.getStage().addActor(selectRightButton);

		// Add clickListener to selectPlayButton.
		selectPlayButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (selectConfirming) {
					return;
				}

				sharedInputEnable = false;
				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);

				selectConfirming = true;
				// Register tweenEngine timeline.
				Timeline.createSequence()
				.push(Tween.set(selectConfirmBackground, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(selectConfirmPlayButton, ImageButtonAccessor.ALPHA).target(0))
				.push(Tween.set(selectConfirmMinusButton, ImageButtonAccessor.ALPHA).target(0))
				.push(Tween.set(selectConfirmPlusButton, ImageButtonAccessor.ALPHA).target(0))
				.push(Tween.set(selectConfirmSpeedFont, BitmapFontAccessor.ALPHA).target(0))
				.beginParallel()
					.push(Tween.to(selectConfirmBackground, SpriteAccessor.ALPHA, 1.1f).target(1))
					.push(Tween.to(selectConfirmPlayButton, ImageButtonAccessor.ALPHA, 1.1f).target(1))
					.push(Tween.to(selectConfirmMinusButton, ImageButtonAccessor.ALPHA, 1.1f).target(1))
					.push(Tween.to(selectConfirmPlusButton, ImageButtonAccessor.ALPHA, 1.1f).target(1))
					.push(Tween.to(selectConfirmSpeedFont, BitmapFontAccessor.ALPHA, 1.1f).target(1))
				.end()
				// Register callback.
				.setCallback(new TweenCallback() {
					@Override
					public void onEvent(int type, BaseTween<?> source) {
						sharedInputEnable = true;
					}
				})
				// Launch
				.start(sharedTweenManager);
			}
		});

		// Register selectPlayButton to stage actor.
		tappy.getStage().addActor(selectPlayButton);

		// Switch to confirm stage.
		tappy.setStage(selectConfirmStage);

		// Add clickListener to selectConfirmMinusButton.
		selectConfirmMinusButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (!selectConfirming) {
					return;
				}

				if (selectPlaySpeed <= 1) {
					return;
				}

				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);
				selectPlaySpeed -= 1;
				tappy.getPreferences().putInteger("settingPlaySpeed", selectPlaySpeed);
			}
		});

		// Register selectConfirmMinusButton to stage actor.
		tappy.getStage().addActor(selectConfirmMinusButton);

		// Add clickListener to selectConfirmPlusButton.
		selectConfirmPlusButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (!selectConfirming) {
					return;
				}

				if (selectPlaySpeed >= 9) {
					return;
				}

				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);
				selectPlaySpeed += 1;
				tappy.getPreferences().putInteger("settingPlaySpeed", selectPlaySpeed);
			}
		});

		// Register selectConfirmPlayButton to stage actor.
		tappy.getStage().addActor(selectConfirmPlusButton);

		// Add clickListener to selectConfirmPlayButton.
		selectConfirmPlayButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (!selectConfirming) {
					return;
				}

				sharedInputEnable = false;
				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);

				// Register tweenEngine timeline.
				selectPlaylistTimeline = Timeline.createSequence().beginParallel();
				for (int i = 0; i < selectPlaylistAlbumSprites.size(); i++) {
					if (selectFocusIndex != i) {
						selectPlaylistTimeline.push(Tween.to(selectPlaylistAlbumSprites.get(i), SpriteAccessor.ALPHA, 0.6f).target(0));
					}
				}
				selectPlaylistTimeline.push(Tween.to(selectBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectForeground, SpriteAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectHomeButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectSettingButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectLeftButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectRightButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectPlayButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectMusicNameFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectMusicHighscoreFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectConfirmBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectConfirmSpeedFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectConfirmPlayButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectConfirmMinusButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.push(Tween.to(selectConfirmPlusButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
				.end()
				// Register callback.
				.setCallback(new TweenCallback() {
					@Override
					public void onEvent(int type, BaseTween<?> source) {
						sharedInputEnable = true;
						sharedBackgroundMusic.stop();
						tappy.setScreen(new PlayScreen(tappy, selectPlaylistRawName.get(selectFocusIndex)));
					}
				})
				// Launch
				.start(sharedTweenManager);
			}
		});

		// Register selectConfirmPlayButton to stage actor.
		tappy.getStage().addActor(selectConfirmPlayButton);

		// Bind inputProcessor with stage.
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		tappy.setStage(selectNormalStage);
		inputMultiplexer.addProcessor(tappy.getStage());
		tappy.setStage(selectConfirmStage);
		inputMultiplexer.addProcessor(tappy.getStage());
		Gdx.input.setInputProcessor(inputMultiplexer);
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
		tappy.setStage(selectNormalStage);
		tappy.getStage().act();
		tappy.setStage(selectConfirmStage);
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
			// Draw all album sprites.
			for (int i = 0; i < selectPlaylistAlbumSprites.size(); i++) {
				selectPlaylistAlbumSprites.get(i).draw(tappy.getSpriteBatch());
			}
			// Draw background.
			selectBackground.draw(tappy.getSpriteBatch());
			// Draw foreground.
			selectForeground.draw(tappy.getSpriteBatch());
			// Draw font.
			selectMusicNameFont.draw(tappy.getSpriteBatch(), selectPlaylistName.get(selectFocusIndex), (Gdx.graphics.getWidth() / 2f) - (selectMusicNameFont.getBounds(selectPlaylistName.get(selectFocusIndex)).width / 2), 96);
			selectMusicHighscoreFont.draw(tappy.getSpriteBatch(), "" + selectPlaylistHighscore.get(selectFocusIndex), (Gdx.graphics.getWidth() / 2f) - (selectMusicHighscoreFont.getBounds("" + selectPlaylistHighscore.get(selectFocusIndex)).width / 2), 43);
		tappy.getSpriteBatch().end();

		tappy.getSpriteBatch().begin();
			// Switch to normal stage.
			tappy.setStage(selectNormalStage);
			// Draw button.
			tappy.getStage().draw();
		tappy.getSpriteBatch().end();

		if (selectConfirming) {
			tappy.getSpriteBatch().begin();
				selectConfirmBackground.draw(tappy.getSpriteBatch());
				selectConfirmSpeedFont.draw(tappy.getSpriteBatch(), "" + selectPlaySpeed, (Gdx.graphics.getWidth() / 2f) - (selectConfirmSpeedFont.getBounds("" + selectPlaySpeed).width / 2), 454);
			tappy.getSpriteBatch().end();

			tappy.getSpriteBatch().begin();
				// Switch to confirm stage.
				tappy.setStage(selectConfirmStage);
				// Draw button.
				tappy.getStage().draw();
			tappy.getSpriteBatch().end();
		}
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
