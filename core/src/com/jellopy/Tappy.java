package com.jellopy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jellopy.screen.SplashScreen;

/**
 * THIS IS A BEST RYTHM GAME 4 EVER!
 *
 * @author Jellopy
 */
public class Tappy extends Game {
	// Main draw zone.
	private SpriteBatch spriteBatch;

	// Application skin.
	private Skin skin;

	// Current game stage.
	private Stage stage;

	// Game setting.
	private Preferences preferences;

	/**
	 * Begin of Tappy 1ea.
	 */
	@Override
	public void create() {
		// System.out.println("[+] Launching...");
		// Create main draw zone.
		spriteBatch = new SpriteBatch();

		// Set application skin.
		skin = new Skin(Gdx.files.internal("skin/tappy.json"));

		preferences = Gdx.app.getPreferences("preferences");

		// Load all resource.
		Assets.initialize();

		// Call slpashScreen.
		setScreen(new SplashScreen(this));
	}

	/**
	 * Render everything!
	 */
	@Override
	public void render() {
		super.render();
	}

	/**
	 * Free-up all resource.
	 */
	@Override
	public void dispose() {
		super.dispose();
		Assets.unload();
		preferences.flush();
		// System.out.println("[-] Terminated...");
	}

	/**
	 * SpriteBatch getter.
	 * @return SpriteBatch
	 */
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	/**
	 * Skin getter.
	 * @return Skin
	 */
	public Skin getSkin() {
		return skin;
	}

	/**
	 * Stage getter.
	 * @return Stage
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * Preferences getter.
	 * @return Skin
	 */
	public Preferences getPreferences() {
		return preferences;
	}

	/**
	 * Stage setter.
	 * @param stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
