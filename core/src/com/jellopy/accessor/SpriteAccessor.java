package com.jellopy.accessor;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * SpriteAccessor for tweenEngine.
 *
 * @author Jellopy
 */
public class SpriteAccessor implements TweenAccessor<Sprite> {
	// Transition opacity.
	public static final int ALPHA = 0;
	// Move the object position.
	public static final int POSITION = 1;
	// Change object size.
	public static final int SIZE = 2;

	/**
	 * Get the values.
	 *
	 * @param target
	 * @param tweenType
	 * @param returnValues
	 * @return The return values length.
	 */
	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		case POSITION:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			return 2;
		case SIZE:
			returnValues[0] = target.getWidth();
			returnValues[1] = target.getHeight();
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
	public void setValues(Sprite target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
			break;
		case POSITION:
			target.setPosition(newValues[0], newValues[1]);
			break;
		case SIZE:
			target.setSize(newValues[0], newValues[1]);
			break;
		default:
			assert false;
			break;
		}
	}

}
