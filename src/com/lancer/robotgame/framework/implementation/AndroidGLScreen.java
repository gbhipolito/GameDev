package com.lancer.robotgame.framework.implementation;

import com.lancer.robotgame.GLGame;
import com.lancer.robotgame.framework.Game;
import com.lancer.robotgame.framework.GameScreen;

public abstract class AndroidGLScreen extends GameScreen {

	protected final AndroidGLGraphics glGraphics;
	protected final GLGame glGame;

	public AndroidGLScreen(Game game) {
		super(game);
		glGame = (GLGame) game;
		glGraphics = glGame.getGLGraphics();
	}
} // AndroidGLScreen