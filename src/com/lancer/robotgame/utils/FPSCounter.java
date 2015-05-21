package com.lancer.robotgame.utils;

import android.util.Log;

public class FPSCounter {
	long startTime = System.nanoTime();
	int frames = 0;
	
	public void logFrame() {
		frames++;
		if( System.nanoTime() - startTime >= 1000000000 ) {
			Log.e("FPS Counter", "fps: " + frames);
			frames = 0;
			startTime = System.nanoTime();
		}
	}
} // FPSCounter