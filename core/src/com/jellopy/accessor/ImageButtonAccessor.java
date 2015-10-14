package com.jellopy.accessor;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

/**
 * ImageButtonAccessor for tweenEngine.
 *
 * @author Jellopy
 */
public class ImageButtonAccessor implements TweenAccessor<ImageButton> {
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
	public int getValues(ImageButton target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		case POSITION:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
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
	public void setValues(ImageButton target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
			break;
		case POSITION:
			target.setPosition(newValues[0], newValues[1]);
			break;
		default:
			assert false;
			break;
		}
	}

}
