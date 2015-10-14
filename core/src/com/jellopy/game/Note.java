package com.jellopy.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jellopy.Assets;

/**
 * Game play note object.
 *
 * @author Jellopy
 */
public class Note {
	// Note sprite.
	private Sprite sprite;

	// Note status.
	private boolean over;

	/**
	 * Note constructor with default position.
	 *
	 * @param x
	 * @param y
	 */
	public Note(float x, float y) {
		// Get note.
		this.sprite = Assets.getSprite("playNote");
		this.sprite.setPosition(x, y);
		this.sprite.setSize(80, 30);

		// Set default status.
		over = false;
	}

	/**
	 * Check note is visible in game viewport.
	 *
	 * @return Boolean true when note in game viewport, false otherwise.
	 */
	public boolean isVisible() {
		return (sprite.getY() > -161f) && (sprite.getY() < (531f + sprite.getHeight()));
	}

	/**
	 * Get note sprite.
	 *
	 * @return Sprite sprite.
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * Get note status.
	 *
	 * @return Boolean true when note is over, false otherwise.
	 */
	public boolean isOver() {
		return over;
	}

	/**
	 * Set note status.
	 *
	 * @param over
	 */
	public void setOver(boolean over) {
		this.over = over;
	}
}
