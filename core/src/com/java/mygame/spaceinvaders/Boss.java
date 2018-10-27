package com.java.mygame.spaceinvaders;

public class Boss extends Anime { // leader enemy
	public Boss(int x, int y) {
		super(x, y);
		img = SpaceInvaders.picFile.getAtlas("pack").findRegion("boss");

		width = img.getRegionWidth();
		height = img.getRegionHeight();
	}
}
