package com.jellopy.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jellopy.Assets;

/**
 * Game play lane object.
 *
 * @author Jellopy
 */
public class Lane {
	// All notes.
	private ArrayList<Note> notes;

	// Lane sprite
	private Sprite sprite;
	private Sprite spriteDown;

	// Lane activity.
	private boolean down;

	/**
	 * Lane constructor with default position.
	 *
	 * @param x
	 * @param y
	 */
	public Lane(float x, float y) {
		// Get default lane.
		this.sprite = Assets.getSprite("playLane");
		this.sprite.setPosition(x, y);
		this.sprite.setSize(80, 692);

		// Get active lane.
		this.spriteDown = Assets.getSprite("playLaneDown");
		this.spriteDown.setPosition(x, y);
		this.spriteDown.setSize(80, 692);
		notes = new ArrayList<Note>();
	}

	/**
	 * Add note to lane (Generate new Note by y coordinate).
	 *
	 * @param y
	 */
	public void addNote(float y) {
		notes.add(new Note(getSprite().getX(), y));
	}

	/**
	 * Add note to lane (passing Note object).
	 *
	 * @param note
	 */
	public void addNote(Note note) {
		notes.add(note);
	}

	/**
	 * Get notes.
	 *
	 * @return ArrayList of all notes.
	 */
	public ArrayList<Note> getNotes() {
		return notes;
	}

	/**
	 * Get sprite by lane activity.
	 *
	 * @return Sprite spriteDown when lane is active, sprite otherwise.
	 */
	public Sprite getSprite() {
		if (isDown()) {
			return spriteDown;
		}
		return sprite;
	}

	/**
	 * Check lane is active.
	 *
	 * @return Boolean true when lane is active, false otherwise.
	 */
	public boolean isDown() {
		return down;
	}

	/**
	 * Set lane activity.
	 *
	 * @param down
	 */
	public void setDown(boolean down) {
		this.down = down;
	}
}
