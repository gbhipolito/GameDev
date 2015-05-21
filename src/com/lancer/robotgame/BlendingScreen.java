package com.lancer.robotgame;

import javax.microedition.khronos.opengles.GL10;

import com.lancer.robotgame.framework.Game;
import com.lancer.robotgame.framework.GameScreen;
import com.lancer.robotgame.framework.gl.Texture;
import com.lancer.robotgame.framework.implementation.AndroidGLGraphics;

public class BlendingScreen extends GameScreen {
	AndroidGLGraphics glGraphics;
	Vertices vertices;
	Texture textureRgb;
	Texture textureRgba;
	
	public BlendingScreen(Game game) {
		super(game);
		glGraphics = ((GLGame)game).getGLGraphics();
		
		textureRgb = new Texture((GLGame)game, "sonicRGB888.jpg");
		textureRgba = new Texture((GLGame)game, "sonicARGB8888.png");
		
		vertices = new Vertices(glGraphics, 8, 12, true, true);
		float rects[] = new float[] {	100, 100, 1, 1, 1, 0.5f, 0, 1,
										228, 100, 1, 1, 1, 0.5f, 1, 1,
										228, 228, 1, 1, 1, 0.5f, 1, 0,
										100, 228, 1, 1, 1, 0.5f, 0, 0,
										
										100, 300, 1, 0, 0, 0.5f, 0, 1,
										228, 300, 0, 1, 0, 0.5f, 1, 1,
										228, 428, 0, 0, 1, 0.5f, 1, 0,
										100, 428, 1, 1, 1, 0.5f, 0, 0 };
		vertices.setVertices(rects, 0, rects.length);
		vertices.setIndices(new short[] {	0, 1, 2,
											2, 3, 0,
											
											4, 5, 6,
											6, 7, 4	}, 0, 12);
	} // constructor
	
	@Override
	public void update(float deltaTime) {
		game.getInput().getTouchEvents();
	}

	@Override
	public void paint(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
		gl.glClearColor(1, 0, 0, 0.5f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0, 320, 0, 480, 1, -1);
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		textureRgb.bind();
		vertices.draw(GL10.GL_TRIANGLES, 0, 6);
		
		textureRgba.bind();
		vertices.draw(GL10.GL_TRIANGLES, 6, 6);
	} // paint

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

	@Override
	public void backButton() {}

} // BlendingScreen