package com.java.mygame.spaceinvaders;

public class Bomb extends Anime {// Bullet alien
	private int speed = 1;// speed bomb

	// constructor
	public Bomb(int x, int y) {
		super(x, y);
		img = SpaceInvaders.picFile.getAtlas("pack").findRegion("bomb");

		width = img.getRegionWidth();
		height = img.getRegionHeight();
	}

	// update action y down
	public boolean update() {
		y -= speed;
		if (y < 15) // GROUND
			return true;
		return false;
	}
}
