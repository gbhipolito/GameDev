package com.lancer.robotgame;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
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
import com.lancer.robotgame.framework.implementation.AndroidGLGraphics;
import com.lancer.robotgame.framework.implementation.AndroidGameAudio;
import com.lancer.robotgame.framework.implementation.AndroidGameFileIO;
import com.lancer.robotgame.framework.implementation.AndroidGameInput;

public abstract class GLGame extends Activity implements Game, Renderer {
	enum GLGameState {
		Initialized, Running, Paused, Finished, Idle
	}

	GLSurfaceView glView;
	AndroidGLGraphics glGraphics;
	GameAudio audio;
	GameInput input;
	GameFileIO fileIO;
	GameScreen screen;
	GLGameState state = GLGameState.Initialized;
	Object stateChanged = new Object();
	long startTime = System.nanoTime();
	WakeLock wakeLock;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		glView = new GLSurfaceView(this);
		glView.setRenderer(this);
		setContentView(glView);
		glGraphics = new AndroidGLGraphics(glView);
		fileIO = new AndroidGameFileIO(this);
		audio = new AndroidGameAudio(this);
		input = new AndroidGameInput(this, glView, 1, 1);
		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
				"GLGame");
	}

	@Override
	public void onResume() {
		super.onResume();
		glView.onResume();
		wakeLock.acquire();
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glGraphics.setGL(gl);
		synchronized (stateChanged) {
			if (state == GLGameState.Initialized)
				screen = getInitScreen();
			state = GLGameState.Running;
			screen.resume();
			startTime = System.nanoTime();
		}
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
	}

	public void onDrawFrame(GL10 gl) {
		GLGameState state = null;
		synchronized (stateChanged) {
			state = this.state;
		}
		if (state == GLGameState.Running) {
			float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
//			float deltaTime = (System.nanoTime() - startTime);
			startTime = System.nanoTime();
			screen.update(deltaTime);
			screen.paint(deltaTime);
		}
		if (state == GLGameState.Paused) {
			screen.pause();
			synchronized (stateChanged) {
				this.state = GLGameState.Idle;
				stateChanged.notifyAll();
			}
		}
		if (state == GLGameState.Finished) {
			screen.pause();
			screen.dispose();
			synchronized (stateChanged) {
				this.state = GLGameState.Idle;
				stateChanged.notifyAll();
			}
		}
	}

	@Override
	public void onPause() {
		synchronized (stateChanged) {
			if (isFinishing())
				state = GLGameState.Finished;
			else
				state = GLGameState.Paused;
			while (true) {
				try {
					stateChanged.wait();
					break;
				} catch (InterruptedException e) {
				}
			}
		}
		wakeLock.release();
		glView.onPause();
		super.onPause();
	}

	public AndroidGLGraphics getGLGraphics() {
		return glGraphics;
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
		throw new IllegalStateException("We are using OpenGL!");
	}

	@Override
	public GameAudio getAudio() {
		return audio;
	}

	@Override
	public void setScreen(GameScreen newScreen) {
		if (screen == null)
			throw new IllegalArgumentException("Screen must not be null");
		this.screen.pause();
		this.screen.dispose();
		newScreen.resume();
		newScreen.update(0);
		this.screen = newScreen;
	}

	@Override
	public GameScreen getCurrentScreen() {
		return screen;
	}

} // GLGame