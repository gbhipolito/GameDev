package com.lancer.robotgame;

import javax.microedition.khronos.opengles.GL10;

import com.lancer.robotgame.framework.Game;
import com.lancer.robotgame.framework.GameScreen;
import com.lancer.robotgame.framework.gl.Texture;
import com.lancer.robotgame.framework.implementation.AndroidGLGraphics;
import com.lancer.robotgame.utils.FPSCounter;

public class SonicScreen extends GameScreen {
	static final int NUM_SONICS = 3;
	AndroidGLGraphics glGraphics;
	Texture sonicTexture;
	Vertices sonicModel;
	Sonic[] sonics;
	FPSCounter fpsCounter = new FPSCounter();
	
	public SonicScreen(Game game) {
		super(game);
		
		glGraphics = ((GLGame)game).getGLGraphics();
		
		sonicTexture = new Texture((GLGame)game, "sonicRGB888.jpg");
		
		sonicModel = new Vertices(glGraphics, 4, 6, false, true);
		sonicModel.setVertices(new float[] {	-16, -16, 0, 1,
												16, -16, 1, 1,
												16, 16, 1, 0,
												-16, 16, 0, 0	}, 0, 16);
		sonicModel.setIndices(new short[] {	0, 1, 2,
											2, 3, 0	}, 0, 6);
		
		sonics = new Sonic[NUM_SONICS];
		for( int i = 0; i < NUM_SONICS; i++ ) {
			sonics[i] = new Sonic();
		}
	} // SonicScreen

	@Override
	public void update(float deltaTime) {
		game.getInput().getTouchEvents();
		
		for( int i = 0; i < NUM_SONICS; i++ ) {
			sonics[i].update(deltaTime);
		}
	} // update

	@Override
	public void paint(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
//		gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
//		gl.glClearColor(1, 0, 0, 1);
//		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
//		gl.glMatrixMode(GL10.GL_PROJECTION);
//		gl.glLoadIdentity();
//		gl.glOrthof(0, 320, 0, 480, 1, -1);
//		
////		gl.glEnable(GL10.GL_BLEND);
////		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
//		
//		gl.glEnable(GL10.GL_TEXTURE_2D);
//		sonicTexture.bind();
//		sonicModel.bind();
		for( int i = 0; i < NUM_SONICS; i++) {
			gl.glLoadIdentity();
			gl.glTranslatef(sonics[i].x, sonics[i].y, 0);
//			gl.glTranslatef(100, 100, 0);
			gl.glRotatef(60, 0.5f, 0, 0.5f);
//			gl.glScalef(7, 0.5f, 1);
			sonicModel.draw(GL10.GL_TRIANGLES, 0, 6);
		}
//		sonicModel.unbind();
		fpsCounter.logFrame();
	} // paint

	@Override
	public void pause() {
		sonicModel.unbind();
	}

	@Override
	public void resume() {
		GL10 gl = glGraphics.getGL();
		gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
		gl.glClearColor(1, 0, 0, 1);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0, 320, 0, 480, 1, -1);
		
//		gl.glEnable(GL10.GL_BLEND);
//		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		sonicTexture.reload();
		gl.glEnable(GL10.GL_TEXTURE_2D);
		sonicTexture.bind();
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		
		sonicModel.bind();
	} // resume

	@Override
	public void dispose() {}

	@Override
	public void backButton() {}

} // SonicScreen