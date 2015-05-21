package com.lancer.robotgame;

import java.util.ArrayList;

import com.lancer.robotgame.framework.GamePixmap;

public class Anim {

	private ArrayList frames;
	private int currentFrame;
	private long animTime;
	private long totalDuration;

	public Anim() {
		frames = new ArrayList();
		totalDuration = 0;

		synchronized( this ) {
			animTime = 0;
			currentFrame = 0;
		}
	}

	public synchronized void addFrame( GamePixmap image, long duration ) {
		totalDuration += duration;
		frames.add( new AnimFrame( image, totalDuration ) );
	}

	public synchronized void update( long elapsedTime ) {
		if( frames.size() > 1 ) {
			animTime += elapsedTime;
			if( animTime >= totalDuration ) {
				animTime = animTime % totalDuration;
				currentFrame = 0;
			}

			while( animTime > getFrame( currentFrame ).endTime ) {
				currentFrame++;

			}
		} // if( frames.size() > 1 )
	}

	public synchronized GamePixmap getImage() {
		if( frames.size() == 0 ) {
			return null;
		}else {
			return getFrame( currentFrame ).image;
		}
	}

	private AnimFrame getFrame( int i ) {
		return ( AnimFrame )frames.get( i );
	}

	private class AnimFrame {

		GamePixmap image;
		long endTime;

		public AnimFrame( GamePixmap image, long endTime ) {
			this.image = image;
			this.endTime = endTime;
		}
	}
}