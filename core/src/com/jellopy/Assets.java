package com.jellopy;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * The Assets
 * Contains all resource of game.
 *
 * @author Jellopy
 */
public class Assets {
	// All textures.
	private static HashMap<String, Texture> textures;

	// All sounds.
	private static HashMap<String, Sound> sounds;

	// All musics.
	private static HashMap<String, Music> musics;

	// All fonts.
	private static HashMap<String, FreeTypeFontGenerator> fonts;

	/**
	 * Assets initializer.
	 */
	public static void initialize() {
		textures = new HashMap<String, Texture>();
		sounds = new HashMap<String, Sound>();
		musics = new HashMap<String, Music>();
		fonts = new HashMap<String, FreeTypeFontGenerator>();

		// Shared.
		loadMusic("sharedBackgroundMusic", "audio/sharedBackgroundMusic.ogg");
		loadSound("sharedButtonDownSound", "audio/sharedButtonDownSound.ogg");
		loadFont("zeroesTwoFont", "fonts/zeroesTwo.ttf");
		loadFont("codeBoldFont", "fonts/codeBold.ttf");

		// SplashScreen.
		loadTexture("splashHeadphone", "texture/splashHeadphone.jpg");
		loadTexture("splashBackground", "texture/splashBackground.jpg");

		// TutorialScreen.
		loadTexture("tutorialBackground", "texture/tutorialBackground.jpg");
		loadTexture("tutorialPlayButtonUp", "texture/tutorialPlayButtonUp.png");
		loadTexture("tutorialPlayButtonOver", "texture/tutorialPlayButtonOver.png");
		loadTexture("tutorialPlayButtonDown", "texture/tutorialPlayButtonDown.png");

		// HomeScreen.
		loadTexture("homeBackground", "texture/homeBackground.jpg");

		loadTexture("homeStartButtonUp", "texture/homeStartButtonUp.png");
		loadTexture("homeStartButtonOver", "texture/homeStartButtonOver.png");
		loadTexture("homeStartButtonDown", "texture/homeStartButtonDown.png");

		loadTexture("homeSettingButtonUp", "texture/homeSettingButtonUp.png");
		loadTexture("homeSettingButtonOver", "texture/homeSettingButtonOver.png");
		loadTexture("homeSettingButtonDown", "texture/homeSettingButtonDown.png");

		// SelectScreen.
		loadTexture("selectBackground", "texture/selectBackground.png");
		loadTexture("selectForeground", "texture/selectForeground.png");
		loadTexture("selectConfirmBackground", "texture/selectConfirmBackground.png");

		loadTexture("selectHomeButtonUp", "texture/selectHomeButtonUp.png");
		loadTexture("selectHomeButtonOver", "texture/selectHomeButtonOver.png");
		loadTexture("selectHomeButtonDown", "texture/selectHomeButtonDown.png");

		loadTexture("selectSettingButtonUp", "texture/selectSettingButtonUp.png");
		loadTexture("selectSettingButtonOver", "texture/selectSettingButtonOver.png");
		loadTexture("selectSettingButtonDown", "texture/selectSettingButtonDown.png");

		loadTexture("selectLeftButtonUp", "texture/selectLeftButtonUp.png");
		loadTexture("selectLeftButtonOver", "texture/selectLeftButtonOver.png");
		loadTexture("selectLeftButtonDown", "texture/selectLeftButtonDown.png");

		loadTexture("selectRightButtonUp", "texture/selectRightButtonUp.png");
		loadTexture("selectRightButtonOver", "texture/selectRightButtonOver.png");
		loadTexture("selectRightButtonDown", "texture/selectRightButtonDown.png");

		loadTexture("selectPlayButtonUp", "texture/selectPlayButtonUp.png");
		loadTexture("selectPlayButtonOver", "texture/selectPlayButtonOver.png");
		loadTexture("selectPlayButtonDown", "texture/selectPlayButtonDown.png");

		loadTexture("selectConfirmPlayButtonUp", "texture/selectConfirmPlayButtonUp.png");
		loadTexture("selectConfirmPlayButtonOver", "texture/selectConfirmPlayButtonOver.png");
		loadTexture("selectConfirmPlayButtonDown", "texture/selectConfirmPlayButtonDown.png");

		loadTexture("selectConfirmMinusButtonUp", "texture/selectConfirmMinusButtonUp.png");
		loadTexture("selectConfirmMinusButtonOver", "texture/selectConfirmMinusButtonOver.png");
		loadTexture("selectConfirmMinusButtonDown", "texture/selectConfirmMinusButtonDown.png");

		loadTexture("selectConfirmPlusButtonUp", "texture/selectConfirmPlusButtonUp.png");
		loadTexture("selectConfirmPlusButtonOver", "texture/selectConfirmPlusButtonOver.png");
		loadTexture("selectConfirmPlusButtonDown", "texture/selectConfirmPlusButtonDown.png");

		// SettingScreen.
		loadTexture("settingBackground", "texture/settingBackground.jpg");

		loadTexture("settingHomeButtonUp", "texture/settingHomeButtonUp.png");
		loadTexture("settingHomeButtonOver", "texture/settingHomeButtonOver.png");
		loadTexture("settingHomeButtonDown", "texture/settingHomeButtonDown.png");

		loadTexture("settingCreditButtonUp", "texture/settingCreditButtonUp.png");
		loadTexture("settingCreditButtonOver", "texture/settingCreditButtonOver.png");
		loadTexture("settingCreditButtonDown", "texture/settingCreditButtonDown.png");

		loadTexture("settingMinusButtonUp", "texture/settingMinusButtonUp.png");
		loadTexture("settingMinusButtonOver", "texture/settingMinusButtonOver.png");
		loadTexture("settingMinusButtonDown", "texture/settingMinusButtonDown.png");

		loadTexture("settingPlusButtonUp", "texture/settingPlusButtonUp.png");
		loadTexture("settingPlusButtonOver", "texture/settingPlusButtonOver.png");
		loadTexture("settingPlusButtonDown", "texture/settingPlusButtonDown.png");

		// PlayScreen
		loadTexture("playBackground", "texture/playBackground.png");
		loadTexture("playPauseBackground", "texture/playPauseBackground.png");

		loadTexture("playProcessBar", "texture/playProcessBar.png");
		loadTexture("playProcessBarLight", "texture/playProcessBarLight.png");

		loadTexture("playPauseButtonUp", "texture/playPauseButtonUp.png");
		loadTexture("playPauseButtonOver", "texture/playPauseButtonOver.png");
		loadTexture("playPauseButtonDown", "texture/playPauseButtonDown.png");

		loadTexture("playPauseRetryButtonUp", "texture/playPauseRetryButtonUp.png");
		loadTexture("playPauseRetryButtonOver", "texture/playPauseRetryButtonOver.png");
		loadTexture("playPauseRetryButtonDown", "texture/playPauseRetryButtonDown.png");

		loadTexture("playPauseSongsButtonUp", "texture/playPauseSongsButtonUp.png");
		loadTexture("playPauseSongsButtonOver", "texture/playPauseSongsButtonOver.png");
		loadTexture("playPauseSongsButtonDown", "texture/playPauseSongsButtonDown.png");

		loadTexture("playPauseHomeButtonUp", "texture/playPauseHomeButtonUp.png");
		loadTexture("playPauseHomeButtonOver", "texture/playPauseHomeButtonOver.png");
		loadTexture("playPauseHomeButtonDown", "texture/playPauseHomeButtonDown.png");

		loadTexture("playPauseResumeButtonUp", "texture/playPauseResumeButtonUp.png");
		loadTexture("playPauseResumeButtonOver", "texture/playPauseResumeButtonOver.png");
		loadTexture("playPauseResumeButtonDown", "texture/playPauseResumeButtonDown.png");

		loadTexture("playComboPerfect", "texture/playComboPerfect.png");
		loadTexture("playComboGreat", "texture/playComboGreat.png");
		loadTexture("playComboCool", "texture/playComboCool.png");
		loadTexture("playComboMiss", "texture/playComboMiss.png");

		loadTexture("playLane", "texture/playLane.png");
		loadTexture("playLaneDown", "texture/playLaneDown.png");

		loadTexture("playNote", "texture/playNote.png");

		// ResultScreen
		loadTexture("resultBackground", "texture/resultBackground.jpg");

		loadTexture("resultReplayButtonUp", "texture/resultReplayButtonUp.png");
		loadTexture("resultReplayButtonOver", "texture/resultReplayButtonOver.png");
		loadTexture("resultReplayButtonDown", "texture/resultReplayButtonDown.png");

		loadTexture("resultSongsButtonUp", "texture/resultSongsButtonUp.png");
		loadTexture("resultSongsButtonOver", "texture/resultSongsButtonOver.png");
		loadTexture("resultSongsButtonDown", "texture/resultSongsButtonDown.png");

		loadTexture("resultHomeButtonUp", "texture/resultHomeButtonUp.png");
		loadTexture("resultHomeButtonOver", "texture/resultHomeButtonOver.png");
		loadTexture("resultHomeButtonDown", "texture/resultHomeButtonDown.png");

		// CreditScreen
		loadTexture("creditBackground", "texture/creditBackground.jpg");
		loadTexture("creditHomeButtonUp", "texture/creditHomeButtonUp.png");
		loadTexture("creditHomeButtonOver", "texture/creditHomeButtonOver.png");
		loadTexture("creditHomeButtonDown", "texture/creditHomeButtonDown.png");
	}

