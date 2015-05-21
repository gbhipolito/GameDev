package com.lancer.robotgame;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;

import com.lancer.robotgame.framework.DynamicGameObject;
import com.lancer.robotgame.framework.Game;
import com.lancer.robotgame.framework.GameInput.GameTouchEvent;
import com.lancer.robotgame.framework.GameObject;
import com.lancer.robotgame.framework.GameScreen;
import com.lancer.robotgame.framework.SpatialHashGrid;
import com.lancer.robotgame.framework.gl.Camera2D;
import com.lancer.robotgame.framework.gl.SpriteBatcher;
import com.lancer.robotgame.framework.gl.Texture;
import com.lancer.robotgame.framework.gl.TextureRegion;
import com.lancer.robotgame.framework.implementation.AndroidGLGraphics;
import com.lancer.robotgame.framework.math.OverlapTester;
import com.lancer.robotgame.framework.math.Vector2;
import com.lancer.robotgame.utils.FPSCounter;

public class SpriteBatcherScreen extends GameScreen {
	final int NUM_TARGETS = 20;
	final float WORLD_WIDTH = 9.6f;
	final float WORLD_HEIGHT = 4.8f;
	AndroidGLGraphics glGraphics;
	Cannon cannon;
	DynamicGameObject ball;
	List<GameObject> targets;
	SpatialHashGrid grid;
	Camera2D camera;
	
	Vector2 touchPos = new Vector2();
	Vector2 gravity = new Vector2(0, -10);
	
	Texture texture;
	
	TextureRegion cannonRegion;
	TextureRegion ballRegion;
	TextureRegion bobRegion;
	SpriteBatcher batcher;

	FPSCounter fpsCounter = new FPSCounter();
	
	public SpriteBatcherScreen(Game game) {
		super(game);
		glGraphics = ((GLGame)game).getGLGraphics();
		cannon = new Cannon(0, 0, 1, 0.5f);
		ball = new DynamicGameObject(0, 0, 0.2f, 0.2f);
		targets = new ArrayList<GameObject>(NUM_TARGETS);
		grid = new SpatialHashGrid(WORLD_WIDTH, WORLD_HEIGHT, 2.5f);
		camera = new Camera2D(glGraphics, WORLD_WIDTH, WORLD_HEIGHT);
		for( int i = 0; i < NUM_TARGETS; i++ ) {
			GameObject target = new GameObject((float)Math.random() * WORLD_WIDTH,
												(float)Math.random() * WORLD_HEIGHT,
												0.5f, 0.5f);
			grid.insertStaticObject(target);
			targets.add(target);
		}
		batcher = new SpriteBatcher(glGraphics, 105);
	} // constructor

	@Override
	public void update(float deltaTime) {
		List<GameTouchEvent> touchEvents = game.getInput().getTouchEvents();
		
		int len = touchEvents.size();
		for( int i = 0; i < len; i++ ) {
			GameTouchEvent event = touchEvents.get(i);
			
			camera.touchToWorld(touchPos.set(event.x, event.y));
			
			cannon.angle = touchPos.sub(cannon.position).angle();
			
			if( event.type == GameTouchEvent.TOUCH_UP ) {
				float radians = cannon.angle * Vector2.TO_RADIANS;
				float ballSpeed = touchPos.len() * 2;
				ball.position.set(cannon.position);
				ball.velocity.x = FloatMath.cos(radians) * ballSpeed;
				ball.velocity.y = FloatMath.sin(radians) * ballSpeed;
				ball.bounds.lowerLeft.set( ball.position.x - 0.1f, ball.position.y - 0.1f );
			}
		} // end for( int i = 0; i < len; i++ )
		
		ball.velocity.add(gravity.x * deltaTime, gravity.y * deltaTime);
		ball.position.add(ball.velocity.x * deltaTime, ball.velocity.y * deltaTime);
		ball.bounds.lowerLeft.add(ball.velocity.x * deltaTime, ball.velocity.y * deltaTime);
		
		List<GameObject> colliders = grid.getPotentialColliders(ball);
		len = colliders.size();
		
		for( int i = 0; i < len; i++ ) {
			GameObject collider = colliders.get(i);
			if( OverlapTester.overlapRectangles(ball.bounds, collider.bounds) ) {
				grid.removeObject(collider);
				targets.remove(collider);
			}
		}
		
		if( ball.position.y > 0 ) {
			camera.position.set(ball.position);
			camera.zoom = 1 + ball.position.y / WORLD_HEIGHT;
		} else {
			camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
			camera.zoom = 1;
		}
	} // update

	@Override
	public void paint(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.setViewportAndMatrices();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_TEXTURE_2D);

		batcher.beginBatch(texture);
		int len = targets.size();
		for( int i = 0; i < len; i++ ) {
			GameObject target = targets.get(i);
			batcher.drawSprite(target.position.x, target.position.y, 0.5f, 0.5f, bobRegion);
		}
		
		batcher.drawSprite(ball.position.x, ball.position.y, 0.2f, 0.2f, ballRegion);
		batcher.drawSprite(cannon.position.x, cannon.position.y, 1, 0.5f, cannon.angle, cannonRegion);
		batcher.endBatch();
		
		fpsCounter.logFrame();
	} // paint

	@Override
	public void pause() {}

	@Override
	public void resume() {
		texture = new Texture(((GLGame) game), "atlas.png");
		cannonRegion = new TextureRegion(texture, 0, 0, 64, 32);
		ballRegion = new TextureRegion(texture, 0, 32, 16, 16);
		bobRegion = new TextureRegion(texture, 32, 32, 32, 32);
	}

	@Override
	public void dispose() {}

	@Override
	public void backButton() {}

} // CollisionScreen