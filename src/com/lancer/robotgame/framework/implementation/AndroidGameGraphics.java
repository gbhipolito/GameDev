package com.lancer.robotgame.framework.implementation;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

import com.lancer.robotgame.framework.GameGraphics;
import com.lancer.robotgame.framework.GamePixmap;

public class AndroidGameGraphics implements GameGraphics {
	AssetManager assets;
	Bitmap frameBuffer;
	Canvas canvas;
	Paint paint;
	Rect srcRect = new Rect();
	Rect dstRect = new Rect();

	public AndroidGameGraphics( AssetManager assets, Bitmap frameBuffer ) {
		this.assets = assets;
		this.frameBuffer = frameBuffer;
		this.canvas = new Canvas( frameBuffer );
		this.paint = new Paint();
	}

	@Override
	public GamePixmap newPixmap( String fileName, GamePixmapFormat format ) {
		Config config = null;
		if( format == GamePixmapFormat.RGB565 )
			config = Config.RGB_565;
		else if( format == GamePixmapFormat.ARGB4444 )
			config = Config.ARGB_4444;
		else
			config = Config.ARGB_8888;

		Options options = new Options();
		options.inPreferredConfig = config;

		InputStream in = null;
		Bitmap bitmap = null;
		try {
			in = assets.open( fileName );
			bitmap = BitmapFactory.decodeStream( in, null, options );
			if( bitmap == null )
				throw new RuntimeException( "Couldn't load bitmap from asset '"
						+ fileName + "'" );
		}catch( IOException e ) {
			throw new RuntimeException( "Couldn't load bitmap from asset '"
					+ fileName + "'" );
		}finally {
			if( in != null ) {
				try {
					in.close();
				}catch( IOException e ) {
				}
			}
		}

		if( bitmap.getConfig() == Config.RGB_565 )
			format = GamePixmapFormat.RGB565;
		else if( bitmap.getConfig() == Config.ARGB_4444 )
			format = GamePixmapFormat.ARGB4444;
		else
			format = GamePixmapFormat.ARGB8888;

		return new AndroidGameImage( bitmap, format );
	}

	@Override
	public void clearScreen( int color ) {	
		canvas.drawRGB( ( color & 0xff0000 ) >> 16, ( color & 0xff00 ) >> 8, ( color & 0xff ) );
		
		int[] a = new int[5];
		
	}

	/**
	 * The Graphics.drawPixel() method will set the pixel at (x,y) in the
		framebuffer to the given color. Coordinates outside the screen will be
		ignored. This is called clipping.
	 */
	@Override
	public void drawPixel(int x, int y, int color) {
		paint.setColor(color);
		canvas.drawPoint(x, y, paint);
	}
	
	@Override
	public void drawLine( int x, int y, int x2, int y2, int color ) {
		paint.setColor( color );
		canvas.drawLine( x, y, x2, y2, paint );
	}

	@Override
	public void drawRect( int x, int y, int width, int height, int color ) {
		paint.setColor( color );
		paint.setStyle( Style.FILL );
		canvas.drawRect( x, y, x + width - 1, y + height - 1, paint );
	}

	@Override
	public void drawARGB( int a, int r, int g, int b ) {
		paint.setStyle( Style.FILL );
		canvas.drawARGB( a, r, g, b );
	}

	@Override
	public void drawString( String text, int x, int y, Paint paint ) {
		canvas.drawText( text, x, y, paint );

	}

	public void drawPixmap( GamePixmap image, int x, int y, int srcX, int srcY,
			int srcWidth, int srcHeight ) {
		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidth;
		srcRect.bottom = srcY + srcHeight;

		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x + srcWidth;
		dstRect.bottom = y + srcHeight;

		canvas.drawBitmap( ( ( AndroidGameImage )image ).bitmap, srcRect,
				dstRect, null );
	}

	@Override
	public void drawPixmap( GamePixmap image, int x, int y ) {
		canvas.drawBitmap( ( ( AndroidGameImage )image ).bitmap, x, y, null );
	}

	public void drawScaledImage( GamePixmap image, int x, int y, int width,
			int height, int srcX, int srcY, int srcWidth, int srcHeight ) {

		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidth;
		srcRect.bottom = srcY + srcHeight;

		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x + width;
		dstRect.bottom = y + height;

		canvas.drawBitmap( ( ( AndroidGameImage )image ).bitmap, srcRect,
				dstRect, null );

	}

	@Override
	public int getWidth() {
		return frameBuffer.getWidth();
	}

	@Override
	public int getHeight() {
		return frameBuffer.getHeight();
	}
}