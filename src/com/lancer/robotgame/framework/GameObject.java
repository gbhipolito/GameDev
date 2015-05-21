package com.lancer.robotgame.framework;

import com.lancer.robotgame.framework.math.Rectangle;
import com.lancer.robotgame.framework.math.Vector2;

public class GameObject {
	public final Vector2 position;
	public final Rectangle bounds;
	
	/**
	 * position is always at center while bounds is at lower left
	 * @param x - center x
	 * @param y - center y
	 */
	public GameObject(float x, float y, float width, float height ) {
		this.position = new Vector2(x,y);
		this.bounds = new Rectangle(x - width/2, y - height/2, width, height);
	}
	
} // GameObject