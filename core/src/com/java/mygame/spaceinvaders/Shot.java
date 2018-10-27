package com.java.mygame.spaceinvaders;

public class Shot extends Anime {//bullet player
	private int speed = 5;// speed shot

	public Shot() {}// constructor no argument

	// constructor
	public Shot(int x, int y) {
		super(x , y);
		setX(x + 7);
		setY(y + 20);

		img = SpaceInvaders.picFile.getAtlas("pack").findRegion("shot");
		width = img.getRegionWidth();
		height = img.getRegionHeight();
	}

	// update and check shot
	public boolean update() {
		y += speed;
		if (y > SpaceInvaders.HEIGHT)
			return true;
		return false;
	}
}
