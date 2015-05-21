package com.lancer.robotgame.framework;

public interface Game {

	public GameAudio getAudio();

	public GameInput getInput();

	public GameFileIO getFileIO();

	public GameGraphics getGraphics();

	public void setScreen( GameScreen screen );

	public GameScreen getCurrentScreen();

	public GameScreen getInitScreen();
}