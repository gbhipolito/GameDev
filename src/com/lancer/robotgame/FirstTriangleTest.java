package com.lancer.robotgame;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.lancer.robotgame.framework.Game;
import com.lancer.robotgame.framework.GameScreen;
import com.lancer.robotgame.framework.implementation.AndroidGLGraphics;

public class FirstTriangleTest extends GLGame {

	@Override
	public GameScreen getInitScreen() {
//		return new TextureAtlasScreen(this);
//		return new SpriteBatcherScreen(this);
		return new AnimationScreen(this);
	}
	
	class FirstTriangleScreen extends GameScreen {
		AndroidGLGraphics glGraphics;
		FloatBuffer vertices;
		
		public FirstTriangleScreen(Game game) {
			super(game);
			glGraphics = ((GLGame)game).getGLGraphics();
			
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * 2 * 4);
			byteBuffer.order(ByteOrder.nativeOrder());
			vertices = byteBuffer.asFloatBuffer();
			vertices.put( new float[] { 0.0f, 0.0f,
										319.0f, 0.0f,
										160.0f, 479.0f} );
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
			gl.glClearColor(0, 1, 0, 1);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, 320, 0, 480, 1, -1);
			gl.glColor4f(0, 1, 0, 1);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glVertexPointer(2, GL10.GL_FLOAT, 8, vertices);
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
	} // FirstTriangleScreen
} // FirstTriangleTest