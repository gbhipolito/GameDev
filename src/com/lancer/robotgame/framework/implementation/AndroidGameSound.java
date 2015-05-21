package com.lancer.robotgame.framework.implementation;

import android.media.SoundPool;

import com.lancer.robotgame.framework.GameSound;

public class AndroidGameSound implements GameSound {
	int soundId;
	SoundPool soundPool;

	public AndroidGameSound( SoundPool soundPool, int soundId ) {
		this.soundId = soundId;
		this.soundPool = soundPool;
	}

	@Override
	public void play( float volume ) {
		soundPool.play( soundId, volume, volume, 0, 0, 1 );
	}

	@Override
	public void dispose() {
		soundPool.unload( soundId );
	}

}