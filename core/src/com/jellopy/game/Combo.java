package com.jellopy.game;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jellopy.Assets;

/**
 * Game play combo sprite pack.
 *
 * @author Jellopy
 */
public class Combo {
	// All sprites.
	private HashMap<String, Sprite> sprites;

	// Combo mode.
	private String mode;

	/**
	 * Combo constructor.
	 */
	public Combo() {
		sprites = new HashMap<String, Sprite>();
		mode = "miss";

		// Get sprites.
		sprites.put("perfect", Assets.getSprite("playComboPerfect"));
		sprites.put("great", Assets.getSprite("playComboGreat"));
		sprites.put("cool", Assets.getSprite("playComboCool"));
		sprites.put("miss", Assets.getSprite("playComboMiss"));

		// Set default sprite value.
		for (String name : sprites.keySet()) {
			sprites.get(name).setPosition(380, 255);
			sprites.get(name).setColor(sprites.get(name).getColor().r, sprites.get(name).getColor().g, sprites.get(name).getColor().b, 0);
		}	}

	/**
	 * Get sprite.
	 *
	 * @return Sprite sprite.
	 */
	public Sprite getSprite() {
		return sprites.get(mode);
	}

	/**
	 * Get mode.
	 *
	 * @return String mode.
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * Set mode.
	 *
	 * @param name.
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
}
