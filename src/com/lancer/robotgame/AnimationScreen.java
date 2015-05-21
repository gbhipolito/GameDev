package com.lancer.robotgame;

import javax.microedition.khronos.opengles.GL10;

import com.lancer.robotgame.framework.Game;
import com.lancer.robotgame.framework.GameScreen;
import com.lancer.robotgame.framework.gl.Camera2D;
import com.lancer.robotgame.framework.gl.SpriteAnimation;
import com.lancer.robotgame.framework.gl.SpriteBatcher;
import com.lancer.robotgame.framework.gl.Texture;
import com.lancer.robotgame.framework.gl.TextureRegion;
import com.lancer.robotgame.framework.implementation.AndroidGLGraphics;
import com.lancer.robotgame.utils.FPSCounter;

public class AnimationScreen extends GameScreen {
	static final float WORLD_WIDTH = 4.8f;
	static final float WORLD_HEIGHT = 3.2f;
	static final int NUM_NOVICE = 10;

	AndroidGLGraphics glGraphics;
	Novice[] novices;
	SpriteBatcher batcher;
	Camera2D camera;
	Texture texture;
	SpriteAnimation walkAnim;

	FPSCounter fpsCounter = new FPSCounter();

	public AnimationScreen(Game game) {
		super(game);
		glGraphics = ((GLGame) game).getGLGraphics();
		novices = new Novice[NUM_NOVICE];
		for (int i = 0; i < NUM_NOVICE; i++) {
			novices[i] = new Novice((float) Math.random(), (float) Math.random(), 1, 1);
		}
		batcher = new SpriteBatcher(glGraphics, NUM_NOVICE);
		camera = new Camera2D(glGraphics, WORLD_WIDTH, WORLD_HEIGHT);
	}

	@Override
	public void update(float deltaTime) {
		int len = novices.length;
		for (int i = 0; i < len; i++) {
			novices[i].update(deltaTime);
		}
	}

	@Override
	public void paint(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.setViewportAndMatrices();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		batcher.beginBatch(texture);
		int len = novices.length;
		for (int i = 0; i < len; i++) {
			Novice novice = novices[i];
			TextureRegion keyFrame = walkAnim.getKeyFrame(novice.walkingTime, SpriteAnimation.ANIMATION_LOOPING);
//			TextureRegion tr = new TextureRegion(texture, 0, 0, 64, 64);
			batcher.drawSprite(novice.position.x, novice.position.y, novice.velocity.x < 0 ? 1 : -1, 1, keyFrame);
		}
		batcher.endBatch();
		
		fpsCounter.logFrame();
	} // paint

	@Override
	public void pause() {}

	@Override
	public void resume() {
		texture = new Texture(((GLGame) game), "walkanim4.png");
		walkAnim = new SpriteAnimation(0.2f,
				new TextureRegion(texture, 0, 0, 64, 64),
				new TextureRegion(texture, 64, 0, 64, 64),
				new TextureRegion(texture, 128, 0, 64, 64),
				new TextureRegion(texture, 192, 0, 64, 64),
				new TextureRegion(texture, 256, 0, 64, 64),
				new TextureRegion(texture, 320, 0, 64, 64),
				new TextureRegion(texture, 384, 0, 64, 64),
				new TextureRegion(texture, 448, 0, 64, 64));
	}

	@Override
	public void dispose() {}

	@Override
	public void backButton() {}

} // AnimationScreen