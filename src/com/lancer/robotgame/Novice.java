package com.lancer.robotgame;

import com.lancer.robotgame.framework.DynamicGameObject;

public class Novice extends DynamicGameObject {
	public float walkingTime = 0;

	public Novice(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.position.set((float) Math.random() * AnimationScreen.WORLD_WIDTH, (float) Math.random() * AnimationScreen.WORLD_HEIGHT);
		this.velocity.set(Math.random() > 0.5f ? -0.5f : 0.5f, 0);
		this.walkingTime = (float) Math.random() * 10;
	}

	public void update(float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		if (position.x < 0)
			position.x = AnimationScreen.WORLD_WIDTH;
		if (position.x > AnimationScreen.WORLD_WIDTH)
			position.x = 0;
		walkingTime += deltaTime;
	}
} // Novice