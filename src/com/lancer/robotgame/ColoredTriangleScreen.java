package com.lancer.robotgame;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.lancer.robotgame.framework.Game;
import com.lancer.robotgame.framework.GameScreen;
import com.lancer.robotgame.framework.implementation.AndroidGLGraphics;

public class ColoredTriangleScreen extends GameScreen {
	final int VERTEX_SIZE = (2 + 4) * 4;
	AndroidGLGraphics glGraphics;
	FloatBuffer vertices;
	
	public ColoredTriangleScreen(Game game) {
		super(game);
		glGraphics = ((GLGame)game).getGLGraphics();
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * VERTEX_SIZE);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertices = byteBuffer.asFloatBuffer();
		vertices.put(new float[] {	0f, 0f, 1f, 0f, 0f, 1f,
									319f, 0f, 0f, 1f, 0f, 1f,
									160f, 479f, 0f, 0f, 1f, 1f });
		vertices.flip();
	}

	@Override
	public void update(float deltaTime) {
		game.getInput().getTouchEvents();
	}

	@Override
	public void paint(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0, 320, 0, 480, 1, -1);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		vertices.position(0);
		gl.glVertexPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
		vertices.position(2);
		gl.glColorPointer(4, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
		
//		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

	@Override
	public void backButton() {}

} // ColoredTriangleScreen