package com.lancer.robotgame.framework.implementation;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import com.lancer.robotgame.framework.Game;
import com.lancer.robotgame.framework.GameAudio;
import com.lancer.robotgame.framework.GameFileIO;
import com.lancer.robotgame.framework.GameGraphics;
import com.lancer.robotgame.framework.GameInput;
import com.lancer.robotgame.framework.GameScreen;

public abstract class AndroidGame extends Activity implements Game {
	AndroidFastRenderView renderView;
	GameGraphics graphics;
	GameAudio audio;
	GameInput input;
	GameFileIO fileIO;
	GameScreen screen;
	WakeLock wakeLock;

	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );

		requestWindowFeature( Window.FEATURE_NO_TITLE );
		getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN );

		boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
//		int frameBufferWidth = isPortrait ? 480 : 800;
//		int frameBufferHeight = isPortrait ? 800 : 480;
		int frameBufferWidth = isPortrait ? 420 : 800;
		int frameBufferHeight = isPortrait ? 800 : 420;
		Bitmap frameBuffer = Bitmap.createBitmap( frameBufferWidth,
				frameBufferHeight, Config.RGB_565 );

		float scaleX = ( float )frameBufferWidth
				/ getWindowManager().getDefaultDisplay().getWidth();
		float scaleY = ( float )frameBufferHeight
				/ getWindowManager().getDefaultDisplay().getHeight();

		System.out.println("sclx: " + scaleX + " scly: " + scaleY);
		
		renderView = new AndroidFastRenderView( this, frameBuffer );
		graphics = new AndroidGameGraphics( getAssets(), frameBuffer );
		fileIO = new AndroidGameFileIO( this );
		audio = new AndroidGameAudio( this );
		input = new AndroidGameInput( this, renderView, scaleX, scaleY );
		screen = getInitScreen();
		setContentView( renderView );

		PowerManager powerManager = ( PowerManager )getSystemService( Context.POWER_SERVICE );
		wakeLock = powerManager.newWakeLock( PowerManager.FULL_WAKE_LOCK,
				"MyGame" );
	}

	@Override
	public void onResume() {
		super.onResume();
		wakeLock.acquire();
		screen.resume();
		renderView.resume();
	}

	@Override
	public void onPause() {
		super.onPause();
		wakeLock.release();
		renderView.pause();
		screen.pause();

		if( isFinishing() )
			screen.dispose();
	}

	@Override
	public GameInput getInput() {
		return input;
	}

	@Override
	public GameFileIO getFileIO() {
		return fileIO;
	}

	@Override
	public GameGraphics getGraphics() {
		return graphics;
	}

	@Override
	public GameAudio getAudio() {
		return audio;
	}

	@Override
	public void setScreen( GameScreen screen ) {
		if( screen == null )
			throw new IllegalArgumentException( "Screen must not be null" );

		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update( 0 );
		this.screen = screen;
	}

	public GameScreen getCurrentScreen() {

		return screen;
	}
}