package com.lancer.robotgame.framework.implementation;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

public class AndroidGLGraphics {
	GLSurfaceView glView;
	GL10 gl;

	public AndroidGLGraphics( GLSurfaceView glView ) {
		this.glView = glView;
	}

	public GL10 getGL() {
		return gl;
	}

	public void setGL( GL10 gl ) {
		this.gl = gl;
	}

	public int getWidth() {
		return glView.getWidth();
	}

	public int getHeight() {
		return glView.getHeight();
	}

} // AndroidGLGraphics