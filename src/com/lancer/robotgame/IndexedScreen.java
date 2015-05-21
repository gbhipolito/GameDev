package com.lancer.robotgame;

import javax.microedition.khronos.opengles.GL10;

import com.lancer.robotgame.framework.Game;
import com.lancer.robotgame.framework.GameScreen;
import com.lancer.robotgame.framework.gl.Texture;
import com.lancer.robotgame.framework.implementation.AndroidGLGraphics;

public class IndexedScreen extends GameScreen {
	AndroidGLGraphics glGraphics;
	Vertices vertices;
	Texture texture;
	
	public IndexedScreen(Game game) {
		super(game);
		glGraphics = ((GLGame)game).getGLGraphics();
		
//		ByteBuffer bytebuffer = ByteBuffer.allocateDirect(4 * VERTEX_SIZE);
//		bytebuffer.order(ByteOrder.nativeOrder());
//		vertices = bytebuffer.asFloatBuffer();
//		vertices.put(new float[] {	0.0f, 0.0f, 0.0f, 1.0f,
//									320.0f, 0.0f, 1.0f, 1.0f,
//									320.0f, 480.0f, 1.0f, 0.0f,
//									0.0f, 480.0f, 0.0f, 0.0f});
//		vertices.put(new float[] {	0f, 0f, 0f, 1f,
//									320f, 0f, 1f, 1f,
//									160f, 479f, 0.5f, 0f});
//		
//		vertices.flip();
		
//		bytebuffer = ByteBuffer.allocateDirect(6 * 2);
//		bytebuffer.order(ByteOrder.nativeOrder());
//		indices = bytebuffer.asShortBuffer();
//		indices.put(new short[] {	0, 1, 2,
//									2, 3, 0 });
//		indices.flip();
		
		vertices = new Vertices(glGraphics, 4, 6, false, true);
		vertices.setVertices(new float[] {	0f, 0f, 0f, 1f,
											320f, 0f, 1f, 1f,
											320f, 480f, 1f, 0f,
											0f, 480f, 0f, 0f	}, 0, 16);
		vertices.setIndices(new short[] {	0, 1, 2,
											2, 3, 0	}, 0, 6);
		
		texture = new Texture((GLGame)game, "sonicARGB8888.png");
	} // constructor

	@Override
	public void update(float deltaTime) {
		game.getInput().getTouchEvents();
	}

	@Override
	public void paint(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
//		gl.glClearColor(0, 1, 0, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0, 320, 0, 480, 1, -1);
		
//		gl.glEnable(GL10.GL_BLEND);
//		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		texture.bind();
		
//		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//		
//		vertices.position(0);
//		gl.glVertexPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
//		vertices.position(2);
//		gl.glTexCoordPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
//		
//		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, indices);
//		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		
		vertices.draw(GL10.GL_TRIANGLES, 0, 6);
		
	} // paint

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

	@Override
	public void backButton() {}

} // IndexedScreen