package com.lancer.robotgame.framework;

public interface GameAudio {
	public GameMusic createMusic( String file );

	public GameSound createSound( String file );
}