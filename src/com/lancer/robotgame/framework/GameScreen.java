package com.lancer.robotgame.framework;
public abstract class GameScreen {
	protected final Game game;
	public GameScreen( Game game ) {
		this.game = game;
	}
	public abstract void update( float deltaTime );
	public abstract void paint( float deltaTime );
	public abstract void pause();
	public abstract void resume();
	public abstract void dispose();
	public abstract void backButton();
}
/**
The constructor receives the Game
instance and stores it in a final member that’s accessible to all subclasses. Via this mechanism,
we can achieve two things:
 - We can get access to the low-level modules of the Game interface to play
back audio, draw to the screen, get user input, and read and write files.
 - We can set a new current Screen by invoking Game.setScreen() when
appropriate (for example, when a button is pressed that triggers a transition
to a new screen).
The first point is pretty much obvious: our Screen implementation needs access to these
modules so that it can actually do something meaningful, like rendering huge numbers of
unicorns with rabies.
The second point allows us to implement our screen transitions easily within the Screen
instances themselves. Each Screen can decide when to transition to which other Screen based
on its state (for example, when a menu button is pressed).
The methods Screen.update() and Screen.present() should be self-explanatory by now: they
will update the screen state and present it accordingly. The Game instance will call them once in
every iteration of the main loop.
The Screen.pause() and Screen.resume() methods will be called when the game is paused or
resumed. This is again done by the Game instance and applied to the currently active Screen.
The Screen.dispose() method will be called by the Game instance in case Game.setScreen() is
called. The Game instance will dispose of the current Screen via this method and thereby give the
Screen an opportunity to release all its system resources (for example, graphical assets stored in
Pixmaps) to make room for the new screen’s resources in memory. The call to the Screen.dispose()
method is also the last opportunity for a screen to make sure that any information that needs
persistence is saved.
*/