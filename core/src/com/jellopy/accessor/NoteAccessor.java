package com.jellopy.accessor;

import aurelienribon.tweenengine.TweenAccessor;

import com.jellopy.game.Note;

/**
 * NoteAccessor for tweenEngine.
 *
 * @author Jellopy
 */
public class NoteAccessor implements TweenAccessor<Note> {
	// Transition opacity.
	public static final int ALPHA = 0;
	// Move the object position.
	public static final int POSITION = 1;

	/**
	 * Get the values.
	 *
	 * @param target
	 * @param tweenType
	 * @param returnValues
	 * @return The return values length.
	 */
	@Override
	public int getValues(Note target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case ALPHA:
			returnValues[0] = target.getSprite().getColor().a;
			return 1;
		case POSITION:
			returnValues[0] = target.getSprite().getX();
			returnValues[1] = target.getSprite().getY();
			return 2;
		default:
			assert false;
			return -1;
		}
	}

	/**
	 * Set the values.
	 *
	 * @param target
	 * @param tweenType
	 * @param newValues
	 */
	@Override
	public void setValues(Note target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case ALPHA:
			target.getSprite().setColor(target.getSprite().getColor().r, target.getSprite().getColor().g, target.getSprite().getColor().b, newValues[0]);
			break;
		case POSITION:
			target.getSprite().setPosition(newValues[0], newValues[1]);
			break;
		default:
			assert false;
			break;
		}
	}

}
