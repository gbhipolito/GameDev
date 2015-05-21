package com.lancer.robotgame;

import com.lancer.robotgame.framework.Game;
import com.lancer.robotgame.framework.GameGraphics;
import com.lancer.robotgame.framework.GameGraphics.GamePixmapFormat;
import com.lancer.robotgame.framework.GameScreen;

public class LoadingScreen extends GameScreen {
	public LoadingScreen( Game game ) {

		super( game );
	}

	@Override
	public void update( float deltaTime ) {
		GameGraphics g = game.getGraphics();
		GameAssets.menu = g.newPixmap( "menu.png", GamePixmapFormat.RGB565 );
		GameAssets.background = g.newPixmap( "background.png", GamePixmapFormat.RGB565 );
		GameAssets.character = g.newPixmap( "character.png", GamePixmapFormat.ARGB4444 );
		GameAssets.character2 = g.newPixmap( "character2.png", GamePixmapFormat.ARGB4444 );
		GameAssets.character3 = g.newPixmap( "character3.png", GamePixmapFormat.ARGB4444 );
		GameAssets.characterJump = g.newPixmap( "jumped.png", GamePixmapFormat.ARGB4444 );
		GameAssets.characterDown = g.newPixmap( "down.png", GamePixmapFormat.ARGB4444 );

		GameAssets.heliboy = g.newPixmap( "heliboy.png", GamePixmapFormat.ARGB4444 );
		GameAssets.heliboy2 = g.newPixmap( "heliboy2.png", GamePixmapFormat.ARGB4444 );
		GameAssets.heliboy3 = g.newPixmap( "heliboy3.png", GamePixmapFormat.ARGB4444 );
		GameAssets.heliboy4 = g.newPixmap( "heliboy4.png", GamePixmapFormat.ARGB4444 );
		GameAssets.heliboy5 = g.newPixmap( "heliboy5.png", GamePixmapFormat.ARGB4444 );

		GameAssets.tiledirt = g.newPixmap( "tiledirt.png", GamePixmapFormat.RGB565 );
		GameAssets.tilegrassTop = g.newPixmap( "tilegrasstop.png", GamePixmapFormat.RGB565 );
		GameAssets.tilegrassBot = g.newPixmap( "tilegrassbot.png", GamePixmapFormat.RGB565 );
		GameAssets.tilegrassLeft = g.newPixmap( "tilegrassleft.png", GamePixmapFormat.RGB565 );
		GameAssets.tilegrassRight = g.newPixmap( "tilegrassright.png", GamePixmapFormat.RGB565 );

		GameAssets.button = g.newPixmap( "button.jpg", GamePixmapFormat.RGB565 );

		// This is how you would load a sound if you had one.
		// Assets.click = game.getAudio().createSound("explode.ogg");

		game.setScreen( new MainMenuScreen( game ) );

	}

	@Override
	public void paint( float deltaTime ) {
		GameGraphics g = game.getGraphics();
		g.drawPixmap( GameAssets.splash, 0, 0 );
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