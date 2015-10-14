package com.jellopy.accessor;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * BitmapFontAccessor for tweenEngine.
 *
 * @author Jellopy
 */
public class BitmapFontAccessor implements TweenAccessor<BitmapFont> {
	// Transition opacity.
	public static final int ALPHA = 0;

	/**
	 * Get the values.
	 *
	 * @param target
	 * @param tweenType
	 * @param returnValues
	 * @return The return values length.
	 */
	@Override
	public int getValues(BitmapFont target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
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
	public void setValues(BitmapFont target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
			break;
		default:
			assert false;
			break;
		}
	}

}
