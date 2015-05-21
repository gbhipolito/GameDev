package com.lancer.robotgame;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.lancer.robotgame.framework.Game;
import com.lancer.robotgame.framework.GameInput.GameTouchEvent;
import com.lancer.robotgame.framework.GameScreen;
import com.lancer.robotgame.framework.implementation.AndroidGLGraphics;
import com.lancer.robotgame.framework.math.Vector2;

public class CannonScreen extends GameScreen {
	float FRUSTUM_WIDTH = 4.8f;
	float FRUSTUM_HEIGHT = 3.2f;
	AndroidGLGraphics glGraphics;
	Vertices vertices;
	Vector2 cannonPos = new Vector2(2.4f, 0.5f);
	float cannonAngle = 0;
	Vector2 touchPos = new Vector2();
	
	public CannonScreen(Game game) {
		super(game);
		glGraphics = ((GLGame)game).getGLGraphics();
		vertices = new Vertices(glGraphics, 3, 0, true, false);
		vertices.setVertices(new float[] {	-0.5f, -0.5f, 1f, 1f, 1f, 1f,
											0.5f, 0f, 0f, 1f, 0f, 1f,
											-0.5f, 0.5f, 1f, 1f, 1f, 1f	}, 0, 18);
	}

	@Override
	public void update(float deltaTime) {
		List<GameTouchEvent> touchEvents = game.getInput().getTouchEvents();
		
		int len = touchEvents.size();
		for( int i = 0; i < len; i++ ) {
			GameTouchEvent event = touchEvents.get(i);
			
			touchPos.x = (event.x / (float)glGraphics.getWidth()) * FRUSTUM_WIDTH;
			touchPos.y = (1 - event.y / (float)glGraphics.getHeight()) * FRUSTUM_HEIGHT;
			cannonAngle = touchPos.sub(cannonPos).angle();
		}
	} // update

	@Override
	public void paint(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef(cannonPos.x, cannonPos.y, 0);
		gl.glRotatef(cannonAngle, 0, 0, 1);
		
		vertices.bind();
		vertices.draw(GL10.GL_TRIANGLES, 0, 3);
		vertices.unbind();
	} // paint

	@Override
	public void pause() {}

	@Override
	public void resume() {
		GL10 gl = glGraphics.getGL();
		gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0, FRUSTUM_WIDTH, 0, FRUSTUM_HEIGHT, 1, -1);
	}

	@Override
	public void dispose() {}

	@Override
	public void backButton() {}
} // CannonScreen