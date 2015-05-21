package com.lancer.robotgame.framework.implementation;

import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import com.lancer.robotgame.framework.GameInput;

public class AndroidGameInput implements GameInput {
	GameTouchHandler touchHandler;

	public AndroidGameInput( Context context, View view, float scaleX,
			float scaleY ) {
		if( Integer.parseInt( VERSION.SDK ) < 5 )
			touchHandler = new GameSingleTouchHandler( view, scaleX, scaleY );
		else
			touchHandler = new GameMultiTouchHandler( view, scaleX, scaleY );
	}

	@Override
	public boolean isTouchDown( int pointer ) {
		return touchHandler.isTouchDown( pointer );
	}

	@Override
	public int getTouchX( int pointer ) {
		return touchHandler.getTouchX( pointer );
	}

	@Override
	public int getTouchY( int pointer ) {
		return touchHandler.getTouchY( pointer );
	}

	@Override
	public List<GameTouchEvent> getTouchEvents() {
		return touchHandler.getTouchEvents();
	}

}