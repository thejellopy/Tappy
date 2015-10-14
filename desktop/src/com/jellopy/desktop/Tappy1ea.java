package com.jellopy.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jellopy.Tappy;

/**
 * THIS IS A BEST RYTHM GAME 4 EVER!
 *
 * @author Jellopy
 */
public class Tappy1ea {
	/**
	 * MAIN METHOD.
	 * @param arg
	 */
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.addIcon("icon/icon-128.png", FileType.Internal);
		config.addIcon("icon/icon-32.png", FileType.Internal);
		config.addIcon("icon/icon-16.png", FileType.Internal);
		config.title = "Tappy 1ea - v.1.1";
		config.width = 960;
		config.height = 540;
		config.resizable = false;
		config.preferencesDirectory = ".tappy/";
		config.fullscreen = false;
		config.vSyncEnabled = true;
		new LwjglApplication(new Tappy(), config);
	}
}