	/**
	 * Load specified texture.
	 *
	 * @param name
	 * @param path
	 */
	public static void loadTexture(String name, String path) {
		if (textures.get(name) != null) {
			return;
		}

		// System.out.println("[+] Loaded '" + path + "' as texture '" + name + "'.");
		textures.put(name, new Texture(Gdx.files.internal(path)));
	}

	/**
	 * Load specified sound.
	 *
	 * @param name
	 * @param path
	 */
	public static void loadSound(String name, String path) {
		if (sounds.get(name) != null) {
			return;
		}

		sounds.put(name, Gdx.audio.newSound(Gdx.files.internal(path)));
	}

	/**
	 * Load specified music.
	 *
	 * @param name
	 * @param path
	 */
	public static void loadMusic(String name, String path) {
		if (musics.get(name) != null) {
			return;
		}

		musics.put(name, Gdx.audio.newMusic(Gdx.files.internal(path)));
	}

	/**
	 * Load specified font.
	 *
	 * @param name
	 * @param path
	 */
	public static void loadFont(String name, String path) {
		if (fonts.get(name) != null) {
			return;
		}

		// System.out.println("[+] Loaded '" + path + "' as font '" + name + "'.");
		fonts.put(name, new FreeTypeFontGenerator(Gdx.files.internal(path)));
	}

