package com.lancer.robotgame.framework.implementation;

import android.graphics.Bitmap;

import com.lancer.robotgame.framework.GamePixmap;
import com.lancer.robotgame.framework.GameGraphics.GamePixmapFormat;

public class AndroidGameImage implements GamePixmap {
	Bitmap bitmap;
	GamePixmapFormat format;

	public AndroidGameImage( Bitmap bitmap, GamePixmapFormat format ) {
		this.bitmap = bitmap;
		this.format = format;
	}

	@Override
	public int getWidth() {
		return bitmap.getWidth();
	}

	@Override
	public int getHeight() {
		return bitmap.getHeight();
	}

	@Override
	public GamePixmapFormat getFormat() {
		return format;
	}

	@Override
	public void dispose() {
		bitmap.recycle();
	}
}