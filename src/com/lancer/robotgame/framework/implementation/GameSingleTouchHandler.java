package com.lancer.robotgame.framework.implementation;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

import com.lancer.robotgame.framework.GamePool;
import com.lancer.robotgame.framework.GameInput.GameTouchEvent;
import com.lancer.robotgame.framework.GamePool.GamePoolObjectFactory;

public class GameSingleTouchHandler implements GameTouchHandler {
	boolean isTouched;
	int touchX;
	int touchY;
	GamePool<GameTouchEvent> touchEventPool;
	List<GameTouchEvent> touchEvents = new ArrayList<GameTouchEvent>();
	List<GameTouchEvent> touchEventsBuffer = new ArrayList<GameTouchEvent>();
	float scaleX;
	float scaleY;

	public GameSingleTouchHandler( View view, float scaleX, float scaleY ) {
		GamePoolObjectFactory<GameTouchEvent> factory = new GamePoolObjectFactory<GameTouchEvent>() {
			@Override
			public GameTouchEvent createObject() {
				return new GameTouchEvent();
			}
		};
		touchEventPool = new GamePool<GameTouchEvent>( factory, 100 );
		view.setOnTouchListener( this );

		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}

	@Override
	public boolean onTouch( View v, MotionEvent event ) {
		synchronized( this ) {
			GameTouchEvent touchEvent = touchEventPool.newObject();
			switch( event.getAction() ) {
				case MotionEvent.ACTION_DOWN:
					touchEvent.type = GameTouchEvent.TOUCH_DOWN;
					isTouched = true;
					break;
				case MotionEvent.ACTION_MOVE:
					touchEvent.type = GameTouchEvent.TOUCH_DRAGGED;
					isTouched = true;
					break;
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
					touchEvent.type = GameTouchEvent.TOUCH_UP;
					isTouched = false;
					break;
			}

			touchEvent.x = touchX = ( int )( event.getX() * scaleX );
			touchEvent.y = touchY = ( int )( event.getY() * scaleY );
			touchEventsBuffer.add( touchEvent );

			return true;
		}
	}

	@Override
	public boolean isTouchDown( int pointer ) {
		synchronized( this ) {
			if( pointer == 0 )
				return isTouched;
			else
				return false;
		}
	}

	@Override
	public int getTouchX( int pointer ) {
		synchronized( this ) {
			return touchX;
		}
	}

	@Override
	public int getTouchY( int pointer ) {
		synchronized( this ) {
			return touchY;
		}
	}

	@Override
	public List<GameTouchEvent> getTouchEvents() {
		synchronized( this ) {
			int len = touchEvents.size();
			for( int i = 0; i < len; i++ )
				touchEventPool.free( touchEvents.get( i ) );
			touchEvents.clear();
			touchEvents.addAll( touchEventsBuffer );
			touchEventsBuffer.clear();
			return touchEvents;
		}
	}
}