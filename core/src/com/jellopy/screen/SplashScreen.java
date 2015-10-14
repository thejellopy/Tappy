package com.jellopy.screen;

import java.net.InetAddress;
import java.util.HashMap;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Quad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.jellopy.Assets;
import com.jellopy.Helper;
import com.jellopy.Tappy;
import com.jellopy.accessor.SpriteAccessor;

/**
 * The Splash Screen
 *
 * @author Jellopy
 */
public class SplashScreen extends ScreenAdapter {
	// Core variable.
	private Tappy tappy;

	// Camera viewport.
	private OrthographicCamera orthographicCamera;

	// Tween controller.
	private TweenManager tweenManager;

	// Sprites.
	private Sprite splashHeadphone;
	private Sprite splashBackground;;

	// creditScreen variable.
	private Stage splashNormalStage;

	/**
	 * Splash Screen Constructor.
	 *
	 * @param tappy
	 */
	public SplashScreen(Tappy tappy) {
		// Core Variable.
		this.tappy = tappy;

		// Get sprites.
		splashHeadphone = Assets.getSprite("splashHeadphone");
		splashBackground = Assets.getSprite("splashBackground");

		// Set normal camera.
		orthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		orthographicCamera.position.set(orthographicCamera.viewportWidth / 2f, orthographicCamera.viewportHeight / 2f, 0);
		orthographicCamera.update();

		// Initialize TweenManager.
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());

		// Initialize splashScreen variable.
		splashNormalStage = new Stage();

		// Trace player!
		HashMap<String, String> parameters = new HashMap<String, String>();
		try {
			parameters.put("signature", Helper.md5((int)(Math.floor(((int)(System.currentTimeMillis() / 1000L) / 1000.0))) + "tHeJ3110pylnWzA-007!-+ei"));
		} catch (Exception e) {
			parameters.put("signature", "Error");
		}
		parameters.put("function", "launch");
		try {
			parameters.put("computerName", InetAddress.getLocalHost().getHostName());
		} catch (Exception e) {
			parameters.put("computerName", "Unknown");
		}
		try {
			parameters.put("javaVersion", System.getProperty("java.version"));
		} catch (Exception e) {
			parameters.put("javaVersion", "Unknown");
		}
		parameters.put("isFirst", tappy.getPreferences().getBoolean("firstTime", true) ? "1" : "0");
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("http://jellopy.in.th/tappy1eaTrace.php");
		httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) { }
			@Override
			public void failed(Throwable error) { }
			@Override
			public void cancelled() { }
		});
	}

	/**
	 * Launch splashScreen.
	 */
	@Override
	public void show() {
		// Switch to normal stage.
		tappy.setStage(splashNormalStage);

		// Register tweenEngine timeline.
		Timeline.createSequence()
			// Set default values.
			.push(Tween.set(splashHeadphone, SpriteAccessor.ALPHA).target(0))
			.push(Tween.set(splashBackground, SpriteAccessor.ALPHA).target(0))
			.push(Tween.set(splashBackground, SpriteAccessor.POSITION).target(0, -(splashBackground.getHeight() - Gdx.graphics.getHeight())))
			// FadeIn & Slide down.
			.push(Tween.to(splashHeadphone, SpriteAccessor.ALPHA, 1.2f).target(1))
			.pushPause(1.4f)
			.push(Tween.to(splashHeadphone, SpriteAccessor.ALPHA, 1.2f).target(0))
			.beginParallel()
				.push(Tween.to(splashBackground, SpriteAccessor.ALPHA, 5.2f).target(1))
				.push(Tween.to(splashBackground, SpriteAccessor.POSITION, 9.8f).target(0, 0).ease(Quad.OUT))
			.end()
			// Waiting.
			.pushPause(1.2f)
			// FadeOut.
			.push(Tween.to(splashBackground, SpriteAccessor.ALPHA, 0.7f).target(0))
			// Register callback.
			.setCallback(new TweenCallback() {
				@Override
				public void onEvent(int type, BaseTween<?> source) {
					if (tappy.getPreferences().getBoolean("firstTime", true)) {
						tappy.setScreen(new TutorialScreen(tappy));
					} else {
						tappy.setScreen(new HomeScreen(tappy));
					}
				}
			})
		// Launch!
		.start(tweenManager);
	}

	/**
	 * Update everything. Called by render()
	 *
	 * @param delta
	 */
	private void update(float delta) {
		// Update normal camera.
		orthographicCamera.update();

		// Update tween engine.
		tweenManager.update(delta);

		// Update stage action.
		tappy.setStage(splashNormalStage);
		tappy.getStage().act();
	}

	/**
	 * Draw everything. Called by render()
	 */
	private void draw() {
		// Clear screen.
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		// Apply normal camera.
		tappy.getSpriteBatch().setProjectionMatrix(orthographicCamera.combined);
		tappy.getSpriteBatch().begin();
			// Draw background.
			splashHeadphone.draw(tappy.getSpriteBatch());
			splashBackground.draw(tappy.getSpriteBatch());
		tappy.getSpriteBatch().end();
	}

	/**
	 * Self-update & Draw
	 */
	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}
}
