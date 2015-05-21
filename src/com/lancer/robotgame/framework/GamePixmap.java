package com.lancer.robotgame.framework;

import com.lancer.robotgame.framework.GameGraphics.GamePixmapFormat;

public interface GamePixmap {
	/**
	 * @return width of pixmap in pixels
	 */
	public int getWidth();

	/**
	 * @return height of pixmap in pixels
	 */
	public int getHeight();

	public GamePixmapFormat getFormat();

	public void dispose();
}