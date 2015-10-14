package com.jellopy.screen;

import java.util.ArrayList;
import java.util.HashMap;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.net.HttpParametersUtils;
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
import com.jellopy.game.Combo;
import com.jellopy.game.Lane;
import com.jellopy.game.Note;

/**
 * The Play Screen
 *
 * @author Jellopy
 */
public class PlayScreen extends ScreenAdapter implements InputProcessor {
	// Core variable.
	private Tappy tappy;

	// Input status.
	private boolean sharedInputEnable;

	// Camera viewport.
	private OrthographicCamera sharedOrthographicCamera;
	private PerspectiveCamera sharedPerspectiveCamera;
	private float sharedPerspectiveCameraBottom;

	// Tween controller.
	private TweenManager sharedTweenManager;

	// Musics.
	private Music sharedBackgroundMusic;

	// Sounds.
	private Sound sharedButtonDownSound;

	// Sprites.
	private Sprite playBackground;
	private Sprite playPauseBackground;

	private Sprite playAlbumCover;

	private Sprite playProcessBar;
	private Sprite playProcessBarLight;

	private Sprite playPauseButtonUp;
	private Sprite playPauseButtonOver;
	private Sprite playPauseButtonDown;

	private Sprite playPauseRetryButtonUp;
	private Sprite playPauseRetryButtonOver;
	private Sprite playPauseRetryButtonDown;

	private Sprite playPauseSongsButtonUp;
	private Sprite playPauseSongsButtonOver;
	private Sprite playPauseSongsButtonDown;

	private Sprite playPauseHomeButtonUp;
	private Sprite playPauseHomeButtonOver;
	private Sprite playPauseHomeButtonDown;

	private Sprite playPauseResumeButtonUp;
	private Sprite playPauseResumeButtonOver;
	private Sprite playPauseResumeButtonDown;

	// Buttons.
	private ImageButton playPauseButton;
	private ImageButton playPauseRetryButton;
	private ImageButton playPauseSongsButton;
	private ImageButton playPauseHomeButton;
	private ImageButton playPauseResumeButton;

	// Fonts.
	private BitmapFont playMusicNameFont;
	private BitmapFont playComboFont;
	private BitmapFont playScoreFont;

	// PlayScreen variable.
	private boolean playPausing;
	private Stage playNormalStage;
	private Stage playPauseStage;

	private Tween playProcess;

	private String playMusicRawName;
	private FileHandle playMusicMetaFile;
	private String [] playMusicMetas;

	private int playCombo;
	private Combo playComboPack;
	private HashMap<String, Integer> playScore;
	private int playScoreSum;
	private int playSpeed;

	private boolean playAutoPilot;

	private ArrayList<Lane> playLanes;
	private HashMap<Integer, Integer> playKeyToLane;

	private FileHandle playChordFile;
	private Timeline playChordTimeline;