	/**
	 * Free textures resource to system.
	 */
	public static void unloadTextures() {
		for (String name : textures.keySet()) {
			textures.get(name).dispose();
			// System.out.println("[-] Unloaded texture '" + name + "'.");
		}
	}

	/**
	 * Free sounds resource to system.
	 */
	public static void unloadSounds() {
		for (String name : sounds.keySet()) {
			sounds.get(name).dispose();
			// System.out.println("[-] Unloaded sound '" + name + "'.");
		}
	}

	/**
	 * Free musics resource to system.
	 */
	public static void unloadMusics() {
		for (String name : musics.keySet()) {
			musics.get(name).dispose();
			// System.out.println("[-] Unloaded music '" + name + "'.");
		}
	}

	/**
	 * Free font resource to system.
	 */
	public static void unloadFonts() {
		for (String name : fonts.keySet()) {
			fonts.get(name).dispose();
			// System.out.println("[-] Unloaded font '" + name + "'.");
		}
	}

	/**
	 * Free all resource to system.
	 */
	public static void unload() {
		unloadTextures();
		unloadSounds();
		unloadMusics();
		unloadFonts();
	}

	/**
	 * Generate new sprite from specified texture.
	 *
	 * @param name
	 * @return Sprite which the specified name, or null if name not found.
	 */
	public static Sprite getSprite(String name) {
		// System.out.println("[+] Generated sprite '" + name + "'.");
		return new Sprite(getTexture(name));
	}

	/**
	 * Get specified texture.
	 *
	 * @param name
	 * @return Texture which the specified name, or null if name not found.
	 */
	public static Texture getTexture(String name) {
		return textures.get(name);
	}

	/**
	 * Get specified sound.
	 *
	 * @param name
	 * @return Sound which the specified name, or null if name not found.
	 */
	public static Sound getSound(String name) {
		return sounds.get(name);
	}

	/**
	 * Get specified music.
	 *
	 * @param name
	 * @return Music which the specified name, or null if name not found.
	 */
	public static Music getMusic(String name) {
		return musics.get(name);
	}


	/**
	 * Get specified font.
	 *
	 * @param name
	 * @return Font which the specified name, or null if name not found.
	 */
	public static FreeTypeFontGenerator getFont(String name) {
		return fonts.get(name);
	}
}
