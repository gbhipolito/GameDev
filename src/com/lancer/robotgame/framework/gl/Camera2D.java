package com.lancer.robotgame.framework.gl;

import javax.microedition.khronos.opengles.GL10;

import com.lancer.robotgame.framework.implementation.AndroidGLGraphics;
import com.lancer.robotgame.framework.math.Vector2;

public class Camera2D {
	public final Vector2 position;
	public float zoom;
	public final float frustumWidth;
	public final float frustumHeight;
	final AndroidGLGraphics glGraphics;
	
	public Camera2D( AndroidGLGraphics glGraphics, float frustumWidth, float frustumHeight ) {
		this.glGraphics = glGraphics;
		this.frustumWidth = frustumWidth;
		this.frustumHeight = frustumHeight;
		this.position = new Vector2(frustumWidth/2, frustumHeight/2);
		this.zoom = 1.0f;
	} // end constructor
	
	/**
	 * sets the viewport and projection matrix considering the zoom and camera position.
	 * using the center of camera
	 */
	public void setViewportAndMatrices() {
		GL10 gl = glGraphics.getGL();
		gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(position.x - frustumWidth * zoom / 2,
					position.x + frustumWidth * zoom / 2,
					position.y - frustumHeight * zoom / 2,
					position.y + frustumHeight * zoom / 2,
					1, -1);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	} // end setViewportAndMatrices
	
	/**
	 * converts touch coordinate to world coordinate considering zoom and camera position
	 * @param touch - user touch coordinate
	 */
	public void touchToWorld( Vector2 touch ) {
		touch.x = (touch.x / (float)glGraphics.getWidth()) * frustumWidth * zoom;
		touch.y = (1 - touch.y / (float)glGraphics.getHeight()) * frustumHeight * zoom;
		touch.add(position).sub(frustumWidth * zoom / 2, frustumHeight * zoom / 2);
	} // end touchToWorld
} // end Camera2D