package com.lancer.robotgame;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import com.lancer.robotgame.framework.Game;
import com.lancer.robotgame.framework.GameScreen;
import com.lancer.robotgame.framework.implementation.AndroidGLGraphics;

public class GLGameTest extends GLGame {

	@Override
	public GameScreen getInitScreen() {
		return new TestScreen(this);
	}

	class TestScreen extends GameScreen {
		AndroidGLGraphics glGraphics;
		Random rand = new Random();

		public TestScreen(Game game) {
			super(game);
			glGraphics = ((GLGame) game).getGLGraphics();
		}

		@Override
		public void paint(float deltaTime) {
			GL10 gl = glGraphics.getGL();
			gl.glClearColor(rand.nextFloat(), rand.nextFloat(),
					rand.nextFloat(), 1);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		}

		@Override
		public void update(float deltaTime) {
		}

		@Override
		public void pause() {
		}

		@Override
		public void resume() {
		}

		@Override
		public void dispose() {
		}

		@Override
		public void backButton() {
		}
	} // TestScreen
} // GLGameTest