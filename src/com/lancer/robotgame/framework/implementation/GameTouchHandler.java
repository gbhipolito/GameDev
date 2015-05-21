package com.lancer.robotgame.framework.implementation;

import java.util.List;

import android.view.View.OnTouchListener;

import com.lancer.robotgame.framework.GameInput.GameTouchEvent;

public interface GameTouchHandler extends OnTouchListener {
	public boolean isTouchDown( int pointer );

	public int getTouchX( int pointer );

	public int getTouchY( int pointer );

	public List<GameTouchEvent> getTouchEvents();
}