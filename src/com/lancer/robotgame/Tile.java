package com.lancer.robotgame;

import android.graphics.Rect;

import com.lancer.robotgame.framework.GamePixmap;

public class Tile {

	private int tileX, tileY, speedX;
	public int type;
	public GamePixmap tileImage;

	private Robot robot = InGameScreen.getRobot();
	private Background bg = InGameScreen.getBg1();

	private Rect r;

	public Tile( int x, int y, int typeInt ) {
		tileX = x * 40;
		tileY = y * 40;
//		tileX = x;
//		tileY = y;
		System.out.println("x is: " + tileX + "; y is: " + tileY);

		type = typeInt;

		r = new Rect();

		if( type == 5 ) {
			tileImage = GameAssets.tiledirt;
		}else if( type == 8 ) {
			tileImage = GameAssets.tilegrassTop;
		}else if( type == 4 ) {
			tileImage = GameAssets.tilegrassLeft;

		}else if( type == 6 ) {
			tileImage = GameAssets.tilegrassRight;

		}else if( type == 2 ) {
			tileImage = GameAssets.tilegrassBot;
		}else {
			type = 0;
		}

	}

	public void update() {
		speedX = bg.getSpeedX() * 5;
		tileX += speedX;
		r.set( tileX, tileY, tileX + 40, tileY + 40 );

		if( Rect.intersects( r, Robot.yellowRed ) && type != 0 ) {
			checkVerticalCollision( Robot.rect, Robot.rect2 );
			checkSideCollision( Robot.rect3, Robot.rect4, Robot.footleft,
					Robot.footright );
		}

	}

	public int getTileX() {
		return tileX;
	}

	public void setTileX( int tileX ) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY( int tileY ) {
		this.tileY = tileY;
	}

	public GamePixmap getTileImage() {
		return tileImage;
	}

	public void setTileImage( GamePixmap tileImage ) {
		this.tileImage = tileImage;
	}

	public void checkVerticalCollision( Rect rtop, Rect rbot ) {
//		if( Rect.intersects( rtop, r ) ) {
//
//		}
		if( Rect.intersects( rtop, r ) && type != 0 ) {
			robot.setSpeedY( -robot.getSpeedY() );
		}

		if( Rect.intersects( rbot, r ) && type == 8 ) {
			robot.setJumped( false );
			robot.setSpeedY( 0 );
			robot.setCenterY( tileY - 63 );
		}
	}

	public void checkSideCollision( Rect rleft, Rect rright, Rect leftfoot,
			Rect rightfoot ) {
		if( type != 5 && type != 2 && type != 0 ) {
			if( Rect.intersects( rleft, r ) ) {
				robot.setCenterX( tileX + 102 );

				robot.setSpeedX( 0 );

			}else if( Rect.intersects( leftfoot, r ) ) {

				robot.setCenterX( tileX + 85 );
				robot.setSpeedX( 0 );
			}

			if( Rect.intersects( rright, r ) ) {
				robot.setCenterX( tileX - 62 );

				robot.setSpeedX( 0 );
			}

			else if( Rect.intersects( rightfoot, r ) ) {
				robot.setCenterX( tileX - 45 );
				robot.setSpeedX( 0 );
			}
		}
	}

}