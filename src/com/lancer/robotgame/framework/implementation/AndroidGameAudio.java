package com.lancer.robotgame.framework.implementation;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import com.lancer.robotgame.framework.GameAudio;
import com.lancer.robotgame.framework.GameMusic;
import com.lancer.robotgame.framework.GameSound;

public class AndroidGameAudio implements GameAudio {
	AssetManager assets;
	SoundPool soundPool;

	public AndroidGameAudio( Activity activity ) {
		activity.setVolumeControlStream( AudioManager.STREAM_MUSIC );
		this.assets = activity.getAssets();
		this.soundPool = new SoundPool( 20, AudioManager.STREAM_MUSIC, 0 );
	}

	@Override
	public GameMusic createMusic( String filename ) {
		try {
			AssetFileDescriptor assetDescriptor = assets.openFd( filename );
			return new AndroidGameMusic( assetDescriptor );
		}catch( IOException e ) {
			throw new RuntimeException( "Couldn't load music '" + filename
					+ "'" );
		}
	}

	@Override
	public GameSound createSound( String filename ) {
		try {
			AssetFileDescriptor assetDescriptor = assets.openFd( filename );
			int soundId = soundPool.load( assetDescriptor, 0 );
			return new AndroidGameSound( soundPool, soundId );
		}catch( IOException e ) {
			throw new RuntimeException( "Couldn't load sound '" + filename
					+ "'" );
		}
	}
}