package com.lancer.robotgame.framework;

import android.graphics.Paint;

public interface GameGraphics {
	public static enum GamePixmapFormat {
		ARGB8888, ARGB4444, RGB565
	}

	/**
	 * load an image given in either JPEG
		or PNG format. We specify a desired format for the resulting Pixmap, which is
		a hint for the loading mechanism. The resulting Pixmap might have a different
		format.
	 */
	public GamePixmap newPixmap( String fileName, GamePixmapFormat format );

	/**
	 * clears the complete framebuffer with the
		given color
	 */
	public void clearScreen( int color );

	/**
	 * set the pixel at (x,y) in the
		framebuffer to the given color. Coordinates outside the screen will be
		ignored. This is called clipping.
	 */
	public void drawPixel(int x, int y, int color);
	
	public void drawLine( int x, int y, int x2, int y2, int color );

	/**
	 * @param x - top left x
	 * @param y - top left y
	 */
	public void drawRect( int x, int y, int width, int height, int color );

	/**
	 * The Graphics.drawPixmap() method draws rectangular portions of a Pixmap
		to the framebuffer.
	 * @param pixmap
	 * @param x - target location in framebuffer top left x
	 * @param y - target location in framebuffer top left y
	 * @param srcX - top left x of rectangular region from Pixmap, given in the Pixmap’s own coordinate system
	 * @param srcY - top left y of rectangular region from Pixmap, given in the Pixmap’s own coordinate system
	 * @param srcWidth - width of portion that is taken from Pixmap
	 * @param srcHeight - height of portion that is taken from Pixmap
	 */
	public void drawPixmap( GamePixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight );

	public void drawPixmap( GamePixmap pixmap, int x, int y );

	void drawString( String text, int x, int y, Paint paint );

	/**
	 * @return width of frame buffer in pixels
	 */
	public int getWidth();

	/**
	 * @return height of frame buffer in pixels
	 */
	public int getHeight();

	public void drawARGB( int i, int j, int k, int l );

}