	/**
	 * Play Screen Constructor.
	 *
	 * @param tappy
	 */
	public PlayScreen(Tappy tappy, String music) {
		// Core Variable.
		this.tappy = tappy;

		// Disable button while animating.
		sharedInputEnable = false;

		// Set normal camera.
		sharedOrthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		sharedOrthographicCamera.position.set(sharedOrthographicCamera.viewportWidth / 2f, sharedOrthographicCamera.viewportHeight / 2f, 0);
		sharedOrthographicCamera.update();

		// Set perspective camera.
		sharedPerspectiveCamera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		sharedPerspectiveCamera.position.set(0f, -160f, 170f);
		sharedPerspectiveCamera.lookAt(0f, 0f, 0f);
		sharedPerspectiveCamera.near = 0.1f;
		sharedPerspectiveCamera.far = 700f;
		sharedPerspectiveCamera.update();

		// Perspective coordinate.
		sharedPerspectiveCameraBottom = -161f;

		// Initialize TweenManager.
		sharedTweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Tween.registerAccessor(ImageButton.class, new ImageButtonAccessor());
		Tween.registerAccessor(BitmapFont.class, new BitmapFontAccessor());

		// Get musics.
		String musicName = "play" + Helper.ucfirst(music) + "Music";
		Assets.loadMusic(musicName, "play/" + music + "/music.ogg");
		sharedBackgroundMusic = Assets.getMusic(musicName);

		// Get sounds.
		sharedButtonDownSound = Assets.getSound("sharedButtonDownSound");

		// Get sprites.
		playBackground = Assets.getSprite("playBackground");
		playPauseBackground = Assets.getSprite("playPauseBackground");

		String textureName = "play" + Helper.ucfirst(music) + "AlbumCover";
		Assets.loadTexture(textureName, "play/" + music + "/album.jpg");
		playAlbumCover = Assets.getSprite(textureName);

		playProcessBar = Assets.getSprite("playProcessBar");
		playProcessBarLight = Assets.getSprite("playProcessBarLight");

		playPauseButtonUp = Assets.getSprite("playPauseButtonUp");
		playPauseButtonOver = Assets.getSprite("playPauseButtonOver");
		playPauseButtonDown = Assets.getSprite("playPauseButtonDown");

		playPauseRetryButtonUp = Assets.getSprite("playPauseRetryButtonUp");
		playPauseRetryButtonOver = Assets.getSprite("playPauseRetryButtonOver");
		playPauseRetryButtonDown = Assets.getSprite("playPauseRetryButtonDown");

		playPauseSongsButtonUp = Assets.getSprite("playPauseSongsButtonUp");
		playPauseSongsButtonOver = Assets.getSprite("playPauseSongsButtonOver");
		playPauseSongsButtonDown = Assets.getSprite("playPauseSongsButtonDown");

		playPauseHomeButtonUp = Assets.getSprite("playPauseHomeButtonUp");
		playPauseHomeButtonOver = Assets.getSprite("playPauseHomeButtonOver");
		playPauseHomeButtonDown = Assets.getSprite("playPauseHomeButtonDown");

		playPauseResumeButtonUp = Assets.getSprite("playPauseResumeButtonUp");
		playPauseResumeButtonOver = Assets.getSprite("playPauseResumeButtonOver");
		playPauseResumeButtonDown = Assets.getSprite("playPauseResumeButtonDown");

		// Styling playPauseButton.
		ImageButtonStyle playPauseButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		playPauseButtonStyle.imageUp = new SpriteDrawable(playPauseButtonUp);
		playPauseButtonStyle.imageOver = new SpriteDrawable(playPauseButtonOver);
		playPauseButtonStyle.imageDown = new SpriteDrawable(playPauseButtonDown);

		// Initialize playPauseButton.
		playPauseButton = new ImageButton(playPauseButtonStyle);
		playPauseButton.setPosition(901, 476);
		playPauseButton.setColor(playPauseButton.getColor().r, playPauseButton.getColor().g, playPauseButton.getColor().b, 0);

		// Styling playPauseRetryButton.
		ImageButtonStyle playPauseRetryButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		playPauseRetryButtonStyle.imageUp = new SpriteDrawable(playPauseRetryButtonUp);
		playPauseRetryButtonStyle.imageOver = new SpriteDrawable(playPauseRetryButtonOver);
		playPauseRetryButtonStyle.imageDown = new SpriteDrawable(playPauseRetryButtonDown);

		// Initialize playPauseRetryButton.
		playPauseRetryButton = new ImageButton(playPauseRetryButtonStyle);
		playPauseRetryButton.setPosition(116, 190);
		playPauseRetryButton.setColor(playPauseRetryButton.getColor().r, playPauseRetryButton.getColor().g, playPauseRetryButton.getColor().b, 0);

		// Styling playPauseSongsButton.
		ImageButtonStyle playPauseSongsButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		playPauseSongsButtonStyle.imageUp = new SpriteDrawable(playPauseSongsButtonUp);
		playPauseSongsButtonStyle.imageOver = new SpriteDrawable(playPauseSongsButtonOver);
		playPauseSongsButtonStyle.imageDown = new SpriteDrawable(playPauseSongsButtonDown);

		// Initialize playPauseSongsButton.
		playPauseSongsButton = new ImageButton(playPauseSongsButtonStyle);
		playPauseSongsButton.setPosition(305, 190);
		playPauseSongsButton.setColor(playPauseSongsButton.getColor().r, playPauseSongsButton.getColor().g, playPauseSongsButton.getColor().b, 0);

		// Styling playPauseHomeButton.
		ImageButtonStyle playPauseHomeButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		playPauseHomeButtonStyle.imageUp = new SpriteDrawable(playPauseHomeButtonUp);
		playPauseHomeButtonStyle.imageOver = new SpriteDrawable(playPauseHomeButtonOver);
		playPauseHomeButtonStyle.imageDown = new SpriteDrawable(playPauseHomeButtonDown);

		// Initialize playPauseHomeButton.
		playPauseHomeButton = new ImageButton(playPauseHomeButtonStyle);
		playPauseHomeButton.setPosition(494, 190);
		playPauseHomeButton.setColor(playPauseHomeButton.getColor().r, playPauseHomeButton.getColor().g, playPauseHomeButton.getColor().b, 0);

		// Styling playPauseResumeButton.
		ImageButtonStyle playPauseResumeButtonStyle = new ImageButtonStyle(tappy.getSkin().get(ButtonStyle.class));
		playPauseResumeButtonStyle.imageUp = new SpriteDrawable(playPauseResumeButtonUp);
		playPauseResumeButtonStyle.imageOver = new SpriteDrawable(playPauseResumeButtonOver);
		playPauseResumeButtonStyle.imageDown = new SpriteDrawable(playPauseResumeButtonDown);

		// Initialize playPauseResumeButton.
		playPauseResumeButton = new ImageButton(playPauseResumeButtonStyle);
		playPauseResumeButton.setPosition(683, 190);
		playPauseResumeButton.setColor(playPauseResumeButton.getColor().r, playPauseResumeButton.getColor().g, playPauseResumeButton.getColor().b, 0);

		// Get fonts.
		FreeTypeFontParameter playComboFontParameter = new FreeTypeFontParameter();
		playComboFontParameter.size = 48;
		playComboFont = Assets.getFont("codeBoldFont").generateFont(playComboFontParameter);
		playComboFont.setColor(1f, 1f, 1f, 0f);
		playComboFont.setFixedWidthGlyphs("0123456789");

		FreeTypeFontParameter playMusicNameFontParameter = new FreeTypeFontParameter();
		playMusicNameFontParameter.size = 26;
		playMusicNameFont = Assets.getFont("codeBoldFont").generateFont(playMusicNameFontParameter);
		playMusicNameFont.setColor(1f, 0.63f, 0f, 0f);

		FreeTypeFontParameter playScoreFontParameter = new FreeTypeFontParameter();
		playScoreFontParameter.size = 24;
		playScoreFont = Assets.getFont("codeBoldFont").generateFont(playScoreFontParameter);
		playScoreFont.setColor(1f, 1f, 1f, 0f);
		playScoreFont.setFixedWidthGlyphs("0123456789");

		// Initialize playScreen variable.
		playPausing = false;
		playNormalStage = new Stage();
		playPauseStage = new Stage();
		playProcess = null;

		playMusicRawName = music;
		playMusicMetaFile = Gdx.files.internal("play/" + music + "/meta.txt");
		playMusicMetas = playMusicMetaFile.readString().split(",");

		playCombo = 0;
		playComboPack = new Combo();
		playScore = new HashMap<String, Integer>();
		playScore.put("perfect", 0);
		playScore.put("great", 0);
		playScore.put("cool", 0);
		playScore.put("miss", 0);
		playScoreSum = 0;
		playSpeed = tappy.getPreferences().getInteger("settingPlaySpeed", 1) * 2;

		playAutoPilot = false;

		playLanes = new ArrayList<Lane>();
		playKeyToLane = new HashMap<Integer, Integer>();

		playChordFile = Gdx.files.internal("play/" + music + "/chords.txt");
		playChordTimeline = null;

		// Initialize play lane.
		for (int i = -2; i < 2; i++) {
			playLanes.add(new Lane(80 * i, -131));
		}

		// Bind keyboard input to lane.
		playKeyToLane.put(Keys.D, 0);
		playKeyToLane.put(Keys.F, 1);
		playKeyToLane.put(Keys.J, 2);
		playKeyToLane.put(Keys.K, 3);

		// Register tweenEngine chords.
		playChordTimeline = Timeline.createSequence().beginParallel();
		for (String chord : playChordFile.readString().split("\n")) {
			// Split chords part.
			String [] part = chord.split(",");
			// Note time (second).
			float time = Float.parseFloat(part[0]) / 1000;
			// Note y coordinate.
			float y = time * playSpeed * 100;
			// Add note & animation.
			for (int i = 1; i < part.length; i++) {
				Lane lane = playLanes.get(Integer.parseInt(part[i]) - 1);
				Note note = new Note(lane.getSprite().getX(), y);
				lane.addNote(note);
				playChordTimeline.push(Tween.to(note.getSprite(), SpriteAccessor.POSITION, time).target(note.getSprite().getX(), sharedPerspectiveCameraBottom).ease(Linear.INOUT));
			}
		}
		// Launch.
		playChordTimeline.end().start(sharedTweenManager);

		// Register tweenEngine timeline.
		Timeline.createSequence()
			.push(Tween.set(playBackground, SpriteAccessor.ALPHA).target(0))
			.push(Tween.set(playAlbumCover, SpriteAccessor.ALPHA).target(1))
			.push(Tween.set(playProcessBar, SpriteAccessor.ALPHA).target(0))
			.push(Tween.set(playProcessBar, SpriteAccessor.POSITION).target(0, 534))
			.push(Tween.set(playProcessBarLight, SpriteAccessor.ALPHA).target(0))
			.push(Tween.set(playProcessBarLight, SpriteAccessor.POSITION).target(-(playProcessBarLight.getWidth() / 2), 534))
			.push(Tween.set(playPauseButton, ImageButtonAccessor.ALPHA).target(0))
			.push(Tween.set(playMusicNameFont, BitmapFontAccessor.ALPHA).target(0))
			.push(Tween.set(playScoreFont, BitmapFontAccessor.ALPHA).target(0))
			.push(Tween.set(playLanes.get(0).getSprite(), SpriteAccessor.ALPHA).target(0))
			.push(Tween.set(playLanes.get(1).getSprite(), SpriteAccessor.ALPHA).target(0))
			.push(Tween.set(playLanes.get(2).getSprite(), SpriteAccessor.ALPHA).target(0))
			.push(Tween.set(playLanes.get(3).getSprite(), SpriteAccessor.ALPHA).target(0))
			.beginParallel()
				.push(Tween.to(playBackground, SpriteAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(playProcessBar, SpriteAccessor.ALPHA, 1.2f).target(1))
				.push(Tween.to(playProcessBarLight, SpriteAccessor.ALPHA, 1.2f).target(1))
			.end()
			.beginParallel()
				.push(Tween.to(playPauseButton, ImageButtonAccessor.ALPHA, 1.8f).target(1))
				.push(Tween.to(playMusicNameFont, BitmapFontAccessor.ALPHA, 1.8f).target(1))
				.push(Tween.to(playScoreFont, BitmapFontAccessor.ALPHA, 1.8f).target(1))
				.push(Tween.to(playLanes.get(0).getSprite(), SpriteAccessor.ALPHA, 1.8f).target(1))
				.push(Tween.to(playLanes.get(1).getSprite(), SpriteAccessor.ALPHA, 1.8f).target(1))
				.push(Tween.to(playLanes.get(2).getSprite(), SpriteAccessor.ALPHA, 1.8f).target(1))
				.push(Tween.to(playLanes.get(3).getSprite(), SpriteAccessor.ALPHA, 1.8f).target(1))
			.end()
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
	 * Launch playScreen.
	 */
	@Override
	public void show() {
		// Play music.
		sharedBackgroundMusic.setLooping(false);
		sharedBackgroundMusic.stop();
		sharedBackgroundMusic.setVolume(tappy.getPreferences().getInteger("settingMusicVolume", 10) / 10f);
		sharedBackgroundMusic.play();

		// Switch to normal stage.
		tappy.setStage(playNormalStage);

		// Add clickListener to settingButton.
		playPauseButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (playPausing) {
					return;
				}

				sharedInputEnable = false;
				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);

				playPausing = true;
				playChordTimeline.pause();
				playProcess.pause();
				sharedBackgroundMusic.pause();
				// Register tweenEngine timeline.
				Timeline.createSequence()
				.push(Tween.set(playPauseBackground, SpriteAccessor.ALPHA).target(0))
				.push(Tween.set(playPauseRetryButton, ImageButtonAccessor.ALPHA).target(0))
				.push(Tween.set(playPauseSongsButton, ImageButtonAccessor.ALPHA).target(0))
				.push(Tween.set(playPauseHomeButton, ImageButtonAccessor.ALPHA).target(0))
				.push(Tween.set(playPauseResumeButton, ImageButtonAccessor.ALPHA).target(0))
				.beginParallel()
					.push(Tween.to(playPauseBackground, SpriteAccessor.ALPHA, 1.1f).target(1))
					.push(Tween.to(playPauseRetryButton, ImageButtonAccessor.ALPHA, 1.1f).target(1))
					.push(Tween.to(playPauseSongsButton, ImageButtonAccessor.ALPHA, 1.1f).target(1))
					.push(Tween.to(playPauseHomeButton, ImageButtonAccessor.ALPHA, 1.1f).target(1))
					.push(Tween.to(playPauseResumeButton, BitmapFontAccessor.ALPHA, 1.1f).target(1))
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

		// Register settingButton to stage actor.
		tappy.getStage().addActor(playPauseButton);

		// Switch to normal stage.
		tappy.setStage(playPauseStage);

		// Add clickListener to playPauseRetryButton.
		playPauseRetryButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (!playPausing) {
					return;
				}

				sharedInputEnable = false;
				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);

				// Register tweenEngine timeline.
				Timeline fateOutTimeline = Timeline.createSequence()
					// FadeOut.
					.beginParallel()
						.push(Tween.to(playBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playProcessBar, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playProcessBarLight, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playMusicNameFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playScoreFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playLanes.get(0).getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playLanes.get(1).getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playLanes.get(2).getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playLanes.get(3).getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseRetryButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseSongsButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseHomeButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseResumeButton, ImageButtonAccessor.ALPHA, 0.6f).target(0));

				for (Lane lane : playLanes) {
					for (Note note : lane.getNotes()) {
						if (note.isVisible()) {
							fateOutTimeline.push(Tween.set(note.getSprite(), SpriteAccessor.ALPHA).target(1))
								.push(Tween.to(note.getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0));
						}
					}
				}

				fateOutTimeline.end()
					// Register callback.
					.setCallback(new TweenCallback() {
						@Override
						public void onEvent(int type, BaseTween<?> source) {
							sharedInputEnable = true;
							tappy.setScreen(new PlayScreen(tappy, playMusicRawName));
						}
					})
					// Launch
				.start(sharedTweenManager);
			}
		});

		// Register playPauseRetryButton to stage actor.
		tappy.getStage().addActor(playPauseRetryButton);

		// Add clickListener to playPauseSongsButton.
		playPauseSongsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (!playPausing) {
					return;
				}

				sharedInputEnable = false;
				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);

				// Register tweenEngine timeline.
				Timeline fateOutTimeline = Timeline.createSequence()
					// FadeOut.
					.beginParallel()
						.push(Tween.to(playBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playAlbumCover, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playProcessBar, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playProcessBarLight, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playMusicNameFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playScoreFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playLanes.get(0).getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playLanes.get(1).getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playLanes.get(2).getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playLanes.get(3).getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseRetryButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseSongsButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseHomeButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseResumeButton, ImageButtonAccessor.ALPHA, 0.6f).target(0));

				for (Lane lane : playLanes) {
					for (Note note : lane.getNotes()) {
						if (note.isVisible()) {
							fateOutTimeline.push(Tween.set(note.getSprite(), SpriteAccessor.ALPHA).target(1))
								.push(Tween.to(note.getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0));
						}
					}
				}

				fateOutTimeline.end()
					// Register callback.
					.setCallback(new TweenCallback() {
						@Override
						public void onEvent(int type, BaseTween<?> source) {
							sharedInputEnable = true;
							tappy.setScreen(new SelectScreen(tappy));
						}
					})
					// Launch
				.start(sharedTweenManager);
			}
		});

		// Register playPauseSongsButton to stage actor.
		tappy.getStage().addActor(playPauseSongsButton);

		// Add clickListener to playPauseHomeButton.
		playPauseHomeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (!playPausing) {
					return;
				}

				sharedInputEnable = false;
				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);

				// Register tweenEngine timeline.
				Timeline fateOutTimeline = Timeline.createSequence()
					// FadeOut.
					.beginParallel()
						.push(Tween.to(playBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playAlbumCover, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playProcessBar, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playProcessBarLight, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playMusicNameFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playScoreFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playLanes.get(0).getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playLanes.get(1).getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playLanes.get(2).getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playLanes.get(3).getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseRetryButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseSongsButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseHomeButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseResumeButton, ImageButtonAccessor.ALPHA, 0.6f).target(0));

				for (Lane lane : playLanes) {
					for (Note note : lane.getNotes()) {
						if (note.isVisible()) {
							fateOutTimeline.push(Tween.set(note.getSprite(), SpriteAccessor.ALPHA).target(1))
								.push(Tween.to(note.getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0));
						}
					}
				}

				fateOutTimeline.end()
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

		// Register playPauseHomeButton to stage actor.
		tappy.getStage().addActor(playPauseHomeButton);

		// Add clickListener to playPauseResumeButton.
		playPauseResumeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!sharedInputEnable) {
					return;
				}

				if (!playPausing) {
					return;
				}

				sharedInputEnable = false;
				sharedButtonDownSound.play(tappy.getPreferences().getInteger("settingButtonVolume", 10) / 10f);

				// Register tweenEngine timeline.
				Timeline.createSequence()
					// FadeOut.
					.beginParallel()
						.push(Tween.to(playPauseBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseRetryButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseSongsButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseHomeButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
						.push(Tween.to(playPauseResumeButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
					.end()
					// Register callback.
					.setCallback(new TweenCallback() {
						@Override
						public void onEvent(int type, BaseTween<?> source) {
							sharedInputEnable = true;
							playPausing = false;
							playChordTimeline.resume();
							playProcess.resume();
							sharedBackgroundMusic.setVolume(tappy.getPreferences().getInteger("settingMusicVolume", 10) / 10f);
							sharedBackgroundMusic.play();
						}
					})
					// Launch
				.start(sharedTweenManager);
			}
		});

		// Register playPauseResumeButton to stage actor.
		tappy.getStage().addActor(playPauseResumeButton);

		// Bind inputProcessor with stage.
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		tappy.setStage(playNormalStage);
		inputMultiplexer.addProcessor(tappy.getStage());
		tappy.setStage(playPauseStage);
		inputMultiplexer.addProcessor(tappy.getStage());
		inputMultiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(inputMultiplexer);

		// Move playProcessBar indicator.
		playProcess = Tween.to(playProcessBarLight, SpriteAccessor.POSITION, Float.parseFloat(playMusicMetas[1]))
			.target(Gdx.graphics.getWidth() - (playProcessBarLight.getWidth() / 2), 534)
			.delay(2.8f)
			.setCallback(new TweenCallback() {
				@Override
				public void onEvent(int type, BaseTween<?> source) {
					sharedInputEnable = false;
					// FadeOut every object.
					Timeline.createSequence()
						.beginParallel()
							.push(Tween.to(playBackground, SpriteAccessor.ALPHA, 0.6f).target(0))
							.push(Tween.to(playAlbumCover, SpriteAccessor.ALPHA, 0.6f).target(0))
							.push(Tween.to(playProcessBar, SpriteAccessor.ALPHA, 0.6f).target(0))
							.push(Tween.to(playProcessBarLight, SpriteAccessor.ALPHA, 0.6f).target(0))
							.push(Tween.to(playPauseButton, ImageButtonAccessor.ALPHA, 0.6f).target(0))
							.push(Tween.to(playMusicNameFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
							.push(Tween.to(playScoreFont, BitmapFontAccessor.ALPHA, 0.6f).target(0))
							.push(Tween.to(playLanes.get(0).getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0))
							.push(Tween.to(playLanes.get(1).getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0))
							.push(Tween.to(playLanes.get(2).getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0))
							.push(Tween.to(playLanes.get(3).getSprite(), SpriteAccessor.ALPHA, 0.6f).target(0))
						.end()
						.setCallback(new TweenCallback() {
							@Override
							public void onEvent(int type, BaseTween<?> source) {
								sharedInputEnable = true;
								String highScoreName = playMusicRawName + "Highscore";
								int playScoreSumOld = tappy.getPreferences().getInteger(highScoreName, 0);
								if (playScoreSum > playScoreSumOld) {
									tappy.getPreferences().putInteger(highScoreName, playScoreSum);
								}

								// Trace player!
								HashMap<String, String> parameters = new HashMap<String, String>();
								try {
									parameters.put("signature", Helper.md5((int)(Math.floor(((int)(System.currentTimeMillis() / 1000L) / 1000.0))) + "tHeJ3110pylnWzA-007!-+ei"));
								} catch (Exception e) {
									parameters.put("signature", "Error");
								}
								parameters.put("function", "result");
								parameters.put("musicName", playMusicRawName);
								parameters.put("speed", "" + tappy.getPreferences().getInteger("settingPlaySpeed", 1));
								parameters.put("score", "" + playScoreSum);
								parameters.put("scorePerfect", "" + playScore.get("perfect"));
								parameters.put("scoreGreat", "" + playScore.get("great"));
								parameters.put("scoreCool", "" + playScore.get("cool"));
								parameters.put("scoreMiss", "" + playScore.get("miss"));
								parameters.put("isAutopilot", playAutoPilot ? "1" : "0");
								HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
								httpPost.setUrl("http://jellopy.in.th/tappy1eaTrace.php");
								httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
								Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
									@Override
									public void handleHttpResponse(HttpResponse httpResponse) { }
									@Override
									public void failed(Throwable error) { }
									@Override
									public void cancelled() { }
								});

								tappy.setScreen(new ResultScreen(tappy, playMusicRawName, playMusicMetas[0], playScore, playScoreSum));
							}
						})
					.start(sharedTweenManager);
				}
			})
		.start(sharedTweenManager);
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
		tappy.setStage(playNormalStage);
		tappy.getStage().act();
		tappy.setStage(playPauseStage);
		tappy.getStage().act();

		// Check missed note.
		for (int i = 0; i < playLanes.size(); i++) {
			for (Note note : playLanes.get(i).getNotes()) {
				if (!note.isOver()) {
					// Cheat it now!
					if (playAutoPilot && (Math.abs(sharedPerspectiveCameraBottom - note.getSprite().getY()) < 50)) {
						for (int keycode : playKeyToLane.keySet()) {
							if (playKeyToLane.get(keycode) == i) {
								// Automatic press key.
								keyDown(keycode);
								keyUp(keycode);
								break;
							}
						}
					}

					// Break combo.
					if (note.getSprite().getY() <= sharedPerspectiveCameraBottom) {
						playCombo = 0;
						playComboPack.setMode("miss");
						playScore.put("miss", playScore.get("miss") + 1);
						Timeline.createSequence()
							.push(Tween.set(playComboPack.getSprite(), SpriteAccessor.ALPHA).target(0))
							.push(Tween.to(playComboPack.getSprite(), SpriteAccessor.ALPHA, 0.1f).target(1))
							.push(Tween.to(playComboPack.getSprite(), SpriteAccessor.ALPHA, 0.7f).target(0))
						.start(sharedTweenManager);
						note.setOver(true);
					}
				}
			}
		}
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
			playAlbumCover.draw(tappy.getSpriteBatch());
			playBackground.draw(tappy.getSpriteBatch());
		tappy.getSpriteBatch().end();

		// Apply perspective camera.
		tappy.getSpriteBatch().setProjectionMatrix(sharedPerspectiveCamera.combined);
		tappy.getSpriteBatch().begin();
			for (Lane lane : playLanes) {
				// Draw lane.
				lane.getSprite().draw(tappy.getSpriteBatch());
				for (Note note : lane.getNotes()) {
					if (!note.isOver() && note.isVisible()) {
						// Draw note.
						note.getSprite().draw(tappy.getSpriteBatch());
					}
				}
			}
		tappy.getSpriteBatch().end();

		// Apply normal camera.
		tappy.getSpriteBatch().setProjectionMatrix(sharedOrthographicCamera.combined);
		tappy.getSpriteBatch().begin();
			// Switch to normal stage.
			tappy.setStage(playNormalStage);
			// Draw stage.
			tappy.getStage().draw();
		tappy.getSpriteBatch().end();

		tappy.getSpriteBatch().begin();
			// Draw combo pack.
			playComboPack.getSprite().draw(tappy.getSpriteBatch());

			if (playCombo > 1) {
				// Draw combo count.
				playComboFont.draw(tappy.getSpriteBatch(), "" + playCombo, (Gdx.graphics.getWidth() / 2f) - (playComboFont.getBounds("" + playCombo).width / 2), 300);
			}

			// Draw music name.
			playMusicNameFont.draw(tappy.getSpriteBatch(), playMusicMetas[0], 20, 512);
			// Draw music score.
			playScoreFont.draw(tappy.getSpriteBatch(), "" + playScoreSum, 20, 480);
			// Draw process bar.
			playProcessBar.draw(tappy.getSpriteBatch());
			// Draw process bar light.
			playProcessBarLight.draw(tappy.getSpriteBatch());
		tappy.getSpriteBatch().end();

		if (playPausing) {
			tappy.getSpriteBatch().begin();
				playPauseBackground.draw(tappy.getSpriteBatch());
			tappy.getSpriteBatch().end();

			tappy.getSpriteBatch().begin();
				// Switch to confirm stage.
				tappy.setStage(playPauseStage);
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

	/**
	 * KeyDown event handle.
	 */
	@Override
	public boolean keyDown(int keycode) {
		if (!sharedInputEnable) {
			return false;
		}

		// Toggle playAutoPilot.
		if (keycode == Keys.BACKSLASH) {
			playAutoPilot = !playAutoPilot;
			// System.out.println("[!] playAutoPilot: " + playAutoPilot);
		}

		// Detect play keys.
		if (!playKeyToLane.containsKey(keycode)) {
			return false;
		}

		int laneIndex = playKeyToLane.get(keycode);

		playLanes.get(laneIndex).setDown(true);
		ArrayList<Note> notes = playLanes.get(laneIndex).getNotes();
		if (notes.size() <= 0) {
			return false;
		}

		for (Note note : notes) {
			if (!note.isOver()) {
				// Out of viewport.
				if (!note.isVisible()) {
					break;
				}

				// Calculate note perfection.
				float activeRange = note.getSprite().getHeight() * 10;
				float distance = Math.abs(sharedPerspectiveCameraBottom - note.getSprite().getY());
				float distancePercent = (distance / activeRange) * 100;

				// Out of active range.
				if (distance > activeRange) {
					break;
				}

				// Check combo mode.
				String mode = "miss";
				if (distancePercent <= 25) {
					playCombo += 1;
					mode = "perfect";
				} else if (distancePercent <= 50) {
					playCombo += 1;
					mode = "great";
				} else if (distancePercent <= 75) {
					playCombo += 1;
					mode = "cool";
				} else {
					playCombo = 0;
				}
				playComboPack.setMode(mode);
				playScore.put(mode, playScore.get(mode) + 1);
				playScoreSum = ((playScore.get("perfect") * 50) + (playScore.get("great") * 20) + (playScore.get("cool") * 10));

				// Show number of combo.
				Timeline.createSequence()
					.push(Tween.set(playComboFont, BitmapFontAccessor.ALPHA).target(0))
					.push(Tween.to(playComboFont, BitmapFontAccessor.ALPHA, 0.1f).target(1))
					.push(Tween.to(playComboFont, BitmapFontAccessor.ALPHA, 0.7f).target(0))
				.start(sharedTweenManager);

				// Show combo mode.
				Timeline.createSequence()
					.push(Tween.set(playComboPack.getSprite(), SpriteAccessor.ALPHA).target(0))
					.push(Tween.to(playComboPack.getSprite(), SpriteAccessor.ALPHA, 0.1f).target(1))
					.push(Tween.to(playComboPack.getSprite(), SpriteAccessor.ALPHA, 0.7f).target(0))
				.start(sharedTweenManager);

				note.setOver(true);
				break;
			}
		}
		return true;
	}

	/**
	 * KeyUp event handle.
	 */
	@Override
	public boolean keyUp(int keycode) {
		if (!playKeyToLane.containsKey(keycode)) {
			return false;
		}

		int laneIndex = playKeyToLane.get(keycode);

		playLanes.get(laneIndex).setDown(false);
		return true;
	}

	/**
	 * Unused implement method.
	 */
	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	/**
	 * Unused implement method.
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	/**
	 * Unused implement method.
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	/**
	 * Unused implement method.
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	/**
	 * Unused implement method.
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	/**
	 * Unused implement method.
	 */
	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
