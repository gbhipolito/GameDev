package com.lancer.robotgame;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.lancer.robotgame.framework.implementation.AndroidGLGraphics;

public class Vertices {
	final AndroidGLGraphics glGraphics;
	final boolean hasColor;
	final boolean hasTexCoords;
	final int vertexSize;
	final FloatBuffer vertices;
	final ShortBuffer indices;
	
	/**
	 * constructor, allocates buffer for vertices
	 * @param glGraphics - instance of AndroidGLGraphics used in game
	 * @param maxVertices - number of vertices to be used
	 * @param maxIndices - number of indices to be used
	 * @param hasColor - if vertices has color
	 * @param hasTexCoords - if vertices has texture coordinates
	 */
	public Vertices( AndroidGLGraphics glGraphics, int maxVertices, int maxIndices, boolean hasColor, boolean hasTexCoords) {
		this.glGraphics = glGraphics;
		this.hasColor = hasColor;
		this.hasTexCoords = hasTexCoords;
		this.vertexSize = (2 + (hasColor?4:0) + (hasTexCoords?2:0))*4;
		
		ByteBuffer buffer = ByteBuffer.allocateDirect(maxVertices * vertexSize);
		buffer.order(ByteOrder.nativeOrder());
		vertices = buffer.asFloatBuffer();
		
		if( maxIndices > 0 ) {
			buffer = ByteBuffer.allocateDirect(maxIndices * Short.SIZE/8);
			buffer.order(ByteOrder.nativeOrder());
			indices = buffer.asShortBuffer();
		} else {
			indices = null;
		}
	} // constructor
	
	/**
	 * put the value of vertices to the buffer
	 * @param vertices - vertex values in float array
	 * @param offset - offset of the float array
	 * @param length - number of floats to write
	 */
	public void setVertices( float[] vertices, int offset, int length ) {
		this.vertices.clear();
		this.vertices.put(vertices, offset, length);
		this.vertices.flip();
	}
	
	/**
	 * put the value of indices to the buffer
	 * @param indices - index values in float array
	 * @param offset - offset of the float array
	 * @param length - number of floats to write
	 */
	public void setIndices( short[] indices, int offset, int length ) {
		this.indices.clear();
		this.indices.put(indices, offset, length);
		this.indices.flip();
	}
	
	/**
	 * enable client states and pointers
	 */
	public void bind() {
		GL10 gl = glGraphics.getGL();
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		vertices.position(0);
		gl.glVertexPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
		
		if( hasColor ) {
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			vertices.position(2);
			gl.glColorPointer(4, GL10.GL_FLOAT, vertexSize, vertices);
		}
		if( hasTexCoords ) {
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			vertices.position(hasColor?6:2);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
		}
	} // bind
	
	public void draw( int primitiveType, int offset, int numVertices ) {
		GL10 gl = glGraphics.getGL();
		
//		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//		vertices.position(0);
//		gl.glVertexPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
		
//		if( hasColor ) {
//			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
//			vertices.position(2);
//			gl.glColorPointer(4, GL10.GL_FLOAT, vertexSize, vertices);
//		}
//		if( hasTexCoords ) {
//			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//			vertices.position(hasColor?6:2);
//			gl.glTexCoordPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
//		}
		
		if( indices != null ) {
			indices.position(offset);
			gl.glDrawElements(primitiveType, numVertices, GL10.GL_UNSIGNED_SHORT, indices);
		} else {
			gl.glDrawArrays(primitiveType, offset, numVertices);
		}
		
//		if( hasColor ) {
//			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
//		}
//		if( hasTexCoords ) {
//			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//		}
	} // draw
	
	/**
	 * disable client states
	 */
	public void unbind() {
		GL10 gl = glGraphics.getGL();
		
		if( hasColor ) {
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		}
		if( hasTexCoords ) {
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
	} // unbind
} //Vertices