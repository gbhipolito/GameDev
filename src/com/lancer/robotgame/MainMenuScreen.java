package com.lancer.robotgame;

import java.util.List;

import com.lancer.robotgame.framework.Game;
import com.lancer.robotgame.framework.GameGraphics;
import com.lancer.robotgame.framework.GameInput.GameTouchEvent;
import com.lancer.robotgame.framework.GameScreen;

public class MainMenuScreen extends GameScreen {
	public MainMenuScreen( Game game ) {
		super( game );
	}

	@Override
	public void update( float deltaTime ) {
		GameGraphics g = game.getGraphics();
		List<GameTouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for( int i = 0; i < len; i++ ) {
			GameTouchEvent event = touchEvents.get( i );
			if( event.type == GameTouchEvent.TOUCH_UP ) {

				if( inBounds( event, 50, 350, 250, 450 ) ) {
					game.setScreen( new InGameScreen( game ) );
				}

			}
		}
	}

	private boolean inBounds( GameTouchEvent event, int x, int y, int width,
			int height ) {
		if( event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1 )
			return true;
		else
			return false;
	}

	@Override
	public void paint( float deltaTime ) {
		GameGraphics g = game.getGraphics();
		g.drawPixmap( GameAssets.menu, 0, 0 );
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
		android.os.Process.killProcess( android.os.Process.myPid() );

	}
}