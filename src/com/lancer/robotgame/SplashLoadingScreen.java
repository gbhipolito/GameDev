package com.lancer.robotgame;

import com.lancer.robotgame.framework.Game;
import com.lancer.robotgame.framework.GameGraphics;
import com.lancer.robotgame.framework.GameGraphics.GamePixmapFormat;
import com.lancer.robotgame.framework.GameScreen;

public class SplashLoadingScreen extends GameScreen {
	public SplashLoadingScreen( Game game ) {
		super( game );
	}

	@Override
	public void update( float deltaTime ) {
		GameGraphics g = game.getGraphics();
		GameAssets.splash = g.newPixmap( "splash.jpg", GamePixmapFormat.RGB565 );

		game.setScreen( new LoadingScreen( game ) );

	}

	@Override
	public void paint( float deltaTime ) {

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
}